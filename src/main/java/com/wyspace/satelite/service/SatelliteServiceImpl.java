package com.wyspace.satelite.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wyspace.satelite.exception.BandwithLimitExceededException;
import com.wyspace.satelite.exception.GenaralException;
import com.wyspace.satelite.model.PassSum;
import com.wyspace.satelite.model.Schedule;

@Service
public class SatelliteServiceImpl implements ISatelliteService{
	
	public static final String PASSES_CALC       = "the 30 minute period where the total downlink will be at its maximum is at ";
	public static final String LIMIT_EXCEEDED    = "The Bandwith limit was exceeded.";
	public static final String GENERAL_EXCEPTION = "The Application found an unexpectred behavior.";

	@Override
	public String calculatePass(MultipartFile file, Integer bandwidth) {
		String message = "";
		try {
			File                scheduleFile  = new File(file.getOriginalFilename());
			FileReader          headPath      = new FileReader(scheduleFile);
			BufferedReader      headFile      = new BufferedReader(headPath);
            Schedule            schedule      = new Schedule();
            ArrayList<Schedule> scheduleArray = new ArrayList<Schedule>();
            ArrayList<PassSum>  scheduleAux   = new ArrayList<PassSum>();
            
			readFile(headFile, schedule, scheduleArray);
			
			for (Schedule obj : scheduleArray) {
				calculatePasses(scheduleAux, obj);
			}

			PassSum sum = new PassSum();
			for (PassSum passSum : scheduleAux) { 
				Integer count = 0;
				if(passSum.getCount() > count) {
					count = passSum.getCount();
					sum   = passSum;
				}
			}
			
			if(bandwidth < sum.getBandwithSum()) {
				throw new BandwithLimitExceededException(LIMIT_EXCEEDED);
			}else {
				message = PASSES_CALC+sum.getPassTime();
			}
		} catch (Exception e) {
			throw new GenaralException(GENERAL_EXCEPTION);
		}
		return message;
	}

	/**
	 * this method is responsible for calculate the passes based on the file from the stations
	 * @param scheduleAux
	 * @param obj
	 */
	private void calculatePasses(ArrayList<PassSum> scheduleAux, Schedule obj) {
		Date    hourAux = obj.getIni();
		PassSum passSum = new PassSum();
		while(hourAux.before(obj.getFin())) {
			if(scheduleAux.isEmpty()) {
				passSum.setBandwithSum(obj.getBandwith());
				passSum.setCount(1);
				passSum.setPassTime(hourAux);
				hourAux = obj.getIni();
				scheduleAux.add(passSum);
				passSum = new PassSum();
			}else {
				Integer size = scheduleAux.size();
				Boolean isEqueal = false;
				for (int i = 0; i < size; i++) {
		            if(scheduleAux.get(i).getPassTime().equals(hourAux)) {
		            	isEqueal = true;
		            	passSum.setCount(scheduleAux.get(i).getCount()+1);
		            	passSum.setBandwithSum(scheduleAux.get(i).getBandwithSum()+obj.getBandwith());
		            	passSum.setPassTime(hourAux);
			    		scheduleAux.add(passSum);
			    		passSum = new PassSum();
		    		}
				}
				if(!isEqueal){
					passSum.setBandwithSum(obj.getBandwith());
		    		passSum.setCount(1);
		    		passSum.setPassTime(hourAux);
		    		scheduleAux.add(passSum);
		    		passSum = new PassSum();
				}
			}
			hourAux = increaseMinutes(hourAux);
		}
	}

	/**
	 * This method is responsible for read the file from the stations
	 * @param headFile
	 * @param schedule
	 * @param scheduleArray
	 * @throws IOException
	 * @throws ParseException
	 */
	private void readFile(BufferedReader headFile, Schedule schedule, ArrayList<Schedule> scheduleArray)throws IOException, ParseException {
		while(headFile.ready()) {
			String text         = headFile.readLine();
		    String textSplit [] = text.split(",");
		    
		    schedule.setName(textSplit[0]);
		    schedule.setBandwith(Integer.parseInt(textSplit[1]));
		    schedule.setIni(convertStrToDate(textSplit[2]));
		    schedule.setFin(convertStrToDate(textSplit[3]));
		    
		    scheduleArray.add(schedule);
		    schedule = new Schedule();
		}
		headFile.close();
	}
	
	private Date increaseMinutes(Date ini) {
	    Calendar formatedHour = Calendar.getInstance();
	    formatedHour.setTime(ini);
	    formatedHour.add(Calendar.MINUTE, 30);
	    return formatedHour.getTime();
	}

	public Date convertStrToDate(String date) throws ParseException {
		DateFormat dateFormat   = new SimpleDateFormat("HH:mm");
	    Date       formatedHour = dateFormat.parse(date);
	    return formatedHour;
	}

}

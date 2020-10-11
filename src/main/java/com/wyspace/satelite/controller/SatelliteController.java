package com.wyspace.satelite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wyspace.satelite.service.ISatelliteService;

@RestController
public class SatelliteController {
	
	@Autowired
	public ISatelliteService satelliteService;
	
	@RequestMapping(value = "/pass/{bandwidth}", method = RequestMethod.POST)
	public String calculatePass(@RequestParam("file") MultipartFile file, @PathVariable Integer bandwidth){
		return satelliteService.calculatePass(file, bandwidth);
	}

}

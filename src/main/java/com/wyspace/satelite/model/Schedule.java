package com.wyspace.satelite.model;

import java.util.Date;

public class Schedule {
	
	private String  name;
	private Integer bandwith;
	private Date    ini;
	private Date    fin;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getBandwith() {
		return bandwith;
	}
	public void setBandwith(Integer bandwith) {
		this.bandwith = bandwith;
	}
	public Date getIni() {
		return ini;
	}
	public void setIni(Date ini) {
		this.ini = ini;
	}
	public Date getFin() {
		return fin;
	}
	public void setFin(Date fin) {
		this.fin = fin;
	}
}

package com.wyspace.satelite.model;

import java.util.Date;

public class PassSum {
	
	private Integer bandwithSum;
	private Date    passTime;
	private Integer count;
	
	public Integer getBandwithSum() {
		return bandwithSum;
	}
	public void setBandwithSum(Integer bandwithSum) {
		this.bandwithSum = bandwithSum;
	}
	public Date getPassTime() {
		return passTime;
	}
	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}

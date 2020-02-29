package com.datastore.main;

import java.util.Date;
import java.util.List;

public class DataModels {

	
	String key;
	String data;
	long timeLimit;
	Date date;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public DataModels(String key, String data, int i) {
		this.key = key;
		this.data = data;
		this.timeLimit = i;
		
	}
	public DataModels() {
		// TODO Auto-generated constructor stub
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public long getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
}

package com.co.johan.server.java.rest;

import java.util.Date;

public class EventDataType {
	
	int IdEvent;
	String IdUnit;
	String IP;
	String Lat;
	String Lon;
	int Speed;
	Date  dateTime;
	String Event;
	
	public int getSpeed() {
		return Speed;
	}
	public void setSpeed(int speed) {
		Speed = speed;
	}
	public int getIdEvent() {
		return IdEvent;
	}
	public void setIdEvent(int idEvent) {
		IdEvent = idEvent;
	}
	public String getIdUnit() {
		return IdUnit;
	}
	public void setIdUnit(String idUnit) {
		IdUnit = idUnit;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getLat() {
		return Lat;
	}
	public void setLat(String lat) {
		Lat = lat;
	}
	public String getLon() {
		return Lon;
	}
	public void setLon(String lon) {
		Lon = lon;
	}
	public Date  getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date  dateTime) {
		this.dateTime = dateTime;
	}
	public String getEvent() {
		return Event;
	}
	public void setEvent(String event) {
		Event = event;
	}

}

package com.huertix.turistguide;

import java.util.ArrayList;

public class Place {
	
	private int id;
	private String country;
	private String city;
	private ArrayList<InterestedPoint> ipList;
	
	public Place (){
		
	
	}
	
	public Place(int id, String country, String city){
		this.id = id;
		this.country = country;
		this.city = city;
		
		ipList = new ArrayList<InterestedPoint>();
	}
	

	public void addIp(String name, String lat, String lng){
		InterestedPoint ip = new InterestedPoint(name,lat,lng);
		ip.setCountry(country);
		ip.setCity(city);		
		ipList.add(ip);
	}
	
	public ArrayList<InterestedPoint> getIp(){
		return ipList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	

}

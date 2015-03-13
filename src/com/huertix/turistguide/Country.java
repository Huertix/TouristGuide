package com.huertix.turistguide;

import java.util.ArrayList;
import java.util.List;

class Country{
	private String country;
	private List<String> cities;

	
	public Country(){
		cities = new ArrayList<String>();
		
	}
	
	public void setName(String c){
		country = c;
	}
	
	public String getName(){
		return country;
	}
	
	public void addCity(String t){
		cities.add(t);
	}
	
	public List<String> getCities(){
		return cities;
	}
	

}

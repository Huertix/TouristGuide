package com.huertix.turistguide;

import java.util.ArrayList;
import java.util.List;

class Country{
	public String country;
	public List<String> cities;

	
	public Country(){
		cities = new ArrayList<String>();
		
	}
	
	public void setName(String c){
		country = c;
	}
	
	public void addCity(String t){
		cities.add(t);
	}
	

}

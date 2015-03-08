package com.huertix.turistguide;


public class InterestedPoint {
	private int id;
	private String country;
	private String city;
	private String name;
	private String latitude;
	private String longitude;
	private String wikiUrl;
	private String srcPicture;
	
	
	public InterestedPoint(){
		
	}
	
	public InterestedPoint(String name, String lat, String lng){
		this.name = name;
		latitude = lat;
		longitude = lng;
	}
	
	
	public void setId(int i){
		this.id=i;
	}
	
	public int getId(){
		return id;
	}
	
	
	public void setCountry(String country){
		this.country = country;
	}
	
	public String getCountry(){
		return country;
	}
	
	public void setCity(String city){
		this.city = city;
	}
	
	public String getCity(){
		return city;
	}
	
	
	public void setSrcPicture(String src){
		srcPicture = src;
	}
	
	public String getSrcPicture(){
		return srcPicture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getWikiUrl() {
		return wikiUrl;
	}

	public void setWikiUrl(String wikiUrl) {
		this.wikiUrl = wikiUrl;
	}
	
	

}

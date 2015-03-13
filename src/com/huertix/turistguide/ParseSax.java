package com.huertix.turistguide;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class ParseSax extends DefaultHandler{
	
	private String tempVal;
	private Country tempCountry;
	private List<Country> myCountries;
	private int level;
	
	
	public ParseSax(InputStream input){
		super();
		
		myCountries = new ArrayList<Country>();
		level = 0;
		
		parseDocument(input);
		
		//printData();
		
		
		
	}
	
	
	
	private void parseDocument(InputStream input) {

		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();

			//parse the file and also register this class for call backs
			sp.parse(input,this);

		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}
	
	//Event Handlers
		public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
			//reset
			//System.out.println("START: "+level+" Name: "+qName);
			
			tempVal = "";
			if(qName.equalsIgnoreCase("SimpleGeoName")) {
				
				if(level==2){
				//create a new instance of employee
				//System.out.println("New Country");
				tempCountry = new Country();				
				
				//tempCountry.setType(attributes.getValue("type"));
				}
				level++;
			}
		}
		
		
		public void characters(char[] ch, int start, int length) throws SAXException {
			tempVal = new String(ch,start,length);
		}
		
		
		public void endElement(String uri, String localName,
				String qName) throws SAXException {

				//System.out.println("END: "+level+" Name: "+qName+"-->"+tempVal);
				if(qName.equalsIgnoreCase("SimpleGeoName")) {
					
					if(level==3){
						//add it to the list
						myCountries.add(tempCountry);
						//System.out.println("Country Added");
					}
					level--;
						
					

				}else if (qName.equalsIgnoreCase("Name")) {
					
					if(level==3){
						tempCountry.setName(tempVal);
						//System.out.println("SetName: "+tempVal);
					}
					else if(level==4)
						tempCountry.addCity(tempVal);
				}

		}
		
		
		public List<Country> returnList(){

			return myCountries;

			/*System.out.println("No of Employees '" + myCountries.size() + "'.");

			Iterator it = myCountries.iterator();
			while(it.hasNext()) {
				Country e = (Country)it.next();
				System.out.println(e.country+","+e.cities.size());
				Iterator t = e.cities.iterator();
				while(t.hasNext()){
					String s = (String) t.next();
					System.out.println("\t"+s);
					
				}
			}*/
		}
		
		public void cleanList(){
			myCountries.clear();
		}
		
		
		
		
		
		
		
	

}

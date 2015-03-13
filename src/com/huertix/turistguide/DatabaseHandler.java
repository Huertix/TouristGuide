package com.huertix.turistguide;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHandler extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "turistguide";
	private static final String TABLE = "places";
	private static final String CITY_TABLE = "cities";
	
	// Table Columns names
	private static final String KEY_ID = "id";
    private static final String KEY_COUNTRY = "country";
    private static final String KEY_CITY = "city";
    private static final String KEY_IP = "Interested_point";
    private static final String KEY_LAT = "latitude";
    private static final String KEY_LNG = "longitude";
    private static final String KEY_WIKI = "url_wiki";
    private static final String KEY_PIC = "src_pic";
    
    
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        
    	/**
    	 * Creamos la tabla de  lista de paises y ciudades
    	 */
    	String CREATE_CITY_TABLE = "CREATE TABLE " + CITY_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_COUNTRY + " TEXT,"
                +KEY_CITY+")";
        db.execSQL(CREATE_CITY_TABLE);
        
        /**
         * Creamos la tabla de Datos de los puntos de interes
         */
        String CREATE_PLACES_TABLE = "CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_COUNTRY + " TEXT,"
                +KEY_CITY+" TEXT,"+ KEY_IP + " TEXT," +KEY_LAT+" TEXT,"+KEY_LNG
                +" TEXT,"+KEY_WIKI+" TEXT,"+KEY_PIC+" TEXT"+")";
        db.execSQL(CREATE_PLACES_TABLE);
    }
    
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
 
        // Create tables again
        onCreate(db);
    }
    
    // Adding new row
    public void addRow(InterestedPoint ip) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_COUNTRY, ip.getCountry());
    	values.put(KEY_CITY, ip.getCity());
		values.put(KEY_IP, ip.getName());
    	values.put(KEY_LAT, ip.getLatitude());
    	values.put(KEY_LNG, ip.getLongitude());
    	values.put(KEY_WIKI,ip.getWikiUrl());
    	values.put(KEY_PIC, ip.getSrcPicture());

    try{	// Inserting Row
        db.insert(TABLE, null, values);
        
    }
    catch(SQLiteException e){
    	Log.v("tag","error sql"+e.getMessage());
    }
        db.close(); // Closing database connection 	
    }
    
    public void addCityToTable(String country, String city){
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_COUNTRY, country);
    	values.put(KEY_CITY, city);
    	try{	// Inserting Row
            db.insert(CITY_TABLE, null, values);
            
        }
        catch(SQLiteException e){
        	Log.v("tag","error sql"+e.getMessage());
        }
        
    	db.close(); // Closing database connection 	
        
    }
    
    
    public List<String> getCountries(){
    	List<String> countries = new ArrayList<String>();
    	
    	String selectQuery = "SELECT "+KEY_COUNTRY+"  FROM " + TABLE+" ORDER BY "+KEY_COUNTRY;
    	
    	SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {
            	countries.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        
        return countries;
    }
    
    public List<String> getCountriesFromList(){
    	List<String> countries = new ArrayList<String>();
    	
    	String selectQuery = "SELECT "+KEY_COUNTRY+"  FROM " + CITY_TABLE+" ORDER BY "+KEY_COUNTRY;
    	
    	SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {
            	countries.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        
        return countries;
    }
    
    
    public List<String> getCities(String country){
    	List<String> cities = new ArrayList<String>();
    	
    	Log.v("tag","Country: "+country);
    	
    	String selectQuery = "SELECT "+KEY_CITY+"  FROM " + TABLE+" WHERE "+KEY_COUNTRY+"='"+country+"' ORDER BY "+KEY_CITY;
    	
    	SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {
            	cities.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        
        return cities;
    }
    
    
    public List<String> getCitiesFromList(String country){
    	List<String> cities = new ArrayList<String>();
    	
    	Log.v("tag","Country: "+country);
    	
    	String selectQuery = "SELECT "+KEY_CITY+"  FROM " + CITY_TABLE+" WHERE "+KEY_COUNTRY+"='"+country+"' ORDER BY "+KEY_CITY;
    	
    	SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {
            	cities.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        
        return cities;
    }
    
    
    public List<InterestedPoint> getIp(String city){
    	List<InterestedPoint> ips = new ArrayList<InterestedPoint>();
    	
    	String selectQuery = "SELECT * FROM " + TABLE+" WHERE "+KEY_CITY+"='"+city+"' ORDER BY "+KEY_IP;
    	
    	SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {         	
            	InterestedPoint ip = new InterestedPoint();
            	ip.setId(Integer.parseInt(cursor.getString(0)));          	
            	ip.setCountry(cursor.getString(1));          	
            	ip.setCity(cursor.getString(2));         	
            	ip.setName(cursor.getString(3));      	
            	ip.setLatitude(cursor.getString(4));
            	ip.setLongitude(cursor.getString(5));
            	ip.setWikiUrl(cursor.getString(6));
            	ip.setSrcPicture(cursor.getString(7));         	
            	ips.add(ip);         	
            }while(cursor.moveToNext());
        }     
        return ips;
    }
    
    public int getIpsCount(){
    	String countQuery = "SELECT  * FROM " + TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }
    
    
    public int updateIp(InterestedPoint ip){
    	SQLiteDatabase db = this.getWritableDatabase();
   	 
        ContentValues values = new ContentValues();
        values.put(KEY_COUNTRY, ip.getCountry());
    	values.put(KEY_CITY, ip.getCity());
		values.put(KEY_IP, ip.getName());
    	values.put(KEY_LAT, ip.getLatitude());
    	values.put(KEY_LNG, ip.getLongitude());
    	values.put(KEY_WIKI,ip.getWikiUrl());
    	values.put(KEY_PIC, ip.getSrcPicture());
     
        // updating row
        return db.update(TABLE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(ip.getId()) });   	
    }
 
}

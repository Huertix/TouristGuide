package com.huertix.turistguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class AddPlace extends Activity {
	
	private DatabaseHandler data_handler; 
	private Button button_save;
	private Button button_getCoord;
	private Button button_take_pic;
	private Button button_map;
	private EditText country;
	private EditText city;
	private EditText ip;
	private EditText lat;
	private EditText lng;
	private EditText url;
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addplace);
		
		country = (EditText) findViewById(R.id.addCountry);
		city = (EditText) findViewById(R.id.addCity);
		ip = (EditText) findViewById(R.id.addip);
		lat = (EditText) findViewById(R.id.addlatitude);
		lng = (EditText) findViewById(R.id.addlongitude);
		url = (EditText) findViewById(R.id.add_url_wiki);
		
		data_handler = new DatabaseHandler(this);
				
		addListenerOnButton();
	}
	
	
    public void addListenerOnButton() {
	 
	        //Select a specific button to bundle it with the action you want
    	button_save = (Button) findViewById(R.id.add_done);	 
    	button_save.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View view) {
	            	
	            	InterestedPoint item = new InterestedPoint();
	            	item.setCountry(country.getText().toString());
	            	item.setCity(city.getText().toString());
	            	item.setName(ip.getText().toString());
	            	item.setLatitude(lat.getText().toString());
	            	item.setLongitude(lng.getText().toString());
	            	item.setWikiUrl(url.getText().toString());
	            	
	            	data_handler.addRow(item);
	            	finish();
	            }
	 
	        });
    	
    	button_take_pic = (Button) findViewById(R.id.add_src_pic);	 
    	button_take_pic.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View view) {
	 
	              //Intent openBrowser =  new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.javacodegeeks.com"));
	              //startActivity(openBrowser);
	            }
	 
	        });
    	
    	button_map = (Button) findViewById(R.id.map_btn);	 
    	button_map.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View view) {
	 
	            	Toast.makeText(getApplicationContext(),"MAP", 
	                        Toast.LENGTH_LONG).show();
	            	
	            	
	            	Intent i_map = new Intent(AddPlace.this, Map.class);
	            	startActivity(i_map);
	            	
	            	
	            	
	            }
	 
	        });
	 
	    }
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_place, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

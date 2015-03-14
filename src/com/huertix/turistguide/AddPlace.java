package com.huertix.turistguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;


public class AddPlace extends Activity {
	
	private static final int CODE_ACTIVITY_MAP = 1;	
	private static final int REQUEST_CODE = 10;
	private DatabaseHandler data_handler; 
	private Button button_save;
	private Button button_getCoord;
	private Button button_take_pic;
	private Button button_map;
	private TextView country;
	private TextView city;
	private EditText ip;
	private TextView lat;
	private TextView lng;
	private EditText url;
	private String country_string;
	private String city_string;
	
	
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addplace);
		
		country = (TextView) findViewById(R.id.addCountry);
		city = (TextView) findViewById(R.id.addCity);
		ip = (EditText) findViewById(R.id.addip);
		lat = (TextView) findViewById(R.id.addlatitude);
		lng = (TextView) findViewById(R.id.addlongitude);
		url = (EditText) findViewById(R.id.add_url_wiki);
		
		Bundle extras = getIntent().getExtras();
		country_string = extras.getString("country");
		city_string = extras.getString("city");
			
		country.setText(country_string);
		city.setText(city_string);
		
		
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
	            	startActivityForResult(i_map, CODE_ACTIVITY_MAP );
	            	
	            	
	            	
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
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(),"1", 
                Toast.LENGTH_LONG).show();
		
		
		if ((requestCode == CODE_ACTIVITY_MAP )){
			if(resultCode == Activity.RESULT_OK){
			
				Toast.makeText(getApplicationContext(),"2", 
	                    Toast.LENGTH_LONG).show();
		    	
		        lat.setText(data.getStringExtra("lat").substring(0, 9));
		        lng.setText(data.getStringExtra("lng").substring(0, 9));	        
			}
	    }
	} 
}

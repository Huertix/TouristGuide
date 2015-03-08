package com.huertix.turistguide;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Country extends Activity {
	
	private DatabaseHandler data_handler;
	private ProgressDialog m_ProgressDialog ;
	private Adapter m_adapter;
	private Runnable viewCities;
	private ArrayList<String> cities;
	private String country_string;
	private ListView myListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.country);
		
		
		
		myListView = (ListView) findViewById(R.id.list_cities);
		
		cities = new ArrayList<String>();
		
		

		TextView country = (TextView) findViewById(R.id.country_activity_country);
		
		Bundle extras = getIntent().getExtras();
		country_string = extras.getString("country");
		
		country.setText(country_string);
		
		data_handler = new DatabaseHandler(this);
		m_adapter = new Adapter(this, R.layout.row_c, cities);
		myListView.setAdapter(this.m_adapter);
		
		Toast.makeText(getApplicationContext(),country_string, 
                Toast.LENGTH_LONG).show();
		
		
		
		myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> parent, final View view,int position, long id) {
	        	final String item = (String) parent.getItemAtPosition(position);
	        	Bundle extras = new Bundle();
	        	extras.putString("city",item);
        	
	        	Intent intent = new Intent(Country.this, City.class);
	        	intent.putExtras(extras);
	        	startActivity(intent);
	        }

	      });
		
		
		
		viewCities = new Runnable(){
            @Override
            public void run() {
            	         	
            	runOnUiThread(returnRes);
                
            }
        };
		Thread thread =  new Thread(null, viewCities, "MagentoBackground");
        thread.start();
        m_ProgressDialog = ProgressDialog.show(this,    
             "Please wait...", "Retrieving data ...", true);
		
	}
	
	private Runnable returnRes = new Runnable() {

        @Override
        public void run() {
        	
        	List<String> lsFromDB = data_handler.getCities(country_string);
        	
        	cities.clear();
        	
        	for(int i=0;i<lsFromDB.size();i++){
        		if(!cities.contains(lsFromDB.get(i)))
        			cities.add(lsFromDB.get(i));
        		Log.v("tag","City: "+lsFromDB.get(i));
        	}
        	
            m_ProgressDialog.dismiss();
            m_adapter.notifyDataSetChanged();
        }
    };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.country, menu);
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
	
	
    private class Adapter extends ArrayAdapter<String> {
    	


        private ArrayList<String> items;

        public Adapter(Context context, int textViewResourceId, ArrayList<String> items) {
                super(context, textViewResourceId, items);
                this.items = items;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.row_c, null);
                }
                String city = items.get(position);
                if (city != null){
                	TextView row_textview = (TextView) v.findViewById(R.id.rowTextView);
                	if(row_textview !=null)
                		row_textview.setText(city);
                }
                return v;
        }
		
    }
	
	
}

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

public class CityActivity extends Activity {
	
	private DatabaseHandler data_handler;
	private ProgressDialog m_ProgressDialog ;
	private Adapter m_adapter;
	private Runnable viewIps;
	private ArrayList<InterestedPoint> ips;
	private String country_string;
	private String city_string;
	private ListView myListView;
	private final int ID_MENU = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city);
		
		
		myListView = (ListView) findViewById(R.id.list_ips);
		
		ips = new ArrayList<InterestedPoint>();
		
		Bundle extras = getIntent().getExtras();

		TextView country = (TextView) findViewById(R.id.country_activity_city);
		TextView city = (TextView) findViewById(R.id.city_activity_city);
		
		country_string = extras.getString("country");
		city_string = extras.getString("city");
	
		country.setText(country_string);
		city.setText(city_string);
	
		data_handler = new DatabaseHandler(this);
		m_adapter = new Adapter(this, R.layout.row_c, ips);
		myListView.setAdapter(this.m_adapter);
		
		myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> parent, final View view,int position, long id) {
	        	final InterestedPoint ip = (InterestedPoint) parent.getItemAtPosition(position);

	        	Bundle extras = new Bundle();
	        	extras.putInt("id",ip.getId());
	        	extras.putString("name", ip.getName());
	        	extras.putString("lat", ip.getLatitude());
	        	extras.putString("lng", ip.getLongitude());
	        	extras.putString("url", ip.getWikiUrl());
	        	extras.putString("img",ip.getSrcPicture());
        	
	        	Intent intent = new Intent(CityActivity.this, Ip.class);
	        	intent.putExtras(extras);
	        	startActivity(intent);
	        }

	      });
		
		Toast.makeText(getApplicationContext(),city_string, 
                Toast.LENGTH_LONG).show();
		
		
		viewIps = new Runnable(){
            @Override
            public void run() {
            	         	
            	runOnUiThread(returnRes);
                
            }
        };
		Thread thread =  new Thread(null, viewIps, "MagentoBackground");
        thread.start();
        m_ProgressDialog = ProgressDialog.show(this,    
             "Please wait...", "Retrieving data ...", true);
		
	}
	
	private Runnable returnRes = new Runnable() {

        @Override
        public void run() {
        	
        	List<InterestedPoint> lsFromDB = data_handler.getIp(city_string);
        	
        	ips.clear();
        	
        	for(int i=0;i<lsFromDB.size();i++){
        		ips.add(lsFromDB.get(i));
        		Log.v("runnable","City: "+lsFromDB.get(i).getName());
        	}
        	
            m_ProgressDialog.dismiss();
            m_adapter.notifyDataSetChanged();
        }
    };
    
	@Override
	protected void onResume(){
		super.onResume();	
		runOnUiThread(returnRes);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	
		MenuItem itemAdd = menu.add(Menu.NONE,ID_MENU,Menu.NONE,R.string.addplace);
		
		menu.add(Menu.NONE,ID_MENU+1,Menu.NONE,R.string.exit);
		
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	//check selected menu item
    	if(item.getItemId() == ID_MENU)
    	{
    		
    		Bundle extras = new Bundle();
        	extras.putString("country",country_string);
        	extras.putString("city",city_string);
    		
    		Intent intent = new Intent(CityActivity.this, AddPlace.class);
    		intent.putExtras(extras);
    		startActivity(intent);
		
    		return true;
    	}
    	else if(item.getItemId() == ID_MENU+1){
    		//close the Activity
    		this.finish();
    		return true;
    	}
 	
    	return false;
    }
	
	
    private class Adapter extends ArrayAdapter<InterestedPoint> {
    	


        private ArrayList<InterestedPoint> items;

        public Adapter(Context context, int textViewResourceId, ArrayList<InterestedPoint> items) {
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
                InterestedPoint ip = items.get(position);
                if (ip != null){
                	TextView row_textview = (TextView) v.findViewById(R.id.rowTextView);
              	
                	if(row_textview !=null)
                		row_textview.setText(ip.getName());
                }
                return v;
        }
		
    }
	
	
}

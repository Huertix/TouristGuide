package com.huertix.turistguide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteException;

public class Main extends Activity  {
	
	private DatabaseHandler data_handler; 
	private ProgressDialog m_ProgressDialog ;
	private Adapter m_adapter;
	private ListView myListView;
	private Runnable viewCountries;
	private ArrayList<String> countries;
	private final int ID_MENU_EXIT = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		myListView = (ListView) findViewById(R.id.list_countries);
		
		// String[] planets = new String[] { "Mercury", "Venus", "Earth", "Mars",  
        //        "Jupiter", "Saturn", "Uranus", "Neptune"}; 
		
		
		countries = new ArrayList<String>();
		
		//countries.addAll(Arrays.asList(planets));
		
		//listAdapter = new ArrayAdapter<String>(this,R.layout.row_c,countries);
		
		//myListView.setAdapter(listAdapter);
		
		data_handler = new DatabaseHandler(this);
		m_adapter = new Adapter(this, R.layout.row_c, countries);
		myListView.setAdapter(this.m_adapter);
		
		
		
		myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> parent, final View view,int position, long id) {
	        	final String item = (String) parent.getItemAtPosition(position);
	        	Bundle extras = new Bundle();
	        	extras.putString("country",item);
        	
	        	Intent intent = new Intent(Main.this, Country.class);
	        	intent.putExtras(extras);
	        	
	        	
	        	
	        	
	    		startActivity(intent);
	        }
	          
	        
	          
	          
	          
	          /*view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
	                @Override
	                public void run() {
	                  countries.remove(item);
	                  m_adapter.notifyDataSetChanged();
	                  view.setAlpha(1);
	                }
				});
	        }*/

	      });
		

		viewCountries = new Runnable(){
            @Override
            public void run() {
            	         	
            	runOnUiThread(returnRes);
                
            }
        };
		Thread thread =  new Thread(null, viewCountries, "MagentoBackground");
        thread.start();
        m_ProgressDialog = ProgressDialog.show(Main.this,    
             "Please wait...", "Retrieving data ...", true);

	}
	
	
	
	
	
	
	
	
	@Override
	protected void onResume(){
		super.onResume();
		
		Toast.makeText(getApplicationContext(),"Vuelve", 
                Toast.LENGTH_LONG).show();
		
		runOnUiThread(returnRes);
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	
		MenuItem itemAdd = menu.add(Menu.NONE,ID_MENU_EXIT,Menu.NONE,R.string.addplace);
		itemAdd.setShortcut('1', 'a');
		
		menu.add(Menu.NONE,ID_MENU_EXIT+1,Menu.NONE,R.string.exit);
		
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	//check selected menu item
    	if(item.getItemId() == ID_MENU_EXIT)
    	{
    		Intent intent = new Intent(Main.this, AddPlace.class);
    		startActivity(intent);
		
    		return true;
    	}
    	else if(item.getItemId() == ID_MENU_EXIT+1){
    		//close the Activity
    		this.finish();
    		return true;
    	}
    	
    	
    	return false;
    }
	
	
	
	
	private Runnable returnRes = new Runnable() {

        @Override
        public void run() {
        	
        	List<String> lsFromDB = data_handler.getCountries();
        	
        	countries.clear();
        	
        	for(int i=0;i<lsFromDB.size();i++){
        		if(!countries.contains(lsFromDB.get(i)))
        			countries.add(lsFromDB.get(i));
        	}

            
            m_ProgressDialog.dismiss();
            m_adapter.notifyDataSetChanged();
        }
    };

	
	
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
                String country = items.get(position);
                if (country != null){
                	TextView row_textview = (TextView) v.findViewById(R.id.rowTextView);
                	if(row_textview !=null)
                		row_textview.setText(country);
                }
                return v;
        }
		
    }












	
}

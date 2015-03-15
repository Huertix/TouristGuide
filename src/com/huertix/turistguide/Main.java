package com.huertix.turistguide;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.AsyncTask;
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
	
	private final static int ID_MENU = 1;
	private final static int TOTAL_CITIES=4615;
	private DatabaseHandler data_handler; 
	private ProgressDialog pDialog;
	private Adapter m_adapter;
	private ListView myListView;
	private ArrayList<String> countries;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		myListView = (ListView) findViewById(R.id.list_countries);

		countries = new ArrayList<String>();
		
		data_handler = new DatabaseHandler(this);
		m_adapter = new Adapter(this, R.layout.row_c, countries);
		myListView.setAdapter(this.m_adapter);
		
		
		
		myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> parent, final View view,int position, long id) {
	        	final String item = (String) parent.getItemAtPosition(position);
	        	Bundle extras = new Bundle();
	        	extras.putString("country",item);
        	
	        	Intent intent = new Intent(Main.this, CountryActivity.class);
	        	intent.putExtras(extras);
	    		startActivity(intent);
	        }
	          
	      });
		

		loadCountries();

	}
	
	

	@Override
	protected void onResume(){
		super.onResume();	
		runOnUiThread(returnRes);
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
            m_adapter.notifyDataSetChanged();              
        }
    };
    
    
    private void loadCountries(){

    	boolean mboolean = false;

    	SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
    	mboolean = settings.getBoolean("FIRST_RUN", false);
    	if (!mboolean) {

    		// do the thing for the first time
    		new LoadCountriesTask().execute();	
    			
    		settings = getSharedPreferences("PREFS_NAME", 0);
	        SharedPreferences.Editor editor = settings.edit();
	        editor.putBoolean("FIRST_RUN", true);
	        editor.commit();    
	       // return true;
    	                    
    	} else {
    	 // other time your app loads
    		//return false;
    	}
    	
        	
    	
    }
    

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	
		MenuItem itemAdd = menu.add(Menu.NONE,ID_MENU,Menu.NONE,R.string.exit);		
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	//check selected menu item
    	if(item.getItemId() == ID_MENU){
    		//close the Activity
    		this.finish();
    		return true;
    	}
 	
    	return false;
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
                String country = items.get(position);
                if (country != null){
                	TextView row_textview = (TextView) v.findViewById(R.id.rowTextView);
                	if(row_textview !=null)
                		row_textview.setText(country);
                }
                return v;
        }
		
    }
    
    
    public class LoadCountriesTask extends AsyncTask<Void, Integer, Boolean> {
    	

    	@Override
    	protected Boolean doInBackground(Void... params) {

    		try {	
    			
    			synchronized(this){
    				
    				int k = 0;	
	    			AssetManager assetMan = getAssets();
	    			InputStream is = assetMan.open("countries.xml"); 
	    			
	    			ParseSax ps = new ParseSax(is);   
	    			
	    			List<Country> ls = ps.returnList();	
	
	    			Iterator<Country> i = ls.iterator();
	    			while(i.hasNext()){
	    				Country c = (Country) i.next();
	    				Iterator<String> j = c.getCities().iterator();
	    		
	    				while(j.hasNext()){
	    					String s = (String) j.next();
	    					data_handler.addCityToTable(c.getName(), s);
	    					publishProgress(k++);
	    				}
	    				
	    			}
	    			ps.cleanList();
	    			ls.clear();
    			}
  	
    		} catch (IOException e) {
    			e.printStackTrace();
    		} catch (Exception e){
    			e.printStackTrace();
    		}

			return true;	
							
    	}
    	
    	@Override
    	protected void onProgressUpdate(Integer... values) {
    		int progreso = values[0].intValue();
    		pDialog.setProgress(progreso);
    	}
    	
    	
    	@Override
    	protected void onPreExecute() {
    		
			//Create a new progress dialog
    		pDialog = new ProgressDialog(Main.this);
			//Set the progress dialog to display a horizontal progress bar 
    		pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			//Set the dialog title to 'Loading...'
    		pDialog.setTitle("Parsing Cities to Data Base...");
			//Set the dialog message to 'Loading application View, please wait...'
    		pDialog.setMessage("First Time Running.");
			//This dialog can't be canceled by pressing the back key
    		pDialog.setCancelable(false);
			//This dialog isn't indeterminate
    		pDialog.setIndeterminate(false);
			//The maximum number of items is 100
    		pDialog.setMax(TOTAL_CITIES);
			//Set the current progress to zero
    		pDialog.setProgress(0);
			//
			pDialog.setOnCancelListener(new OnCancelListener() {
    			@Override
    			public void onCancel(DialogInterface dialog) {
    				LoadCountriesTask.this.cancel(true);
    			}
    		});
			//pDialog the progress dialog
			pDialog.show();  		
    	}
    	
    	@Override
    	protected void onPostExecute(Boolean result) {
    		if(result){
    			pDialog.dismiss();
    			Toast.makeText(Main.this, "4615 Cities Loaded!", Toast.LENGTH_SHORT).show();
    			runOnUiThread(returnRes);
    		}
    	}
    	
    	@Override
    	protected void onCancelled() {
    		Toast.makeText(Main.this, "Tarea cancelada!",Toast.LENGTH_SHORT).show();
    	}
    	
    }	
}

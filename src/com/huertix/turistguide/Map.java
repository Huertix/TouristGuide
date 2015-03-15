package com.huertix.turistguide;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.maps.MapView;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class Map extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, GoogleMap.OnMarkerClickListener{
	

	private static final int MAP_ZOOM = 3;
	private Button ok_map_btn;
	private Button rm_map_btn;
	private Marker marker;
	private GoogleMap myMap;
	private boolean isMarked=false;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		final Intent intent = new Intent(Map.this,AddPlace.class);
		
		
	
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
		
		ok_map_btn = (Button) findViewById(R.id.ok_map_btn);	 
		ok_map_btn.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View view) {	            		                   	     
	            	
	            	
	            	if(isMarked){
	            		intent.putExtra("lat",""+marker.getPosition().latitude);
	            		intent.putExtra("lng",""+marker.getPosition().longitude);
	            		
	            	}
	            	else{
	            		intent.putExtra("lat",""+myMap.getMyLocation().getLatitude());
	            		intent.putExtra("lng",""+myMap.getMyLocation().getLongitude());
	            		
	            	}
	            	
	            	setResult(Activity.RESULT_OK,intent);
	            	finish();
  	
	            }
	 
	        });
		
		rm_map_btn = (Button) findViewById(R.id.rm_map_btn);	 
		rm_map_btn.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View view) {	            	
	            	myMap.clear();	
            		isMarked=false;
	            }
	 
	        });
		
		
		

	}


	@Override
	public void onMapReady(final GoogleMap map) {
		myMap = map;
		
		map.setMyLocationEnabled(true);

		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
	        buildAlertMessageNoGps();
	    }
			
		map.setOnMapClickListener(new OnMapClickListener() {

	        @Override
	        public void onMapClick(LatLng point) {
	            
	        	map.clear();
     	
	        	Log.d("Map","Map clicked");
	            Toast.makeText(getApplicationContext(),"Lat: "+point.latitude+"\nLng: "+point.longitude, 
	                    Toast.LENGTH_LONG).show();
	            marker = map.addMarker(new MarkerOptions()
	            		.position(new LatLng(point.latitude,point.longitude))
	            		.title("Marca")
	            		.snippet("Otra info"));
	            isMarked=true;
	            
	            
	            
	            rm_map_btn.setEnabled(true);
	            
	        }        
		});
		
		map.setOnMapLoadedCallback(this);

    }
	
	 private void buildAlertMessageNoGps() {
		    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
		           .setCancelable(false)
		           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		               public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
		                   startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		               }
		           })
		           .setNegativeButton("No", new DialogInterface.OnClickListener() {
		               public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
		                    dialog.cancel();
		               }
		           });
		    final AlertDialog alert = builder.create();
		    alert.show();
	 }
	 
	 
	    /*
	     * Returns the user's current location as a LatLng object.
	     * Returns null if location could not be found (such as in an AVD emulated virtual device).
	     */
	 private LatLng getMyLocation() {
		 // try to get location three ways: GPS, cell/wifi network, and 'passive' mode
	     LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	     Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	     if (loc == null) {
	            // fall back to network if GPS is not available
	            loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	     }
	     if (loc == null) {
	            // fall back to "passive" location if GPS and network are not available
	            loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
	     }
	
	     if (loc == null) {
	            return null;   // could not get user's location
	     } else {
	            double myLat = loc.getLatitude();
	            double myLng = loc.getLongitude();
	            return new LatLng(myLat, myLng);
	      }
	    }
	    
	 
	 
	    /*
	     * Called when user clicks on any of the city map markers.
	     * Adds a line from the user's location to that city.
	     */    
	    public boolean onMarkerClick(Marker marker) {
	        //if (myMap.isMyLocationEnabled()) {
	            LatLng markerLatLng = marker.getPosition();
	            LatLng currentLatLng = new LatLng(myMap.getMyLocation().getLatitude(),myMap.getMyLocation().getLongitude());
	            myMap.addPolyline(new PolylineOptions()
	                            .add(currentLatLng)
	                            .add(markerLatLng)
	            );
	            return true;
	        //} else {
	       //     return false;
	       // }
	    }


		@Override
		public void onMapLoaded() {
			LatLng  current = getMyLocation();		
			if(current!=null)
				myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, MAP_ZOOM));
	
		}
	 

}


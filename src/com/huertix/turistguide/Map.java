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
import android.widget.Toast;


public class Map extends FragmentActivity implements OnMapReadyCallback{
	
	private static final long MIN_TIME_BW_UPDATES = 100;
	private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
	private MapView mapview;
	private MapController map_controller;
	private GeoPoint geop;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);

	}


	@Override
	public void onMapReady(final GoogleMap map) {
		
		map.setMyLocationEnabled(true);
		
		LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
		
		if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
	        buildAlertMessageNoGps();
	    }
		
		if (locationManager != null) {
			Location myLocation =locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (myLocation != null) {
				LatLng myCoord = new LatLng(myLocation.getAltitude(), myLocation.getAltitude());
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(myCoord, 13));
				map.addMarker(new MarkerOptions().title("My Position").snippet("Yesssss").position(myCoord));
            }
        }
		
		
		map.setOnMapClickListener(new OnMapClickListener() {

	        @Override
	        public void onMapClick(LatLng point) {
	            
	        	map.clear();
	        	
	        	
	        	Log.d("Map","Map clicked");
	            Toast.makeText(getApplicationContext(),"Lat: "+point.latitude+"\nLng: "+point.longitude, 
	                    Toast.LENGTH_LONG).show();
	            Marker marker = map.addMarker(new MarkerOptions()
	            		.position(new LatLng(point.latitude,point.longitude))
	            		.title("Marca")
	            		.snippet("Otra info"));
	        }
		});
				
		//Location myLocation = map.getMyLocation();

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

	
	
	
	

}


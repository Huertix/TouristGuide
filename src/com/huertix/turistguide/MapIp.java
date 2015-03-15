package com.huertix.turistguide;

import java.util.Arrays;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.maps.android.SphericalUtil;



public class MapIp extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback{
	
	private static final int MAP_ZOOM = 14;
	private Marker marker;
	private String ipStr;
	private String latStr;
	private String lngStr;
	private GoogleMap myMap;
	private Polyline mPolyline;
	private LatLng  current;
	private double distance;
	private TextView distView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapip);
		
		distView = (TextView) findViewById(R.id.dist_mapip);
		
		Bundle extras = getIntent().getExtras();
		ipStr = extras.getString("ip");
		latStr = extras.getString("lat");
		lngStr = extras.getString("lng");
		

		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map_i, menu);
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
	public void onMapLoaded() {
		
		Marker markerA = marker;
		current = getMyLocation();
		
		if(current!=null){
			//LatLng currentPos = new LatLng(myMap.getMyLocation().getLatitude(),myMap.getMyLocation().getLongitude());
			distance = SphericalUtil.computeDistanceBetween(markerA.getPosition(), current);
			myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), MAP_ZOOM));
			
			Toast.makeText(getApplicationContext(),"Distance: "+formatNumber(distance), 
	                Toast.LENGTH_LONG).show();
			
			
			updatePolyline();
			
		}else{
			Toast.makeText(getApplicationContext(),"Location Fails ", 
	                Toast.LENGTH_LONG).show();
		}
		
		
	}
	
	private void updatePolyline() {
        mPolyline.setPoints(Arrays.asList(marker.getPosition(), current));
        marker.setSnippet(formatNumber(distance));
        marker.showInfoWindow();
        distView.setText(formatNumber(distance));
    }

	@Override
	public void onMapReady(final GoogleMap map) {

		myMap = map;
		map.setMyLocationEnabled(true);

		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
	        buildAlertMessageNoGps();
	    }
		
		marker = map.addMarker(new MarkerOptions()
		.position(new LatLng(Float.valueOf(latStr),Float.valueOf(lngStr)))
		.title(ipStr));
			
		marker.setDraggable(false);
		marker.showInfoWindow();
		mPolyline = myMap.addPolyline(new PolylineOptions().geodesic(true));
		
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
	 
	 
	 private String formatNumber(double distance) {
	        String unit = "m";
	        if (distance < 1) {
	            distance *= 1000;
	            unit = "mm";
	        } else if (distance > 1000) {
	            distance /= 1000;
	            unit = "km";
	        }

	        return String.format("%4.1f%s", distance, unit);
	    }



}

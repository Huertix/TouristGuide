package com.huertix.turistguide;

import java.io.File;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Ip extends Activity {
	
	private DatabaseHandler dbHandler;
	private String placeString;
	private ImageView imageTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ip);
		
		TextView nameTV = (TextView) findViewById(R.id.ip_act);
		imageTV = (ImageView) findViewById(R.id.ip_img);
		TextView latTV = (TextView) findViewById(R.id.lat_ip_act);
		TextView lngTV = (TextView) findViewById(R.id.lng_ip_act);
		TextView distTV = (TextView) findViewById(R.id.dist_ip_act);
		
		Bundle extras = getIntent().getExtras();
		placeString = extras.getString("name");
		nameTV.setText(placeString);
		
		latTV.setText(extras.getString("lat"));
		lngTV.setText(extras.getString("lng"));
	
	}
	
	
	private void loadImage(){
		File imgFile = new  File("/sdcard/TouristGuide/tmp/image/noimage.jpg");

		if(imgFile.exists()){

		    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());


		    imageTV.setImageBitmap(myBitmap);

		}
	}
	

	
}

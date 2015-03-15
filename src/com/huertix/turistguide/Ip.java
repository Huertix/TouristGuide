package com.huertix.turistguide;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Ip extends Activity {
	
	private DatabaseHandler dbHandler;
	private String placeString;
	private String lat;
	private String lng;
	private ImageView imageTV;
	private Button map_btn;
	private Button ar_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ip);
		
		TextView nameTV = (TextView) findViewById(R.id.ip_act);
		imageTV = (ImageView) findViewById(R.id.ip_img);
		TextView latTV = (TextView) findViewById(R.id.lat_ip_act);
		TextView lngTV = (TextView) findViewById(R.id.lng_ip_act);
		TextView distTV = (TextView) findViewById(R.id.dist_ip_act);
		map_btn = (Button) findViewById(R.id.map_ip_btn);
		ar_btn = (Button) findViewById(R.id.ar_ip_btn);

		
		Bundle extras = getIntent().getExtras();
		placeString = extras.getString("name");
		nameTV.setText(placeString);
		
		lat = extras.getString("lat");
		latTV.setText(lat);
		lng = extras.getString("lng");
		lngTV.setText(lng);
		
		loadImage(extras.getString("img"));
		
		map_btn.setOnClickListener(new OnClickListener() {
			 
            @Override
            public void onClick(View view) {

            	Bundle extras = new Bundle();
	        	extras.putString("ip",placeString);
	        	extras.putString("lat",lat);
	        	extras.putString("lng",lng);
        	
	        	Intent intent = new Intent(Ip.this, MapIp.class);
	        	intent.putExtras(extras);
	        	startActivity(intent);
            	
            }
		});
	}
	
	
	private void loadImage(String path){
		
		if(!path.equals("")){

		BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
       
        Bitmap bitmap = BitmapFactory.decodeFile( path, options );
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, out);
        Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));

        imageTV.setImageBitmap(decoded);
		}
		
	}
	

	
}

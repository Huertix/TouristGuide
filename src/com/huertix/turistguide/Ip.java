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
		
		loadImage(extras.getString("img"));
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

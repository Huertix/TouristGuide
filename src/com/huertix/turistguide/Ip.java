package com.huertix.turistguide;

import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
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

public class Ip extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ip);
		
		TextView nameTV = (TextView) findViewById(R.id.ip_act);
		WebView webView = (WebView) findViewById(R.id.web);
		TextView latTV = (TextView) findViewById(R.id.lat_ip_act);
		TextView lngTV = (TextView) findViewById(R.id.lng_ip_act);
		TextView distTV = (TextView) findViewById(R.id.dist_ip_act);
		
		Bundle extras = getIntent().getExtras();
		nameTV.setText(extras.getString("name"));
		
		String url = extras.getString("url");
		
		if(!url.equals("")){
			
			if(!url.contains("http"))
				url = "http://"+url;
	
			webView.setWebViewClient(new WebViewClient());
			webView.loadUrl(extras.getString("url"));
		}
		else{
			String data = "<html><body>Sorry...<p>There is not any URL linked<p>  to this interesting point</body></html>";
			webView.getSettings().setJavaScriptEnabled(true);
			webView.loadDataWithBaseURL("", data, "text/html", "UTF-8", "");
		}
		//imgTV.setImageResource(R.drawable.mountains);
		latTV.setText(extras.getString("lat"));
		lngTV.setText(extras.getString("lng"));
		
	
		
	}

	
}

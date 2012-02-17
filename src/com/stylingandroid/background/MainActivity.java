package com.stylingandroid.background;

import java.net.MalformedURLException;
import java.net.URL;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.main );
	}
	
	public void doAsyncTask( View v ) throws MalformedURLException
	{
		MyAsyncTask task = new MyAsyncTask( this );
		URL url = new URL( "http://www.google.com" );
		task.execute( url, url, url, url );
	}
}
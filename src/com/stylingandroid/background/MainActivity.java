package com.stylingandroid.background;

import java.net.MalformedURLException;
import java.net.URL;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
		LoaderCallbacks<String>
{
	private ProgressBar loaderProgress;
	private Button loaderButton;

	private ProgressBar serviceProgress;
	private Button serviceButton;

	private MyServiceReceiver receiver = new MyServiceReceiver();

	/** Called when the activity is first created. */
	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.main );

		loaderProgress = (ProgressBar) findViewById( R.id.loaderProgress );
		loaderButton = (Button) findViewById( R.id.loaderButton );

		serviceProgress = (ProgressBar) findViewById( R.id.serviceProgress );
		serviceButton = (Button) findViewById( R.id.serviceButton );
	}

	/*
	 * AsyncTask
	 */
	public void doAsyncTask( View v ) throws MalformedURLException
	{
		MyAsyncTask task = new MyAsyncTask( this );
		URL url = new URL( "http://www.google.com" );
		task.execute( url, url, url, url );
	}

	/*
	 * Loader
	 */
	public void doLoader( View v )
	{
		LoaderManager lm = getSupportLoaderManager();
		Loader<String> loader = lm.restartLoader( 0, null, this );
		loaderProgress.setVisibility( View.VISIBLE );
		loaderButton.setVisibility( View.INVISIBLE );
		loader.forceLoad();
	}

	@Override
	public Loader<String> onCreateLoader( int id, Bundle args )
	{
		return new MyLoader( this );
	}

	@Override
	public void onLoadFinished( Loader<String> loader, String str )
	{
		loaderProgress.setVisibility( View.INVISIBLE );
		loaderButton.setVisibility( View.VISIBLE );
		Toast.makeText( this, str, Toast.LENGTH_SHORT ).show();
	}

	@Override
	public void onLoaderReset( Loader<String> loader )
	{
	}

	/*
	 * Service
	 */
	public void doService( View v )
	{
		serviceProgress.setVisibility( View.VISIBLE );
		serviceButton.setVisibility( View.INVISIBLE );
		startService( new Intent( MyService.ACTION_DO_SOMETHING ) );
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		IntentFilter filter = new IntentFilter();
		filter.addAction( MyService.ACTION_UPDATE );
		filter.addAction( MyService.ACTION_PROGRESS );
		registerReceiver( receiver, filter );
	}

	@Override
	protected void onStop()
	{
		unregisterReceiver( receiver );
		super.onStop();
	}

	private class MyServiceReceiver extends BroadcastReceiver
	{

		@Override
		public void onReceive( Context context, Intent intent )
		{
			if ( intent.getAction().equals( MyService.ACTION_PROGRESS ) )
			{
				int progress = intent.getIntExtra( MyService.EXTRA_PROGRESS, 0 );
				int max = intent.getIntExtra( MyService.EXTRA_MAX, 0 );
				serviceProgress.setMax( max );
				serviceProgress.setProgress( progress );
			}
			if ( intent.getAction().equals( MyService.ACTION_UPDATE ) )
			{
				String text = intent
						.getStringExtra( MyService.EXTRA_UPDATE_TEXT );
				serviceProgress.setVisibility( View.INVISIBLE );
				serviceButton.setVisibility( View.VISIBLE );
				Toast.makeText( MainActivity.this, text, Toast.LENGTH_SHORT )
						.show();
			}
		}
	}
}
package com.stylingandroid.background;

import java.net.MalformedURLException;
import java.net.URL;

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

	/** Called when the activity is first created. */
	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.main );

		loaderProgress = (ProgressBar) findViewById( R.id.loaderProgress );
		loaderButton = (Button) findViewById( R.id.loaderButton );
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
		Loader<String> loader = lm.initLoader( 0, null, this );
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

}
package com.stylingandroid.background;

import android.app.IntentService;
import android.content.Intent;

public class MyService extends IntentService
{
	public static final String ACTION_DO_SOMETHING = 
			"com.stylingandroid.background.ACTION_DO_SOMETHING";
	public static final String ACTION_UPDATE = 
			"com.stylingandroid.background.ACTION_UPDATE";
	public static final String ACTION_PROGRESS = 
			"com.stylingandroid.background.ACTION_PROGRESS";

	public static final String EXTRA_UPDATE_TEXT = "UPDATE_TEXT";
	public static final String EXTRA_PROGRESS = "PROGRESS";
	public static final String EXTRA_MAX = "MAX";

	public MyService()
	{
		super( "MyService" );
	}

	@Override
	protected void onHandleIntent( Intent intent )
	{
		if ( intent.getAction().equals( ACTION_DO_SOMETHING ) )
		{
			doSomething();
		}
	}

	private void doSomething()
	{
		for ( int i = 0; i < 5; i++ )
		{
			Intent progressIntent = 
					new Intent( ACTION_PROGRESS );
			progressIntent.putExtra( EXTRA_PROGRESS, i );
			progressIntent.putExtra( EXTRA_MAX, 4 );
			sendBroadcast( progressIntent );
			try
			{
				Thread.sleep( 2000 );
			}
			catch ( InterruptedException e )
			{
			}
		}
		Intent updateIntent = new Intent( ACTION_UPDATE );
		updateIntent.putExtra( EXTRA_UPDATE_TEXT, 
				"Finished Service" );
		sendBroadcast( updateIntent );
	}
}

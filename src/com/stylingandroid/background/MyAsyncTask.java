package com.stylingandroid.background;

import java.net.URL;

import com.stylingandroid.background.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MyAsyncTask extends AsyncTask<URL, Integer, String>
{
	private final Activity activity;

	private ProgressBar progress;
	private Button button;
	private int count = 0;

	/*
	 * Please Note: This code is slightly different from the code in the article
	 * at http://blog.stylingandroid.com/archives/848. However this is purely so
	 * that it does something meaningful in the context of the enclosing app.
	 * The techniques used are exactly as described in the article.
	 */

	public MyAsyncTask( Activity activity )
	{
		this.activity = activity;
	}

	@Override
	protected void onPreExecute()
	{
		button = (Button) activity.findViewById( R.id.asyncTaskButton );
		progress = (ProgressBar) activity.findViewById( R.id.asyncTaskProgress );
		button.setVisibility( View.INVISIBLE );
		progress.setVisibility( View.VISIBLE );
	}

	@Override
	protected String doInBackground( URL... params )
	{
		String ret = null;
		count = params.length;
		for ( int i = 0; i < count; i++ )
		{
			publishProgress( i );
			try
			{
				Thread.sleep( 2000 );
			}
			catch ( InterruptedException e )
			{
			}
		}
		ret = "Finished AsyncTask";
		return ret;
	}

	@Override
	protected void onProgressUpdate( Integer... values )
	{
		progress.setMax( count );
		progress.setProgress( values[0] );
	}

	@Override
	protected void onPostExecute( String result )
	{
		Toast.makeText( activity, result, Toast.LENGTH_SHORT ).show();
		button.setVisibility( View.VISIBLE );
		progress.setVisibility( View.INVISIBLE );
	}
}

package com.stylingandroid.background;

import android.app.Activity;
import android.support.v4.content.AsyncTaskLoader;
import android.widget.ProgressBar;

public class MyLoader extends AsyncTaskLoader<String>
{
	private ProgressBar progress;

	/*
	 * Please Note: This code is slightly different from the code in the article
	 * at http://blog.stylingandroid.com/archives/853. However this is purely so
	 * that it does something meaningful in the context of the enclosing app.
	 * The techniques used are exactly as described in the article.
	 */

	public MyLoader( Activity activity )
	{
		super( activity );
		progress = (ProgressBar) activity.findViewById( R.id.loaderProgress );
	}

	@Override
	public String loadInBackground()
	{
		progress.setMax( 4 );
		for ( int i = 0; i < 5; i++ )
		{
			progress.setProgress( i );
			try
			{
				Thread.sleep( 2000 );
			}
			catch ( InterruptedException e )
			{
			}
		}
		return "Finished Loader";
	}
}

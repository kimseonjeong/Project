package com.mavlux.framework.view;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public abstract class ActivityLayout<ACTIVITY extends Activity, LAYOUT_LISTENER extends LayoutListener> extends BaseLayout {

	private ACTIVITY activity;
	private LAYOUT_LISTENER layoutListener;

	public ActivityLayout( ACTIVITY activity, LAYOUT_LISTENER layoutListener ) {

		this.activity = activity;
		this.layoutListener = layoutListener;

		inflateLayout( getLayoutResId() );
	}

	@Override
	protected final View inflateLayout( int layoutResId ) {

		View rootView = LayoutInflater.from(activity).inflate( layoutResId, null );
		if ( null != rootView ) {

			setRootView( rootView );
			initToolbar( rootView );
			onLayoutInflated();
		}

		return getRootView();
	}

	@TargetApi( Build.VERSION_CODES.LOLLIPOP )
	private void initToolbar( View rootView ) {

		if ( Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT ) {

//			Toolbar toolbar = (Toolbar) rootView.findViewById( R.id.toolbar );
//			if ( null != toolbar )
//				activity.setActionBar( toolbar );
		}
	}

	@Override
	protected final Context getContext() {

		return getActivity();
	}

	@Override
	protected final ACTIVITY getActivity() {

		return activity;
	}

	protected final LAYOUT_LISTENER getLayoutListener() {

		return layoutListener;
	}

	public boolean onCreateOptionsMenu( Menu menu ) {

		return false;
	}

	public boolean onOptionsItemSelected( MenuItem item ) {

		return false;
	}

	public boolean onPrepareOptionsMenu( Menu menu ) {

		return false;
	}

}

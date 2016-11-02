package com.mavlux.framework.controller;


import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;


public class BaseActivity extends ActionBarActivity {

	private boolean activityRunning = false;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );
		setWindowContentOverlayCompat();
	}

	protected void setWindowContentOverlayCompat() {

		if ( Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2 ) {

			View contentView = findViewById( android.R.id.content );

			if ( contentView instanceof FrameLayout) {

				TypedValue tv = new TypedValue();

				if ( getTheme().resolveAttribute( android.R.attr.windowContentOverlay, tv, true ) ) {

					if ( tv.resourceId != 0 )
						( (FrameLayout) contentView ).setForeground( getResources().getDrawable( tv.resourceId ) );
				}
			}
		}
	}

	@Override
	public void onConfigurationChanged( Configuration newConfig ) {

		super.onConfigurationChanged( newConfig );
	}

	@Override
	protected void onResume() {

		super.onResume();
		activityRunning = true;
	}

	@Override
	protected void onPause() {

		super.onPause();
		activityRunning = false;
	}

	public boolean isActivityRunning() {

		return activityRunning;
	}

}

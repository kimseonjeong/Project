package com.mavlux.framework.view;


import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mavlux.framework.controller.BaseActivity;
import com.mavlux.test.R;


public abstract class SupportActivityLayout<ACTIVITY extends BaseActivity, LAYOUT_LISTENER extends LayoutListener> extends BaseLayout {

	private ACTIVITY activity;
	private LAYOUT_LISTENER layoutListener;

	private AlertDialog loadingDialog;

	public SupportActivityLayout( ACTIVITY activity, LAYOUT_LISTENER layoutListener ) {

		this.activity = activity;
		this.layoutListener = layoutListener;

		inflateLayout( getLayoutResId() );
		onLayoutInflated();
	}

	@Override
	protected final View inflateLayout( int layoutResId ) {

		View rootView = LayoutInflater.from(activity).inflate( layoutResId, null );
		if ( null != rootView ) {

			setRootView( rootView );
			initToolbar( rootView );
		}

		return getRootView();
	}

	private void initToolbar( View rootView ) {

		Toolbar toolbar = (Toolbar) rootView.findViewById( R.id.toolbarLayout);
		if ( null != toolbar )
			activity.setSupportActionBar( toolbar );
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

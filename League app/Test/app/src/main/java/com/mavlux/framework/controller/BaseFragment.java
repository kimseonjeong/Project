package com.mavlux.framework.controller;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;



public class BaseFragment extends Fragment {

	private PowerManager.WakeLock screenDimWakeLock;

	@Override
	public void onResume() {

		super.onResume();
	}

	@Override
	public void onDestroyView() {

		super.onDestroyView();
		releaseScreenDimWakeLock();
	}

	public BaseActivity getBaseActivity() {

		ActionBarActivity activity = getActionBarActivity();

		if ( activity instanceof BaseActivity )
			return (BaseActivity) activity;
		else
			return null;
	}

	public final ActionBarActivity getActionBarActivity() {

		FragmentActivity fragmentActivity = getActivity();

		if ( fragmentActivity instanceof ActionBarActivity)
			return (ActionBarActivity) getActivity();
		else
			return null;
	}

	@SuppressLint( "Wakelock" )
	@SuppressWarnings( "deprecation" )
	public void acquireScreenDimWakeLock() {

		if ( isAdded() ) {

			if ( null != screenDimWakeLock && screenDimWakeLock.isHeld() )
				return;

			PowerManager powerManager = (PowerManager) getActivity().getSystemService( Context.POWER_SERVICE );
			screenDimWakeLock = powerManager.newWakeLock( PowerManager.SCREEN_DIM_WAKE_LOCK, "ScreenDimWakeLock" );
			screenDimWakeLock.acquire();
		}
	}

	public void releaseScreenDimWakeLock() {

		if ( null != screenDimWakeLock ) {

			screenDimWakeLock.release();
			screenDimWakeLock = null;
		}
	}

	public boolean onBackPressed() {

		return false;
	}

}

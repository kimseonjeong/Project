

package com.mavlux.framework.util;


import android.app.Activity;
import android.widget.Toast;


public class BackPressedHandler {
	
	private Activity activity;
	
	private long intervalTime;
	private long backPressedTime;
	
	private Toast toast;
	private String message;
	
	private static final String DEFAULT_MESSAGE = "";
	
	public BackPressedHandler( Activity activity ) {
	
		this( activity, 2000 );
	}
	
	public BackPressedHandler( Activity activity, long intervalTime ) {
	
		this.activity = activity;
		
		setIntervalTime( intervalTime );
		this.backPressedTime = 0;
		
		setCustomToast( null );
		setMessage( DEFAULT_MESSAGE );
	}
	
	public void setIntervalTime( long intervalTime ) {
	
		if ( 0 < intervalTime )
			this.intervalTime = intervalTime;
	}
	
	public long getIntervalTime() {
	
		return intervalTime;
	}
	
	public void setMessage( int messageResId ) {
	
		setMessage( activity.getResources().getString( messageResId ) );
	}
	
	public void setMessage( String message ) {
	
		if ( null != message )
			this.message = message;
	}
	
	public String getMessage() {
	
		return message;
	}
	
	public void setCustomToast( Toast toast ) {
	
		this.toast = toast;
	}
	
	public Toast getToast() {
	
		if ( null == toast )
			return Toast.makeText(activity, message, Toast.LENGTH_SHORT);
		else
			return toast;
	}
	
	public void onBackPressed() {
	
		long tempTime = System.currentTimeMillis();
		long intervalTime = tempTime - backPressedTime;
		
		if ( 0 <= intervalTime && this.intervalTime >= intervalTime ) {
			
			activity.finish();
		}
		else {
			
			backPressedTime = tempTime;
			getToast().show();
		}
	}
	
}

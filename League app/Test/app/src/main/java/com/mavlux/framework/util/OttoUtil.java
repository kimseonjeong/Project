

package com.mavlux.framework.util;


import android.os.Handler;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;


public class OttoUtil {
	
	private static OttoUtil ottoUtil;
	
	private Bus bus;
	private Handler handler = new Handler();
	
	private OttoUtil() {
	
		bus = new Bus( ThreadEnforcer.ANY );
	}
	
	public static void start(){
		
		getInstance();
	}
	
	public static OttoUtil getInstance() {
	
		if ( null == ottoUtil )
			ottoUtil = new OttoUtil();
		
		return ottoUtil;
	}
	
	public void register( Object object ) {
	
		bus.register( object );
	}
	
	public void unregister( Object object ) {
	
		bus.unregister( object );
	}
	
	public void post( Event event ) {
	
		bus.post( event );
	}
	
	public void postInMainThread( final Event event ) {
	
		handler.post( new Runnable() {
			
			@Override
			public void run() {
			
				bus.post( event );
			}
		} );
	}
	
	public interface Event {
		
	}
	
}

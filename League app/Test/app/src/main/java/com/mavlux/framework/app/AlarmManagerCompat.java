package com.mavlux.framework.app;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

import java.util.GregorianCalendar;


public class AlarmManagerCompat {

	private AlarmManager alarmManager;

	private AlarmManagerCompat( Context context ) {

		this.alarmManager = (AlarmManager) context.getSystemService( Context.ALARM_SERVICE );
	}

	public static AlarmManagerCompat from( Context context ) {

		return new AlarmManagerCompat( context );
	}

	@SuppressLint( "NewApi" )
	public void setDailyRepeatingAlarm( int hourOfDay, int minute, PendingIntent alarmPendingIntent ) {

		if ( Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT )
			alarmManager.setExact( AlarmManager.RTC_WAKEUP, getTriggerAtMillis( hourOfDay, minute ), alarmPendingIntent );
		else
			alarmManager.setRepeating( AlarmManager.RTC_WAKEUP, getTriggerAtMillis( hourOfDay, minute ), AlarmManager.INTERVAL_DAY, alarmPendingIntent );
	}

	private long getTriggerAtMillis( int hourOfDay, int minute ) {

		GregorianCalendar currentCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
		int currentHourOfDay = currentCalendar.get( GregorianCalendar.HOUR_OF_DAY );
		int currentMinute = currentCalendar.get( GregorianCalendar.MINUTE );

		if ( currentHourOfDay < hourOfDay || ( currentHourOfDay == hourOfDay && currentMinute <= minute ) )
			return getTimeInMillisToday( hourOfDay, minute );
		else
			return getTimeInMillisTomorrow( hourOfDay, minute );
	}

	private long getTimeInMillisToday( int hourOfDay, int minute ) {

		return getTimeInMillis( false, hourOfDay, minute );
	}

	private long getTimeInMillis( boolean tomorrow, int hourOfDay, int minute ) {

		GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();

		if ( tomorrow )
			calendar.add( GregorianCalendar.DAY_OF_YEAR, 1 );

		calendar.set( GregorianCalendar.HOUR_OF_DAY, hourOfDay );
		calendar.set( GregorianCalendar.MINUTE, minute );
		calendar.set( GregorianCalendar.SECOND, 0 );
		calendar.set( GregorianCalendar.MILLISECOND, 0 );

		return calendar.getTimeInMillis();
	}

	private long getTimeInMillisTomorrow( int hourOfDay, int minute ) {

		return getTimeInMillis( true, hourOfDay, minute );
	}

	@SuppressLint( "NewApi" )
	public void resetDailyRepeatingAlarm( int hourOfDay, int minute, PendingIntent alarmPendingIntent ) {

		if ( Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT )
			alarmManager.setExact( AlarmManager.RTC_WAKEUP, getTimeInMillisTomorrow( hourOfDay, minute ), alarmPendingIntent );
	}

	@SuppressLint( "NewApi" )
	public void setOnceAlarm( int hourOfDay, int minute, PendingIntent alarmPendingIntent ) {

		if ( Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT )
			alarmManager.setExact( AlarmManager.RTC_WAKEUP, getTriggerAtMillis( hourOfDay, minute ), alarmPendingIntent );
		else
			alarmManager.set( AlarmManager.RTC_WAKEUP, getTriggerAtMillis( hourOfDay, minute ), alarmPendingIntent );
	}

	public void cancelAlarm( PendingIntent alarmPendingIntent ) {

		alarmManager.cancel( alarmPendingIntent );
	}

}

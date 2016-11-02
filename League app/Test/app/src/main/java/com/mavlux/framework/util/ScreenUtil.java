package com.mavlux.framework.util;


import android.content.Context;
import android.util.DisplayMetrics;


public class ScreenUtil {

	private static Context context;
	private static DisplayMetrics displayMetrics;

	public static void setContext( Context context ) {

		ScreenUtil.context = context;
	}

	public static double dipToPixel( Context context, double dip ) {

		return dip * getDisplayMetrics( context ).densityDpi / 160.0;
	}

	public static double pixelToDip( Context context, double pixel ) {

		return pixel * 160.0 / getDisplayMetrics( context ).densityDpi;
	}

	private static DisplayMetrics getDisplayMetrics( Context context ) {

		if ( null == displayMetrics ) {

			try {

				displayMetrics = context.getResources().getDisplayMetrics();
			}
			catch ( NullPointerException e ) {

//				CrashlyticsUtil.s( e );
				displayMetrics = ScreenUtil.context.getResources().getDisplayMetrics();
			}
		}

		return displayMetrics;
	}

	public static int getWidthPixels( Context context ) {

		return getDisplayMetrics( context ).widthPixels;
	}

	public static int getHeightPixels( Context context ) {

		return getDisplayMetrics( context ).heightPixels;
	}

	public static float getScreenRatio( Context context ) {

		float width = getWidthPixels( context );
		float height = getHeightPixels( context );

		return width / height;
	}

}

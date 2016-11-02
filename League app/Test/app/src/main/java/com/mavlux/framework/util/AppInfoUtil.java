

package com.mavlux.framework.util;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;


public class AppInfoUtil {

	private static AppVersion appVersion = null;

	public static AppVersion getAppVersion( Context context ) {

		if ( null == appVersion ) {

			try {

				PackageInfo packageInfo = context.getPackageManager().getPackageInfo( context.getPackageName(), 0 );

				appVersion = new AppVersion();
				appVersion.versionCode = packageInfo.versionCode;
				appVersion.versionName = packageInfo.versionName;
			}
			catch ( NameNotFoundException e ) {

			}
		}

		return appVersion;
	}

	public static class AppVersion {

		public int versionCode;
		public String versionName;

	}

}

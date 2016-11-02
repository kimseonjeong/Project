package com.mavlux.framework.util;


import android.content.ContentUris;
import android.net.Uri;


public class UriBuilder {

	public static Uri buildUriWithId( Uri contentUri, long id ) {

		return ContentUris.withAppendedId(contentUri, id);
	}

}

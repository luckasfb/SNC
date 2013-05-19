package com.pdroidandroid.simplenumericclock;

import android.util.Log;


public class Constants
{
	public static final String PREF_SHADOW = "drop-shadow";
	public static final String PREF_24HOUR = "use-24";

	public static final String LOG_TAG = "Clock";
	public static final boolean LOGD = false;

	public static void log( String message ) {
		if( LOGD )
			Log.d( LOG_TAG, message );
	}
}
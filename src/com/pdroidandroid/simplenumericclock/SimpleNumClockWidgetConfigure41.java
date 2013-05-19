package com.pdroidandroid.simplenumericclock;

import android.appwidget.AppWidgetManager;
import android.content.Context;


public class SimpleNumClockWidgetConfigure41 extends SimpleNumClockWidgetConfigure
{
	protected static final String PREF_PREFIX_KEY = "prefix41_";
	
	@Override
	protected void updateAppWidget( final Context context, boolean use24, boolean useShadow, AppWidgetManager appWidgetManager )
	{
		Constants.log( getClass().getSimpleName() + "::updateAppWidget" );
		ClockAppWidgetProvider41.updateAppWidget( context, appWidgetManager, mAppWidgetId, use24, useShadow );
	}
	
	@Override
	protected void savePrefs( final Context context, boolean use24, boolean useShadow )
	{
		Constants.log( getClass().getSimpleName() + "::savePrefs" );
		savePref( context, mAppWidgetId, Constants.PREF_24HOUR, use24 );
		savePref( context, mAppWidgetId, Constants.PREF_SHADOW, useShadow  );
	}
	
	static void savePref( Context context, int appWidgetId, String name, Boolean value )
	{
		savePref( context, appWidgetId, PREF_PREFIX_KEY, name, value );
	}

	static boolean loadPref( Context context, int appWidgetId, String name )
	{
		return loadPref( context, appWidgetId, PREF_PREFIX_KEY, name );
	}

}
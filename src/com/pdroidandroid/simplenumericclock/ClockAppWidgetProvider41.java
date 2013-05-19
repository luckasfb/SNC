package com.pdroidandroid.simplenumericclock;

import com.pdroidandroid.simplenumericclock.service.ClockService41;
import java.util.Date;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;


public class ClockAppWidgetProvider41 extends ClockAppWidgetProvider
{
	protected void startClockService( Context context )
	{
		context.startService( new Intent( ClockService41.class.getName() ) );
	}
	
	protected void stopClockService( Context context )
	{
		context.stopService( new Intent( ClockService41.class.getName() ) );
	}
	
	@Override
	protected void handleIntent( Context context, Intent intent )
	{
		Constants.log( getClass().getSimpleName() + "::handleIntent" );
		
		if( intent.getAction().equals( Intent.ACTION_TIME_TICK ) )
		{
			AppWidgetManager manager = AppWidgetManager.getInstance( context );
			int appWidgetIds[] = manager.getAppWidgetIds( new ComponentName( context, ClockAppWidgetProvider41.class.getName() ) );
			onUpdate( context, manager, appWidgetIds );
		}
	}
	
	@Override
	public void onUpdate( Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds )
	{
		Constants.log( getClass().getSimpleName() + "::onUpdate. " + appWidgetIds.length );
		
		final int N = appWidgetIds.length;
		for( int i = 0; i < N; i++ )
		{
			int appWidgetId = appWidgetIds[i];
			Constants.log( "appWidgetId: " + appWidgetId );
			
			boolean use24 = SimpleNumClockWidgetConfigure41.loadPref( context, appWidgetId, Constants.PREF_24HOUR );
			boolean useShadow = SimpleNumClockWidgetConfigure41.loadPref( context, appWidgetId, Constants.PREF_SHADOW );
			updateAppWidget( context, appWidgetManager, appWidgetId, use24, useShadow );
		}
	}

	public static void updateAppWidget( Context context, AppWidgetManager appWidgetManager, int appWidgetId, boolean use24,
			boolean useShadow )
	{
		Date date = new Date();
		int layoutId = useShadow ? R.layout.widget41 : R.layout.widget41_noshadow;
		ClockAppWidgetProvider.updateAppWidget( context, appWidgetManager, appWidgetId, use24, date, layoutId );
	}
}
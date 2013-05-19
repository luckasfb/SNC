package com.pdroidandroid.simplenumericclock;

import com.pdroidandroid.simplenumericclock.service.ClockService;
import com.pdroidandroid.simplenumericclock.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class ClockAppWidgetProvider extends AppWidgetProvider
{
	public static SimpleDateFormat h12_format = new SimpleDateFormat( "hh" );
	public static SimpleDateFormat h24_format = new SimpleDateFormat( "HH" );
	public static SimpleDateFormat m_format = new SimpleDateFormat( "mm" );
	public static SimpleDateFormat day_format = new SimpleDateFormat( "EEEEE" );
	public static SimpleDateFormat date_format = new SimpleDateFormat( "MMMMM dd" );

	@Override
	public void onDeleted( Context context, int[] appWidgetIds )
	{
		Constants.log( "ClockAppWidgetProvider::onDeleted" );
		super.onDeleted( context, appWidgetIds );
	}

	@Override
	public void onDisabled( Context context )
	{
		Constants.log( "ClockAppWidgetProvider::onDisabled" );
		stopClockService( context );
		super.onDisabled( context );
	}

	protected void startClockService( Context context )
	{
		context.startService( new Intent( ClockService.class.getName() ) );
	}

	protected void stopClockService( Context context )
	{
		context.stopService( new Intent( ClockService.class.getName() ) );
	}

	@Override
	public void onEnabled( Context context )
	{
		Constants.log( "ClockAppWidgetProvider::onEnabled" );
		startClockService( context );
		super.onEnabled( context );
	}


	@Override
	public void onReceive( Context context, Intent intent )
	{
		Constants.log( getClass().getSimpleName() + "::onReceive: " + intent.getAction() );
		super.onReceive( context, intent );
		handleIntent( context, intent );
	}

	protected void handleIntent( Context context, Intent intent )
	{
		Constants.log( getClass().getSimpleName() + "::handleIntent" );

		if( intent.getAction().equals( Intent.ACTION_TIME_TICK ) )
		{
			AppWidgetManager manager = AppWidgetManager.getInstance( context );
			int appWidgetIds[] = manager.getAppWidgetIds( new ComponentName( context, ClockAppWidgetProvider.class.getName() ) );
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
			boolean use24 = SimpleNumClockWidgetConfigure.loadPref( context, appWidgetId, Constants.PREF_24HOUR );
			boolean useShadow = SimpleNumClockWidgetConfigure.loadPref( context, appWidgetId, Constants.PREF_SHADOW );

			updateAppWidget( context, appWidgetManager, appWidgetId, use24, useShadow );
		}
	}

	public static void updateAppWidget( Context context, AppWidgetManager appWidgetManager, int appWidgetId, boolean use24,
			boolean useShadow )
	{
		Date date = new Date();
		int layoutId = useShadow ? R.layout.widget42 : R.layout.widget42_noshadow;
		updateAppWidget( context, appWidgetManager, appWidgetId, use24, date, layoutId );
	}

	/**
	 * @param context
	 * @param appWidgetManager
	 * @param appWidgetId
	 * @param use24
	 * @param date
	 * @param layoutId
	 */
	protected static void updateAppWidget( Context context, AppWidgetManager appWidgetManager, int appWidgetId, boolean use24,
			Date date, int layoutId )
	{
		Constants.log( "ClockAppWidgetProvider::updateAppWidget. appWidgetId=" + appWidgetId + ", layoutId=" + layoutId );

		RemoteViews remoteView = new RemoteViews( context.getPackageName(), layoutId );
		setPendingIntent( context, remoteView );
		updateAppWidget( appWidgetManager, appWidgetId, use24, date, remoteView );
	}

	/**
	 * @param context
	 * @param remoteView
	 */
	protected static void setPendingIntent( Context context, RemoteViews remoteView )
	{
		Intent alarmIntent = new Intent( Intent.ACTION_MAIN ).addCategory( Intent.CATEGORY_LAUNCHER );
		ComponentName alarmName = getComponentName();
		alarmIntent.setComponent( alarmName );
		PendingIntent pendingIntent = PendingIntent.getActivity( context, 0, alarmIntent, 0 );

		remoteView.setOnClickPendingIntent( R.id.main_layout, pendingIntent );
	}

	/**
	 * @return
	 */
	protected static ComponentName getComponentName()
	{
		ComponentName alarmName = new ComponentName( "com.android.deskclock", "com.android.deskclock.DeskClock" );

		if( android.os.Build.PRODUCT.equals( "GT-I9000" ) )
			alarmName = new ComponentName( "com.sec.android.app.clockpackage", "com.sec.android.app.clockpackage.ClockPackage" );
		else if( android.os.Build.PRODUCT.equals( "htc_bravo" ) )
			alarmName = new ComponentName( "com.htc.android.worldclock", "com.htc.android.worldclock.WorldClockTabControl" );
		return alarmName;
	}

	protected static void updateAppWidget( AppWidgetManager appWidgetManager, int appWidgetId, boolean use24, Date date,
			RemoteViews remoteView )
	{
		String hours;
		String am;

		if( use24 )
		{
			hours = h24_format.format( date );
			am = "";
		} else
		{
			hours = h12_format.format( date );
			am = date.getHours() >= 12 ? "PM" : "AM";
		}

		remoteView.setTextViewText( R.id.text_minutes, m_format.format( date ) );
		remoteView.setTextViewText( R.id.text_hours, hours );
		remoteView.setTextViewText( R.id.text_am, am );
		remoteView.setTextViewText( R.id.text_day, StringUtils.capitalizeFirstLetter( day_format.format( date ) ) + ", " );
		remoteView.setTextViewText( R.id.text_date, StringUtils.capitalizeFirstLetter( date_format.format( date ) ) + " " );
		appWidgetManager.updateAppWidget( appWidgetId, remoteView );
	}
}
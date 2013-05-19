package com.pdroidandroid.simplenumericclock.service;

import com.pdroidandroid.simplenumericclock.ClockAppWidgetProvider;
import com.pdroidandroid.simplenumericclock.Constants;
import android.app.Service;
import android.appwidget.AppWidgetProvider;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class ClockService extends Service
{
	protected AppWidgetProvider receiver;

	public ClockService()
	{
		Constants.log( getClass().getSimpleName() + "::newInstance" );
	}

	@Override
	public IBinder onBind( Intent intent )
	{
		Constants.log( getClass().getSimpleName() + "::onBind" );
		return null;
	}

	@Override
	public void onStart( Intent intent, int startId )
	{
		Constants.log( getClass().getSimpleName() + "::onStart" );
		super.onStart( intent, startId );
	}

	@Override
	public void onCreate()
	{
		Constants.log( getClass().getSimpleName() + "::onCreate" );
		super.onCreate();
		createReceiver();
		registerReceiver( receiver, new IntentFilter( Intent.ACTION_TIME_TICK ) );
	}

	protected void createReceiver()
	{
		receiver = new ClockAppWidgetProvider();
		Constants.log( getClass().getSimpleName() + "::createReceiver: " + receiver );
	}

	@Override
	public void onDestroy()
	{
		Constants.log( getClass().getSimpleName() + "::onDestroy" );
		unregisterReceiver();
		super.onDestroy();
	}

	protected void unregisterReceiver()
	{
		Constants.log( getClass().getSimpleName() + "::unregisterReceiver: " + receiver );
		unregisterReceiver( receiver );
	}
}

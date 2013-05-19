package com.pdroidandroid.simplenumericclock.service;

import com.pdroidandroid.simplenumericclock.ClockAppWidgetProvider41;
import com.pdroidandroid.simplenumericclock.Constants;


public class ClockService41 extends ClockService
{
	@Override
	protected void createReceiver()
	{
		receiver = new ClockAppWidgetProvider41();
		Constants.log( getClass().getSimpleName() + "::createReceiver: " + receiver );
	}

	@Override
	protected void unregisterReceiver()
	{
		Constants.log( getClass().getSimpleName() + "::unregisterReceiver: " + receiver );
		unregisterReceiver( receiver );
	}
}
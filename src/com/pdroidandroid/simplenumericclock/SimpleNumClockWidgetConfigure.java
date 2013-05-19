package com.pdroidandroid.simplenumericclock;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class SimpleNumClockWidgetConfigure extends Activity
{
	protected static final String PREFS_NAME = "com.pdroidandroid.simplenumericclock";
	protected static final String PREF_PREFIX_KEY = "prefix42_";
	protected int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	
	protected CheckBox mUse24Checkbox;
	protected CheckBox mShadowCheckbox;

	public SimpleNumClockWidgetConfigure()
	{
		super();
	}

	@Override
	public void onCreate( Bundle icicle )
	{
		super.onCreate( icicle );

		setResult( RESULT_CANCELED );
		setContentView( R.layout.configure );
		
		mUse24Checkbox = (CheckBox) findViewById( R.id.checkbox_24 );
		mShadowCheckbox = (CheckBox) findViewById( R.id.checkbox_shadow );
		
		findViewById( R.id.save_button ).setOnClickListener( mOnClickListener );

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		
		if( extras != null )
		{
			mAppWidgetId = extras.getInt( AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID );
		}

		if( mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID )
		{
			finish();
		}
	}

	protected View.OnClickListener mOnClickListener = new View.OnClickListener() 
	{
		
		public void onClick( View v )
		{
			final Context context = getContext();
			boolean use24 = mUse24Checkbox.isChecked();
			boolean useShadow = mShadowCheckbox.isChecked();
			
			savePrefs( context, use24, useShadow );

			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance( context );
			Constants.log( "Configure completed!" );
			updateAppWidget( context, use24, useShadow, appWidgetManager );

			Intent resultValue = new Intent();
			resultValue.putExtra( AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId );
			setResult( RESULT_OK, resultValue );
			finish();
		}
	};
	
	protected Context getContext() {
		Constants.log( this.getClass().getSimpleName() + "::getContext" );
		return this;
	}
	
	protected void savePrefs( final Context context, boolean use24, boolean useShadow )
	{
		Constants.log( getClass().getSimpleName() + "::savePrefs" );
		savePref( context, mAppWidgetId, Constants.PREF_24HOUR, use24 );
		savePref( context, mAppWidgetId, Constants.PREF_SHADOW, useShadow  );
	}
	
	protected void updateAppWidget( final Context context, boolean use24, boolean useShadow, AppWidgetManager appWidgetManager )
	{
		Constants.log( getClass().getSimpleName() + "::updateAppWidget" );
		ClockAppWidgetProvider.updateAppWidget( context, appWidgetManager, mAppWidgetId, use24, useShadow );
	}

	static void savePref( Context context, int appWidgetId, String name, Boolean value )
	{
		savePref( context, appWidgetId, PREF_PREFIX_KEY, name, value );
	}

	static boolean loadPref( Context context, int appWidgetId, String name )
	{
		return loadPref( context, appWidgetId, PREF_PREFIX_KEY, name );
	}

	protected static void savePref( Context context, int appWidgetId, String prefix, String name, Boolean value )
	{
		Constants.log( "savePrev. prefix=" + prefix + ", appWidgetId=" + appWidgetId + ", name=" + name );
		SharedPreferences.Editor prefs = context.getSharedPreferences( PREFS_NAME, 0 ).edit();
		prefs.putBoolean( prefix + appWidgetId + "-" + name, value );
		prefs.commit();
	}
	
	protected static boolean loadPref( Context context, int appWidgetId, String prefix, String name )
	{
		Constants.log( "loadPrev. prefix=" + prefix + ", appWidgetId=" + appWidgetId + ", name=" + name );
		SharedPreferences prefs = context.getSharedPreferences( PREFS_NAME, 0 );
		boolean value = prefs.getBoolean( prefix + appWidgetId + "-" + name, false );
		return value;
	}

}
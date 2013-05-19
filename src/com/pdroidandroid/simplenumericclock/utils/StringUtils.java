package com.pdroidandroid.simplenumericclock.utils;

import java.util.StringTokenizer;

public class StringUtils
{

	public static String capitalizeFirstLetter( String str )
	{
		final StringTokenizer st = new StringTokenizer( str, " ", true );
		final StringBuilder sb = new StringBuilder();

		while( st.hasMoreTokens() )
		{
			String token = st.nextToken();
			token = String.format( "%s%s", Character.toUpperCase( token.charAt( 0 ) ), token.substring( 1 ) );
			sb.append( token );
		}
		return sb.toString();
	}
}
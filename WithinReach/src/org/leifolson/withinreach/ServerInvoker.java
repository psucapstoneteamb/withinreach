package org.leifolson.withinreach;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.content.Context;
import android.os.Handler;

public class ServerInvoker 
{
	private Handler handler;
	private Context context;
	
	private int time; //time of day in minutes
	private int day;
	private int month;
	private int year;
	
	private String url; //url to pass to ServerComMgr
	
	
	public ServerInvoker(Context context, Handler handler, double latitude, double longitude, int modeCode, int timeConstraint) 
	{
		this.handler = handler;
		this.context = context;
		
		GregorianCalendar calendar = (GregorianCalendar)Calendar.getInstance();
		day = calendar.get(Calendar.DAY_OF_WEEK);
		month = calendar.get(Calendar.MONTH);
		year = calendar.get(Calendar.YEAR);
		time = (60 * calendar.get(Calendar.HOUR_OF_DAY)) + calendar.get(Calendar.MINUTE);
		

		url = "http://withinreach.herokuapp.com/json?";
		url += ("lat=" + latitude);
		url += ("&long=" + longitude);
		url += ("&time=" + time);
		url += ("&day=" + day);
		url += ("&month=" + month);
		url += ("&year=" + year);
		url += ("&mode_code=" + modeCode);
		url += ("&constraint=" + timeConstraint);
	}
	
	public void invokeServerComMgr()
	{
		System.out.println(url);
		new ServerComMgr(context, handler).execute(url);
	
	}
	
}

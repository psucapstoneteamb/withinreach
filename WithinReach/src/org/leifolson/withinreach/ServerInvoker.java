/*
Copyright (c) 2013, Haneen Abu-Khater, Alex Flyte, Kyle Greene, Vi Nguyen, Clinton Olson, and Hanrong Zhao
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following disclaimer in the documentation
and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
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
	
	private double latitude;
	private double longitude;
	
	private int modeCode;
	private int timeConstraint;
	private int time; //time of day
	private int day;
	private int month;
	private int year;
	
	private String url; //url to pass to ServerComMgr
	
	
	public ServerInvoker(Context context, Handler handler, double latitude, double longitude, int modeCode, int timeConstraint) 
	{
		this.handler = handler;
		this.context = context;
		
		this.latitude = latitude;
		this.longitude = longitude;
		this.modeCode = modeCode;
		this.timeConstraint = timeConstraint;
		
		GregorianCalendar calendar = (GregorianCalendar)Calendar.getInstance();
		day = calendar.get(Calendar.DAY_OF_WEEK);
		month = calendar.get(Calendar.MONTH);
		year = calendar.get(Calendar.YEAR);
		time = calendar.get(Calendar.MINUTE);
		

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

package org.leifolson.withinreach;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class ServicesMgr extends AsyncTask<String, Void, String>
{
	Handler uiHandler;
	String service;
	
	public ServicesMgr(Handler handler)
	{
		this.uiHandler = handler;
		service = "";
	}

	protected String doInBackground(String... searchTerms) 
	{	
		HttpClient client = new DefaultHttpClient();
		String url = "";
		
		service = searchTerms[0]; //0 = Places, 1 = Directions, 2 = Place detail
		
		
		if (searchTerms[0].equals("0"))
		{
			url = "https://maps.googleapis.com/maps/api/place/search/json?";
			url += "location=" + searchTerms[2] + "," + searchTerms[3];
			url += "&radius=" + searchTerms[4];
			url += "&keyword=" + searchTerms[1];
			url += "&sensor=false&key=AIzaSyAAlYH3-CUofsc7dFG73vF-C3q8YQaK71k";
			
		}
		
		else if (searchTerms[0].equals("1"))
		{
			url = "http://maps.googleapis.com/maps/api/directions/json?";
			url += "origin=" + searchTerms[1] + "," + searchTerms[2];
			url += "&destination=" + searchTerms[3] + "," + searchTerms[4] + "&sensor=false";
			url += "&mode=";
			int modeCode = Integer.parseInt(searchTerms[5]);
			
			if (modeCode == 1 || modeCode == 3 || modeCode == 5 || modeCode == 7)
			{
				url += "walking,";
			}
			if (modeCode == 2 || modeCode == 3 || modeCode == 6 || modeCode == 7)
			{
				url += "bicycling,";
			}
			if (modeCode == 4 || modeCode == 5 || modeCode == 6 || modeCode == 7)
			{
				url += "transit";
				Calendar calendar = Calendar.getInstance();
				Date date = calendar.getTime();
				System.out.println("date is " + (date.getTime()/1000));
				url += "&departure_time=" + (date.getTime()/1000);
			}
			//remove ending comma if there, otherwise it defaults to driving
			if (url.endsWith(","))
			{
				url = url.substring(0, url.length()-1);
			}
		}
		
		else if (searchTerms[0].equals("2"))
		{
			url = "https://maps.googleapis.com/maps/api/place/details/json?";
			url += "reference=" + searchTerms[1];
			url += "&sensor=false&key=AIzaSyAAlYH3-CUofsc7dFG73vF-C3q8YQaK71k";
		}
		
		
		HttpGet httpGet = new HttpGet(url);
		String result = null;
		try 
		{
			HttpResponse response = client.execute(httpGet);
			
			HttpEntity entity = response.getEntity();
		    InputStream content = entity.getContent();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
		    String line;
		    StringBuilder stringBuilder = new StringBuilder();

		
		    while ((line = reader.readLine()) != null)
		    {
		        stringBuilder.append(line + "\n");
		    }
		    result = stringBuilder.toString();
		} 
		catch (ClientProtocolException e1) 
		{
			e1.printStackTrace();
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		
		
		
		return result;
	}

	protected void onPostExecute(String str)
	{
		Bundle bundle = new Bundle();
		if (service.equals("0"))
			bundle.putString("PlacesJSON", str);
		else if (service.equals("1"))
			bundle.putString("DirectionsJSON", str);
		else if (service.equals("2"))
			bundle.putString("PlaceDetailJSON", str);
		Message msg = Message.obtain();
		msg.setData(bundle);
        msg.what = 1; //sending 1 means server call is done
        uiHandler.sendMessage(msg);
		
	}
}

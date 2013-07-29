package org.leifolson.withinreach;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

public class PlacesMgr extends AsyncTask<String, Void, String>
{
	Handler uiHandler;
	
	public PlacesMgr(Handler handler)
	{
		this.uiHandler = handler;
	}

	protected String doInBackground(String... searchTerms) 
	{
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGetter = new HttpGet("https://maps.googleapis.com/maps/api/place/search/json?location=45.52,-122.67&radius=1000&keyword=coffee&sensor=false&key=AIzaSyAAlYH3-CUofsc7dFG73vF-C3q8YQaK71k");
		String result = null;
		try 
		{
			HttpResponse response = client.execute(httpGetter);
			
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
		bundle.putString("PlacesJSON", str);
		Message msg = Message.obtain();
		msg.setData(bundle);
        msg.what = 1; //sending 1 means server call is done
        uiHandler.sendMessage(msg);
		
	}
}

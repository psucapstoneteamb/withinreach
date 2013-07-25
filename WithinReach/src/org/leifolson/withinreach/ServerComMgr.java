package org.leifolson.withinreach;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Environment;

public class ServerComMgr extends AsyncTask<String, Void, String> 
{
	Handler uiHandler;
	Context context;
	public ServerComMgr(Context context, Handler handler)
	{
		this.uiHandler = handler;
		this.context = context;
		
	}

	protected String doInBackground(String... params) 
	{
		
		
		
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(params[0]);
		HttpResponse response;
		StringBuilder stringBuilder = new StringBuilder();
		String result;
		
		try //getting the http response and building a string out of it
		{
			response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			HttpEntity entity = response.getEntity();
		    InputStream content = entity.getContent();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
		    String line;
		
		    while ((line = reader.readLine()) != null)
		    {
		        stringBuilder.append(line + "\n");
		    }
			
			
		} 
		catch (ClientProtocolException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result = stringBuilder.toString();
		


		try 
		{
			FileOutputStream fstream = context.openFileOutput("jsonResult.txt", Context.MODE_PRIVATE);
			fstream.write(result.getBytes());
			fstream.close();
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




		
		return null;
	}
	
	protected void onPostExecute(String str)
	{
		Message msg = Message.obtain();
        msg.what = 1; //sending 1 means server call is done
        uiHandler.sendMessage(msg);
		
	}

}

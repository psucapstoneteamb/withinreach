package org.leifolson.withinreach;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class WithinReachActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_within_reach);
		
		// setting up the button
		Button menuButton = (Button) findViewById(R.id.button1);
		
		// setting up the onclick listener to handle the button click
		// this is just a way to launch the menu for now

		
		menuButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				startMenu();
				
			}
		}); 
	}
	private void startMenu(){
		Intent launchMenu = new Intent(this,MenuActivity.class);
		startActivity(launchMenu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.within_reach, menu);
		return true;
	}
	
	public void onNewIntent(Intent t) //This gets called from MenuActivity when it launches the WithinReachActivity
	{
		Bundle extras = t.getExtras();
		if (extras != null)
		{
			int serverDone = extras.getInt("serverCallDone");
			if (serverDone == 1)
			{
				handleDataFile();		
			}
		}
		else
			System.out.println("EXTRAS ARE NULL");
	}
	
	public void handleDataFile()
	{
		FileInputStream fileInputStream = null;
		try
		{
			fileInputStream = openFileInput("jsonResult.txt");
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		InputStreamReader inputStreamReader = new InputStreamReader ( fileInputStream ) ;
        BufferedReader bufferedReader = new BufferedReader ( inputStreamReader ) ;
        String stringReader;
        String fullString = "";
        try 
        {
	        while ((stringReader = bufferedReader.readLine()) != null)
	        {
	        	fullString += stringReader;
	        }
	        fileInputStream.close();

        }
        catch (IOException e)
        {
        	e.printStackTrace();
        	
        }
        try 
        {
			JSONObject jsonObject = new JSONObject(fullString);
			JSONObject jsonObject2 = new JSONObject();
			
			System.out.println("JSON IS " + jsonObject.toString());

			EditText textField = (EditText) findViewById(R.id.editText1);
			textField.setText("Server Response: " + jsonObject.getJSONObject("result").getJSONObject("1").getJSONArray("coordinate").getJSONObject(0).getString("long"));
				
		}
        catch (JSONException e) 
		{

			e.printStackTrace();
		}
		
	}

}

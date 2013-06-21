package org.leifolson.withinreach;

import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;


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
				//params[0] should be the url of the json file. I used:
				// "http://withinreach.herokuapp.com/arrival/6309" as url for this test
				
				//to call this function in main thread:
				//String url = "http://withinreach.herokuapp.com/arrival/6309";
				//AsyncTask<String,Void,String> smgr = new ServerComMgr().execute(url);
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

}

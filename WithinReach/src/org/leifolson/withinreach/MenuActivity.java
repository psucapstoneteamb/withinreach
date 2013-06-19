package org.leifolson.withinreach;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

// adding as a test to see if git repo manager picks this up
public class MenuActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		// setting up the button
		Button menuButton = (Button) findViewById(R.id.menu_done_button);
		
		// setting up the onclick listener to handle the button click
		// this is just a way to launch the menu for now
		menuButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				startWithinReach();
				
			}
		});
		
	}
	
	// when the user clicks the "Done!" button, this method will be called,
	// launching the main WithinReach activity
	public void startWithinReach(){
		Intent launchWithinReach = new Intent(this, WithinReachActivity.class);
		startActivity(launchWithinReach);
	}

}

/* Author: Haneen Abu-Khater
 * Email: haneen.abukhater@gmail.com
 * Last Change: June 23, 2013
 * License:
 */
package org.leifolson.withinreach;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class HelpActivity extends Activity {
	
	//UI elements setup and definitions 
	private Button Question1Button;
	private Button Question2Button;
	private Button Question3Button;
	private Button Question4Button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		Question1Button = (Button) findViewById(R.id.button1);
		Question1Button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Question2Button = (Button) findViewById(R.id.button2);
		Question2Button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Question3Button = (Button) findViewById(R.id.button3);
		Question3Button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Question4Button = (Button) findViewById(R.id.button4);
		Question4Button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
}

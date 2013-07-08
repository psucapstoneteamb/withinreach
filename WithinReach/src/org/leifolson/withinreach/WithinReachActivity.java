package org.leifolson.withinreach;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.support.v4.app.FragmentActivity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class WithinReachActivity extends FragmentActivity {
	
	// used as a handle to the map object
	private GoogleMap mMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// inflate the UI
		setContentView(R.layout.activity_within_reach);
		
		setUpMapIfNeeded();
		setupSettingsFile();
		
	}
	
	/***
	 *  launches the MenuActivity where the user can specify:
	 *  time constraint
	 *  transport mode
	 */
	private void startMenu(){
		Intent launchMenu = new Intent(this,MenuActivity.class);
		startActivity(launchMenu);
	}

	/***
	 * this method inflates the menu UI when the user presses the hardware menu key
	 * on their android device. 
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.within_reach, menu);
		
		// make this menu item available in the action bar if API supports it
		if(Build.VERSION.SDK_INT>11){
			MenuItem settingsItem = menu.findItem(R.id.action_settings);
			settingsItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | 
										 MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		}
		return true;
	}
	

	
	// handles user selections from menu interface
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		
		if (item.getItemId() == R.id.action_settings){
		
			// this is the settings menu item
			// launch the menu activity to specify app settings
			startMenu();
			return true;
		}
		else return false;
	}
	
    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not have been
     * completely destroyed during this process (it is likely that it would only be stopped or
     * paused), {@link #onCreate(Bundle)} may not be called again so we should call this method in
     * {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
    
    private void setupSettingsFile()
    {
    	GregorianCalendar calendar = (GregorianCalendar)Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
    	JSONObject settingsJson = new JSONObject();
    	double latitude = 0;
    	double longitude = 0;
		try 
		{
			
			settingsJson.put("lat", latitude);
			settingsJson.put("long", longitude);
			settingsJson.put("time", 200);
			settingsJson.put("day", day);
			settingsJson.put("month", month);
			settingsJson.put("year", year);
			settingsJson.put("mode", 1);
			settingsJson.put("constraint", 0);
		} 
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileOutputStream fstream;
		try 
		{
			fstream = openFileOutput("settingsJson.txt", Context.MODE_PRIVATE);

			fstream.write(settingsJson.toString().getBytes());
			
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
	
			String result = jsonObject.getJSONObject("result").getJSONObject("1").getJSONArray("coordinate").getJSONObject(0).getString("lat");
			System.out.println(result);
				
		}
        catch (JSONException e) 
		{

			e.printStackTrace();
		}
        
    
		
	}

}

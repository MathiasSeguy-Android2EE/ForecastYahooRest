package com.android2ee.formation.restservice.sax.forecastyahoo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android2ee.formation.restservice.sax.forecastyahoo.arrayadpater.ForecastArrayAdapter;
import com.android2ee.formation.restservice.sax.forecastyahoo.model.YahooForcast;
import com.android2ee.formation.restservice.sax.forecastyahoo.service.ForecastCallBack;
import com.android2ee.formation.restservice.sax.forecastyahoo.service.ForecastService;

public class MainActivity extends Activity {
	/******************************************************************************************/
	/** Attributes **************************************************************************/
	/******************************************************************************************/

	/**
	 * The ArrayAdapter to use
	 */
	private ForecastArrayAdapter arrayAdapter;
	/**
	 * The ListView
	 */
	private ListView listView;
	/**
	 * The displayed object
	 */
	private ArrayList<YahooForcast> forecasts;
	/**
	 * The sharedPreference that listening for network connectivity changes
	 */
	SharedPreferences prefs = null;
	/**
	 * The SharedPreferences listener
	 */
	SharedPreferences.OnSharedPreferenceChangeListener prefsListener = null;
	/**
	 * The connectivity status
	 */
	boolean isConnected;

	/******************************************************************************************/
	/** Managing Life Cycle **************************************************************************/
	/******************************************************************************************/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		forecasts = new ArrayList<YahooForcast>();
		manageConnectivityStatus();
		if (savedInstanceState == null || !savedInstanceState.containsKey("forcasts_list")) {
			loadWeatherForecast();
		} else {
			updateGuiWithForecast();
		}
	}

	/**
	 * The call to the service to load weather forecast
	 * Iff there is network
	 */
	private void loadWeatherForecast() {
		// if connected ask for the weather
		if (isConnected) {
			ForecastService.getInstance().getForecast(new ForecastCallBack() {
				@Override
				public void forecastLoaded(List<YahooForcast> forecasts) {
					forecastLoadedGuiUpdate(forecasts);
				}
			});
		} else {
			// if not connected: say something to your user
			Toast.makeText(this, getString(R.string.no_network_message), Toast.LENGTH_LONG).show();
			// Hide the progressBar
			findViewById(R.id.progressBar1).setVisibility(View.GONE);
			findViewById(R.id.txvNoNetwork).setVisibility(View.VISIBLE);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		forecasts.clear();
		for (Parcelable parcel : savedInstanceState.getParcelableArrayList("forcasts_list")) {
			forecasts.add((YahooForcast) parcel);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList("forcasts_list", forecasts);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		prefs.unregisterOnSharedPreferenceChangeListener(prefsListener);
	}

	/******************************************************************************************/
	/** Managing connectivity **************************************************************************/
	/******************************************************************************************/
	/**
	 * Manage your connectivity status
	 */
	private void manageConnectivityStatus() {
		// Add a listener on the sharedPreference that is knowing the network state
		prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		// first find your connection status using the broadcast receiver job
		if (!prefs.contains(getString(R.string.has_network))) {
			initConnectivityStatus();
		}
		isConnected = prefs.getBoolean(getString(R.string.has_network), true);
		Log.e("MainActivity", "isConnected : " + isConnected);
		prefsListener = new SharedPreferences.OnSharedPreferenceChangeListener()
		{
			@Override
			public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
				// try to reload the weather
				connectivityChange();
			}
		};
		prefs.registerOnSharedPreferenceChangeListener(prefsListener);
	}

	/**
	 * When the activity is fisrt launch we need to initialize that values
	 */
	private void initConnectivityStatus() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		boolean isConnected = false;
		boolean isWifi = false;
		if (null != networkInfo) {
			isConnected = networkInfo.isConnected();
			Log.e("MainActivity", "isConnected : " + isConnected);
			isWifi = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
			Log.e("MainActivity", "isWifi : " + isWifi);
		}
		// update the preferences
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(getString(R.string.has_network), isConnected);
		editor.putBoolean(getString(R.string.has_wifi), isWifi);
		editor.commit();
	}

	/**
	 * Called when the connectivity status changes
	 */
	private void connectivityChange() {
		// first if you have already load the data no need to reload them
		if (isConnected == false) {
			// if the network is still not connected or has been disconnected, don't load
			if (prefs.getBoolean(getString(R.string.has_network), false) == true) {
				isConnected = true;
				loadWeatherForecast();
			}
		}
	}

	/******************************************************************************************/
	/** Loading Forecast **************************************************************************/
	/******************************************************************************************/
	/**
	 * Update the Gui using the List of Forecast retrieve by the service
	 * 
	 * @param forecasts
	 */
	private void forecastLoadedGuiUpdate(List<YahooForcast> forecasts) {
		// Instanciate the listView
		this.forecasts = (ArrayList<YahooForcast>) forecasts;
		if (this.forecasts==null||this.forecasts.size() == 0) {

			// if not connected: say something to your user
			Toast.makeText(this, getString(R.string.no_network_message), Toast.LENGTH_LONG).show();
			// Hide the progressBar
			findViewById(R.id.progressBar1).setVisibility(View.GONE);
			((TextView)findViewById(R.id.txvNoNetwork)).setText(getString(R.string.no_data_message));
			findViewById(R.id.txvNoNetwork).setVisibility(View.VISIBLE);

		} else {
			updateGuiWithForecast();
		}
	}

	/**
	 * Update the gui with the list of forecast
	 */
	private void updateGuiWithForecast() {
		// Instanciate the listView
		this.forecasts = (ArrayList<YahooForcast>) forecasts;
		listView = (ListView) findViewById(R.id.myListView);
		arrayAdapter = new ForecastArrayAdapter(this, this.forecasts);
		listView.setAdapter(arrayAdapter);
		// Hide the progressBar
		findViewById(R.id.progressBar1).setVisibility(View.GONE);
		// Set the listView Visible
		listView.setVisibility(View.VISIBLE);

	}

	/******************************************************************************************/
	/** Managing Menu **************************************************************************/
	/******************************************************************************************/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

package com.android2ee.formation.restservice.sax.forecastyahoo.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android2ee.formation.restservice.sax.forecastyahoo.MotherActivity;
import com.android2ee.formation.restservice.sax.forecastyahoo.MyApplication;
import com.android2ee.formation.restservice.sax.forecastyahoo.R;
import com.android2ee.formation.restservice.sax.forecastyahoo.service.ForecastCallBack;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.interfaces.ConnectivityIsBackIntf;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.model.YahooForcast;
import com.android2ee.formation.restservice.sax.forecastyahoo.view.arrayadpater.ForecastArrayAdapter;

public class MainActivity extends MotherActivity implements ConnectivityIsBackIntf,
		SwipeRefreshLayout.OnRefreshListener, ForecastCallBack {
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
	private ListView listView = null;
	/**
	 * The ImageView that displays the yahooLogo
	 */
	private ImageView imvYahooLogo;
	/**
	 * The last update textview displaying when the data was last updated
	 */
	private TextView txvLastUpdate;
	/**
	 * The SwipeLayout
	 */
	private SwipeRefreshLayout swipeLayout = null;
	/**
	 * The displayed object
	 */
	private ArrayList<YahooForcast> forecasts;

	/**
	 * The connectivity status
	 */
	private boolean isConnected;
	/**
	 * Data are loaded
	 */
	private boolean dataLoaded = false;
	/**
	 * When refreshing the data
	 */
	private boolean isRefreshing = false;
	/**
	 * The textview that displays the NoConnectionMessage
	 */
	private TextView txvNoNetwork;

	/******************************************************************************************/
	/** Managing Life Cycle **************************************************************************/
	/******************************************************************************************/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		forecasts = new ArrayList<YahooForcast>();
		// add the swipe to refresh feature
		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		Log.e("MainActivity","onCreate swipeLyout "+swipeLayout);
		swipeLayout.setOnRefreshListener(this);
		swipeLayout.setColorSchemeResources(R.color.blue_pure, R.color.blue_pure_1, R.color.blue_pure_2,
				R.color.blue_pure_3);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		// no need to use such a method, the serviceData is caching the data dude
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// no need to use such a method, the serviceData is caching the data dude
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		Log.e("MainActivity", "onPause");
		MyApplication.instance.unregisterAsConnectivityBackListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();		
		// display the connection status to the user if no connected
		isConnected = MyApplication.instance.isConnected();
		MyApplication.instance.registerAsConnectivityBackListener(this);
		Log.v("MainActivity", " isConnected=" + isConnected);
		manageNoConnectionMessage();
		// then load the data
		loadForecast();
	}

	/******************************************************************************************/
	/** Managing connectivity **************************************************************************/
	/******************************************************************************************/
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.android2ee.formation.restservice.sax.forecastyahoo.transverse.interfaces.
	 * ConnectivityIsBackIntf#connectivityIsBack(boolean, int)
	 */
	@Override
	public void connectivityIsBack(boolean isWifi, int telephonyType) {
		// Ok so the connectivity is back, we should load the data
		if (!isConnected&&MyApplication.instance.isConnected()) {
			//because that means the connectivity status has changed from not connection to connected
			if (!dataLoaded) {
				Log.e("MainActivity", "connectivityIsBack called asking to load data");
				MyApplication.instance.getServiceManager().getForecastServiceData().getForecast(this);
			}
		}
		//change the connection status
		isConnected=MyApplication.instance.isConnected();
		// else do nothing data already loaded

		// then insure the NoNetwork error message is hidden
		manageNoConnectionMessage();
	}

	/******************************************************************************************/
	/** Loading Forecast **************************************************************************/
	/******************************************************************************************/

	/**
	 * Ask to load the data
	 * The connection state is managed by the service, activity don't have to deal with that when
	 * requesting data
	 */
	private void loadForecast() {
		Log.e("MainActivity", "loadForecast called asking to load data");
		// Ask for the weather what ever the connection is
		MyApplication.instance.getServiceManager().getForecastServiceData().getForecast(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.android2ee.formation.restservice.sax.forecastyahoo.service.ForecastCallBack#forecastLoaded
	 * (java.util.List)
	 */
	public void forecastLoaded(List<YahooForcast> forecasts) {
		// first check if the return list is null or empty
		if (forecasts == null || forecasts.size() == 0) {
			//do nothing because it only occurs at the first launch when retrieving data from the database which is empty
			//ExceptionManager.displayAnError(getString(R.string.no_data_message));
		} else {
			// else copy paste the data in this.forecast
			this.forecasts.clear();
			for (YahooForcast forcast : forecasts) {
				this.forecasts.add(forcast);
			}
			// then update the view
			updateGui();
		}
		// data are refreshed
		refreshed();
	}

	/**
	 * Update the gui with the list of forecast
	 */
	private void updateGui() {
		// Instanciate the listView
		if (listView == null) {
			listView = (ListView) findViewById(R.id.myListView);
			View viewFooter = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
			imvYahooLogo = (ImageView) viewFooter.findViewById(R.id.imv_yahoo_logo);
			imvYahooLogo.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					yahooRequirement();
				}

			});
			View viewHeader = LayoutInflater.from(this).inflate(R.layout.list_header, null);
			txvLastUpdate = (TextView) viewHeader.findViewById(R.id.txv_last_update);
			txvLastUpdate.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					onRefresh();
					Toast.makeText(MainActivity.this, getString(R.string.txv_last_update_swipe), Toast.LENGTH_LONG)
							.show();
				}

			});
			listView.addHeaderView(viewHeader);
			listView.addFooterView(viewFooter);
			arrayAdapter = new ForecastArrayAdapter(this, this.forecasts);
			listView.setAdapter(arrayAdapter);
		}
		// Hide the noNetworkMessage
		manageNoConnectionMessage();
		// Set the listView Visible
		listView.setVisibility(View.VISIBLE);
		arrayAdapter.notifyDataSetChanged();
		// update the last update textView
		SharedPreferences prefs = MyApplication.instance.getSharedPreferences(MyApplication.CONNECTIVITY_STATUS,
				Context.MODE_PRIVATE);
		txvLastUpdate.setText(getString(R.string.txv_last_update,
				prefs.getString(MyApplication.instance.getString(R.string.last_update), "null")));
		// ok data loaded
		dataLoaded = true;

	}

	/******************************************************************************************/
	/** Manage the No connection and no datamessage **************************************************************************/
	/******************************************************************************************/

	/**
	 * Depending on the state of the connectivity, display or hide the connection message
	 */
	private void manageNoConnectionMessage() {
		// instanciate if noit already instanciate
		if (txvNoNetwork == null) {
			txvNoNetwork = (TextView) findViewById(R.id.txvNoNetwork);
		}
		// then display or not the message
		if (!isConnected) {
			showNoConnectionMessage();
		} else {
			hideNoConnectionMessage();
		}
	}

	/**
	 * Show the no connection message
	 */
	private void showNoConnectionMessage() {
		// if not connected: say something to your user
		Toast.makeText(this, getString(R.string.no_network_message), Toast.LENGTH_LONG).show();
		// data are refreshed in a way...
		refreshed();
		// display the no connection message
		txvNoNetwork.setVisibility(View.VISIBLE);
	}

	/**
	 * Hide the no connection message
	 */
	private void hideNoConnectionMessage() {
		txvNoNetwork.setVisibility(View.GONE);
	}

	/******************************************************************************************/
	/** Manage the Yahoo Requiremenets **************************************************************************/
	/******************************************************************************************/

	/**
	 * Open the YahooRequirement URL
	 */
	private void yahooRequirement() {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(getString(R.string.yahoo_requirment)));
		startActivity(i);
	}

	/******************************************************************************************/
	/** Managing SwipeToRefresh listener **************************************************************************/
	/******************************************************************************************/

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener#onRefresh()
	 */
	@Override
	public void onRefresh() {
		// call service updater
		if (!isRefreshing) {
			swipeLayout.setRefreshing(true);
			isRefreshing = true;
			if (isConnected) {
				MyApplication.instance.getServiceManager().getForecastServiceUpdater().updateForecastFromServer(this);
			} else {
				// no connection dude
				showNoConnectionMessage();
			}
		}
	}

	/**
	 * To be called when the data are refreshed
	 */
	private void refreshed() {
		// You have finished to refresh
		isRefreshing = false;
		swipeLayout.setRefreshing(false);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent browserIntent;
		switch (item.getItemId()) {
		case R.id.action_show_android2ee:
			// open browser on the web pages
			browserIntent = new Intent("android.intent.action.VIEW");
			// if (getResources().getConfiguration().locale.getDisplayName().contains("fr")) {
			// open the browser on android2ee
			browserIntent.setData(Uri.parse(getResources().getString(R.string.android2ee_url)));
			// } else {
			// // open the browser on android2ee english version
			// browserIntent.setData(Uri.parse("http://www.android2ee.com/en"));
			// }
			startActivity(browserIntent);
			return true;
		case R.id.action_training_android2ee:
			// open browser on the web pages
			browserIntent = new Intent("android.intent.action.VIEW");
			// if (getResources().getConfiguration().locale.getDisplayName().contains("fr")) {
			// open the browser on android2ee
			browserIntent.setData(Uri.parse(getResources().getString(R.string.android2ee_url_training)));
			// } else {
			// // open the browser on android2ee english version
			// browserIntent.setData(Uri.parse("http://www.android2ee.com/en"));
			// }
			startActivity(browserIntent);
			return true;
		case R.id.action_show_mathias:
			// open browser on the web pages
			browserIntent = new Intent("android.intent.action.VIEW");
			browserIntent.setData(Uri.parse(getResources().getString(R.string.mse_dvp_url)));
			startActivity(browserIntent);
			return true;
		case R.id.action_mail_mathias:
			// load string for email:
			String subject = getResources().getString(R.string.mail_subject);
			String body = getResources().getString(R.string.mail_body);
			// send an email
			final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			emailIntent.setType("plain/text");
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
					new String[] { getResources().getString(R.string.mse_email) });
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
			startActivity(Intent.createChooser(emailIntent, "Send mail..."));
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

}

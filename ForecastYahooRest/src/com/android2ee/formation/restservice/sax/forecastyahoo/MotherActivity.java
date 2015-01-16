package com.android2ee.formation.restservice.sax.forecastyahoo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.exceptions.ExceptionManager;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.interfaces.ConnectivityIsBackIntf;
import com.crashlytics.android.Crashlytics;

public class MotherActivity extends ActionBarActivity implements ConnectivityIsBackIntf {
	/**
	 * The connectivity status
	 */
	protected boolean isConnected;
	/**
	 * The action Bar
	 */
	private ActionBar actionBar;
	/**
	 * The Textview that displays the NoConnectionMessage
	 */
	private TextView txvNoNetwork;
	/******************************************************************************************/
	/** Managing LifeCycle **************************************************************************/
	/******************************************************************************************/

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Crashlytics.start(this);
		actionBar = getSupportActionBar();
		actionBar.setTitle("A REST Architecture");
		actionBar.setSubtitle("By Android2EE");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		MyApplication.instance.onResumeActivity();
		// register the broadcast receiver that listen to the Error_Intent sends by the
		// ExceptionManager
		registerReceiver(receiver, new IntentFilter(ExceptionManager.Error_Intent_ACTION));
		// display the connection status to the user if no connected
		isConnected = MyApplication.instance.isConnected();
		MyApplication.instance.registerAsConnectivityBackListener(this);
		Log.v("MotherActivity", " isConnected=" + isConnected);
		// Manage the connection message
		manageNoConnectionMessage();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		MyApplication.instance.onPauseActivity();
		// unregister the broadcast receiver that listen to the Error_Intent sends by the
		// ExceptionManager
		unregisterReceiver(receiver);
		//unregister the connection listener
		MyApplication.instance.unregisterAsConnectivityBackListener(this);
	}

	/******************************************************************************************/
	/** Managing the Exceptions **************************************************************************/
	/******************************************************************************************/
	/**
	 * The txvException
	 */
	private TextView txvException;
	/**
	 * The click to dismiss string
	 */
	private String strClickToDismiss;
	/**
	 * The broadcast receiver that listen to the Error_Intent sends by the ExceptionManager
	 */
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			displayException(intent.getStringExtra(ExceptionManager.Error_Intent_MESSAGE));
		}
	};

	/**
	 * Display the Exception carried by the Intent
	 * 
	 * @param String
	 *            the error message to display
	 */
	private void displayException(String errorMessage) {
		// Display the error to your user
		// To do so, every layout of your Activity or Fragment should include the
		// exceptionLayout at it top level
		if (txvException == null) {
			// instantiate the TextView and the Drawable
			txvException = (TextView) findViewById(R.id.txv_exception_message);
			strClickToDismiss = getString(R.string.txv_exception_clicktodismiss);
			txvException.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					hideTxvException();
				}
			});
		}
		if (errorMessage == null) {
			txvException.setText(getString(R.string.no_error_message));
		} else {
			txvException.setText(errorMessage + strClickToDismiss);
		}
		txvException.setVisibility(View.VISIBLE);
	}

	/**
	 * Hide the TextView
	 */
	private void hideTxvException() {
		txvException.setVisibility(View.GONE);
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
		// change the connection status
		isConnected = MyApplication.instance.isConnected();
		// then insure the NoNetwork error message is hidden
		manageNoConnectionMessage();
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
	/** Managing Menu **************************************************************************/
	/******************************************************************************************/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mother, menu);
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

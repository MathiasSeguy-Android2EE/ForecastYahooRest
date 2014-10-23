package com.android2ee.formation.restservice.sax.forecastyahoo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.exceptions.ExceptionManager;

public class MotherActivity extends ActionBarActivity {
	/**
	 * The action Bar
	 */
	private ActionBar actionBar;

	/* (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
	private  String strClickToDismiss;
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
	 * @param String the error message to display
	 */
	private void displayException(String errorMessage) {
		// Display the error to your user
		// To do so, every layout of your Activity or Fragment should include the
		// exceptionLayout at it top level
		if(txvException==null) {
			//instantiate the TextView and the Drawable
			txvException=(TextView) findViewById(R.id.txv_exception_message);
			strClickToDismiss=getString(R.string.txv_exception_clicktodismiss);
			txvException.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					hideTxvException();
				}
			});
		}
		if(errorMessage==null) {
			txvException.setText(getString(R.string.no_error_message));
		}else {
			txvException.setText(errorMessage+strClickToDismiss);
		}
		txvException.setVisibility(View.VISIBLE);
	}

	/**
	 * Hide the TextView
	 */
	private void hideTxvException() {
		txvException.setVisibility(View.GONE);
	}
}

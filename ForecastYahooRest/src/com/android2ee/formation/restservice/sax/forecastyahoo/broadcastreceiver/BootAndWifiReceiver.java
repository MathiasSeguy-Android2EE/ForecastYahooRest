/**<ul>
 * <li>project-jcertifmobile-app</li>
 * <li>com.jcertif.android.braodcastreceiver</li>
 * <li>1 juin 2012</li>
 * 
 * <li>======================================================</li>
 *
 * <li>Projet : JCertif Africa 2012 Project</li>
 * <li>Produit par MSE.</li>
 */
package com.android2ee.formation.restservice.sax.forecastyahoo.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android2ee.formation.restservice.sax.forecastyahoo.R;


/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to:
 *        <ul>
 *        <li></li>
 *        </ul>
 */
public class BootAndWifiReceiver extends BroadcastReceiver {


	/*
	 * (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
	 * android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("BootAndWifiReceiver", "Intent received :" + intent.getAction());
		// Here we are because we receive either the boot completed event
		// either the connection changed event
		// either the wifi state changed event
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		boolean isConnected = false;
		boolean isWifi = false;
		if (null != networkInfo) {
			isConnected = networkInfo.isConnected();
			isWifi = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
		}
		// update the preferences
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(context.getString(R.string.has_network), isConnected);
		editor.putBoolean(context.getString(R.string.has_wifi), isWifi);
		editor.commit();
	}
}

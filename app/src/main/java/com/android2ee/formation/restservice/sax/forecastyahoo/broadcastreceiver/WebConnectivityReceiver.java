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
import android.util.Log;

import com.android2ee.formation.restservice.sax.forecastyahoo.MyApplication;


/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to:
 *        <ul>
 *        <li></li>
 *        </ul>
 */
public class WebConnectivityReceiver extends BroadcastReceiver {


	

	/*
	 * (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
	 * android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("WebConnectivityReceiver", "Intent received :" + intent.getAction());
		MyApplication.instance.manageConnectivtyState();
		 
	}
}

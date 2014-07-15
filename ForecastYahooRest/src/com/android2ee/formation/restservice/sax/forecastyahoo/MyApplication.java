/**<ul>
 * <li>ForecastRestYahooSax</li>
 * <li>com.android2ee.formation.restservice.sax.forecastyahoo</li>
 * <li>28 mai 2014</li>
 * 
 * <li>======================================================</li>
 *
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 *
 /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br> 
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 *  Belongs to <strong>Mathias Seguy</strong></br>
 ****************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * 
 * *****************************************************************************************************************</br>
 *  Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 *  Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br> 
 *  Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 *  <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */
package com.android2ee.formation.restservice.sax.forecastyahoo;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android2ee.formation.restservice.sax.forecastyahoo.service.ServiceManager;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.interfaces.ConnectivityIsBackIntf;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to:
 *        <ul>
 *        <li></li>
 *        </ul>
 */
public class MyApplication extends Application {
	/**
	 * The constant to obtain the sharedPreference for the connectivity status
	 */
	public static final String CONNECTIVITY_STATUS = "ConnectivityStatus";

	/**
	 * The instance
	 */
	public static MyApplication instance;

	/**
	 * The service manager used to manage the services
	 */
	private ServiceManager serviceManager;

	/**
	 * To know if the service manager already exist
	 */
	private boolean servcieManagerAlreadyExist = false;

	/******************************************************************************************/
	/** Managing LifeCycle **************************************************************************/
	/******************************************************************************************/

	/**
	 * 
	 */
	public MyApplication() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		initializeServiceKiller();
	}

	/******************************************************************************************/
	/** Managing Connectivity state **************************************************************************/
	/******************************************************************************************/
	/**
	 * To know if there is a connection (wifi or GPRS)
	 */
	private boolean isConnected = false;
	/**
	 * To know if the connection is Wifi
	 */
	private boolean isWifi = false;
	/**
	 * To know the GRPS connectivity
	 */
	private int telephonyType = 0;
	/**
	 * To know if the connectivity state has been initialized
	 */
	private boolean isConnectivityStateInitialized = false;
	/**
	 * The list of interfaces that want to be notify when the connectivity is back
	 */
	private ArrayList<ConnectivityIsBackIntf> connectivityIsBackListeners = null;
	/**
	 * To know when to notify the connectivityIsBackListeners
	 */
	private boolean notifyConnIsBackListeners = false;

	/**
	 * Manage the connectivity state of the device
	 */
	public void manageConnectivtyState() {
		// Here we are because we receive either the boot completed event
		// either the connection changed event
		// either the wifi state changed event
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		// update the preferences
		SharedPreferences prefs = getSharedPreferences(MyApplication.CONNECTIVITY_STATUS, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		if (null == networkInfo) {
			// This is the airplane mode
			isConnected = false;
			isWifi = false;
			telephonyType = 0;
		} else {
			switch (networkInfo.getType()) {
			case ConnectivityManager.TYPE_WIFI:
				if (isConnected == false) {
					// then it means the connectivity is back
					// so we need to notify about that
					notifyConnIsBackListeners = true;
				}
				isConnected = true;
				isWifi = true;
				break;
			case ConnectivityManager.TYPE_MOBILE:
				if (isConnected == false) {
					// then it means the connectivity is back
					// so we need to notify about that
					notifyConnIsBackListeners = true;
				}
				isConnected = true;
				telephonyType = telephonyManager.getNetworkType();
				// For information TelephonyType is one of the following
				// switch (telephonyManager.getNetworkType()) {
				// case TelephonyManager.NETWORK_TYPE_LTE:// 150Mb/s
				// case TelephonyManager.NETWORK_TYPE_HSDPA:// 42Mb/s
				// break;
				// case TelephonyManager.NETWORK_TYPE_EDGE:// 215kb/s
				// break;
				// case TelephonyManager.NETWORK_TYPE_GPRS:// 45kb/s
				// break;
				// default:
				// break;
				// }
				break;
			default:
				break;
			}
		}
		editor.putInt(getString(R.string.telephonyType), telephonyType);
		editor.putBoolean(getString(R.string.has_network), isConnected);
		editor.putBoolean(getString(R.string.networkStateInitialized), true);
		editor.putBoolean(getString(R.string.has_wifi), isWifi);
		editor.commit();
		if (notifyConnIsBackListeners) {
			notifyConnectivityIsBack();
		}
		Log.e("MyApplication", "manageConnectivtyState called and return isConnected=" + isConnected + ", isWifi="
				+ isWifi + ", telephonyType=" + telephonyType);
	}

	/**
	 * Call this method when you want to be notify of the connectivity back
	 * It means you were offline and then connectivity is back and you want to be notify of that
	 * fact
	 * Should be called in onResume
	 * 
	 * @param conBackListener
	 */
	public void registerAsConnectivityBackListener(ConnectivityIsBackIntf conBackListener) {
		if (null == connectivityIsBackListeners) {
			connectivityIsBackListeners = new ArrayList<ConnectivityIsBackIntf>();
		}
		connectivityIsBackListeners.add(conBackListener);
	}

	/**
	 * Call this method in onPause if you had called registerAsConnectivityBackListener in onResume
	 * 
	 * @param conBackListener
	 */
	public void unregisterAsConnectivityBackListener(ConnectivityIsBackIntf conBackListener) {
		if (null != connectivityIsBackListeners) {
			connectivityIsBackListeners.remove(conBackListener);
			if (connectivityIsBackListeners.size() == 0) {
				connectivityIsBackListeners = null;
			}
		}
	}

	/**
	 * This method is called when we switch from no connectivity to connected to the internet
	 */
	private void notifyConnectivityIsBack() {
		// notify the listeners (if there is some because this method can be called even if no
		// activity alived)
		if (connectivityIsBackListeners != null) {
			for (ConnectivityIsBackIntf listener : connectivityIsBackListeners) {
				listener.connectivityIsBack(isWifi, telephonyType);
			}
		}
		// The job is done, go back to false
		notifyConnIsBackListeners = false;
	}

	/**
	 * Return if the device is connected to internet
	 * 
	 * @return the isConnected
	 */
	public final boolean isConnected() {
		initializedConnStateIfNecessary();
		return isConnected;
	}

	/**
	 * Return if the device is connected to internet using WIFI
	 * 
	 * @return the isWifi
	 */
	public final boolean isWifi() {
		initializedConnStateIfNecessary();
		return isWifi;
	}

	/**
	 * Return the connectivity type
	 * (NETWORK_TYPE_LTE,NETWORK_TYPE_HSDPA,NETWORK_TYPE_EDGE,NETWORK_TYPE_GPRS)
	 * when the device is connected to internet using GPRS
	 * 
	 * @return the telephonyType
	 */
	public final int getTelephonyType() {
		initializedConnStateIfNecessary();
		return telephonyType;
	}

	/**
	 * Check if the connectivity state has been initialized
	 * If not, it initializes them
	 */
	private void initializedConnStateIfNecessary() {
		if (!isConnectivityStateInitialized) {
			SharedPreferences prefs = getSharedPreferences(MyApplication.CONNECTIVITY_STATUS, Context.MODE_PRIVATE);
			// first insure the manageConnectivtyState has been called once (I mean once in the
			// whole device's life)
			if (!prefs.getBoolean(getString(R.string.networkStateInitialized), false)) {
				manageConnectivtyState();
			}
			isWifi = prefs.getBoolean(getString(R.string.has_wifi), isWifi);
			isConnected = prefs.getBoolean(getString(R.string.has_network), isConnected);
			telephonyType = prefs.getInt(getString(R.string.telephonyType), telephonyType);
		}
	}

	/******************************************************************************************/
	/** Managing ServiceManager **************************************************************************/
	/******************************************************************************************/
	/**
	 * @return the serviceManager
	 */
	public final ServiceManager getServiceManager() {
		if (null == serviceManager) {
			serviceManager = new ServiceManager(this);
			servcieManagerAlreadyExist = true;
		}
		return serviceManager;
	}

	/**
	 * @return true if the ServiceManager is already instantiate
	 */
	public final boolean serviceManagerAlreadyExist() {
		return servcieManagerAlreadyExist;
	}

	/******************************************************************************************/
	/** Managing destruction **************************************************************************/
	/******************************************************************************************/
	//Listening for activities life cycle to tricker the serviceManager death
	/**
	 * The AtomicBoolean to know if there is an active activity
	 */
	private AtomicBoolean isActivityAlive=new AtomicBoolean(false);
	/**
	 * To be called by activities when they go in their onStop method
	 */
	public void onPauseActivity() {
		isActivityAlive.set(false);	
		// launch the Runnable in 2 seconds
		mServiceKillerHandler.postDelayed(mServiceKiller, 1000);
	}
	/**
	 * To be called by activities when they go in their onResume method
	 */
	public void onResumeActivity() {
		isActivityAlive.set(true);
	}
	//The 1 second pattern to kill activityManager in 1 second
	/** * The Runnable that will look if there are no activity alive and launch the serviceManager death*/
	Runnable mServiceKiller;
	/** * The handler that manages the runnable */
	Handler mServiceKillerHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			//Kill serviceManager
			killServiceManager();
		}
		
	};
	/** * initialize the runnable */
	private void initializeServiceKiller() {
		mServiceKiller = new Runnable() {
			@Override
			public void run() {
				if (!isActivityAlive.get()) {
					//one second later still no activity alive, so kill ServiceManager
					mServiceKillerHandler.dispatchMessage(mServiceKillerHandler.obtainMessage());
				}
			}
		};
		
	}
	//Now the Killing method
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Application#onLowMemory()
	 */
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		killServiceManager();
	}

	/**
	 * Kill the service manager and all the services managed by it
	 */
	private void killServiceManager() {
		Log.e("MyApplication", "killServiceManager is called");
		if (null != serviceManager) {
			serviceManager.unbindAndDie();
			serviceManager=null;
			servcieManagerAlreadyExist=false;
		}
	}
}

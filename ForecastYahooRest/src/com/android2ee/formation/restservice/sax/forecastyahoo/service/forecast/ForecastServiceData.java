/**<ul>
 * <li>ForecastRestYahooSax</li>
 * <li>com.android2ee.formation.restservice.sax.forecastyahoo.service</li>
 * <li>22 nov. 2013</li>
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
package com.android2ee.formation.restservice.sax.forecastyahoo.service.forecast;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android2ee.formation.restservice.sax.forecastyahoo.MyApplication;
import com.android2ee.formation.restservice.sax.forecastyahoo.R;
import com.android2ee.formation.restservice.sax.forecastyahoo.dao.forecast.ForecastDAO;
import com.android2ee.formation.restservice.sax.forecastyahoo.service.ServiceManager;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.exceptions.ExceptionManaged;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.exceptions.ExceptionManager;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.model.YahooForcast;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to load the data from database and ask the serviceupdater to update (if
 *        needed)
 */
public class ForecastServiceData {

	/******************************************************************************************/
	/** Attributes **************************************************************************/
	/******************************************************************************************/

	/**
	 * The forecasts to display (the cach)
	 */
	private List<YahooForcast> forecasts = null;
	/**
	 * The callBack to update activity
	 * To understand what a weakRefrence
	 * is:https://weblogs.java.net/blog/2006/05/04/understanding-weak-references
	 */
	private WeakReference<ForecastCallBack> callback = null;
	/**
	 * The Dao
	 */
	private ForecastDAO forcastDao = null;
	/**
	 * The date parser used to set the last update date format in the preference
	 */
	public SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
	/**
	 * The woeid of the city corresponding to the forecast managed
	 */
	private String woeid;
	/**
	 * To know if tha data has to be reloaded
	 */
	private boolean reload = true;

	/******************************************************************************************/
	/** Constructors **************************************************************************/
	/******************************************************************************************/

	/**
	 * Constructor
	 */
	public ForecastServiceData(ServiceManager srvManager) {
		// NOthing to initialize
		// the parameter is to ensure only srvManager cant create it
	}

	/******************************************************************************************/
	/** Public methods **************************************************************************/
	/******************************************************************************************/

	/**
	 * Return the forecast
	 * 
	 * @param callback
	 *            The callback to use to deliver the data when data loaded
	 * @param woeid
	 *            The id of the city associated with the forecasts
	 */
	public void getForecast(ForecastCallBack callback, String woeid) {
		Log.e("ForecastServiceData", "getForecast called with woeid=" + woeid);
		this.callback = new WeakReference<ForecastCallBack>(callback);
		reload = false;
		if (this.woeid != woeid) {
			this.woeid = woeid;
			reload = true;
		}
		// use the caching mechanism
		if (forecasts != null && !reload) {
			callback.forecastLoaded(forecasts);
		} else {
			// then link the Handler with the handler of the runnable
			if (daoCallRunnable.daoCallHandler == null) {
				daoCallRunnable.daoCallHandler = daoCallHandler;
			}
			// then launch it
			MyApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(daoCallRunnable);
		}
	}

	/******************************************************************************************/
	/** Updating data from D.A.O **************************************************************************/
	/******************************************************************************************/

	/**
	 * The runnable to execute when requesting update from the server
	 */
	private DaoLoadRunnable daoCallRunnable = new DaoLoadRunnable();

	/**
	 * @author Mathias Seguy (Android2EE)
	 * @goals
	 *        This class aims to implements a Runnable with an Handler
	 */
	private class DaoLoadRunnable implements Runnable {
		/**
		 * The handler to use to communicate outside the runnable
		 */
		public Handler daoCallHandler = null;

		@Override
		public void run() {
			// Do the rest http call
			// Parse the element
			// store the data in DAO
			forcastDao = new ForecastDAO();
			forecasts = forcastDao.loadAll(woeid);
			forcastDao = null;
			daoCallHandler.sendMessage(daoCallHandler.obtainMessage());
		}

	}

	/**
	 * The handler awoke when the Runnable has finished it's execution
	 */
	private Handler daoCallHandler = new Handler() {
		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			returnForecast();
		}

	};

	/**
	 * Called when the forecast are built
	 * Return that list to the calling Activity using the ForecastCallBack
	 */
	private void returnForecast() {
		if (callback != null) {
			// use the callback to prevent the client
			if (callback.get() != null) {
				// yep, we use a weakReference
				callback.get().forecastLoaded(forecasts);
			}
			if (forecasts.isEmpty()&&reload) {
				updateForecastRequest();
			} else {
				// then ask the serviceupdater to update data
				// but update only if one day of difference between the last update and now is more
				// than
				// one day
				SharedPreferences prefs = MyApplication.instance.getSharedPreferences(
						MyApplication.CONNECTIVITY_STATUS, Context.MODE_PRIVATE);
				String strLastUpdate = prefs.getString(MyApplication.instance.getString(R.string.last_update), "");
				Log.e("ForecastServiceData", "strLastUpdate " + strLastUpdate);
				try {
					// empty data base case and empty SharedPreference
					if (strLastUpdate.equals("")) {
						updateForecastRequest();
					} else {
						// current case
						Date lastUpdate = sdf.parse(strLastUpdate);
						if (new Date().getTime() - lastUpdate.getTime() > 1000 * 60 * 60 * 24) {
							// if the last update was one day ago, then make an automatic update
							updateForecastRequest();
						}
					}
				} catch (ParseException e) {
					ExceptionManager.manage(new ExceptionManaged(this.getClass(), R.string.exc_date_parsing, e));
				}
			}
		}
	}

	/******************************************************************************************/
	/** Ask for Back end server Synchronization using ServiceUpdater ***************************/
	/******************************************************************************************/

	/**
	 * Call the ForecastServiceUpdater to update the data from the web
	 */
	private void updateForecastRequest() {
		MyApplication.instance.getServiceManager().getForecastServiceUpdater()
				.updateForecastFromServer(new ForecastCallBack() {
					@Override
					public void forecastLoaded(List<YahooForcast> forecasts) {
						forecastUpdatedFromServiceUpdater(forecasts);
					}
				}, woeid);
	}

	/**
	 * Called when the forecast are updated from the service updater (
	 */
	private void forecastUpdatedFromServiceUpdater(List<YahooForcast> forecasts) {
		if (callback != null) {
			// update your forecast
			this.forecasts = forecasts;
			// use the callback to prevent the client
			if (callback.get() != null) {
				// yep, we use a weakReference
				callback.get().forecastLoaded(forecasts);
			}
		}
	}

}

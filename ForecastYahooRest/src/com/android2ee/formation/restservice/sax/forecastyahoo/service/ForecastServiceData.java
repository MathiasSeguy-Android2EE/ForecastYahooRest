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
package com.android2ee.formation.restservice.sax.forecastyahoo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.android2ee.formation.restservice.sax.forecastyahoo.MyApplication;
import com.android2ee.formation.restservice.sax.forecastyahoo.R;
import com.android2ee.formation.restservice.sax.forecastyahoo.dao.ForecastDAO;
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

	/**
	 * The forecasts to display
	 */
	private List<YahooForcast> forecasts;
	/**
	 * The callBack to update activity
	 */
	private ForecastCallBack callback;
	/**
	 * The Dao
	 */
	private ForecastDAO forcastDao;
	/**
	 * The date parser used to set the last update date format in the preference
	 */
	public SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");

	/**
	 * Constructor
	 */
	public ForecastServiceData(ServiceManager srvManager) {
		// NOthing to initialize
		// the parameter is to ensure only srvManager cant create it
	}

	/**
	 * Return the forecast
	 * 
	 * @param callback
	 */
	public void getForecast(ForecastCallBack callback) {
		this.callback = callback;
		// retrieve the url
		new AsynDaoCall().execute();
	}

	/**
	 * Called when the forecast are built
	 * Return that list to the calling Activity using the ForecastCallBack
	 */
	private void returnForecast() {
		if (callback != null) {
			// use the callback to prevent the client
			callback.forecastLoaded(forecasts);
			// then ask the serviceupdater to update data
			// but update only if one day of difference between the last update and now
			SharedPreferences prefs = MyApplication.instance.getSharedPreferences(MyApplication.CONNECTIVITY_STATUS,
					Context.MODE_PRIVATE);
			String strLastUpdate = prefs.getString(MyApplication.instance.getString(R.string.last_update), "");
			try {
				Date lastUpdate = sdf.parse(strLastUpdate);
				if (new Date().getTime() - lastUpdate.getTime() > 1000 * 60 * 60 * 24) {
					MyApplication.instance.getServiceManager().getForecastServiceUpdater()
							.updateForecastFromServer(new ForecastCallBack() {
								@Override
								public void forecastLoaded(List<YahooForcast> forecasts) {
									forecastUpdatedFromServiceUpdater(forecasts);
								}
							});
				}
			} catch (ParseException e) {
				ExceptionManager.manage(new ExceptionManaged(this.getClass(), R.string.exc_date_parsing, e));
			}

		}
	}

	/**
	 * Called when the forecast are updated from the service updater (
	 */
	private void forecastUpdatedFromServiceUpdater(List<YahooForcast> forecasts) {
		if (callback != null) {
			// use the callback to prevent the client
			callback.forecastLoaded(forecasts);
		}
	}

	/**
	 * @author Mathias Seguy (Android2EE)
	 * @goals
	 *        This class aims to make an async call to the server and build the forecast
	 */
	private class AsynDaoCall extends AsyncTask<Void, String, String> {
		/*
		 * * (non-Javadoc) *
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(Void... arg0) {
			// Do the rest http call
			// Parse the element
			// store the data in DAO
			forcastDao = new ForecastDAO();
			forecasts = forcastDao.loadAll();
			forcastDao = null;
			return null;
		}

		/*
		 * * (non-Javadoc) *
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			// build the forecast GUI
			returnForecast();
		}
	}
}

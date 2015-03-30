/**<ul>
 * <li>ForecastYahooRest</li>
 * <li>com.android2ee.formation.restservice.sax.forecastyahoo.service.city</li>
 * <li>18 nov. 2014</li>
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
 * This code is free for any usage except training and can't be distribute.</br>
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
package com.android2ee.formation.restservice.sax.forecastyahoo.service.city;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android2ee.formation.restservice.sax.forecastyahoo.MyApplication;
import com.android2ee.formation.restservice.sax.forecastyahoo.R;
import com.android2ee.formation.restservice.sax.forecastyahoo.dao.city.CityDAO;
import com.android2ee.formation.restservice.sax.forecastyahoo.service.ServiceManager;
import com.android2ee.formation.restservice.sax.forecastyahoo.service.city.callback.AddedCityCallBack;
import com.android2ee.formation.restservice.sax.forecastyahoo.service.city.callback.CitiesCallBack;
import com.android2ee.formation.restservice.sax.forecastyahoo.service.city.callback.CityCallBack;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.exceptions.ExceptionManager;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.model.City;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to manage the cities
 */
public class CityServiceData {
	/******************************************************************************************/
	/** Attributes **************************************************************************/
	/******************************************************************************************/

	/**
	 * The forecasts to display (the cach)
	 */
	private List<City> cities = null;
	/**
	 * The current city
	 */
	private City city = null;
	/**
	 * The callBack to update activity
	 * To understand what a weakRefrence
	 * is:https://weblogs.java.net/blog/2006/05/04/understanding-weak-references
	 */
	private WeakReference<CitiesCallBack> callback = null;
	/**
	 * The callBack to find a city according to its woeid
	 * To understand what a weakRefrence
	 * is:https://weblogs.java.net/blog/2006/05/04/understanding-weak-references
	 */
	private WeakReference<CityCallBack> cityCallback = null;
	/**
	 * The callBack to added city
	 * To understand what a weakRefrence
	 * is:https://weblogs.java.net/blog/2006/05/04/understanding-weak-references
	 */
	private WeakReference<AddedCityCallBack> addedCityCallBack = null;
	/**
	 * The Dao
	 */
	private CityDAO cityDao = null;

	/******************************************************************************************/
	/** Constructors **************************************************************************/
	/******************************************************************************************/

	/**
	 * Constructor
	 */
	public CityServiceData(ServiceManager srvManager) {
		// NOthing to initialize
		// the parameter is to ensure only srvManager cant create it
	}

	/******************************************************************************************/
	/** Public methods **************************************************************************/
	/******************************************************************************************/

	/**
	 * Return the list of the cities
	 * 
	 * @param callback
	 *            The callback to use to deliver the data when data loaded
	 */
	public void getCities(CitiesCallBack callback) {
		Log.e("CityServiceData", "getCities called");
		this.callback = new WeakReference<CitiesCallBack>(callback);

		// then link the Handler with the handler of the runnable
		if (daoLoadRunnable.daoLoadHandler == null) {
			daoLoadRunnable.daoLoadHandler = daoLoadHandler;
		}
		// then launch it
		MyApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(daoLoadRunnable);

	}

	/**
	 * Return the list of the cities
	 * 
	 * @param callback
	 *            The callback to use to deliver the data when data loaded
	 */
	public void loadCity(CityCallBack callback, String woeid) {
		Log.e("CityServiceData", "loadCity called for woeid =" + woeid);
		this.cityCallback = new WeakReference<CityCallBack>(callback);
		// then link the Handler with the handler of the runnable
		if (daoLoadCityRunnable.daoLoadCityHandler == null) {
			daoLoadCityRunnable.daoLoadCityHandler = daoLoadCityHandler;
		}
		daoLoadCityRunnable.woeid = woeid;
		// then launch it
		MyApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(daoLoadCityRunnable);

	}

	/**
	 * Add a City in the database
	 */
	public void addCity(City city, AddedCityCallBack addedCityCallBack) {
		Log.e("CityServiceData", "addCities called");
		this.addedCityCallBack = new WeakReference<AddedCityCallBack>(addedCityCallBack);
		// Set the city to add in the runnable
		// then link the Handler with the handler of the runnable
		if (daoAddRunnable.daoAddCityHandler == null) {
			daoAddRunnable.daoAddCityHandler = daoAddCityHandler;
		}
		daoAddRunnable.cityToAdd = city;
		// then launch it
		MyApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(daoAddRunnable);

	}

	/**
	 * Delete a City in the database
	 */
	public void deleteCity(City city) {
		Log.e("CityServiceData", "deleteCities called");
		// Set the city to add in the runnable
		daoDeleteRunnable.cityToDelete = city;
		// then launch it
		MyApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(daoDeleteRunnable);

	}

	/******************************************************************************************/
	/** Loading data from D.A.O **************************************************************************/
	/******************************************************************************************/

	/**
	 * The runnable to execute when requesting update from the server
	 */
	private DaoLoadRunnable daoLoadRunnable = new DaoLoadRunnable();

	/**
	 * @author Mathias Seguy (Android2EE)
	 * @goals
	 *        This class aims to implements a Runnable with an Handler
	 */
	private class DaoLoadRunnable implements Runnable {
		/**
		 * The handler to use to communicate outside the runnable
		 */
		public Handler daoLoadHandler = null;

		@Override
		public void run() {
			// Do the rest http call
			// Parse the element
			// store the data in DAO
			cityDao = new CityDAO();
			cities = cityDao.loadAll();
			cityDao = null;
			daoLoadHandler.sendMessage(daoLoadHandler.obtainMessage());
		}

	}

	/**
	 * The handler awoke when the Runnable has finished it's execution
	 */
	private Handler daoLoadHandler = new Handler() {
		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			returnCities();
		}

	};

	/**
	 * Called when the cities are loaded from database and built
	 * Return that list to the calling Activity using the CitiesCallBack
	 */
	private void returnCities() {
		if (callback != null) {
			// use the callback to prevent the client
			if (callback.get() != null) {
				// yep, we use a weakReference
				callback.get().citiesLoaded(cities);
			}
		}
	}

	/******************************************************************************************/
	/** Loading a city from D.A.O **************************************************************************/
	/******************************************************************************************/

	/**
	 * The runnable to execute when requesting update from the server
	 */
	private DaoLoadCityRunnable daoLoadCityRunnable = new DaoLoadCityRunnable();

	/**
	 * @author Mathias Seguy (Android2EE)
	 * @goals
	 *        This class aims to implements a Runnable with an Handler
	 */
	private class DaoLoadCityRunnable implements Runnable {
		/**
		 * The handler to use to communicate outside the runnable
		 */
		public Handler daoLoadCityHandler = null;
		/**
		 * The woeid of the searched city
		 */
		public String woeid = null;

		@Override
		public void run() {
			// Do the rest http call
			// Parse the element
			// store the data in DAO
			cityDao = new CityDAO();
			city = cityDao.loadCityFromWoeid(woeid);
			Log.e("CityServiceData", "DaoLoadCityRunnable called city found in dao " + city);
			cityDao = null;
			daoLoadCityHandler.sendMessage(daoLoadCityHandler.obtainMessage());
		}

	}

	/**
	 * The handler awoke when the Runnable has finished it's execution
	 */
	private Handler daoLoadCityHandler = new Handler() {
		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			returnCity();
		}

	};

	/**
	 * Called when the cities are loaded from database and built
	 * Return that list to the calling Activity using the CitiesCallBack
	 */
	private void returnCity() {
		Log.e("CityServiceData", "returnCity called with currentcity =" + city);
		if (cityCallback != null) {
			Log.e("CityServiceData", "returnCity called cityCallback != null");
			// use the callback to prevent the client
			if (cityCallback.get() != null) {
				Log.e("CityServiceData", "returnCity called cityCallback.get() != null");
				// yep, we use a weakReference
				cityCallback.get().cityLoaded(city);
			}
		}
	}

	/******************************************************************************************/
	/** Adding data from D.A.O **************************************************************************/
	/******************************************************************************************/

	/**
	 * The runnable to execute when requesting update from the server
	 */
	private DaoAddRunnable daoAddRunnable = new DaoAddRunnable();

	/**
	 * @author Mathias Seguy (Android2EE)
	 * @goals
	 *        This class aims to implements a Runnable with an Handler
	 */
	private class DaoAddRunnable implements Runnable {
		/**
		 * The handler to use to communicate outside the runnable
		 */
		public Handler daoAddCityHandler = null;
		/**
		 * The city to add
		 */
		public City cityToAdd = null;

		@Override
		public void run() {
			// Do the adding
			cityDao = new CityDAO();
			boolean isok = cityDao.add(cityToAdd);
			if (!isok) {
				ExceptionManager.displayAnError(MyApplication.instance.getString(R.string.err_adding_city_failed));
			}
			cityDao = null;
			daoAddCityHandler.sendMessage(daoAddCityHandler.obtainMessage());
		}

	}

	/**
	 * The handler awoke when the Runnable has finished it's execution
	 */
	private Handler daoAddCityHandler = new Handler() {
		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			returnAddedCity();
		}

	};

	/**
	 * Called when the cities are loaded from database and built
	 * Return that list to the calling Activity using the CitiesCallBack
	 */
	private void returnAddedCity() {
		if (addedCityCallBack != null) {
			// use the callback to prevent the client
			if (addedCityCallBack.get() != null) {
				// yep, we use a weakReference
				addedCityCallBack.get().cityAdded(daoAddRunnable.cityToAdd);
			}
		}
	}

	/******************************************************************************************/
	/** Deleting data from D.A.O **************************************************************************/
	/******************************************************************************************/

	/**
	 * The runnable to execute when requesting update from the server
	 */
	private DaoDeleteRunnable daoDeleteRunnable = new DaoDeleteRunnable();

	/**
	 * @author Mathias Seguy (Android2EE)
	 * @goals
	 *        This class aims to implements a Runnable with an Handler
	 */
	private class DaoDeleteRunnable implements Runnable {
		public City cityToDelete = null;

		@Override
		public void run() {
			// Do the deletion
			cityDao = new CityDAO();
			boolean isok = cityDao.delete(cityToDelete);
			if (!isok) {
				ExceptionManager.displayAnError(MyApplication.instance.getString(R.string.err_deleting_city_failed));
			}
			cityDao = null;
		}

	}
}

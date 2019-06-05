/**<ul>
 * <li>ForecastYahooRest</li>
 * <li>com.android2ee.formation.restservice.sax.forecastyahoo.service.city</li>
 * <li>24 oct. 2014</li>
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

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android2ee.formation.restservice.sax.forecastyahoo.MyApplication;
import com.android2ee.formation.restservice.sax.forecastyahoo.R;
import com.android2ee.formation.restservice.sax.forecastyahoo.service.ServiceManager;
import com.android2ee.formation.restservice.sax.forecastyahoo.service.city.callback.CitiesCallBack;
import com.android2ee.formation.restservice.sax.forecastyahoo.service.city.jsonparser.JsonCitiesParser;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.exceptions.ExceptionManaged;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.exceptions.ExceptionManager;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.model.City;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to:
 *        <ul>
 *        <li></li>
 *        </ul>
 */
public class CitiesServiceRequester {
	/******************************************************************************************/
	/** Attributes **************************************************************************/
	/******************************************************************************************/

	/**
	 * The url to use
	 */
	private String url;
	/**
	 * The object used to communicate with http
	 */
	private HttpClient client;
	/**
	 * The raw xml answer
	 */
	private String responseBody;
	/**
	 * The forecasts to display
	 */
	private List<City> cities;
	/**
	 * The logCat's tag
	 */
	private final String tag = "CitiesServiceRequester";
	/**
	 * The callBack to update activity
	 */
	private WeakReference<CitiesCallBack> callback;
	/**
	 * The searched city
	 */
	City searchedCity;

	/******************************************************************************************/
	/** Constructors **************************************************************************/
	/******************************************************************************************/

	/**
	 * Constructor
	 */
	public CitiesServiceRequester(ServiceManager srvManager) {
		// NOthing to initialize
		// the parameter is to ensure only srvManager cant create it
	}

	/******************************************************************************************/
	/** Public method **************************************************************************/
	/******************************************************************************************/

	/**
	 * Return the forecast
	 * 
	 * @param callback
	 */
	public void findCities(CitiesCallBack callback, City city) {
		this.callback = new WeakReference<CitiesCallBack>(callback);
		searchedCity = city;
		Log.e("CitiesServiceRequester ", "findCities called ");
		if (MyApplication.instance.isConnected()) {
			// then load data from network
			// retrieve the url
//			url = MyApplication.instance.getString(R.string.city_list_2)+"\""+city.getName() + "\"";
//			+"&"+ MyApplication.instance.getString(R.string.url_format);
			url = MyApplication.instance.getString(R.string.city_list_3,city.getName().replaceAll("\\s",""))+"&"+ MyApplication.instance.getString(R.string.url_format);
			Log.e("CitiesServiceRequester ", "findCities trying to reach url " + url);
			// then link the Handler with the handler of the runnable
			if (restCallRunnable.restCallHandler == null) {
				restCallRunnable.restCallHandler = restCallHandler;
			}
			// then launch it
			MyApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(restCallRunnable);
		} else {
			// else use the callback to return null to the client
			callback.citiesLoaded(null);
		}
	}

	/******************************************************************************************/
	/** Private methods **************************************************************************/
	/******************************************************************************************/

	/**
	 * The runnable to execute when requesting update from the server
	 */
	private RestCallRunnable restCallRunnable = new RestCallRunnable();

	/**
	 * @author Mathias Seguy (Android2EE)
	 * @goals
	 *        This class aims to implements a Runnable with an Handler
	 */
	private class RestCallRunnable implements Runnable {
		/**
		 * The handler to use to communicate outside the runnable
		 */
		public Handler restCallHandler = null;

		@Override
		public void run() {
			// Do the rest http call
			// Parse the element
			rebuildCities(getCities());
			restCallHandler.sendMessage(restCallHandler.obtainMessage());
		}

		/**
		 * Retrieve the forecast
		 */
		private String getCities() {
			// The HTTP get method send to the URL
			HttpGet getMethod = new HttpGet(url);
			// The basic response handler
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			// instantiate the http communication
			client = new DefaultHttpClient();
			// Call the URL and get the response body
			try {
				responseBody = client.execute(getMethod, responseHandler);
			} catch (ClientProtocolException e) {
				ExceptionManager.manage(new ExceptionManaged(this.getClass(), R.string.exc_client_protocol, e));
			} catch (IOException e) {
				ExceptionManager.manage(new ExceptionManaged(this.getClass(), R.string.exc_http_get_error, e));
			}
			if (responseBody != null) {
				Log.d(tag, responseBody);
			}
			// parse the response body
			return responseBody;
		}

		/**
		 * Build the Forecasts list by parsing the xml response using SAX
		 * 
		 * @param raw
		 *            the xml response of the web server
		 */
		private void rebuildCities(String raw) {
			try {
				// Parse your data
				cities = JsonCitiesParser.parse(new JSONObject(raw), searchedCity);
			} catch (JSONException e) {
				ExceptionManager.manage(new ExceptionManaged(this.getClass(), R.string.exc_parsing_city, e));
			}
		}
	}

	/**
	 * The handler awoke when the Runnable has finished it's execution
	 */
	private Handler restCallHandler = new Handler() {
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
	 * Called when the forecast are built
	 * Return that list to the calling Activity using the ForecastCallBack
	 */
	private void returnCities() {
		// use the callback to prevent the client
		for (City city : cities) {
			Log.e("ForcastServiceUpdater ", "Found " + city);
		}
		if(callback.get()!=null) {
			callback.get().citiesLoaded(cities);
		}
	}
}

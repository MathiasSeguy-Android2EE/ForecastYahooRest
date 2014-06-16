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

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.os.AsyncTask;
import android.util.Log;

import com.android2ee.formation.restservice.sax.forecastyahoo.MyApplication;
import com.android2ee.formation.restservice.sax.forecastyahoo.R;
import com.android2ee.formation.restservice.sax.forecastyahoo.model.YahooForcast;
import com.android2ee.formation.restservice.sax.forecastyahoo.saxparser.ForcastSaxHandler;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to:
 *        <ul>
 *        <li></li>
 *        </ul>
 */
public class ForecastService {

	/******************************************************************************************/
	/** Singleton Pattern **************************************************************************/
	/******************************************************************************************/
	private static ForecastService instance;

	public static ForecastService getInstance() {
		if (null == instance) {
			instance = new ForecastService();
		}
		return instance;
	}

	/******************************************************************************************/
	/** Attributes **************************************************************************/
	/******************************************************************************************/

	/** Thr url to use */
	private String url;
	/** The object used to communicate with http */
	private HttpClient client;
	/** The raw xml answer */
	private String responseBody;
	/**
	 * The forecasts to display
	 */
	private List<YahooForcast> forecasts;
	/**
	 * The logCat's tag
	 */
	private final String tag = "ForecastActivity";
	/**
	 * The callBack to update activity
	 */
	ForecastCallBack callback;

	/**
	 * 
	 */
	public ForecastService() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Return the forecast
	 * 
	 * @param callback
	 */
	public void getForecast(ForecastCallBack callback) {
		this.callback = callback;
		// retrieve the url
		url = MyApplication.instance.getString(R.string.forcast_url)+"&"+MyApplication.instance.getString(R.string.forcast_url_degres);
		new AsynRestCall().execute();
	}

	/**
	 * Called when the forecast are built
	 * Return that list to the calling Activity using the ForecastCallBack
	 */
	public void returnForecast() {
		callback.forecastLoaded(forecasts);
	}

	/**
	 * @author Mathias Seguy (Android2EE)
	 * @goals
	 *        This class aims to make an async call to the server and build the forecast
	 */
	public class AsynRestCall extends AsyncTask<Void, String, String> {
		/*
		 *  * (non-Javadoc) *
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(Void... arg0) {
			// Do the rest http call
			// Parse the element
			buildForecasts(getForecast());
			return null;
		}

		/**
		 * Retrieve the forecast
		 */
		private String getForecast() {
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
				Log.e(tag, e.getMessage());
			} catch (IOException e) {
				Log.e(tag, e.getMessage());
			}
			if(responseBody!=null) {
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
		private void buildForecasts(String raw) {

			try {
				// Create a new instance of the SAX parser
				SAXParserFactory saxPF = SAXParserFactory.newInstance();
				SAXParser saxP = saxPF.newSAXParser();
				// The xml reader
				XMLReader xmlR = saxP.getXMLReader();
				// Create the Handler to handle each of the XML tags.
				ForcastSaxHandler forecastHandler = new ForcastSaxHandler();
				xmlR.setContentHandler(forecastHandler);
				// then parse
				xmlR.parse(new InputSource(new StringReader(raw)));
				// and retrieve the parsed forecasts
				forecasts = forecastHandler.getForecasts();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/*
		 *  * (non-Javadoc) *
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

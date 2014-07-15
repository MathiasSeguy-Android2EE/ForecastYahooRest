/**<ul>
 * <li>ForecastYahooRest</li>
 * <li>com.android2ee.formation.restservice.sax.forecastyahoo.service</li>
 * <li>10 juil. 2014</li>
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

import android.util.Log;

import com.android2ee.formation.restservice.sax.forecastyahoo.MyApplication;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to manage services
 *        It can be access only through MyApplication object
 *        by calling MyApplication.getServiceManager()
 */
public class ServiceManager {
	/**
	 * The Forecast service
	 */
	ForecastServiceUpdater forecastServiceUpdater = null;
	/**
	 * The Forecast service
	 */
	ForecastServiceData forecastServiceData = null;

	/**
	 * Insure only the Application object can instantiate once this object
	 * If not the case throw an Exception
	 */
	public ServiceManager(MyApplication application) {
		if (application.serviceManagerAlreadyExist()) {
			throw new ExceptionInInitializerError();
		}
	}

	/**
	 * @return the forecastServiceData
	 */
	public final ForecastServiceData getForecastServiceData() {
		if (null == forecastServiceData) {
			forecastServiceData = new ForecastServiceData(this);
		}
		return forecastServiceData;
	}

	/**
	 * @return the ForecastServiceUpdater
	 */
	public ForecastServiceUpdater getForecastServiceUpdater() {
		if (forecastServiceUpdater == null) {
			forecastServiceUpdater = new ForecastServiceUpdater(this);
		}
		return forecastServiceUpdater;
	}

	/**
	 * To be called when you need to release all the services
	 * Is managed by the MyApplication object in fact
	 */
	public void unbindAndDie() {
		Log.e("ServiceManager", "UnbindAndDie is called");
		forecastServiceUpdater = null;
		forecastServiceData = null;
	}

}

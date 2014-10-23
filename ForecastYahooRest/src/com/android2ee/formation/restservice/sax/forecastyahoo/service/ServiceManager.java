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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

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
		if (cancelableThreadsExecutor != null) {
			killCancelableThreadExecutor();
		}
		if (keepAliveThreadsExceutor != null) {
			killKeepAliveThreadExecutor();
		}
	}

	/******************************************************************************************/
	/** Pool Executor for Threads that has to cancelled when the application shutdown**/
	/******************************************************************************************/
	/**
	 * The pool executor to use for all cancellable thread and Threads that has to cancelled when the application shutdown
	 */
	private ExecutorService cancelableThreadsExecutor = null;

	/**
	 * @return the cancelableThreadsExceutor
	 */
	public final ExecutorService getCancelableThreadsExecutor() {
		if (cancelableThreadsExecutor == null) {
			cancelableThreadsExecutor = Executors.newFixedThreadPool(12, new BackgroundThreadFactory());
		}
		return cancelableThreadsExecutor;
	}

	/** * And its associated factory */
	private class BackgroundThreadFactory implements ThreadFactory {
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			return t;
		}
	}

	/**
	 * Kill all running Thread and destroy then all
	 * Kill the cancelableThreadsExceutor
	 */
	private void killCancelableThreadExecutor() {
		if (cancelableThreadsExecutor != null) {
			cancelableThreadsExecutor.shutdownNow(); // Disable new tasks from being submitted and kill every running task using Thread.interrupt
			try {// as long as your threads hasn't finished
				while (!cancelableThreadsExecutor.isTerminated()) {
					// Wait a while for existing tasks to terminate
					if (!cancelableThreadsExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
						// Cancel currently executing tasks
						cancelableThreadsExecutor.shutdownNow();
						Log.e("MyApp", "Probably a memory leak here");
					}
				}
			} catch (InterruptedException ie) {
				// (Re-)Cancel if current thread also interrupted
				cancelableThreadsExecutor.shutdownNow();
				cancelableThreadsExecutor=null;
				Log.e("MyApp", "Probably a memory leak here too");
			}
		}
		cancelableThreadsExecutor=null;
	}
	/******************************************************************************************/
	/** Pool Executor for Threads that has to finish they threatment when the application shutdown**/
	/******************************************************************************************/
	/**
	 * The pool executor to use for all cancellable thread and Threads that has to cancelled when the application shutdown
	 */
	private ExecutorService keepAliveThreadsExceutor = null;

	/**
	 * @return the cancelableThreadsExceutor
	 */
	public final ExecutorService getKeepAliveThreadsExecutor() {
		if (keepAliveThreadsExceutor == null) {
			keepAliveThreadsExceutor = Executors.newFixedThreadPool(12, new BackgroundThreadFactory());
		}
		return keepAliveThreadsExceutor;
	}


	/**
	 * Kill all running Thread and destroy then all
	 * Kill the cancelableThreadsExceutor
	 */
	private void killKeepAliveThreadExecutor() {
		if (keepAliveThreadsExceutor != null) {
			keepAliveThreadsExceutor.shutdown(); // Disable new tasks from being submitted
			try {// as long as your threads hasn't finished
				while (!keepAliveThreadsExceutor.isTerminated()) {
					// Wait a while for existing tasks to terminate
					if (!keepAliveThreadsExceutor.awaitTermination(5, TimeUnit.SECONDS)) {
						// Cancel currently executing tasks
						keepAliveThreadsExceutor.shutdown();
						Log.e("MyApp", "Probably a memory leak here");
					}
				}
			} catch (InterruptedException ie) {
				// (Re-)Cancel if current thread also interrupted
				keepAliveThreadsExceutor.shutdownNow();
				keepAliveThreadsExceutor=null;
				Log.e("MyApp", "Probably a memory leak here too");
			}
		}
		keepAliveThreadsExceutor=null;
	}
}

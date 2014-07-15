/**<ul>
 * <li>MyConso</li>
 * <li>com.proxyserve.myconso.android.transverse.exception</li>
 * <li>16 janv. 2014</li>
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
package com.android2ee.formation.restservice.sax.forecastyahoo.transverse.exceptions;

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
public class ExceptionManager {
	/******************************************************************************************/
	/** Attribute and constants **************************************************************************/
	/******************************************************************************************/
	/**
	 * The name of the Action of the Intent send thought the system to warm the graphical layer
	 * (your activity) that an exception occurs and a message as to be displayed to the user
	 */
	public static final String Error_Intent_ACTION = "EXCEPTION_MANAGER_NEW_EXCEPTION_FIRED";
	/**
	 * The name of the Extra that handles the error message of the Intent send thought the system to warm the graphical layer
	 * (your activity) that an exception occurs and a message as to be displayed to the user
	 */
	public static final String Error_Intent_MESSAGE = "EXCEPTION_MANAGER_NEW_EXCEPTION_FIRED";
	/**
	 * The Intent send thought the system to warm the graphical layer
	 * (your activity) that an exception occurs and a message as to be displayed to the user
	 */
	private static final Intent Error_Intent=new Intent(Error_Intent_ACTION);

	/******************************************************************************************/
	/** Static method **************************************************************************/
	/******************************************************************************************/


	/**
	 * @param exception
	 */
	public static void manage(ExceptionManaged exception) {
		management(exception);
	}

	/******************************************************************************************/
	/** Private method **************************************************************************/
	/******************************************************************************************/

	/**
	 * @param exc
	 */
	private static void management(ExceptionManaged exc) {
		if (!exc.isManaged()) {
			exc.setManaged(true);
			//Firts fire the error Intent thought the system
			Error_Intent.putExtra(Error_Intent_MESSAGE, exc.getErrorMessage());
			MyApplication.instance.sendBroadcast(Error_Intent);
			// Should prevent the backend server
			//You should do it, This tutorial has no backend$
			
			//log
			Log.e(exc.getRootClass().getSimpleName(), exc.getErrorMessage(),exc);
		}
	}
}

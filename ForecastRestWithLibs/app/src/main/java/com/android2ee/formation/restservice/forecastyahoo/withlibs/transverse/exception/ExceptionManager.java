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
package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.exception;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.ErrorEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.ExceptionManagedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;

import org.greenrobot.eventbus.EventBus;


/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to manage exception
 *        It has to display an Error to the user
 *        And send a message to the developpers team
 */
public class ExceptionManager {


	/******************************************************************************************/
	/** Static method **************************************************************************/
	/******************************************************************************************/


	/**
	 * @param exception
	 */
	public synchronized static void manage(ExceptionManaged exception) {
		management(exception);
	}
	
	/**
	 * This method has to be called when you want to display an error to the user
	 * Because you don't manage error using the Exception mechanic but sometimes you want to display error
	 */
	public synchronized static void displayAnError(String errorMessage) {
		EventBus.getDefault().post(new ErrorEvent(errorMessage));
		//You should make a feedback to the team
		MyLog.e("ExceptionManaged", "Error managed :" + errorMessage);
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
			EventBus.getDefault().post(new ExceptionManagedEvent(exc));
			// Should prevent the backend server
			//You should do it, This tutorial has no backend$
			
			//MyLog
			MyLog.e("ExceptionManaged", exc.getErrorMessage(), exc);
			MyLog.e(exc.getRootClass().getSimpleName(), exc.getErrorMessage(), exc);
		}
	}
	
	
}

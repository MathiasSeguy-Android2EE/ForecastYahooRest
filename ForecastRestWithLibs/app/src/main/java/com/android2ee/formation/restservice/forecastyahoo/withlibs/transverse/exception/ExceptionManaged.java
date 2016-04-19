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


import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to define the exception of the application. So in every catch(Exception
 *        ex) you find, the code has to be
 *        {
 *        ExceptionManaged excManaged=new ExceptionManaged(R.string.err_**,ex);
 *        ExceptionManager.manage(excManaged);
 *        }finally{
 *        //Do your finally
 *        }
 */
public class ExceptionManaged extends Exception {
	/******************************************************************************************/
	/** Constant **************************************************************************/
	/******************************************************************************************/
	private final static int NO_ERROR_MESSAGE_ID = -1;
	/******************************************************************************************/
	/** Attribute **************************************************************************/
	/******************************************************************************************/
	// TODO you should had fields depending of your need
	/**
	 * The error message to display to the user
	 */
	private int errorMessageId = NO_ERROR_MESSAGE_ID;
	/**
	 * The error message to display to the user
	 */
	private String errorMessage = null;
	/**
	 * The boolean to know if the exception has been managed
	 */
	private boolean managed = false;
	/**
	 * The class where the exception occurs
	 */
	private Class rootClass = null;
	/**
	 * for serialization (not used)
	 */
	private static final long serialVersionUID = 1L;

	/******************************************************************************************/
	/** Constructors **************************************************************************/
	/******************************************************************************************/

	/**
	 * SHould not be used, you should provide an errorMessageID
	 * 
	 * @param exc
	 */
	public ExceptionManaged(Class rootClass, Exception exc) {
		this(rootClass, NO_ERROR_MESSAGE_ID, exc);
	}

	/**
	 * @param detailMessageId
	 *            The id of the error message string (R.string.myErrorMessage)
	 * @param exc
	 *            The exception that generates the crash
	 */
	public ExceptionManaged(Class rootClass, int detailMessageId, Exception exc) {
		this(rootClass, loadMessage(detailMessageId), detailMessageId, exc);
	}

	/**
	 * @param detailMessage
	 *            The error message string
	 * @param detailMessageId
	 *            The id of the error message string (R.string.myErrorMessage)
	 * @param exc
	 *            The exception that generates the crash
	 */
	private ExceptionManaged(Class rootClass, String detailMessage, int detailMessageId, Exception exc) {
		super(detailMessage, exc);
		this.errorMessageId = detailMessageId;
		this.errorMessage = detailMessage;
		this.rootClass = rootClass;
	}

	/**
	 * SHould not be used, you should provide an errorMessageID
	 * 
	 * @param throwable
	 *            The throwable that generates the exception
	 */
	public ExceptionManaged(Class rootClass, Throwable throwable) {
		this(rootClass, NO_ERROR_MESSAGE_ID, throwable);
	}

	/**
	 * @param detailMessageId
	 *            The id of the error message string (R.string.myErrorMessage)
	 * @param throwable
	 *            The throwable that generates the exception
	 */
	public ExceptionManaged(Class rootClass, int detailMessageId, Throwable throwable) {
		this(rootClass, loadMessage(detailMessageId), detailMessageId, throwable);
	}

	/**
	 * @param detailMessage
	 *            The error message string
	 * @param detailMessageId
	 *            The id of the error message string (R.string.myErrorMessage)
	 * @param throwable
	 *            The throwable that generates the exception
	 */
	private ExceptionManaged(Class rootClass, String detailMessage, int detailMessageId, Throwable throwable) {
		super(detailMessage, throwable);
		this.errorMessageId = detailMessageId;
		this.errorMessage = detailMessage;
		this.rootClass = rootClass;
	}
	/**
	 *
	 * @param rootClass The class where the exception occurs
	 * @param detailMessageId The message id to display to the user
	 */
	public ExceptionManaged(Class rootClass, int detailMessageId) {
		this(rootClass, loadMessage(detailMessageId), detailMessageId);
	}

	/**
	 * @param detailMessage
	 *            The error message string
	 * @param detailMessageId
	 *            The id of the error message string (R.string.myErrorMessage)
	 */
	private ExceptionManaged(Class rootClass, String detailMessage, int detailMessageId) {
		super(detailMessage);
		this.errorMessage = detailMessage;
		this.errorMessageId = detailMessageId;
		this.rootClass = rootClass;
	}

	/******************************************************************************************/
	/** Private static methods **************************************************************************/
	/******************************************************************************************/

	/**
	 * This method is called when you have the errorMessageId and you want to load the real error
	 * message
	 * It sets, according to messageId, the errorMessage
	 */
	private static String loadMessage(int errorMessageId) {
		if (errorMessageId != NO_ERROR_MESSAGE_ID) {
			return MyApplication.instance.getResources().getString(errorMessageId);
		} else {
			return MyApplication.instance.getResources().getString(R.string.no_error_message);
		}
	}

	/******************************************************************************************/
	/** Getters/Setter **************************************************************************/
	/******************************************************************************************/

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the managed
	 */
	public boolean isManaged() {
		return managed;
	}

	/**
	 * @param managed
	 *            the managed to set
	 */
	public void setManaged(boolean managed) {
		this.managed = managed;
	}

	/**
	 * @return the errorMessageId
	 */
	public final int getErrorMessageId() {
		return errorMessageId;
	}

	/**
	 * @param errorMessageId
	 *            the errorMessageId to set
	 */
	public final void setErrorMessageId(int errorMessageId) {
		this.errorMessageId = errorMessageId;
	}

	/**
	 * @return the rootClass
	 */
	public final Class getRootClass() {
		return rootClass;
	}

}

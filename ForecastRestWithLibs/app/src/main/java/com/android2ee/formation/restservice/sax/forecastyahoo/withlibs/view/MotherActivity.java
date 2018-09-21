/**
 * <ul>
 * <li>MotherActivity</li>
 * <li>com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view</li>
 * <li>24/02/2016</li>
 * <p/>
 * <li>======================================================</li>
 * <p/>
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 * <p/>
 * /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br>
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 * Belongs to <strong>Mathias Seguy</strong></br>
 * ***************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * <p/>
 * *****************************************************************************************************************</br>
 * Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 * Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br>
 * Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */

package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.sax.forecastyahoo.R;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.event.ConnectivityChangeEvent;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.event.ErrorEvent;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.event.ExceptionManagedEvent;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.utils.MyLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Mathias Seguy - Android2EE on 24/02/2016.
 */
public abstract class MotherActivity extends AppCompatActivity {
    private static final String TAG = "MotherActivity";
    /**
     * Connection status
     */
    protected boolean isConnected;
    /**
     * The toolbar
     */
    Toolbar toolbar;
    @Override
    protected void onStart() {
        super.onStart();
        //say to the application an activity is alived
        MyApplication.instance.onStartActivity();
        manageConnectivityStatus(MyApplication.instance.isConnected());
        EventBus.getDefault().register(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Weather");
        actionBar.setSubtitle("By Android2EE");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //say to the application an activity is going away
        MyApplication.instance.onStopActivity();
        EventBus.getDefault().unregister(this);
    }

    /******************************************************************************************/
    /** Managing the Exceptions **************************************************************************/
    /******************************************************************************************/
    /**
     * The txvException
     */
    private TextView txvException;
    /**
     * The click to dismiss string
     */
    private String strClickToDismiss=null;

    /***
     * The method that listen for Exception
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ExceptionManagedEvent event){
        int errorMessageId=event.getExceptionManaged().getErrorMessageId();
        String errorMessage;
        StackTraceElement[] stack=event.getExceptionManaged().getStackTrace();
        StringBuilder stackSB=new StringBuilder();
        for (StackTraceElement stackTraceElement : stack) {
            stackSB.append(stackTraceElement.toString());
            stackSB.append("\r\n");
        }
        if(errorMessageId==0){
            errorMessage=event.getExceptionManaged().getErrorMessage();
        }else{
            errorMessage=getResources().getString(errorMessageId)+"\n\r and the exception message is "+stackSB.toString();
        }
        displayException(errorMessage);
    }

    /**
     * Display the Exception carried by the Intent
     *
     * @param errorMessage
     *            the error message to display
     */
    private void displayException(String errorMessage) {
        // Display the error to your user
        // To do so, every layout of your Activity or Fragment should include the
        // exceptionLayout at it top level
        if (txvException == null) {
            // instantiate the TextView and the Drawable
            txvException = (TextView) findViewById(R.id.txv_exception_message);
            if(strClickToDismiss==null){
                strClickToDismiss = getString(R.string.txv_exception_clicktodismiss);
            }
            txvException.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideTxvException();
                }
            });
        }
        if (errorMessage == null) {
            txvException.setText(getString(R.string.no_error_message));
        } else {
            txvException.setText(errorMessage + strClickToDismiss);
        }
        txvException.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the TextView
     */
    private void hideTxvException() {
        txvException.setVisibility(View.GONE);
    }
    /******************************************************************************************/
    /** Managing the Errors **************************************************************************/
    /******************************************************************************************/
    /**
     * The txvException
     */
    private TextView txvErrors;

    /***
     * The method that listen for Exception
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ErrorEvent event){
        int errorMessageId=event.getErrorMessageId();
        String errorMessage;
        if(errorMessageId==0){
            errorMessage=event.getErrorMessage();
        }else{
            errorMessage=getResources().getString(errorMessageId);
        }
        displayError(errorMessage);
    }

    /**
     * Display the Exception carried by the Intent
     *
     * @param errorMessage
     *            the error message to display
     */
    private void displayError(String errorMessage) {
        // Display the error to your user
        // To do so, every layout of your Activity or Fragment should include the
        // exceptionLayout at it top level
        if (txvErrors == null) {
            // instantiate the TextView and the Drawable
            txvErrors = (TextView) findViewById(R.id.txv_error_message);
            if(strClickToDismiss==null){
                strClickToDismiss = getString(R.string.txv_exception_clicktodismiss);
            }
            txvErrors.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideTxvError();
                }
            });
        }
        if (errorMessage == null) {
            txvErrors.setText(getString(R.string.no_error_message));
        } else {
            txvErrors.setText(errorMessage + strClickToDismiss);
        }
        txvErrors.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the TextView
     */
    private void hideTxvError() {
        txvErrors.setVisibility(View.GONE);
    }
    /******************************************************************************************/
    /** Managing the (network) connectivity  **************************************************************************/
    /******************************************************************************************/
    /**
     * The txvException
     */
    private TextView txvNoNetwork;

    /***
     * The method that listen for Exception
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ConnectivityChangeEvent event){
        MyLog.d(TAG, "onEventMainThread() called with: " + "event = [" + event + "] and isConnected "+event.isConnected());
        manageConnectivityStatus(event.isConnected());
    }

    private void manageConnectivityStatus(boolean isConnected) {
        this.isConnected=isConnected;
        // Display the error to your user
        // To do so, every layout of your Activity or Fragment should include the
        // exceptionLayout at it top level
        if (txvNoNetwork == null) {
            // instantiate the TextView and the Drawable
            txvNoNetwork = (TextView) findViewById(R.id.txvNoNetwork);
            if(strClickToDismiss==null){
                strClickToDismiss = getString(R.string.txv_exception_clicktodismiss);
            }
            txvNoNetwork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideTxvNoConnectivity();
                }
            });
        }
        //then managed the visibility of the TxvNoNetwork according to the connectivity
        if(isConnected){
            //hide the no network message
            if(txvNoNetwork.getVisibility()== View.VISIBLE){
                txvNoNetwork.setVisibility(View.GONE);
            }
        }else{
            //display the no network message
            if(txvNoNetwork.getVisibility()==View.GONE
                    ||txvNoNetwork.getVisibility()==View.INVISIBLE){
                txvNoNetwork.setVisibility(View.VISIBLE);

            }
        }
    }

    /**
     * Hide the TextView
     */
    private void hideTxvNoConnectivity() {
        txvErrors.setVisibility(View.GONE);
    }


}

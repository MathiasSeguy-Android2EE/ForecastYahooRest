/**
 * <ul>
 * <li>MotherActivity</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.view</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.view;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.ConnectivityChangeEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.ErrorEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.ExceptionManagedEvent;

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
     * The action Bar
     */
    private ActionBar actionBar;
    @Override
    protected void onStart() {
        super.onStart();
        //say to the application an activity is alived
        MyApplication.instance.onStartActivity();
        //register for eventBus
        EventBus.getDefault().register(this);
        EventBus.getDefault().register(getPresenter());
        manageConnectivityStatus(MyApplication.instance.isConnected());
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        //Manage the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Weather");
        actionBar.setSubtitle("By Android2EE");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //say to the application an activity is going away
        MyApplication.instance.onStopActivity();
        //unregister for eventbus
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().unregister(getPresenter());
    }
    /***********************************************************
     *  Managing Presenters
     **********************************************************/
    public abstract MotherPresenter getPresenter();

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
        if(errorMessageId==0){
            errorMessage=event.getExceptionManaged().getErrorMessage();
        }else{
            errorMessage=getResources().getString(errorMessageId);
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
        Log.d(TAG, "onEventMainThread() called with: " + "event = [" + event + "] and isConnected "+event.isConnected());
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
    /******************************************************************************************/
    /** Managing Menu **************************************************************************/
    /******************************************************************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mother, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent browserIntent;
        switch (item.getItemId()) {
            case R.id.action_show_android2ee:
                // open browser on the web pages
                browserIntent = new Intent("android.intent.action.VIEW");
                // if (getResources().getConfiguration().locale.getDisplayName().contains("fr")) {
                // open the browser on android2ee
                browserIntent.setData(Uri.parse(getResources().getString(R.string.android2ee_url)));
                // } else {
                // // open the browser on android2ee english version
                // browserIntent.setData(Uri.parse("http://www.android2ee.com/en"));
                // }
                startActivity(browserIntent);
                return true;
            case R.id.action_training_android2ee:
                // open browser on the web pages
                browserIntent = new Intent("android.intent.action.VIEW");
                // if (getResources().getConfiguration().locale.getDisplayName().contains("fr")) {
                // open the browser on android2ee
                browserIntent.setData(Uri.parse(getResources().getString(R.string.android2ee_url_training)));
                // } else {
                // // open the browser on android2ee english version
                // browserIntent.setData(Uri.parse("http://www.android2ee.com/en"));
                // }
                startActivity(browserIntent);
                return true;
            case R.id.action_show_mathias:
                // open browser on the web pages
                browserIntent = new Intent("android.intent.action.VIEW");
                browserIntent.setData(Uri.parse(getResources().getString(R.string.mse_dvp_url)));
                startActivity(browserIntent);
                return true;
            case R.id.action_mail_mathias:
                // load string for email:
                String subject = getResources().getString(R.string.mail_subject);
                String body = getResources().getString(R.string.mail_body);
                // send an email
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                        new String[] { getResources().getString(R.string.mse_email) });
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

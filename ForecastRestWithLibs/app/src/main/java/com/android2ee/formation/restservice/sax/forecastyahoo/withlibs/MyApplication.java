/**
 * <ul>
 * <li>MyApplication</li>
 * <li>com.android2ee.formation.restservice.sax.forecastyahoo.withlibs</li>
 * <li>23/02/2016</li>
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

package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;

import com.android2ee.formation.restservice.sax.forecastyahoo.BuildConfig;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.injector.Injector;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.service.ServiceManagerIntf;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.service.weather.WeatherDataUpdaterWorker;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.event.ConnectivityChangeEvent;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;

import net.danlew.android.joda.JodaTimeAndroid;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkStatus;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Mathias Seguy - Android2EE on 23/02/2016.
 * This class aims to managed 3 stuff
 * Temps de dev:
 * 5h le 25/02
 * 2h le 27/02
 * 1h le 02/03
 * 2h le 05/03
 * 2h le 06/03
 * 5h le 07/03
 * 6h le 08/03
 * 5h le 09/03
 * 4h le 10/03
 */
public class MyApplication extends Application {

    private static final String TAG = "MyApplication";
    public static final String WeatherWorkerTag = "WeatherWorker";
    /**
     * The instance
     */
    public static MyApplication instance;
    /**
     * Manage the WeatherUpdaterWork schedule
     * The observer to know if any Work of this type is already registered
     */
    private Observer<List<WorkStatus>> weatherWorkerObserver;
    /**
     * The LiveData of the WorkStatus for the WeatherUpdaterWork
     */
    LiveData<List<WorkStatus>> status;
    /******************************************************************************************/
    /** Managing LifeCycle **************************************************************************/
    /******************************************************************************************/

    /**
     *
     */
    public MyApplication() {
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Application#onCreate()
     */
    @Override
    public void onCreate() {
        super.onCreate();
        MyLog.e("MyApplication", "onCreate is called");
        instance = this;
        Fabric.with(this,new Crashlytics());

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }

        //manage connectivity state
        manageConnectivityState();
        connectivityChangedReceiever=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                manageConnectivityState();
            }
        };
        // Registers BroadcastReceiver to track network connection changes.
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityChangedReceiever, filter);
        //initiliaze JodaTime
        JodaTimeAndroid.init(this);
        scheduleWeatherWorker();
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }
    /***********************************************************
     *  Weather Updater Worker
     **********************************************************/
    /**
     * Schedule the weather worker
     */
    private void scheduleWeatherWorker() {
        status= WorkManager.getInstance().getStatusesByTag(MyApplication.WeatherWorkerTag);
        //define the observer and schedule the work if it doesn't exist yet
        if(weatherWorkerObserver==null){
            weatherWorkerObserver = new Observer<List<WorkStatus>>() {
                @Override
                public void onChanged(@Nullable List<WorkStatus> workStatuses) {
                    MyLog.e(TAG,"The current number of work is "+workStatuses.size());
                    if(workStatuses==null||workStatuses.size()==0){
                        MyLog.e(TAG,"We go again into the null or empty case for the works");
                        //create
                        enqueueWeatherWorker();
                    }else{
                        //do nothing
                        MyLog.e(TAG,"We do nothing for the work");
                    }
                    //what ever
                    status.removeObserver(weatherWorkerObserver);
                }
            };
        }
        status.observeForever(weatherWorkerObserver);
        //then start an immedaite update?
    }

    /**
     * Create the WeatherWorker and Enqueue it
     * ben tant mieux
     */
    private void enqueueWeatherWorker() {
        //Register your WeatherWorker: It will update forecast database
        // Create a Constraints that defines when the task should run
        Constraints myConstraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                // Many other constraints are available, see the
                // Constraints.Builder reference
                .build();
        PeriodicWorkRequest weatherUpdaterWork = new PeriodicWorkRequest.Builder(WeatherDataUpdaterWorker
                .class, 1, TimeUnit.HOURS)
                .addTag(WeatherWorkerTag)
                .setConstraints(myConstraints).build();
        WorkManager.getInstance().enqueue(weatherUpdaterWork);
    }

    /******************************************************************************************/
    /** Managing ServiceManager **************************************************************************/
    /******************************************************************************************/
    /**
     * The service manager used to manage the services
     */
    private ServiceManagerIntf serviceManagerIntf;

    /**
     * To know if the service manager already exist
     */

    private boolean servcieManagerAlreadyExist = false;
    /**
     * @return the serviceManager
     */
    public final ServiceManagerIntf getServiceManager() {
        MyLog.d(TAG, "getServiceManager() called with: " + "");
        if (null == serviceManagerIntf) {
            serviceManagerIntf = Injector.getServiceManager(this);
            servcieManagerAlreadyExist = true;
        }
        return serviceManagerIntf;
    }

    /**
     * @return true if the ServiceManager is already instantiate
     */
    public final boolean serviceManagerAlreadyExist() {
        return servcieManagerAlreadyExist;
    }
    /******************************************************************************************/
    /** Managing destruction : the 1 second pattern**************************************************************************/
    /******************************************************************************************/
    //Listening for activities life cycle to trigger the serviceManager death
    /**
     * The AtomicBoolean to know if there is an active activity
     */
    private AtomicInteger isActivityAlive=new AtomicInteger(0);
    /**
     * To be called by activities when they go in their onStop method
     */
    public void onStartActivity() {
        MyLog.e(TAG, "onStartActivity() called with: " + "");
        isActivityAlive.set(isActivityAlive.get()+1);
        // launch the Runnable in 2 seconds
        mServiceKillerHandler.postDelayed(mServiceKiller, 1000);
    }
    /**
     * To be called by activities when they go in their onResume method
     */
    public void onStopActivity() {
        MyLog.e(TAG, "onStopActivity() called with: " + "");
        isActivityAlive.set(isActivityAlive.get()-1);
        // launch the Runnable in 2 seconds
        mServiceKillerHandler.postDelayed(mServiceKiller, 1000);
    }
    //The 1 second pattern to kill activityManager in 1 second
    /** * The Runnable that will look if there are no activity alive and launch the serviceManager death*/
    Runnable mServiceKiller;
    /** * The handler that manages the runnable */
    Handler mServiceKillerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            MyLog.e("MyApplication", "in the Handler isActivityAlive==" + isActivityAlive.get());
            if(isActivityAlive.get()==0) {
                //What you should do when application should die
                applicationShouldDie();
            }
        }

    };

    private void applicationShouldDie(){
        MyLog.e("MyApplication","applicationShouldDie is called");
        //first unregister broadcast
        if(connectivityChangedReceiever!=null) {
            unregisterReceiver(connectivityChangedReceiever);
            connectivityChangedReceiever = null;
        }
        //kill you serviceManager
        //call unbind and die
        killServiceManager();
        //Kill your DataCommunication
        Injector.getDataCommunication().releaseMemory();
        //die
    }

    /** * initialize the runnable */
    private void initializeServiceKiller() {
        mServiceKiller = new Runnable() {
            @Override
            public void run() {
                //one second later still no activity alive, so kill ServiceManager
                mServiceKillerHandler.dispatchMessage(mServiceKillerHandler.obtainMessage());
            }
        };

    }
    //Now the Killing method
	/*
	 * (non-Javadoc)
	 *
	 * @see android.app.Application#onLowMemory()
	 */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        killServiceManager();
    }

    /**
     * Kill the service manager and all the services managed by it
     */
    private void killServiceManager() {
        MyLog.e("MyApplication", "killServiceManager is called");
        if (null != serviceManagerIntf) {
            serviceManagerIntf.unbindAndDie();
            serviceManagerIntf =null;
            servcieManagerAlreadyExist=false;
        }

    }
    /***********************************************************
     *  Manage Connectivity
     **********************************************************/
    /***
     * BroadCastr Receiver to listen to connectivity changes
     */
    BroadcastReceiver connectivityChangedReceiever;
    /**
     * To know if there is a connection (wifi or GPRS)
     */
    private boolean isConnected = false;
    /**
     * To know if the connection is Wifi
     */
    private boolean isWifi = false;
    /**
     * To know the GRPS connectivity
     */
    private int telephonyType = 0;
    /**
     * Manage the connectivity state of the device
     */
    public void manageConnectivityState() {
        MyLog.e(TAG, "manageConnectivityState() called with: " + "");
        // Here we are because we receive either the boot completed event
        // either the connection changed event
        // either the wifi state changed event
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (null == networkInfo) {
            // This is the airplane mode
            isConnected = false;
            isWifi = false;
            telephonyType = 0;
        } else {
            switch (networkInfo.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    isConnected = true;
                    isWifi = true;
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    isConnected = true;
                    telephonyType = telephonyManager.getNetworkType();
                    // For information TelephonyType is one of the following
                    // switch (telephonyManager.getNetworkType()) {
                    // case TelephonyManager.NETWORK_TYPE_LTE:// 150Mb/s
                    // case TelephonyManager.NETWORK_TYPE_HSDPA:// 42Mb/s
                    // break;
                    // case TelephonyManager.NETWORK_TYPE_EDGE:// 215kb/s
                    // break;
                    // case TelephonyManager.NETWORK_TYPE_GPRS:// 45kb/s
                    // break;
                    // default:
                    // break;
                    // }
                    break;
                default:
                    break;
            }
        }
        notifyConnectivityChanged();
        MyLog.e("MyApplication", "manageConnectivityState called and return isConnected=" + isConnected + ", isWifi="
                + isWifi + ", telephonyType=" + telephonyType);
    }

    /**
     * This method is called when we switch from no connectivity to connected to the internet
     */
    private void notifyConnectivityChanged() {
        MyLog.d(TAG, "notifyConnectivityChanged() called with: " + "");
        // notify the listeners (if there is some because this method can be called even if no
        // activity alived)
        EventBus.getDefault().post(new ConnectivityChangeEvent(telephonyType,isConnected,isWifi));
    }
    /**
     * Return if the device is connected to internet
     *
     * @return the isConnected
     */
    public final boolean isConnected() {
        MyLog.e("MyApplication","isConnected : "+isConnected);
        return isConnected;
    }

    /**
     * Return if the device is connected to internet using WIFI
     *
     * @return the isWifi
     */
    public final boolean isWifi() {
        MyLog.d(TAG, "isWifi() called with: " + "");
        return isWifi;
    }

    /**
     * Return the connectivity type
     * (NETWORK_TYPE_LTE,NETWORK_TYPE_HSDPA,NETWORK_TYPE_EDGE,NETWORK_TYPE_GPRS)
     * when the device is connected to internet using GPRS
     *
     * @return the telephonyType
     */
    public final int getTelephonyType() {
        MyLog.d(TAG, "getTelephonyType() called with: " + "");
        return telephonyType;
    }


}

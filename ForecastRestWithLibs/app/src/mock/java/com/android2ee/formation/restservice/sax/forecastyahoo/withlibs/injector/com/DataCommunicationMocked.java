/**
 * <ul>
 * <li>DataCommunication</li>
 * <li>com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.com</li>
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

package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.injector.com;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.com.DataCommunicationIntf;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.current.FindCitiesResponse;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.current.WeatherData;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.forecast.Forecast;

/**
 * Created by Mathias Seguy - Android2EE on 24/02/2016.
 * This class aims to make the Http call according to the interface DataCommunicationIntf
 * It's based on the principle of NoConnectivity No Http call
 */
public class DataCommunicationMocked implements DataCommunicationIntf{
    /***********************************************************
     * Singleton Design Pattern
     **********************************************************/
    private static DataCommunicationMocked INSTANCE;
    public static DataCommunicationMocked getINSTANCE(){
        if(INSTANCE==null){
            INSTANCE=new DataCommunicationMocked();
        }
        return INSTANCE;
    }

    private DataCommunicationMocked() {
            //then according to your connectivity status, intialize your network communication
    }

    /**
     * To be called when the application died
     * This is the main problem of the singleton pattern
     * They live as long as the process live
     */
    public void releaseMemory(){
        INSTANCE=null;
    }
    /***********************************************************
     * Business method
     **********************************************************/


    private boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) MyApplication.instance.getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager telephonyManager = (TelephonyManager) MyApplication.instance.getSystemService(Context.TELEPHONY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (null == networkInfo) {
            // This is the airplane mode
            return false;
        } else {
            switch (networkInfo.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                case ConnectivityManager.TYPE_MOBILE:
                    return true;
                default:
                    return false;
            }
        }
    }

    @Override
    public FindCitiesResponse getCitiesByName(String cityName) {
        return null;
    }

    @Override
    public WeatherData getWeatherByCityServerId(long cityId) {
        return null;
    }

    @Override
    public Forecast getForecastByCityId(long cityId) {
        return null;
    }
}

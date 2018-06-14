/**
 * <ul>
 * <li>WeatherServiceMocked</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.service</li>
 * <li>10/04/2016</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.service;

import android.util.Log;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.transverse.DataGenerator;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.ServiceManagerIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.weather.WeatherServiceIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.WeatherDownloadedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.WeatherLoadedEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Mathias Seguy - Android2EE on 10/04/2016.
 */
public class WeatherServiceMocked implements WeatherServiceIntf{
    private static final String TAG = "WeatherServiceMocked";
    /**
     * Load the WeatherData in an asynchronous way
     *
     * @param cityId The id of the city associated with the forecasts
     */
    @Override
    public void loadCurrentWeatherAsync(int cityId) {
        WeatherLoadedEvent weatherLoadedEvent = new WeatherLoadedEvent(DataGenerator.getWeather(cityId),cityId);
        Log.e(TAG, "WeatherLoadedEvent posted" );
        EventBus.getDefault().post(weatherLoadedEvent);
    }

    /***********************************************************
     * Listening for downloaded data events
     * ********************************************************
     *
     * @param event
     */
    @Override
    public void onEvent(WeatherDownloadedEvent event) {
        //update the event
        if(event.getWeather()!=null) {
            WeatherLoadedEvent weatherLoadedEvent = new WeatherLoadedEvent(event.getWeather(),event.getCityId());
        }else{
            //there is no connection, do nothing dude
        }
    }

    /**
     * Clean your resource when your service die
     */
    @Override
    public void onDestroy() {

    }

    /**
     * Clean your resource when your service die
     *
     * @param srvManager
     */
    @Override
    public void onDestroy(ServiceManagerIntf srvManager) {

    }
}

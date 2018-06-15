/**
 * <ul>
 * <li>WeatherDataUpdaterTest</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.service</li>
 * <li>06/03/2016</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.service;

import android.test.AndroidTestCase;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.DataCheck;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.CityForecastDownloadedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.WeatherDownloadedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Mathias Seguy - Android2EE on 06/03/2016.
 */
public class WeatherDataUpdaterTest extends AndroidTestCase {

    public static final int CITY_ID = 2972315;

    public void setUp() throws Exception {
        super.setUp();
        MyLog.e("WeatherDataUpdaterTest", "setUp() is called");
        EventBus.getDefault().register(this);
    }
    public void tearDown() throws Exception {
        super.tearDown();
        MyLog.e("WeatherDataUpdaterTest", "tearDown() is called");
        EventBus.getDefault().unregister(this);
    }
    AtomicBoolean eventReceived,eventForecastReceived;

    /***********************************************************
     * Testing Downloading current weather
     **********************************************************/

    public void testDowloadCurrentWeatherASync() throws Exception {
        eventReceived=new AtomicBoolean(false);
        MyApplication.instance.getServiceManager().getWeatherUpdaterService().downloadCurrentWeatherAsync(CITY_ID);
        int timeout = 0;
        while(!eventReceived.get()){
            Thread.currentThread().sleep(1000);
            MyLog.e("WeatherDataUpdaterTest", "waiting for the event WeatherDownloadedEvent");

            if (timeout == 5) {
                fail("Timeout");
            }
            timeout++;
        }
    }

    @Subscribe
    public void onEvent(WeatherDownloadedEvent event){
        MyLog.e("WeatherDataUpdaterTest", "WeatherDownloadedEvent received");
        assertNotNull(event);
        DataCheck.getInstance().checkWeather(event.getWeather(),CITY_ID);
        eventReceived.set(true);
    }
    /***********************************************************
     * Testing Downloading forecast weather
     **********************************************************/
    public void testDownloadForecastWeatherAsync() throws Exception {
        eventForecastReceived=new AtomicBoolean(false);
        MyApplication.instance.getServiceManager().getWeatherUpdaterService().downloadForecastWeatherAsync(CITY_ID);
        int timeout = 0;
        while(!eventForecastReceived.get()){
            Thread.currentThread().sleep(100);
            MyLog.e("WeatherDataUpdaterTest", "waiting for the event CityForecastDownloadedEvent");
            if (timeout == 5) {
                fail("Timeout");
            }
            timeout++;
        }
    }

    @Subscribe
    public void onEvent(CityForecastDownloadedEvent event){
        MyLog.e("WeatherDataUpdaterTest", "CityForecastDownloadedEvent received");
        assertNotNull(event);
        DataCheck.getInstance().checkCityForecast(event.getCityForecast(),CITY_ID);
        eventForecastReceived.set(true);
    }
}

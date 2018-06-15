/**
 * <ul>
 * <li>WeatherServiceTest</li>
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
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.weather.WeatherDaoIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.Injector;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.DataCheck;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.WeatherLoadedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.DataGenerator;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Mathias Seguy - Android2EE on 06/03/2016.
 */
public class WeatherServiceTest extends AndroidTestCase {
    private Weather testedElement;
    private int testedCityId=11021974;

    public void setUp() throws Exception {
        super.setUp();
        MyLog.e("WeatherServiceTest", "setUp() is called");
        EventBus.getDefault().register(this);
    }
    public void tearDown() throws Exception {
        super.tearDown();
        MyLog.e("WeatherServiceTest", "tearDown() is called");
        EventBus.getDefault().unregister(this);
    }
    AtomicBoolean eventReceived;

    /***********************************************************
     * Testing Downloading current weather
     **********************************************************/

    public void testLoadCurrentWeatherAsync() throws Exception {
        //let sugarOrm initialize itself
        Thread.currentThread().sleep(100);
        //insure the element you are testing is in the database
        testedElement=DataGenerator.getWeather(testedCityId);
        WeatherDaoIntf wdao= Injector.getDaoManager().getWeatherDao();
        wdao.insertOrUpdate(testedElement);//the city id is 11021974
        wdao=null;

        eventReceived=new AtomicBoolean(false);
        MyApplication.instance.getServiceManager().getWeatherService().loadCurrentWeatherAsync(testedCityId);
        int timeout = 0;

        while(!eventReceived.get()){
            Thread.currentThread().sleep(1000);
            MyLog.e("WeatherServiceTest", "waiting for the event WeatherLoadedEvent");
            if (timeout == 5) {
                fail("Timeout");
            }
            timeout++;
        }
    }

    @Subscribe
    public void onEvent(WeatherLoadedEvent event){
        MyLog.e("WeatherServiceTest", "WeatherLoadedEvent received");
        assertNotNull(event);
        eventReceived.set(true);
        try{
            DataCheck.getInstance().checkWeather(event.getWeather(), testedCityId);
        }finally {
            WeatherDaoIntf wdao = Injector.getDaoManager().getWeatherDao();
            wdao.delete(testedElement);//the city id is 11021974
            wdao = null;
        }
    }
}

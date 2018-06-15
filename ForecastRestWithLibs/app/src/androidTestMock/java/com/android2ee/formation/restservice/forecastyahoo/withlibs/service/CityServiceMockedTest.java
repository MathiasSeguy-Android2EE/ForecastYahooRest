/**
 * <ul>
 * <li>CityServiceTest</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.service</li>
 * <li>11/04/2016</li>
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
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.transverse.DataGenerator;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.DataCheck;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.DeepDataCheck;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.CitiesLoadedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.CityAddedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.FindCitiesResponseEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.City;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Mathias Seguy - Android2EE on 11/04/2016.
 * So the goal here is to test the serviceManager in its mocked mode
 * That means you are in the flavor MockDebug
 * And you use the ServiceManager (the real one)
 */
public class CityServiceMockedTest extends AndroidTestCase {

    public static final String TAG = "CityServiceMockedTest";
    private String testedCityName = "Toulouse";

    public void setUp() throws Exception {
        super.setUp();
        MyLog.e(TAG, "setUp() is called");
        EventBus.getDefault().register(this);
    }

    public void tearDown() throws Exception {
        super.tearDown();
        MyLog.e(TAG, "tearDown() is called");
        EventBus.getDefault().unregister(this);
    }

    AtomicBoolean eventReceived, event1Received;

    /***********************************************************
     * Testing Downloading current city
     **********************************************************/

    public void testFindCityByNameAsync() throws Exception {
        //track entrance
        MyLog.e(TAG, "testFindCityByNameAsync() has been called");
        eventReceived = new AtomicBoolean(false);
        //be sure to use the real ServiceManager (don't myApp.getServiceManager
        // because you'll have the MockedServiceManager)
        ServiceManager serviceManager = new ServiceManager(MyApplication.instance);
        serviceManager.getCityService().findCityByNameAsync(testedCityName);
        while (!eventReceived.get()) {
            Thread.currentThread().sleep(500);
            MyLog.e(TAG, "waiting for the event FindCitiesResponseEvent in testFindCityByNameAsync");
        }
    }

    public void testReloadFindCitiesResponse() throws Exception {
        MyLog.e(TAG, "testReloadFindCitiesResponse() has been called");
        eventReceived = new AtomicBoolean(false);
        //be sure to use the real ServiceManager (don't myApp.getServiceManager
        // because you'll have the MockedServiceManager)
        ServiceManager serviceManager = new ServiceManager(MyApplication.instance);
        serviceManager.getCityService().findCityByNameAsync(testedCityName);
        while (!eventReceived.get()) {
            Thread.currentThread().sleep(500);
            MyLog.e(TAG, "waiting for the event FindCitiesResponseEvent in testReloadFindCitiesResponse");
        }
    }

    @Subscribe
    public void onEvent(FindCitiesResponseEvent event) {
        MyLog.e(TAG, "FindCitiesResponseEvent received");
        MyLog.e(TAG, "FindCitiesResponseEvent received" + event.getFindCitiesResponse().getCities().get(0));
        assertNotNull(event);
        assertNotNull(event.getFindCitiesResponse());
        assertNotNull(event.getFindCitiesResponse().getCities());
        assertNotNull(event.getFindCitiesResponse().getCities().get(0));
        DataCheck.getInstance().checkCity(event.getFindCitiesResponse().getCities().get(0));
        DeepDataCheck.getInstance().checkCity(DataGenerator.getFindCitiesResponse().getCities().get(0), event.getFindCitiesResponse().getCities().get(0));

        eventReceived.set(true);
    }


    /***********************************************************
     * Testing Adding city
     **********************************************************/
    public void testAddCityAsync() throws Exception {
        //track entrance
        MyLog.e(TAG, "testAddCityAsync() has been called on thread "+Thread.currentThread().getName());
        eventReceived = new AtomicBoolean(false);
        //be sure to use the real ServiceManager (don't myApp.getServiceManager
        // because you'll have the MockedServiceManager)
        ServiceManager serviceManager = new ServiceManager(MyApplication.instance);
        serviceManager.getCityService().addCityAsync(DataGenerator.getCity());
        while (!eventReceived.get()) {
            Thread.currentThread().sleep(500);
            MyLog.e(TAG, "waiting for the event FindCitiesResponseEvent in testAddCityAsync");
        }
    }

    @Subscribe
    public void onEvent(CityAddedEvent event) {
        MyLog.e(TAG, "CityAddedEvent received" + event.getCity()+" on thread "+Thread.currentThread().getName());
        DataCheck.getInstance().checkCity(event.getCity());
        DeepDataCheck.getInstance().checkCity(DataGenerator.getCity(), event.getCity());
        eventReceived.set(true);
    }
    /***********************************************************
     * Testing loading a bunch of cities
     **********************************************************/
    public void testLoadCitiesAsync() throws Exception {
        //track entrance
        MyLog.e(TAG, "testAddCityAsync() has been called");
        eventReceived = new AtomicBoolean(false);
        //be sure to use the real ServiceManager (don't myApp.getServiceManager
        // because you'll have the MockedServiceManager)
        ServiceManager serviceManager = new ServiceManager(MyApplication.instance);
        serviceManager.getCityService().loadCitiesAsync();
        while (!eventReceived.get()) {
            Thread.currentThread().sleep(500);
            MyLog.e(TAG, "waiting for the event FindCitiesResponseEvent in testAddCityAsync");
        }
    }

    @Subscribe
    public void onEvent(CitiesLoadedEvent event) {
        MyLog.e(TAG, "CityAddedEvent received" + event.getCities());
        ArrayList<City> cities= (ArrayList<City>) event.getCities();
        //There should be 4 cities
        assertTrue(cities.size()==4);
        for(int i=0;i<4;i++){
            DataCheck.getInstance().checkCity(cities.get(i));
        }
        DeepDataCheck.getInstance().checkCity(DataGenerator.getCity(1102), cities.get(0));
        DeepDataCheck.getInstance().checkCity(DataGenerator.getCity(1312), cities.get(1));
        DeepDataCheck.getInstance().checkCity(DataGenerator.getCity(0411), cities.get(2));
        DeepDataCheck.getInstance().checkCity(DataGenerator.getCity(1911), cities.get(3));
        eventReceived.set(true);
    }

    /***********************************************************
     * Testing deleting city
     **********************************************************/
    public void testDeleteCityAsync() throws Exception {
        //track entrance
        MyLog.e(TAG, "testAddCityAsync() has been called");
        eventReceived = new AtomicBoolean(false);
        //be sure to use the real ServiceManager (don't myApp.getServiceManager
        // because you'll have the MockedServiceManager)
        ServiceManager serviceManager = new ServiceManager(MyApplication.instance);
        serviceManager.getCityService().deleteCityAsync(DataGenerator.getCity());
        //it's a stupid test... no assert possible ... :'(
    }

}


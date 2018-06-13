/**
 * <ul>
 * <li>CityServiceTest</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.service</li>
 * <li>08/03/2016</li>
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
import android.util.Log;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.DataCheck;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.FindCitiesResponseEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.FindCitiesResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Mathias Seguy - Android2EE on 08/03/2016.
 */
public class CityServiceTest extends AndroidTestCase {
    private FindCitiesResponse testedElement;
    private String testedCityName="Toulouse";

    public void setUp() throws Exception {
        super.setUp();
        Log.e("CityServiceTest", "setUp() is called");
        EventBus.getDefault().register(this);
    }
    public void tearDown() throws Exception {
        super.tearDown();
        Log.e("CityServiceTest", "tearDown() is called");
        EventBus.getDefault().unregister(this);
    }
    AtomicBoolean eventReceived;
    /***********************************************************
     * Testing Downloading current weather
     **********************************************************/

    public void testFindCityByNameAsync() throws Exception {
        eventReceived=new AtomicBoolean(false);
        MyApplication.instance.getServiceManager().getCityService().findCityByNameAsync(testedCityName);
        int timeout = 0;
        while(!eventReceived.get()){
            Thread.currentThread().sleep(500);
            Log.e("CityServiceTest", "waiting for the event FindCitiesResponseEvent");
            if (timeout == 5) {
                fail("Timeout");
            }
            timeout++;
        }
    }

    @Subscribe
    public void onEvent(FindCitiesResponseEvent event){
        Log.e("CityServiceTest", "FindCitiesResponseEvent received");
        Log.e("CityServiceTest", "FindCitiesResponseEvent received" + event.getFindCitiesResponse().getCities().get(0));
        assertNotNull(event);
        assertNotNull(event.getFindCitiesResponse());
        assertNotNull(event.getFindCitiesResponse().getCities());
        assertNotNull(event.getFindCitiesResponse().getCities().get(0));
        DataCheck.getInstance().checkCity(event.getFindCitiesResponse().getCities().get(0));

        eventReceived.set(true);
    }
}

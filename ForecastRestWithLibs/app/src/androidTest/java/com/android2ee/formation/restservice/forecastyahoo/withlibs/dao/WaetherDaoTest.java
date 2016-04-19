/**
 * <ul>
 * <li>WaetherdaoTest</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.dao</li>
 * <li>07/03/2016</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.dao;

import android.test.AndroidTestCase;
import android.util.Log;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.weather.WeatherDaoIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.Injector;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.DataGenerator;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Clouds;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Coordinates;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Rain;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Snow;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.WeatherDetails;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Wind;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.Ephemeris;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.weathermetadata.WeatherMetaData;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.weathermetadata.WeatherMetaData_Weather;

import java.util.List;

/**
 * Created by Mathias Seguy - Android2EE on 07/03/2016.
 */
public class WaetherDaoTest extends AndroidTestCase {
    private static final String TAG = "WaetherdaoTest";
    private int testedCityId=11021974;

    public void setUp() throws Exception {
        super.setUp();
        Log.i("DataCommunicationTest", "setUp() is called");

    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCRUD()throws Exception {
        //let sugarOrm initialize itself
        Thread.currentThread().sleep(100);
        //insure the element you are testing is in the database
        Weather testedElement= DataGenerator.getWeather(testedCityId);
        WeatherDaoIntf wdao= Injector.getDaoManager().getWeatherDao();
        //test Load all
        int initialElementNumber=0,afterDeletionElementNumber=0;
        List<Weather> weathers = wdao.findAllCurrentWeatherFor(testedCityId);
        if (weathers != null) {
            initialElementNumber=weathers.size();
        }
        //Test insertion
        wdao.insertOrUpdate(testedElement);//the city id is 11021974
        Log.i(TAG,"testedElement id ="+testedElement.getId());
        //test loading
        Weather foundElement=  wdao.findCurrentWeatherFor(testedCityId);
        Log.i(TAG,"foundElement id ="+foundElement.getId());
        //also test the equality of objects:
        assertEquals(testedElement.toString(), foundElement.toString());
        assertEquals(testedElement.getTimeStampUTC(),foundElement.getTimeStampUTC());
        assertEquals(testedElement.getCityId(),foundElement.getCityId());
        assertEquals(testedElement.getClouds(),foundElement.getClouds());
        assertEquals(testedElement.getCoordinates(),foundElement.getCoordinates());
        assertEquals(testedElement.getEphemeris(),foundElement.getEphemeris());
        assertEquals(testedElement.getName(),foundElement.getName());
        assertEquals(testedElement.getRain(), foundElement.getRain());
        assertEquals(testedElement.getSnow(),foundElement.getSnow());
        assertEquals(testedElement.getWeatherDetails(),foundElement.getWeatherDetails());
        assertEquals(testedElement.getWeatherMetaData(),foundElement.getWeatherMetaData());
        assertEquals(testedElement.getWind(), foundElement.getWind());
        assertEquals(testedElement, foundElement);
        //test update
        foundElement.setCityId(13121974);
        wdao.insertOrUpdate(foundElement);
        Weather foundElementUpdated=  wdao.findCurrentWeatherFor(13121974);
        Log.e(TAG, "foundElementUpdated id =" + foundElementUpdated.getId());
        assertEquals(foundElement.getId(), foundElementUpdated.getId());
        assertEquals(foundElementUpdated.getCityId(), 13121974);
        //test deleting
        wdao.delete(foundElement);
        weathers = wdao.findAllCurrentWeatherFor(testedCityId);
        if (weathers != null) {
            afterDeletionElementNumber=weathers.size();
        }
        assertEquals(initialElementNumber, afterDeletionElementNumber);
        //then test complete deletion (recursive deletion of sub object)
        assertNull(Clouds.findById(Clouds.class, testedElement.getClouds().getId()));
        assertNull(Coordinates.findById(Coordinates.class, testedElement.getCoordinates().getId()));
        assertNull(Ephemeris.findById(Ephemeris.class, testedElement.getEphemeris().getId()));
        assertNull(Rain.findById(Rain.class, testedElement.getRain().getId()));
        assertNull(Snow.findById(Snow.class, testedElement.getSnow().getId()));
        assertNull(WeatherDetails.findById(WeatherDetails.class, testedElement.getWeatherDetails().getId()));
        assertNull(Wind.findById(Wind.class, testedElement.getWind().getId()));
        for(WeatherMetaData_Weather metadata:testedElement.getWeatherMetaData()){
            assertNull(WeatherMetaData_Weather.findById(WeatherMetaData_Weather.class, ((WeatherMetaData) metadata).getId()));
        }
    }
}

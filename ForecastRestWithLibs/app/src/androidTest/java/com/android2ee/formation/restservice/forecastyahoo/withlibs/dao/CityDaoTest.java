/**
 * <ul>
 * <li>CityDaoTest</li>
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

import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.city.CityDaoIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.Injector;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.DataGenerator;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Clouds;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Coordinates;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Ephemeris;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.WeatherDetails;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Wind;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.weathermetadata.WeatherMetaData;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.weathermetadata.WeatherMetaData_City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;

import java.util.List;

/**
 * Created by Mathias Seguy - Android2EE on 07/03/2016.
 */
public class CityDaoTest  extends AndroidTestCase {
    private static final String TAG = "WaetherdaoTest";
    private int testedCityId;

    public void setUp() throws Exception {
        super.setUp();
        MyLog.i("DataCommunicationTest", "setUp() is called");

    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCRUD()throws Exception {
        //let sugarOrm initialize itself
        Thread.currentThread().sleep(100);
        //insure the element you are testing is in the database
        City toulouse=DataGenerator.getCity();
        testedCityId=toulouse.getCityId();
        CityDaoIntf cityDaoIntf = Injector.getDaoManager().getCityDao();
        //test Load all
        int initialElementNumber=0,afterDeletionElementNumber=0;
        List<City> cities = cityDaoIntf.findAll();
        if (cities != null) {
            initialElementNumber=cities.size();
        }
        //Test insertion
        cityDaoIntf.insertOrUpdate(toulouse);//the city id is 11021974
        MyLog.i(TAG,"testedElement id ="+toulouse.getId());
        //test loading
        City foundElement=  cityDaoIntf.findCity(testedCityId);
        MyLog.i(TAG, "foundElement id =" + foundElement.getId());
        MyLog.i(TAG, "toulouse =" + toulouse);
        MyLog.i(TAG, "foundElement =" + foundElement);
        //also test the equality of objects:
        assertEquals(toulouse.toString(), foundElement.toString());
        assertEquals(toulouse.getTimeStamp(),foundElement.getTimeStamp());
        assertEquals(toulouse.getCityId(),foundElement.getCityId());
        assertEquals(toulouse.getClouds(),foundElement.getClouds());
        assertEquals(toulouse.getCoordinates(),foundElement.getCoordinates());
        assertEquals(toulouse.getEphemeris(),foundElement.getEphemeris());
        assertEquals(toulouse.getName(),foundElement.getName());
        assertEquals(toulouse.getWeatherDetails(),foundElement.getWeatherDetails());
        assertEquals(toulouse.getMetaDataList(),foundElement.getMetaDataList());
        assertEquals(toulouse.getWind(), foundElement.getWind());
        assertEquals(toulouse, foundElement);
        //test update (pure update)
        foundElement.setName("Ma Ville à moi");
        foundElement.setCityId(13121974);
        cityDaoIntf.insertOrUpdate(foundElement);
        City foundElementUpdated=  cityDaoIntf.findCity(13121974);
        MyLog.i(TAG, "foundElementUpdated id =" + foundElementUpdated.getId());
        assertEquals(foundElement.getId(), foundElementUpdated.getId());
        assertEquals(foundElementUpdated.getCityId(), 13121974);
        //test deleting
        cityDaoIntf.delete(foundElement);
        cities = cityDaoIntf.findAll();
        if (cities != null) {
            afterDeletionElementNumber=cities.size();
        }
        assertEquals(initialElementNumber, afterDeletionElementNumber);
        //then test complete deletion (recursive deletion of sub object)
        assertNull(Clouds.findById(Clouds.class, toulouse.getClouds().getId()));
        assertNull(Coordinates.findById(Coordinates.class, toulouse.getCoordinates().getId()));
        assertNull(Ephemeris.findById(Ephemeris.class, toulouse.getEphemeris().getId()));
        assertNull(WeatherDetails.findById(WeatherDetails.class, toulouse.getWeatherDetails().getId()));
        assertNull(Wind.findById(Wind.class, toulouse.getWind().getId()));
        for(WeatherMetaData_City metadata:toulouse.getMetaDataList()){
            assertNull(WeatherMetaData_City.findById(WeatherMetaData_City.class, ((WeatherMetaData) metadata).getId()));
        }
    }
}

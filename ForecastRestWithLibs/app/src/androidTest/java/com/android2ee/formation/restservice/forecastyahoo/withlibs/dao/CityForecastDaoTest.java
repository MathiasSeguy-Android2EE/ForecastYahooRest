/**
 * <ul>
 * <li>CityForecastDaoTest</li>
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

import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.cityforecast.CityForecastDaoIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.Injector;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.DataGenerator;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Coordinates;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.forecast.CityForecast;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.forecast.WeatherForecast;

import java.util.List;

/**
 * Created by Mathias Seguy - Android2EE on 07/03/2016.
 */
public class CityForecastDaoTest extends AndroidTestCase {
    private static final String TAG = "CityForecastDaoTest";

    int initialElementNumber=0,afterDeletionElementNumber=0;
    CityForecast foundElement,testedElement,foundElementUpdated;
    long begin,end;
    private int testedCityId=11021974;

    public void setUp() throws Exception {
        super.setUp();
        Log.i("DataCommunicationTest", "setUp() is called");

    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCRUD()throws Exception{
        long begin = System.currentTimeMillis();
        //let sugarOrm initialize itself
        Thread.currentThread().sleep(300);
        testedElement=DataGenerator.getCityForecast(testedCityId);
        CityForecastDaoIntf cfDao= Injector.getDaoManager().getCityForecastDao();
        //test Load all
        loadAll(cfDao);
        List<CityForecast> cityForecasts;
        //Test insertion
        insertion(testedElement, cfDao);
        //test loading
        loadingCurrent(cfDao);
        //also test the equality of objects:
//        assertEquals(testedElement.toString(), foundElement.toString());
        fieldsEquality();
        equality();
        //test update
        update(cfDao);
        //test deleting
        deletion(cfDao);
        long end = System.currentTimeMillis();
        Log.i(TAG, "testCRUD() duration " + (end - begin) + "ms");
    }

    private void deletion(CityForecastDaoIntf cfDao) {
        begin=System.currentTimeMillis();
        List<CityForecast> cityForecasts;
        cfDao.delete(foundElement);
        cityForecasts = cfDao.findAllForecastFor(testedCityId);
        if (cityForecasts != null) {
            afterDeletionElementNumber=cityForecasts.size();
        }
        assertEquals(initialElementNumber, afterDeletionElementNumber);
        //then test complete deletion (recursive deletion of sub object)
        assertNull(Coordinates.findById(Coordinates.class, testedElement.getCoordinates().getId()));
        for(WeatherForecast wf:testedElement.getWeatherForecasts()){
            assertNull(WeatherForecast.findById(WeatherForecast.class, wf.getId()));
        }
        end=System.currentTimeMillis();
        Log.w(TAG, "deletion() duration " + (end - begin) +"ms");
    }

    private void update(CityForecastDaoIntf cfDao) {
        begin = System.currentTimeMillis();
        foundElement.setCityId(13121974);
        cfDao.insertOrUpdate(foundElement);
        foundElementUpdated=  cfDao.findCurrentForecastFor(13121974);
        Log.i(TAG, "foundElementUpdated id =" + foundElementUpdated.getId());
        assertEquals(foundElement.getId(), foundElementUpdated.getId());
        assertEquals(foundElementUpdated.getCityId(), 13121974);
        end=System.currentTimeMillis();
        Log.w(TAG, "update() duration " + (end - begin) +"ms");
    }

    private void equality() {
        begin=System.currentTimeMillis();
        assertEquals(testedElement, foundElement);
        end=System.currentTimeMillis();
        Log.w(TAG, "equality() duration " + (end - begin) +"ms");
    }

    private void fieldsEquality() {
        begin=System.currentTimeMillis();
        assertEquals(testedElement.getCityId(), foundElement.getCityId());
        assertEquals(testedElement.getCoordinates(),foundElement.getCoordinates());
        assertEquals(testedElement.getCoordinates(),foundElement.getCoordinates());
        assertEquals(testedElement.getCountry(),foundElement.getCountry());
        assertEquals(testedElement.getCod(),foundElement.getCod());
        assertEquals(testedElement.getName(),foundElement.getName());
        assertEquals(testedElement.getWeatherForecasts(),foundElement.getWeatherForecasts());
        end=System.currentTimeMillis();
        Log.w(TAG, "fieldsEquality() duration " + (end - begin) +"ms");
    }

    private void loadingCurrent(CityForecastDaoIntf cfDao) {
        begin=System.currentTimeMillis();
        foundElement=  cfDao.findCurrentForecastFor(testedCityId);
        Log.i(TAG, "foundElement id =" + foundElement.getId());
        end=System.currentTimeMillis();
        Log.w(TAG, "loadingCurrent() duration " + (end - begin) +"ms");
    }

    private void insertion(CityForecast testedElement, CityForecastDaoIntf cfDao) {
        begin=System.currentTimeMillis();
        cfDao.insertOrUpdate(testedElement);//the city id is 11021974
        Log.i(TAG, "testedElement id =" + testedElement.getId());
        end=System.currentTimeMillis();
        Log.w(TAG, "insertion() duration " + (end - begin) + "ms");
    }

    private void loadAll(CityForecastDaoIntf cfDao) {
        begin=System.currentTimeMillis();
        List<CityForecast> cityForecasts = cfDao.findAllForecastFor(testedCityId);
        if (cityForecasts != null) {
            initialElementNumber=cityForecasts.size();
        }
        end=System.currentTimeMillis();
        Log.w(TAG, "loadAll() duration " + (end - begin) +"ms");
    }
}

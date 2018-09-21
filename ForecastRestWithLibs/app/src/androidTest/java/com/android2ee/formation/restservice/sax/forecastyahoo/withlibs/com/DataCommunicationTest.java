package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.com;

import android.support.test.runner.AndroidJUnit4;

import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.injector.Injector;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.DataCheck;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.Weather;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.current.City;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.current.FindCitiesResponse;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.utils.MyLog;

import org.junit.runner.RunWith;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Created by Mathias Seguy - Android2EE on 24/02/2016.
 */
@RunWith(AndroidJUnit4.class)
public class DataCommunicationTest {

    public static final int CITY_ID = 2972315;

    public void setUp() throws Exception {
        super.setUp();
        MyLog.e("DataCommunicationTest", "setUp() is called");
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Testing the findCityByName
     * @throws Exception
     */
    public void testFindCityByName() throws Exception {
        if(MyApplication.instance.isConnected()) {
            MyLog.i("DataCommunicationTest", "testFindCityByName isConnected=true");
            FindCitiesResponse citiesFound = Injector.getDataCommunication().findCityByName("Toulouse");

            assertEquals("like",citiesFound.getMessage());
            assertEquals(200, citiesFound.getCod());
//            assertEquals(1, citiesFound.getCount());
            City toulouse = citiesFound.getCities().get(0);
            MyLog.i("DataCommunicationTest", "testFindCityByName find toulouse: " + toulouse.toString());
            //check the main information
            assertEquals(CITY_ID,toulouse.getCityId());
            assertEquals("Toulouse", toulouse.getName());
            assertEquals(1.44367f, toulouse.getCoordinates().getLon());
            assertEquals(43.604259f, toulouse.getCoordinates().getLat());
            assertTrue(toulouse.getDateTimeUnix() != 0);
            //check weather details
            DataCheck.getInstance().checkWeatherDetails(toulouse.getWeatherDetails());

            //check wind
            DataCheck.getInstance().checkWind(toulouse.getWind());
            //ephemeris
            assertNotNull(toulouse.getEphemeris());
            assertEquals("FR",toulouse.getEphemeris().getCountry());
            //clouds
            assertNotNull(toulouse.getClouds());
        }else{
            MyLog.i("DataCommunicationTest", "testFindCityByName isConnected=false");
            FindCitiesResponse citiesFound = Injector.getDataCommunication().findCityByName("Toulouse");
            assertNull(citiesFound);
        }

    }




    /**
     * Testing the findWeatherByCityServerId
     * @throws Exception
     */
    public void testFindWeatherByCityId() throws Exception {
        if(MyApplication.instance.isConnected()) {
            MyLog.i("DataCommunicationTest", "testFindWeatherByCityId isConnected=true");
            Weather weather= Injector.getDataCommunication().findWeatherByCityId(CITY_ID);
            DataCheck.getInstance().checkWeather(weather,CITY_ID);
        }else{
            MyLog.i("DataCommunicationTest", "testFindWeatherByCityId isConnected=true");
            Weather weather=Injector.getDataCommunication().findWeatherByCityId(CITY_ID);
            assertNull(weather);
        }
    }



    public void testFindForecastByCityId() throws Exception {
        if(MyApplication.instance.isConnected()) {
            MyLog.i("DataCommunicationTest", "testFindForecastByCityId isConnected=true");
            CityForecast cityForecast=Injector.getDataCommunication().findForecastByCityId(CITY_ID);
        }else{
            MyLog.i("DataCommunicationTest", "testFindWeatherByCityId isConnected=true");
            CityForecast forecast=Injector.getDataCommunication().findForecastByCityId(CITY_ID);
            assertNull(forecast);
        }
    }

}
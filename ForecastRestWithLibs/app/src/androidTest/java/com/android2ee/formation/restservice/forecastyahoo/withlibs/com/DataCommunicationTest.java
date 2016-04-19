package com.android2ee.formation.restservice.forecastyahoo.withlibs.com;

import android.test.AndroidTestCase;
import android.util.Log;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.Injector;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.DataCheck;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.FindCitiesResponse;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.forecast.CityForecast;


/**
 * Created by Mathias Seguy - Android2EE on 24/02/2016.
 */
public class DataCommunicationTest extends AndroidTestCase {

    public static final int CITY_ID = 2972315;

    public void setUp() throws Exception {
        super.setUp();
        Log.e("DataCommunicationTest", "setUp() is called");
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
            Log.i("DataCommunicationTest", "testFindCityByName isConnected=true");
            FindCitiesResponse citiesFound = Injector.getDataCommunication().findCityByName("Toulouse");

            assertEquals("like",citiesFound.getMessage());
            assertEquals(200, citiesFound.getCod());
            assertEquals(1, citiesFound.getCount());
            City toulouse = citiesFound.getCities().get(0);
            Log.i("DataCommunicationTest", "testFindCityByName find toulouse: " + toulouse.toString());
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
            Log.i("DataCommunicationTest", "testFindCityByName isConnected=false");
            FindCitiesResponse citiesFound = Injector.getDataCommunication().findCityByName("Toulouse");
            assertNull(citiesFound);
        }

    }




    /**
     * Testing the findWeatherByCityId
     * @throws Exception
     */
    public void testFindWeatherByCityId() throws Exception {
        if(MyApplication.instance.isConnected()) {
            Log.i("DataCommunicationTest", "testFindWeatherByCityId isConnected=true");
            Weather weather= Injector.getDataCommunication().findWeatherByCityId(CITY_ID);
            DataCheck.getInstance().checkWeather(weather,CITY_ID);
        }else{
            Log.i("DataCommunicationTest", "testFindWeatherByCityId isConnected=true");
            Weather weather=Injector.getDataCommunication().findWeatherByCityId(CITY_ID);
            assertNull(weather);
        }
    }



    public void testFindForecastByCityId() throws Exception {
        if(MyApplication.instance.isConnected()) {
            Log.i("DataCommunicationTest", "testFindForecastByCityId isConnected=true");
            CityForecast cityForecast=Injector.getDataCommunication().findForecastByCityId(CITY_ID);
        }else{
            Log.i("DataCommunicationTest", "testFindWeatherByCityId isConnected=true");
            CityForecast forecast=Injector.getDataCommunication().findForecastByCityId(CITY_ID);
            assertNull(forecast);
        }
    }

}
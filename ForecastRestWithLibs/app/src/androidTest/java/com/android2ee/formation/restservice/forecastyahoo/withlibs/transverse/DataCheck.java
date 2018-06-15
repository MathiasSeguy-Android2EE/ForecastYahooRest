/**
 * <ul>
 * <li>DataCheck</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.WeatherDetails;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Wind;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.forecast.CityForecast;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.weathermetadata.WeatherMetaData;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.weathermetadata.WeatherMetaData_Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by Mathias Seguy - Android2EE on 06/03/2016.
 */
public class DataCheck extends TestCase {
    private static DataCheck instance=null;
    public static DataCheck getInstance(){
        if(instance==null){
            instance=new DataCheck();
        }
        return instance;
    }


    /**
     * Check for city foecast consistency
     * @param cityForecast
     */
    public void checkCityForecast(CityForecast cityForecast,int cityId) {
        MyLog.i("DataCheck", "testFindForecastByCityId find" + cityForecast.toString());
        assertNotNull(cityForecast);
        assertEquals("Toulouse", cityForecast.getName());
        assertEquals("FR", cityForecast.getCountry());
        assertEquals(cityId, cityForecast.getCityId());
        assertNotNull(cityForecast.getCod());
        assertNotNull(cityForecast.getMessage());
        //check coordinates
        assertNotNull(cityForecast.getCoordinates());
        assertEquals(1.44367f, cityForecast.getCoordinates().getLon());
        assertEquals(43.60426f, cityForecast.getCoordinates().getLat());
        //weather forecast
        assertNotNull(cityForecast.getWeatherForecasts());
        assertTrue(cityForecast.getWeatherForecasts().get(0).getDateTimeStampUtc() != 0);
        //check weather detail
        checkWeatherDetails(cityForecast.getWeatherForecasts().get(0).getWeatherDetails());
        //check weather metadata
        checkWeatherMetaData(cityForecast.getWeatherForecasts().get(0).getWeathers());
        assertNotNull(cityForecast.getWeatherForecasts().get(0).getClouds());
    }

    /**
     * Check for the consistency of the weather object
     */
    public void checkWeather(Weather weather,int fakeCityId) {
        assertNotNull(weather);
        assertEquals("Toulouse", weather.getName());
        assertEquals(fakeCityId, weather.getCityId());
        assertTrue(weather.getCod() != 0);
        assertTrue(weather.getTimeStampUTC() != 0);
        assertNotNull(weather.getBase());
        MyLog.i("DataCheck", "testFindWeatherByCityId find: " + weather.toString());
        //check coordinates
        assertNotNull(weather.getCoordinates());
        assertEquals(1.44f, weather.getCoordinates().getLon());
        assertEquals(43.6f, weather.getCoordinates().getLat());
        //check weatherDetail
        List<WeatherMetaData_Weather> weathers = weather.getWeatherMetaData();
        checkWeatherMetaData(weathers);
        //check for wind
        checkWind(weather.getWind());
        //checks for clouds, rains, snow
        assertNotNull(weather.getClouds());
        assertNotNull(weather.getRain());
        assertNotNull(weather.getSnow());
        //checks ephemeris
        assertNotNull(weather.getEphemeris());
        assertTrue(weather.getEphemeris().getSunrise() != 0);
        assertTrue(weather.getEphemeris().getSunset() != 0);
        assertTrue(weather.getEphemeris().getMessage() != 0);
        assertEquals("FR", weather.getEphemeris().getCountry());
    }

    /**
     * Check WeatherDetail object
     */
    public void checkWeatherDetails(WeatherDetails weatherDetails) {
        //check Weather detail
        assertNotNull(weatherDetails);
        assertTrue(weatherDetails.getHumidity() != 0);
        assertTrue(weatherDetails.getPressure() != 0);
        assertTrue(weatherDetails.getTemp() != 0);
    }

    /**
     * Check for the wond values
     * @param wind
     */
    public void checkWind(Wind wind) {
        assertNotNull(wind);
        assertTrue(wind.getDeg() != 0);
        assertTrue(wind.getSpeed() != 0);
    }

    /**
     * Check for ytje WeatherMetaData value
     * @param weathers
     */
    private void checkWeatherMetaData(List<? extends WeatherMetaData> weathers) {
        assertNotNull(weathers);
        assertNotNull(weathers.get(0));
        assertTrue(weathers.get(0).getWeatherConditionId() != 0);
        assertNotNull(weathers.get(0).getMain());
        assertNotNull(weathers.get(0).getDescription());
        assertNotNull(weathers.get(0).getIcon());
    }

    /**
     * Check for the validity of a City object
      * @param city
     */
    public void checkCity(City city){
        assertNotNull(city);
        assertNotNull(city.getName());
        //check coordinates
        assertNotNull(city.getCoordinates());
        assertEquals(1.44367f, city.getCoordinates().getLon());
        assertEquals(43.60426f, city.getCoordinates().getLat());
        checkWeatherDetails(city.getWeatherDetails());
        //check for wind
        checkWind(city.getWind());
        //checks for clouds
        assertNotNull(city.getClouds());
        //checks ephemeris
        assertNotNull(city.getEphemeris());
        assertEquals("FR", city.getEphemeris().getCountry());
    }

}

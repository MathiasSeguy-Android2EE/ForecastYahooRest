/**
 * <ul>
 * <li>WeatherDataUpdaterMocked</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.service</li>
 * <li>10/04/2016</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.service;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.transverse.DataGenerator;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.ServiceManagerIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.weather.WeatherDataUpdaterIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.CityForecastDownloadedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.WeatherDownloadedEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Mathias Seguy - Android2EE on 10/04/2016.
 */
public class WeatherDataUpdaterMocked implements WeatherDataUpdaterIntf {
    /**
     * Download the current Weather in an asynchronous way
     *
     * @param cityId The id of the city associated with the forecasts
     */
    @Override
    public void downloadCurrentWeatherAsync(int cityId) {
        WeatherDownloadedEvent weatherDownloadedEvent= new WeatherDownloadedEvent(DataGenerator.getWeather(cityId),cityId);
        EventBus.getDefault().post(weatherDownloadedEvent);
    }

    /**
     * Download the current Weather in an synchronous way
     *
     * @param cityId The id of the city associated with the forecasts
     */
    @Override
    public void downloadCurrentWeatherSync(int cityId) {
        WeatherDownloadedEvent weatherDownloadedEvent= new WeatherDownloadedEvent(DataGenerator.getWeather(cityId),cityId);
        EventBus.getDefault().post(weatherDownloadedEvent);

    }

    /**
     * Download the current Weather in an asynchronous way
     *
     * @param cityId The id of the city associated with the forecasts
     */
    @Override
    public void downloadForecastWeatherAsync(int cityId) {
        CityForecastDownloadedEvent cityForecastDownloadedEvent= new CityForecastDownloadedEvent(DataGenerator.getCityForecast(cityId),cityId);
         EventBus.getDefault().post(cityForecastDownloadedEvent);
    }

    /**
     * Download the current Weather in an synchronous way
     *
     * @param cityId The id of the city associated with the forecasts
     */
    @Override
    public void downloadForecastWeatherSync(int cityId) {
        CityForecastDownloadedEvent cityForecastDownloadedEvent= new CityForecastDownloadedEvent(DataGenerator.getCityForecast(cityId),cityId);
        EventBus.getDefault().post(cityForecastDownloadedEvent);

    }

    /**
     * Clean your resource when your service die
     */
    @Override
    public void onDestroy() {

    }

    /**
     * Clean your resource when your service die
     *
     * @param srvManager
     */
    @Override
    public void onDestroy(ServiceManagerIntf srvManager) {

    }
}

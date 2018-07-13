/**
 * <ul>
 * <li>ServiceManagerIntf</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.service</li>
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
package com.android2ee.formation.restservice.forecastyahoo.withlibs.service;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.city.CityServiceIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.weather.ForecastRepositoryIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.weather.WeatherDataUpdaterIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.weather.WeatherOfTheDayRepositoryIntf;

import java.util.concurrent.ExecutorService;

/**
 * Created by Mathias Seguy - Android2EE on 10/04/2016.
 */
public interface ServiceManagerIntf {
    /**
     * To be called when you need to release all the services
     * Is managed by the MyApplication object in fact
     */
    void unbindAndDie();
    /**
     * @return the forecastServiceData
     */
    WeatherDataUpdaterIntf getWeatherUpdaterService();
    /**
     * @return the ForecastRepository
     */
    ForecastRepositoryIntf getForecastRepository();

    /**
     * @return the cityService
     */
    CityServiceIntf getCityService();
    /**
     * @return the WeatherOfTheDayRepository
     */
    WeatherOfTheDayRepositoryIntf getWeatherOfTheDayRepository();
    /**
     * @return the cancelableThreadsExceutor
     */
    ExecutorService getCancelableThreadsExecutor();

    /**
     * @return the cancelableThreadsExceutor
     */
    ExecutorService getKeepAliveThreadsExecutor();
}

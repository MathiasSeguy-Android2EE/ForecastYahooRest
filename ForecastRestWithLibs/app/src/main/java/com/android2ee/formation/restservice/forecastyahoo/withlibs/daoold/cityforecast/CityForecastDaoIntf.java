/**
 * <ul>
 * <li>CityForecastDaoIntf</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.cityforecast</li>
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
package com.android2ee.formation.restservice.forecastyahoo.withlibs.daoold.cityforecast;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.forecast.CityForecast;

import java.util.List;

/**
 * Created by Mathias Seguy - Android2EE on 10/04/2016.
 */
public interface CityForecastDaoIntf {
    /**
     * The find the current forecast by cityId
     * @param cityId
     * @return
     */
    CityForecast findCurrentForecastFor(int cityId);

    /**
     * The find all the forecasts by cityId
     * @param cityId
     * @return
     */
    List<CityForecast> findAllForecastFor(int cityId);

    /**
     * The insertOrUpdate weather
     * @param cityForecast
     * @return
     */
    long insertOrUpdate(CityForecast cityForecast);

    void delete(CityForecast cityForecast);
}

/**
 * <ul>
 * <li>WeatherDaoMocked</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.dao</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.dao;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.daoold.weather.WeatherDaoIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.transverse.DataGenerator;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.Weather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mathias Seguy - Android2EE on 10/04/2016.
 */
public class WeatherDaoMocked implements WeatherDaoIntf {
    /**
     * The find by cityId
     *
     * @param cityId
     * @return
     */
    @Override
    public Weather findCurrentWeatherFor(int cityId) {
        return DataGenerator.getWeather(cityId);
    }

    /**
     * The find list of current weather by cityId
     *
     * @param cityId
     * @return
     */
    @Override
    public List<Weather> findAllCurrentWeatherFor(int cityId) {
        ArrayList<Weather> returned=new ArrayList<>(4);
        returned.add(DataGenerator.getWeather(1102));
        returned.add(DataGenerator.getWeather(1312));
        returned.add(DataGenerator.getWeather(0411));
        returned.add(DataGenerator.getWeather(1911));
        return returned;
    }

    /**
     * The insertOrUpdate weather
     *
     * @param weather
     * @return
     */
    @Override
    public long insertOrUpdate(Weather weather) {
        return 0;
    }

    @Override
    public void delete(Weather weather) {

    }
}

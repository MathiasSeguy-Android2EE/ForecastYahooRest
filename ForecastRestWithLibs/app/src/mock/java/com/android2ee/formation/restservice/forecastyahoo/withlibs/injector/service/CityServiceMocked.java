/**
 * <ul>
 * <li>CityServiceMocked</li>
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

import android.util.Log;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.transverse.DataGenerator;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.ServiceManagerIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.city.CityServiceIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.CitiesLoadedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.CityAddedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.FindCitiesResponseEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.City;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by Mathias Seguy - Android2EE on 10/04/2016.
 */
public class CityServiceMocked implements CityServiceIntf {
    private static final String TAG = "CityServiceMocked";
    private ArrayList<City> cities=null;
    /**
     * Find the Cities that match the city name in an asynchronous way
     *
     * @param cityName The name of the city searched
     */
    @Override
    public void findCityByNameAsync(String cityName) {
        //track entrance
        Log.e(TAG, "findCityByNameAsync() has been called");
        FindCitiesResponseEvent findCitiesResponseEvent = new FindCitiesResponseEvent(DataGenerator.getFindCitiesResponse());
        EventBus.getDefault().post(findCitiesResponseEvent);
    }

    /**
     * Reload the FindCitiesResponse :
     * if not null resent the data
     */
    @Override
    public void reloadFindCitiesResponse() {
        //track entrance
        Log.e(TAG, "reloadFindCitiesResponse() has been called");
        FindCitiesResponseEvent findCitiesResponseEvent = new FindCitiesResponseEvent(DataGenerator.getFindCitiesResponse());
        EventBus.getDefault().post(findCitiesResponseEvent);
    }

    /**
     * Add a city in the database
     * That way you will look at its weather
     *
     * @param city
     */
    @Override
    public void addCityAsync(City city) {
        cities.add(city);
        EventBus.getDefault().post(new CityAddedEvent(city));

    }

    /**
     * Load all the cities from the database asynchronously
     */
    @Override
    public void loadCitiesAsync() {
        //track entrance
        Log.e(TAG, "loadCitiesAsync() has been called and cities"+cities);
        if(cities==null) {
            cities = new ArrayList<>();
            cities.add(DataGenerator.getCity(1102, "Math Town"));
            cities.add(DataGenerator.getCity(1312, "Nine city"));
            cities.add(DataGenerator.getCity(0411, "Base Tower"));
            cities.add(DataGenerator.getCity(1911, "Lila castle"));
        }
        CitiesLoadedEvent citiesLoadedEvent = new CitiesLoadedEvent(cities);
        EventBus.getDefault().post(citiesLoadedEvent);
    }

    /**
     * Delete a city from the database
     *
     * @param city
     */
    @Override
    public void deleteCityAsync(City city) {
//track entrance
        Log.e(TAG, "deleteCityAsync() has been called");
        if(cities.contains(city)){
            cities.remove(city);
        }
    }

    /**
     * Clean your resource when your service die
     */
    @Override
    public void onDestroy() {
        cities=null;
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

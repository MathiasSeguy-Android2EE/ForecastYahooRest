/**
 * <ul>
 * <li>CityPresenterIntf</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.view.findcity</li>
 * <li>09/03/2016</li>
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
package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.findcity;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.City;

import java.util.ArrayList;

/**
 * Created by Mathias Seguy - Android2EE on 09/03/2016.
 */
public interface CityPresenterIntf {
    /**
     * This method is called when you need to search for a city
     * The connection state is managed by the service, activity don't have to deal with that when
     * requesting data
     */
    void searchCity(String cityName);

    /**
     * This method is called when a city is selected
     *
     * @param position
     *            The position of the city
     */
    void selectCity(int position);

    /**
     * @return the list of cities to display
     */
    ArrayList<City> getCities();

    /**
     * Reload cities because configuration changed
     * @return the cities in cach, could be null
     */
    void reloadCities();
}

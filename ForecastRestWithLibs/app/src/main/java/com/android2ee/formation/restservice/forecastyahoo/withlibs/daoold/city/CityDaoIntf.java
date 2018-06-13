/**
 * <ul>
 * <li>CityDaoIntf</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.city</li>
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
package com.android2ee.formation.restservice.forecastyahoo.withlibs.daoold.city;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.City;

import java.util.List;

/**
 * Created by Mathias Seguy - Android2EE on 10/04/2016.
 */
public interface CityDaoIntf {
    /***********************************************************
    *  Business Methods
    **********************************************************/
    City findCity(int cityId);

    List<City> findAll();

    long insertOrUpdate(City city);

    void delete(City city);
}

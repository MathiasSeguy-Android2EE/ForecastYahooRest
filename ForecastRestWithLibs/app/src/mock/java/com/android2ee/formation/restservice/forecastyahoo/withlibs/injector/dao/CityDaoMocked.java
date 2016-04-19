/**
 * <ul>
 * <li>CityDaoMocked</li>
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

import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.city.CityDaoIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.transverse.DataGenerator;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mathias Seguy - Android2EE on 10/04/2016.
 * This is pipo land for CityDao
 */
public class CityDaoMocked implements CityDaoIntf {
    /***********************************************************
     * Business Methods
     * ********************************************************
     *
     * @param cityId
     */
    @Override
    public City findCity(int cityId) {
        //Should return a stuff
        return DataGenerator.getCity(cityId);
    }

    @Override
    public List<City> findAll() {
        ArrayList<City> returned=new ArrayList<>(4);
        returned.add(DataGenerator.getCity(1102));
        returned.add(DataGenerator.getCity(1312));
        returned.add(DataGenerator.getCity(0411));
        returned.add(DataGenerator.getCity(1911));
        return returned;
    }

    @Override
    public long insertOrUpdate(City city) {
        return 12350l;
    }

    @Override
    public void delete(City city) {

    }
}

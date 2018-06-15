/**
 * <ul>
 * <li>CityDao</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.dao</li>
 * <li>25/02/2016</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.city;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.DaoManager;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.exception.ExceptionManager;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Clouds;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Coordinates;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Ephemeris;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.WeatherDetails;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Wind;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.weathermetadata.WeatherMetaData_City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;

import java.util.List;

/**
 * Created by Mathias Seguy - Android2EE on 25/02/2016.
 */
@Deprecated
public class CityDao implements CityDaoIntf{
    /***********************************************************
     * Constructor
     **********************************************************/
    private CityDao(){};
    public CityDao(DaoManager daoManager){
        //to ensure only DaoManager can instanciate this element
    }
    private static final String TAG = "CityDao";
    /***********************************************************
    *  Business Methods
    **********************************************************/
    @Override
    public City findCity(int cityId){
        //TODO extract string
        List<City> cities= City.find(City.class, "CITY_ID =?", Integer.toString(cityId));
        if(cities!=null){
            if(cities.size()==1){
                return cities.get(0);
            }else if(cities.size()==0){
                ExceptionManager.displayAnError("Could not find City with CityId = "+cityId);
                return null;
            }else {
                ExceptionManager.displayAnError("Have Found multiple cities with the same cityID"+cityId);
                return null;
            }
        }else{
            ExceptionManager.displayAnError("Could not find City with CityId = "+cityId);
            return null;
        }
    }

    @Override
    public List<City> findAll(){
        return City.find(City.class,null,(String[])null);
    }

    @Override
    public long insertOrUpdate(City city){
        long id;
        //insert element if and only if the cityId is not already in the database
        List<City> cities= City.find(City.class, "CITY_ID =?", Integer.toString(city.getCityId()));
        if(city.getId()==null&&cities.size()==0) {
            MyLog.e(TAG, "insert case" + city);
            //you need to manually save others elements (first)
            Coordinates.save(city.getCoordinates());
            WeatherDetails.save(city.getWeatherDetails());
            Wind.save(city.getWind());
            Clouds.save(city.getClouds());
            Ephemeris.save(city.getEphemeris());
            id = City.save(city);
            for (WeatherMetaData_City weatherMetaD : city.getMetaDataList()) {
                weatherMetaD.setCity(city);
                WeatherMetaData_City.save(weatherMetaD);
            }
        }else{
            //the already existing case
            if(cities.size()==1){
                //copy pasting Id should work
                city.setId(cities.get(0).getId());
                //but if you leave like that you duplicate the WeatherMetaData_City
                //so you have to do the same with them: Delete the existing records
                WeatherMetaData_City.deleteAll(WeatherMetaData_City.class,"CITY =?",cities.get(0).getId().toString());
            }
            MyLog.e(TAG, "update " + city);
            city.getCoordinates().save();
            city.getWeatherDetails().save();
            city.getWind().save();
            city.getClouds().save();
            city.getEphemeris().save();
            city.save();
            for (WeatherMetaData_City weatherMetaD : city.getMetaDataList()) {
                weatherMetaD.setCity(city);
                weatherMetaD.save();
            }
            id =city.getId();
        }

        return id;
    }

    @Override
    public void delete(City city){
        if(city.getId()!=null) {
            MyLog.e(TAG, "delete case" + city);
            //you need to manually delete others elements (first)
            Coordinates.delete(city.getCoordinates());
            WeatherDetails.delete(city.getWeatherDetails());
            Wind.delete(city.getWind());
            Clouds.delete(city.getClouds());
            Ephemeris.delete(city.getEphemeris());
            for (WeatherMetaData_City weatherMetaD : city.getMetaDataList()) {
                WeatherMetaData_City.delete(weatherMetaD);
            }
            City.delete(city);
        }
    }
}

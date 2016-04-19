/**
 * <ul>
 * <li>DaoManager</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.dao</li>
 * <li>09/04/2016</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.dao;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.city.CityDao;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.city.CityDaoIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.cityforecast.CityForecastDao;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.cityforecast.CityForecastDaoIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.weather.WeatherDao;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.weather.WeatherDaoIntf;

/**
 * Created by Mathias Seguy - Android2EE on 09/04/2016.
 * This class aims to centralize the access to the Dao classes
 * This is usefull for injection (for the test)
 */
public class DaoManager {
    /***********************************************************
    *  Managing LifeCycle
    **********************************************************/
    private static DaoManager INSTANCE=null;
    public static DaoManager getInstance() {
        if(INSTANCE==null){
            INSTANCE=new DaoManager();
        }
        return INSTANCE;
    }
    private DaoManager(){};
    /**
     * To be called when the application died
     * This is the main problem of the singleton pattern
     * They live as long as the process live
     */
    public void releaseMemory(){
        INSTANCE=null;
        cityDaoIntf =null;
        cityForecastDaoIntf =null;
        weatherDaoIntf =null;
    }
    /***********************************************************
    *  Attributes
    **********************************************************/
    /**
     * The CityDao
     */
    CityDaoIntf cityDaoIntf =null;
    /**
     * The CityForecastDao
     */
    CityForecastDaoIntf cityForecastDaoIntf =null;
    /**
     * The WeatherDao
     */
    WeatherDaoIntf weatherDaoIntf =null;
    /***********************************************************
    *  Getters/Setters And
    **********************************************************/
    public CityDaoIntf getCityDao() {
        if(cityDaoIntf ==null){
            cityDaoIntf =new CityDao(this);
        }
        return cityDaoIntf;
    }

    public WeatherDaoIntf getWeatherDao() {
        if(weatherDaoIntf ==null){
            weatherDaoIntf =new WeatherDao(this);
        }
        return weatherDaoIntf;
    }

    public CityForecastDaoIntf getCityForecastDao() {
        if(cityForecastDaoIntf ==null){
            cityForecastDaoIntf =new CityForecastDao(this);
        }
        return cityForecastDaoIntf;
    }
}

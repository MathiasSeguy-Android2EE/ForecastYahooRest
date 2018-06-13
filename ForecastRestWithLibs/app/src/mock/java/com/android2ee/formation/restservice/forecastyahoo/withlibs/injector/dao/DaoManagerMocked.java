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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.dao;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.daoold.city.CityDaoIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.daoold.cityforecast.CityForecastDaoIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.daoold.weather.WeatherDaoIntf;

/**
 * Created by Mathias Seguy - Android2EE on 09/04/2016.
 * This class aims to centralize the access to the Dao classes
 * This is usefull for injection (for the test)
 */
public class DaoManagerMocked {
    /***********************************************************
    *  Managing LifeCycle
    **********************************************************/
    private static DaoManagerMocked INSTANCE=null;
    public static DaoManagerMocked getInstance() {
        if(INSTANCE==null){
            INSTANCE=new DaoManagerMocked();
        }
        return INSTANCE;
    }
    private DaoManagerMocked(){};
    /**
     * To be called when the application died
     * This is the main problem of the singleton pattern
     * They live as long as the process live
     */
    public void releaseMemory(){
        INSTANCE=null;
        cityDao=null;
        cityForecastDao=null;
        weatherDao=null;
    }
    /***********************************************************
    *  Attributes
    **********************************************************/
    /**
     * The CityDao
     */
    CityDaoIntf cityDao=null;
    /**
     * The CityForecastDao
     */
    CityForecastDaoIntf cityForecastDao=null;
    /**
     * The WeatherDao
     */
    WeatherDaoIntf weatherDao=null;
    /***********************************************************
    *  Getters/Setters And
    **********************************************************/
    public CityDaoIntf getCityDao() {
        if(cityDao==null){
            cityDao=new CityDaoMocked();
        }
        return cityDao;
    }

    public WeatherDaoIntf getWeatherDao() {
        if(weatherDao==null){
            weatherDao=new WeatherDaoMocked();
        }
        return weatherDao;
    }

    public CityForecastDaoIntf getCityForecastDao() {
        if(cityForecastDao==null){
            cityForecastDao=new CityForecastDaoMocked();
        }
        return cityForecastDao;
    }
}

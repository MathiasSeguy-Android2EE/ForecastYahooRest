/**
 * <ul>
 * <li>CityForecastDao</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.dao</li>
 * <li>06/03/2016</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.cityforecast;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.DaoManager;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Clouds;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Coordinates;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.WeatherDetails;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Wind;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.forecast.CityForecast;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.forecast.WeatherForecast;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.weathermetadata.WeatherMetaData_WeatherForecast;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

/**
 * Created by Mathias Seguy - Android2EE on 06/03/2016.
 */
@Deprecated
public class CityForecastDao implements CityForecastDaoIntf {
    /***********************************************************
     * Constructor
     **********************************************************/
    private CityForecastDao(){};
    public CityForecastDao(DaoManager daoManager){
        //to ensure only DaoManager can instanciate this element
    }
    /***********************************************************
     *  Business Methods
     **********************************************************/
    private static final String TAG = "CityForecastDao";

    /**
     * The find the current forecast by cityId
     * @param cityId
     * @return
     */
    @Override
    public CityForecast findCurrentForecastFor(int cityId){
        long begin = System.currentTimeMillis();
        List<CityForecast> foundElements= Select.from(CityForecast.class)
//                    .orderBy("TIME_STAMP_UTC")
                .where(Condition.prop("CITY_ID").eq(cityId))
                .orderBy("ID")
                .list();
        MyLog.i("CityForecastDao", "findCurrentForecastFor " + cityId + " find " + foundElements + " elements\r\n");
        if(foundElements.size()>0) {
            CityForecast returnedCityForecast = foundElements.get(foundElements.size() - 1);
            MyLog.i("CityForecastDao", "findCurrentForecastFor " + cityId + " is " + returnedCityForecast.getId() + " \r\n" + returnedCityForecast);
            //Your code here
            long end = System.currentTimeMillis();
            MyLog.e(TAG, "findCurrentForecastFor() duration " + (end - begin) + "ms");
            MyLog.d(TAG, "findCurrentForecastFor() called with: " + "cityId = [" + cityId + "]");
            return returnedCityForecast;
        }else{
            return null;
        }
    }
    /**
     * The find all the forecasts by cityId
     * @param cityId
     * @return
     */
    @Override
    public List<CityForecast>  findAllForecastFor(int cityId){
        MyLog.d(TAG, "findAllForecastFor() called with: " + "cityId = [" + cityId + "]");
        List<CityForecast> cityForecastList = CityForecast.find(CityForecast.class, "CITY_ID =?", Integer.toString(cityId));
        return cityForecastList;
    }
    /**
     * The insertOrUpdate weather
     * @param cityForecast
     * @return
     */
    @Override
    public long insertOrUpdate(CityForecast cityForecast){
        MyLog.d(TAG, "insertOrUpdate() called with: " + "cityForecast = [" + cityForecast + "]");
        long id;
        if(cityForecast.getId()==null) {
            MyLog.i(TAG, "insert case");
            //insert case
            Coordinates.save(cityForecast.getCoordinates());
            id = CityForecast.save(cityForecast);
            for (WeatherForecast weatherForecast : cityForecast.getWeatherForecasts()) {
                WeatherDetails.save(weatherForecast.getWeatherDetails());
                Clouds.save(weatherForecast.getClouds());
                Wind.save(weatherForecast.getWind());
                weatherForecast.setCityForecast(cityForecast);
                WeatherForecast.save(weatherForecast);
                for(WeatherMetaData_WeatherForecast wmd_wf:weatherForecast.getWeathers()){
                    wmd_wf.setWeatherForecast(weatherForecast);
                    WeatherMetaData_WeatherForecast.save(wmd_wf);
                }
            }
            return id;
        }else{
            MyLog.i(TAG, "update case");
            //update case
            cityForecast.getCoordinates().save();
            cityForecast.save();
            for (WeatherForecast weatherForecast : cityForecast.getWeatherForecasts()) {
                weatherForecast.getWeatherDetails().save();
                weatherForecast.getClouds().save();
                weatherForecast.getWind().save();
                weatherForecast.save();
                for(WeatherMetaData_WeatherForecast wmd_wf:weatherForecast.getWeathers()){
                    wmd_wf.save();
                }
            }
        }
        return cityForecast.getId();
    }

    @Override
    public void delete(CityForecast cityForecast) {
        if (cityForecast.getId() != null) {
            MyLog.i(TAG, "delete " + cityForecast);
            //you need to make a recursive deletion
            Coordinates.delete(cityForecast.getCoordinates());
            MyLog.i(TAG, "delete coord " + cityForecast.getCoordinates());
            for (WeatherForecast weatherForecast : cityForecast.getWeatherForecasts()) {
                for(WeatherMetaData_WeatherForecast wmd_wf:weatherForecast.getWeathers()){
                    WeatherMetaData_WeatherForecast.delete(wmd_wf);
                }
                WeatherForecast.delete(weatherForecast);
            }
            CityForecast.delete(cityForecast);
        }
    }
}

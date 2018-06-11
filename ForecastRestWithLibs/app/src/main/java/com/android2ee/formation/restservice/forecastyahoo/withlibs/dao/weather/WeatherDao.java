/**
 * <ul>
 * <li>WeatherDataDao</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.weather;

import android.util.Log;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.DaoManager;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Clouds;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Coordinates;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Rain;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Snow;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.WeatherDetails;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Wind;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Ephemeris;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.weathermetadata.WeatherMetaData_Weather;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

/**
 * Created by Mathias Seguy - Android2EE on 25/02/2016.
 */
public class WeatherDao implements WeatherDaoIntf {
    /***********************************************************
     * Constructor
     **********************************************************/
    private WeatherDao(){};
    public WeatherDao(DaoManager daoManager){
        //to ensure only DaoManager can instanciate this element
    }
    /***********************************************************
     *  Business Methods
     **********************************************************/
    private static final String TAG = "WeatherDao";
    /**
     * The find by cityId
     * @param cityId
     * @return
     */
    @Override
    public Weather findCurrentWeatherFor(int cityId){
        Log.e(TAG,"findCurrentWeatherFor is called");
        try {
            List<Weather> foundElements=Select.from(Weather.class)
//                    .orderBy("TIME_STAMP_UTC")
                    .where(Condition.prop("CITY_ID").eq(cityId))
                    .orderBy("ID")
                    .list();
            if(foundElements.size()>0) {
                Weather returnedWeather = foundElements.get(foundElements.size() - 1);
                Log.e("WeatherDao", "findCurrentWeatherFor " + cityId + " is " + returnedWeather.getId() + " \r\n" + returnedWeather);
                return returnedWeather;
            }else{
                return null;
            }
        }catch(Exception e){
            Log.e(TAG,"findCurrentWeatherFor",e);
        }
        return null;
    }
    /**
     * The find list of current weather by cityId
     * @param cityId
     * @return
     */
    @Override
    public List<Weather> findAllCurrentWeatherFor(int cityId){
        try {
            List<Weather> weathers = Weather.find(Weather.class, "CITY_ID =?", Integer.toString(cityId));
//            if (weathers != null) {
//                for(Weather w:weathers){
//                    Log.e("WeatherDao","+++++++++++++++++++++++++++++++++++++++++++++++");
//                    Log.e("WeatherDao","find a lot of weathers "+w);
//                }
//            }
            return weathers;
        }catch(Exception e){
            Log.e(TAG,"findCurrentWeatherFor",e);
        }
        return null;
    }

    /**
     * The insertOrUpdate weather
     * @param weather
     * @return
     */
    @Override
    public long insertOrUpdate(Weather weather){
        long id;
        if(weather.getId()==null) {
            Log.e(TAG, "insert case " + weather);
            //you need to manually save others elements (first)
            Coordinates.save(weather.getCoordinates());
            WeatherDetails.save(weather.getWeatherDetails());
            Wind.save(weather.getWind());
            Clouds.save(weather.getClouds());
            Rain.save(weather.getRain());
            Snow.save(weather.getSnow());
            Ephemeris.save(weather.getEphemeris());
             id = Weather.save(weather);
            Log.e(TAG, "insertOrUpdate coord " + weather.getCoordinates());
            for (WeatherMetaData_Weather weatherMetaD : weather.getWeatherMetaData()) {
                weatherMetaD.setWeather(weather);
                WeatherMetaData_Weather.save(weatherMetaD);
            }
        }else{
            Log.e(TAG, "update " + weather);
            weather.getCoordinates().save();
            weather.getWeatherDetails().save();
            weather.getWind().save();
            weather.getClouds().save();
            weather.getRain().save();
            weather.getSnow().save();
            weather.getEphemeris().save();
            weather.save();
            for (WeatherMetaData_Weather weatherMetaD : weather.getWeatherMetaData()) {
                weatherMetaD.save();
            }
            id =weather.getId();
        }

        return id;
    }

    @Override
    public void delete(Weather weather) {
        if (weather.getId() != null) {
            Log.e(TAG, "delete " + weather);
            //you need to manually save others elements (first)
            Coordinates.delete(weather.getCoordinates());
            WeatherDetails.delete(weather.getWeatherDetails());
            Wind.delete(weather.getWind());
            Clouds.delete(weather.getClouds());
            Rain.delete(weather.getRain());
            Snow.delete(weather.getSnow());
            Ephemeris.delete(weather.getEphemeris());
            Log.e(TAG, "delete coord " + weather.getCoordinates());
            for (WeatherMetaData_Weather weatherMetaD : weather.getWeatherMetaData()) {
                WeatherMetaData_Weather.delete(weatherMetaD);
            }
            Weather.delete(weather);
        }
    }
}

/**
 * <ul>
 * <li>WeatherDataUpdater</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.service.weather</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.service.weather;

import android.util.Log;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.daoold.cityforecast.CityForecastDaoIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.daoold.weather.WeatherDaoIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.MotherBusinessService;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.ServiceManagerIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.CityForecastDownloadedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.WeatherDownloadedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.Injector;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.forecast.CityForecast;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Mathias Seguy - Android2EE on 06/03/2016.
 */
public class WeatherDataUpdater extends MotherBusinessService implements WeatherDataUpdaterIntf {
    private static final String TAG = "WeatherDataUpdater";
    /***********************************************************
     *  Attributes
     **********************************************************/

    /**
     * The event to send back the current weather
     */
    private WeatherDownloadedEvent weatherDownloadedEvent=null;
    /**
     * The event to send back the forecast weather
     */
    private CityForecastDownloadedEvent cityForecastDownloadedEvent=null;
    /**
     * The Weather Dao
     */
    private WeatherDaoIntf weatherDataDao = null;
    /**
     * The CityForecast Dao
     */
    private CityForecastDaoIntf cityForecastDaoIntf =null;
    /******************************************************************************************/
    /** Constructors and destructor **************************************************************************/
    /******************************************************************************************/

    /**
     * Constructor
     */
    public WeatherDataUpdater(ServiceManagerIntf srvManager) {
        super(srvManager);
        // NOthing to initialize
        // the parameter is to ensure only srvManager cant create it
    }

    /**
     * Clean your resource when your service die
     */
    @Override
    public void onDestroy() {
        //No stuff to release
    }
    /***********************************************************
     *  Buisness Methods
     **********************************************************/
    /******************************************************************************************/
    /** DownLoading Current Weather**************************************************************************/
    /******************************************************************************************/

    /**
     * Download the current Weather in an asynchronous way
     *
     * @param cityId
     *            The id of the city associated with the forecasts
     */
    @Override
    public void downloadCurrentWeatherAsync(int cityId) {
        Log.e(TAG, "downloadCurrentWeatherAsync called with woeid=" + cityId);
         // then launch it
        MyApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(new DownloadCurWeatherRunnable(cityId));

    }
    /**
     * Download the current Weather in an synchronous way
     *
     * @param cityId
     *            The id of the city associated with the forecasts
     */
    @Override
    public void downloadCurrentWeatherSync(int cityId) {
        Log.e(TAG, "downloadCurrentWeatherSync for city =" + cityId);
        // Load data from the web
        Weather weather=Injector.getDataCommunication().findWeatherByCityId(cityId);
        //store in the database
        weatherDataDao = Injector.getDaoManager().getWeatherDao();
        weatherDataDao.insertOrUpdate(weather);
        weatherDataDao=null;
        Log.d(TAG, "downloadCurrentWeatherSync found " + weather);
        //send send back the answer using eventBus
        if(weatherDownloadedEvent==null){
            weatherDownloadedEvent= new WeatherDownloadedEvent(weather,cityId);
        }else{
            weatherDownloadedEvent.setWeather(weather);
            weatherDownloadedEvent.setCityId(cityId);
        }
        Log.e(TAG, "WeatherDownloadedEvent launched for city =" + cityId);
        EventBus.getDefault().post(weatherDownloadedEvent);
    }

    /**
     * @author Mathias Seguy (Android2EE)
     * @goals
     *        This class aims to implements a Runnable with an Handler
     */
    private class DownloadCurWeatherRunnable implements Runnable {
        int cityId;

        public DownloadCurWeatherRunnable(int cityId) {
            this.cityId = cityId;
        }

        @Override
        public void run() {
            downloadCurrentWeatherSync(cityId);
        }
    }
    /******************************************************************************************/
    /** DownLoading Forecast Weather**************************************************************************/
    /******************************************************************************************/

    /**
     * Download the current Weather in an asynchronous way
     *
     * @param cityId
     *            The id of the city associated with the forecasts
     */
    @Override
    public void downloadForecastWeatherAsync(int cityId) {
        Log.e(TAG, "downloadForecastWeatherAsync called with woeid=" + cityId);
        // then launch it
        MyApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(new DownloadForecastWeatherRunnable(cityId));

    }
    /**
     * Download the current Weather in an synchronous way
     *
     * @param cityId
     *            The id of the city associated with the forecasts
     */
    @Override
    public void downloadForecastWeatherSync(int cityId) {
        Log.e(TAG, "downloadForecastWeatherSync for city =" + cityId);
        // Load data from the web
        CityForecast cityForecast=Injector.getDataCommunication().findForecastByCityId(cityId);
        //Store those data in the database
        cityForecastDaoIntf = Injector.getDaoManager().getCityForecastDao();
        cityForecastDaoIntf.insertOrUpdate(cityForecast);
        cityForecastDaoIntf =null;
        Log.d(TAG, "downloadForecastWeatherSync found =" + cityId);
        //send send back the answer using eventBus
        if(cityForecastDownloadedEvent==null){
            cityForecastDownloadedEvent= new CityForecastDownloadedEvent(cityForecast,cityId);
        }else{
            cityForecastDownloadedEvent.setCityForecast(cityForecast);
            cityForecastDownloadedEvent.setCityId(cityId);
        }
        Log.d(TAG, "cityForecastDownloadedEvent send =" + cityForecast);
        EventBus.getDefault().post(cityForecastDownloadedEvent);
    }


    /**
     * @author Mathias Seguy (Android2EE)
     * @goals
     *        This class aims to implements a Runnable with an Handler
     */
    private class DownloadForecastWeatherRunnable implements Runnable {
        int cityId;

        public DownloadForecastWeatherRunnable(int cityId) {
            this.cityId = cityId;
        }

        @Override
        public void run() {
            downloadForecastWeatherSync(cityId);
        }
    }


}

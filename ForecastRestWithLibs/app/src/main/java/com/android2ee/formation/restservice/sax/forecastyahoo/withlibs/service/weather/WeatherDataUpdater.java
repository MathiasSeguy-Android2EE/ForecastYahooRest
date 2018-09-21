/**
 * <ul>
 * <li>WeatherDataUpdater</li>
 * <li>com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.service.weather</li>
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

package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.service.weather;

import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.dao.DaoWrapper;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.dao.ForecastDatabase;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.injector.Injector;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.service.MotherBusinessService;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.service.ServiceManagerIntf;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.current.WeatherData;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.forecast.Forecast;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.utils.MyLog;

/**
 * Created by Mathias Seguy - Android2EE on 06/03/2016.
 */
public class WeatherDataUpdater extends MotherBusinessService implements WeatherDataUpdaterIntf {
    private static final String TAG = "WeatherDataUpdater";
    /***********************************************************
     *  Attributes
     **********************************************************/

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
     * @param cityServerId
     *            The id of the city associated with the forecasts
     */
    @Override
    public void downloadCurrentWeatherAsync(long cityServerId) {
        MyLog.e(TAG, "downloadCurrentWeatherAsync called with woeid=" + cityServerId);
         // then launch it
        MyApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(new DownloadCurWeatherRunnable(cityServerId));

    }
    /**
     * Download the current Weather in an synchronous way
     *
     * @param cityServerId
     *            The id of the city associated with the forecasts
     */
    @Override
    public void downloadCurrentWeatherSync(long cityServerId) {
        MyLog.e(TAG, "downloadCurrentWeatherSync for city =" + cityServerId);
        // Load data from the web
        WeatherData weatherData=Injector.getDataCommunication().getWeatherByCityServerId(cityServerId);
        if(weatherData!=null) {
            //store in the database
            Long id = ForecastDatabase.getInstance().getCityDao().loadLiveDataByServerId(cityServerId);
            weatherData.setCityId(id);
            DaoWrapper.getInstance().saveWeatherData(weatherData);
            MyLog.d(TAG, "downloadCurrentWeatherSync found " + weatherData);
        }
    }

    /**
     * @author Mathias Seguy (Android2EE)
     * @goals
     *        This class aims to implements a Runnable with an Handler
     */
    private class DownloadCurWeatherRunnable implements Runnable {
        long cityId;

        public DownloadCurWeatherRunnable(long cityId) {
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
     * @param cityServerId
     *            The id of the city associated with the forecasts
     */
    @Override
    public void downloadForecastWeatherAsync(long cityServerId) {
        MyLog.e(TAG, "downloadForecastWeatherAsync called with woeid=" + cityServerId);
        // then launch it
        MyApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(new DownloadForecastWeatherRunnable(cityServerId));

    }
    /**
     * Download the current Weather in an synchronous way
     *
     * @param cityServerId
     *            The id of the city associated with the forecasts
     */
    @Override
    public void downloadForecastWeatherSync(long cityServerId) {
        MyLog.e(TAG, "downloadForecastWeatherSync for city =" + cityServerId);
        // Load data from the web
        Forecast forecast=Injector.getDataCommunication().getForecastByCityId(cityServerId);
        if(forecast!=null) {
            DaoWrapper.getInstance().saveForecast(forecast);
        }
    }


    /**
     * @author Mathias Seguy (Android2EE)
     * @goals
     *        This class aims to implements a Runnable with an Handler
     */
    private class DownloadForecastWeatherRunnable implements Runnable {
        long cityId;

        public DownloadForecastWeatherRunnable(long cityId) {
            this.cityId = cityId;
        }

        @Override
        public void run() {
            downloadForecastWeatherSync(cityId);
        }
    }


}

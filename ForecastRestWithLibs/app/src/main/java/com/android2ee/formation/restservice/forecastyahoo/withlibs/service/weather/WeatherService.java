/**
 * <ul>
 * <li>WeatherService</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.service.weather</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.service.weather;

import android.util.SparseArray;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.weather.WeatherDaoIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.Injector;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.MotherBusinessService;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.ServiceManagerIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.WeatherDownloadedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.WeatherLoadedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.joda.time.DateTime;
import org.joda.time.Interval;

/**
 * Created by Mathias Seguy - Android2EE on 25/02/2016.
 * This is a business service, it doesn't need to extends the Android Service
 */
@Deprecated
public class WeatherService extends MotherBusinessService implements WeatherServiceIntf {
    private static final String TAG = "WeatherService";
    /******************************************************************************************/
    /** Attributes **************************************************************************/
    /******************************************************************************************/

    /**
     * The weatherdata to display (the cache)
     */
    private SparseArray<Weather> weatherList = null;
    /**
     * The event to send back the information and the data
     */
    private WeatherLoadedEvent weatherLoadedEvent =null;
    /**
     * The Dao
     */
    private WeatherDaoIntf weatherDataDao = null;
    /**
     * To know if tha data has to be reloaded
     */
    private boolean reload = true;

    /******************************************************************************************/
    /** Constructors and destructor **************************************************************************/
    /******************************************************************************************/

    /**
     * Constructor
     */
    public WeatherService(ServiceManagerIntf srvManager) {
        super(srvManager);
        weatherList=new SparseArray<>();
    }

    /**
     * Clean your resource when your service die
     */
    @Override
    public void onDestroy() {
        //No stuff to release
    }
    /******************************************************************************************/
    /** Loading WeatherData**************************************************************************/
    /******************************************************************************************/

    /**
     * Load the WeatherData in an asynchronous way
     *
     * @param cityId
     *            The id of the city associated with the forecasts
     */
    @Override
    public void loadCurrentWeatherAsync(Long cityId) {
        MyLog.e(TAG, "loadCurrentWeatherAsync called with cityId=" + cityId);
        reload = false;
        if (weatherList.get(cityId.intValue())!=null) {
            reload = true;
        }
        // use the caching mechanism
        if (reload) {
            //send send back the answer using eventBus
            postWeatherDataLoadedEvent(cityId,weatherList.get(cityId.intValue()));
        } else {
            // then launch it
            MyApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoLoadRunnable(cityId));
        }
    }
    /**
     * Load the WeatherData in an synchronous way
     *
     * @param cityId
     *            The id of the city associated with the forecasts
     */
    private void loadCurrentWeatherSync(Long cityId) {
        MyLog.e(TAG, "loadCurrentWeatherSync called with cityId=" + cityId);
        // Load data from database
        weatherDataDao = Injector.getDaoManager().getWeatherDao();
        Weather weather = weatherDataDao.findCurrentWeatherFor(cityId);
        weatherDataDao = null;
        //store the result
        weatherList.put(cityId.intValue(),weather);
        MyLog.e("WeatherService", "loadCurrentWeatherSync has returned=" + weather);
        //send send back the answer using eventBus
        postWeatherDataLoadedEvent(cityId,weather);
        //update the data if needed
        updateDataIfNeeded(cityId);
    }
    /**
     * @author Mathias Seguy (Android2EE)
     * @goals
     *        This class aims to implements a Runnable with an Handler
     */
    private class DaoLoadRunnable implements Runnable {
        Long cityId;

        public DaoLoadRunnable(Long cityId) {
            this.cityId = cityId;
        }

        @Override
        public void run() {
            loadCurrentWeatherSync(cityId);
        }
    }
    /***********************************************************
     *  Updating strategy and update listening
     **********************************************************/
    /**
     * Called when the forecast are built
     * Return that list to the calling Activity using the ForecastCallBack
     */
    private void updateDataIfNeeded(Long cityId) {
        MyLog.e(TAG, "updateDataIfNeeded() called with: " + "");
        if(weatherList.get(cityId.intValue()) !=null){
            Interval interval=new Interval(weatherList.get(cityId.intValue()).getTimeStamp(), new DateTime());
            MyLog.e(TAG, "updateDataIfNeeded() weather.getTimeStamp(): " +weatherList.get(cityId.intValue()).getTimeStamp());
            MyLog.e(TAG, "updateDataIfNeeded() new DateTime(): " +new DateTime());
            MyLog.e(TAG, "updateDataIfNeeded() duration in day found: " +interval.toDuration().getStandardDays());
            if(interval.toDuration().getStandardDays()>1){
                launchWeatherDataUpdater(cityId);
            }
        }else{
            //ok you can update
            launchWeatherDataUpdater(cityId);
        }
    }

    /**
     * Launch the update of the data
     */
    private void launchWeatherDataUpdater(Long cityId) {
        //ok you can update
        WeatherDataUpdaterIntf updater= MyApplication.instance.getServiceManager().getWeatherUpdaterService();
        updater.downloadCurrentWeatherSync(cityId.intValue());
        updater.downloadForecastWeatherSync(cityId.intValue());
    }

    /**
     * Brodcast the Weather loaded event
     */
    private void postWeatherDataLoadedEvent(Long cityId, Weather weather) {
        if(weatherLoadedEvent ==null){
            weatherLoadedEvent = new WeatherLoadedEvent(weather,cityId);
        }else{
            weatherLoadedEvent.setWeather(weather);
            weatherLoadedEvent.setCityId(cityId);
        }
        MyLog.e(TAG, "WeatherLoadedEvent posted" );
        EventBus.getDefault().post(weatherLoadedEvent);
    }
    /***********************************************************
     *  Listening for downloaded data events
     **********************************************************/
    @Override
    @Subscribe
    public void onEvent(WeatherDownloadedEvent event){
        //update the event
        if(event.getWeather()!=null) {
            weatherList.put(event.getCityId().intValue(), event.getWeather());
            //then broadcast the information
            postWeatherDataLoadedEvent(event.getCityId(),event.getWeather());
        }else{
            //there is no connection, do nothing dude
        }
    }
}

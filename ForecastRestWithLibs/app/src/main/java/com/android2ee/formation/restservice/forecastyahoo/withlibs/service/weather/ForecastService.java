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

import android.util.Log;
import android.util.SparseArray;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.cityforecast.CityForecastDaoIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.MotherBusinessService;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.ServiceManagerIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.CityForecastDownloadedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.CityForecastLoadedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.Injector;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.forecast.CityForecast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Mathias Seguy - Android2EE on 25/02/2016.
 * This is a business service, it doesn't need to extends the Android Service
 */
public class ForecastService extends MotherBusinessService implements ForecastServiceIntf {
    private static final String TAG = "ForecastService";
    /******************************************************************************************/
    /** Attributes **************************************************************************/
    /******************************************************************************************/

    /**
     * The cache for CityForecast
     */
    SparseArray<CityForecast> cityForcastList;
    /**
     * The event to send back the information and the data
     */
    private CityForecastLoadedEvent cityForecastLoadedEvent=null;
    /**
     * The Dao
     */
    private CityForecastDaoIntf cityForecastDaoIntf = null;
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
    public ForecastService(ServiceManagerIntf srvManager) {
        super(srvManager);
        cityForcastList=new SparseArray<>();
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
    public void loadForecastAsync(int cityId) {
        Log.e(TAG, "loadForecastAsync() called with: " + "cityId = [" + cityId + "]");
        reload = false;
        if (cityForcastList.get(cityId)!=null) {
            reload = true;
        }
        // use the caching mechanism
        if (reload) {
            //send send back the answer using eventBus
            postForecastDataLoadedEvent(cityForcastList.get(cityId),cityId);
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
    private void loadForecastSync(int cityId) {
        Log.e(TAG, "loadForecastSync() called with: " + "cityId = [" + cityId + "]");
        // Load data from database
        cityForecastDaoIntf = Injector.getDaoManager().getCityForecastDao();
        CityForecast cityForecast = cityForecastDaoIntf.findCurrentForecastFor(cityId);
        cityForecastDaoIntf = null;
        //update your own cache
        cityForcastList.put(cityId,cityForecast);
        //send send back the answer using eventBus
        postForecastDataLoadedEvent(cityForecast, cityId);
    }
    /**
     * @author Mathias Seguy (Android2EE)
     * @goals
     *        This class aims to implements a Runnable with an Handler
     */
    private class DaoLoadRunnable implements Runnable {
        int cityId;

        public DaoLoadRunnable(int cityId) {
            this.cityId = cityId;
        }

        @Override
        public void run() {
            loadForecastSync(cityId);
        }
    }
    /***********************************************************
     *  Updating strategy and update listening
     **********************************************************/
     /**
     * Brodcast the Weather loaded event
     */
    private void postForecastDataLoadedEvent(CityForecast cityForecast,int cityId) {
        Log.e(TAG, "postForecastDataLoadedEvent() called as found CityForecast with " + cityForecast.getWeatherForecasts().size() + " elements and cityId="+cityId);
        if(cityForecastLoadedEvent==null){
            cityForecastLoadedEvent= new CityForecastLoadedEvent(cityForecast,cityId);
        }else{
            cityForecastLoadedEvent.setCityForecast(cityForecast);
            cityForecastLoadedEvent.setCityId(cityId);
        }
        Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + cityForecastLoadedEvent.getCityForecast() + " elements and cityId="+cityForecastLoadedEvent.getCityId());
        EventBus.getDefault().post(cityForecastLoadedEvent);
    }
    /***********************************************************
     *  Listening for downloaded data events
     **********************************************************/
    @Override
    @Subscribe
    public void onEvent(CityForecastDownloadedEvent event){
        Log.e(TAG, "onEvent() called with: " + "event = [" + event + "] and cityId="+event.getCityId());
        //update the event
        if(event.getCityForecast()!=null) {
            //update your own cache
            cityForcastList.put(event.getCityId(),event.getCityForecast());
            //then broadcast the information
            postForecastDataLoadedEvent(event.getCityForecast(),event.getCityId());
        }else{
            //there is no connection, do nothing dude
        }
    }

}

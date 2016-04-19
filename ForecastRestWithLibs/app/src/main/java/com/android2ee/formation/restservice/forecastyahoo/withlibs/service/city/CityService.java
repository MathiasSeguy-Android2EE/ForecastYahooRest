/**
 * <ul>
 * <li>CityService</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.service.city</li>
 * <li>08/03/2016</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.service.city;

import android.util.Log;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.city.CityDaoIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.MotherBusinessService;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.ServiceManagerIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.CitiesLoadedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.CityAddedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.FindCitiesResponseEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.Injector;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.FindCitiesResponse;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.City;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Mathias Seguy - Android2EE on 08/03/2016.
 */
public class CityService extends MotherBusinessService implements CityServiceIntf {
    private static final String TAG = "CityService";
    /******************************************************************************************/
    /** Attributes **************************************************************************/
    /******************************************************************************************/

    /**
     * The findCitiesResponse == the list of cities found
     */
    private FindCitiesResponse findCitiesResponse = null;
    /**
     * The event to send back the information and the data
     */
    private FindCitiesResponseEvent findCitiesResponseEvent=null;
    /**
     * The Dao
     */
    private CityDaoIntf cityDaoIntf = null;
    /**
     * The city to add in the database
     */
    private City cityToAdd;
    /**
     * The event launched when the city is added
     */
    private CityAddedEvent cityAddedEvent;
    /**
     * The event launched when the cities are loaded from db
     */
    private CitiesLoadedEvent citiesLoadedEvent;
    /**
     * The city to add in the database
     */
    private City cityToDelete;
    /******************************************************************************************/
    /** Constructors and destructor **************************************************************************/
    /******************************************************************************************/

    /**
     * Constructor
     */
    public CityService(ServiceManagerIntf srvManager) {
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
    /******************************************************************************************/
    /** Loading FindCitiesResponse**************************************************************************/
    /******************************************************************************************/

    /**
     * Find the Cities that match the city name in an asynchronous way
     * @param cityName The name of the city searched
     */
    @Override
    public void findCityByNameAsync(String cityName) {
        Log.d(TAG, "findCityByNameAsync() called with: " + "cityName = [" + cityName + "]");
        MyApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(new DownloadRunnable(cityName));
    }
    /**
     * Find the Cities that match the city name in an asynchronous way
     * @param cityName The name of the city searched
     */
    private void findCityByNameSync(String cityName) {
        Log.d(TAG, "findCityByNameSync() called with: " + "cityName = [" + cityName + "]");
        // Load data from the web
        findCitiesResponse= Injector.getDataCommunication().findCityByName(cityName);
        //send send back the answer using eventBus
        postFindCitiesResponseEvent();
    }
    /**
     * @author Mathias Seguy (Android2EE)
     * @goals
     *        This class aims to implements a Runnable with an Handler
     */
    private class DownloadRunnable implements Runnable {
        String cityName;

        public DownloadRunnable(String cityName) {
            this.cityName = cityName;
        }

        @Override
        public void run() {
            findCityByNameSync(cityName);
        }
    }
    /**
     * Brodcast the findCitiesResponse event
     */
    private void postFindCitiesResponseEvent() {
        if(findCitiesResponseEvent ==null){
            findCitiesResponseEvent = new FindCitiesResponseEvent(findCitiesResponse);
        }else{
            findCitiesResponseEvent.setFindCitiesResponse(findCitiesResponse);
        }
        Log.e(TAG, "FindCitiesResponseEvent posted" );
        EventBus.getDefault().post(findCitiesResponseEvent);
    }

    /***********************************************************
     *  Reload city from cache
     **********************************************************/

    /**
     * Reload the FindCitiesResponse :
     * if not null resent the data
     */
    @Override
    public void reloadFindCitiesResponse(){
        if(findCitiesResponse!=null){
         postFindCitiesResponseEvent();
        }
    }
    /******************************************************************************************/
    /** Add City **************************************************************************/
    /******************************************************************************************/

    /**
     * Add a city in the database
     * That way you will look at its weather
     * @param city
     */
    @Override
    public void addCityAsync(City city) {
        Log.d(TAG, "addCityAsync() called with: " + "city = [" + city + "]");
        cityToAdd=city;
        MyApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(daoInsertRunnable);
    }

    /**
     *
     * @param city
     */
    private void addCitySync(City city) {
        Log.d(TAG, "addCitySync() called with: " + "city = [" + city + "]");
        // Load data from the database
        cityDaoIntf = Injector.getDaoManager().getCityDao();
        cityDaoIntf.insertOrUpdate(city);
        cityDaoIntf =null;
        //send  back the answer using eventBus
        postCityAddedEvent(city);
    }

    /**
     * The runnable to execute the insert
     */
    private DaoInsertRunnable daoInsertRunnable = new DaoInsertRunnable();
    /**
     * @author Mathias Seguy (Android2EE)
     * @goals
     *        This class aims to implements a Runnable
     */
    private class DaoInsertRunnable implements Runnable {
        @Override
        public void run() {
            addCitySync(cityToAdd);
        }
    }
    /**
     * Brodcast the city- added event
     */
    private void postCityAddedEvent(City city) {
        if(cityAddedEvent ==null){
            cityAddedEvent = new CityAddedEvent(city);
        }else{
            cityAddedEvent.setCity(city);
        }
        Log.e(TAG, "postCityAddedEvent posted" );
        EventBus.getDefault().post(cityAddedEvent);
    }

    /******************************************************************************************/
    /** Load all Cities from database **************************************************************************/
    /******************************************************************************************/

    /**
     * Load all the cities from the database asynchronously
     */
    @Override
    public void loadCitiesAsync() {
        Log.d(TAG, "loadCities() called with: " + "");
        MyApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(daoFindAllRunnable);
    }

    /**
     *
     * Load all the cities from the database synchronously
     */
    private void loadCitiesSync() {
        // Load data from DB
        cityDaoIntf = Injector.getDaoManager().getCityDao();
        //send back the answer using eventBus
        postCitiesLoadedEvent(cityDaoIntf.findAll());
        cityDaoIntf =null;
    }

    /**
     * The runnable to execute
     */
    private DaoFindAllRunnable daoFindAllRunnable = new DaoFindAllRunnable();
    /**
     * @author Mathias Seguy (Android2EE)
     * @goals
     *        This class aims to implements a Runnable
     */
    private class DaoFindAllRunnable implements Runnable {
        @Override
        public void run() {
            loadCitiesSync();
        }
    }
    /**
     * Broadcast the Cities Loaded event
     */
    private void postCitiesLoadedEvent(List<City> city) {
        if(citiesLoadedEvent ==null){
            citiesLoadedEvent = new CitiesLoadedEvent(city);
        }else{
            citiesLoadedEvent.setCities(city);
        }
        Log.e(TAG, "postCitiesLoadedEvent posted" );
        EventBus.getDefault().post(citiesLoadedEvent);
    }
    /******************************************************************************************/
    /** Delete City from database **************************************************************************/
    /******************************************************************************************/

    /**
     * Delete a city from the database
     * @param city
     */
    @Override
    public void deleteCityAsync(City city) {
        Log.d(TAG, "deleteCityAsync() called with: " + "city = [" + city + "]");
        cityToDelete=city;
        MyApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(daoDeleteRunnable);
    }

    /**
     *
     * @param city
     */
    private void deleteCitySync(City city) {
        Log.d(TAG, "deleteCitySync() called with: " + "city = [" + city + "]");
        // Load data from the database
        cityDaoIntf = Injector.getDaoManager().getCityDao();
        cityDaoIntf.delete(city);
        cityDaoIntf =null;
    }

    /**
     * The runnable to execute the deletion
     */
    private DaoDeletionRunnable daoDeleteRunnable = new DaoDeletionRunnable();
    /**
     * @author Mathias Seguy (Android2EE)
     * @goals
     *        This class aims to implements a Runnable
     */
    private class DaoDeletionRunnable implements Runnable {
        @Override
        public void run() {
            deleteCitySync(cityToDelete);
        }
    }
}

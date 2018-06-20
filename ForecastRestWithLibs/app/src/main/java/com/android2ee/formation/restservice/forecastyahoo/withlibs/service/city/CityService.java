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

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.com.retrofit.ConnectedDataCommunication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.database.ForecastDatabase;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.Injector;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.MotherBusinessService;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.ServiceManagerIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.CityAddedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.exception.ExceptionManaged;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.exception.ExceptionManager;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.FindCitiesResponse;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;

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
     * The list of cities found from the network
     * You have to observe them
     */
    private MutableLiveData<List<City>> liveFindCitiesResponse=new MutableLiveData<>();

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
        MyLog.d(TAG, "findCityByNameAsync() called with: " + "cityName = [" + cityName + "]");
        MyApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(new DownloadRunnable(cityName));
    }
    /**
     * Find the Cities that match the city name in an asynchronous way
     * @param cityName The name of the city searched
     */
    private void findCityByNameSync(String cityName) {
        MyLog.d(TAG, "findCityByNameSync() called with: " + "cityName = [" + cityName + "]");
        // Load data from the web
        FindCitiesResponse findCitiesResponse = Injector.getDataCommunication().getCitiesByName(cityName);
        MyLog.d(TAG, "findCityByNameSync() updating with[" + cityName + "] with numCity:"+findCitiesResponse.getList());
        //then update the MutableLiveData to update the obeserver
        liveFindCitiesResponse.postValue(findCitiesResponse.getList());
    }

    /**
     *The liveData to observe when Querying Cities (from the web)
     * @return
     */
    public MutableLiveData<List<City>> getLiveFindCitiesResponse() {
        return liveFindCitiesResponse;
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
        MyLog.d(TAG, "addCityAsync() called with: " + "city = [" + city + "]");
        MyApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new RunnableAddCity(city));
    }

    /**
     *
     * @param city
     */
    private void addCitySync(City city) {
        MyLog.d(TAG, "addCitySync() called with: " + "city = [" + city + "]");
        // add it in the DB
        Long id=ForecastDatabase.getInstance().getCityDao().loadLiveDataByServerId(city.getServerId());
        if(id == null){
            id=ForecastDatabase.getInstance().getCityDao().insert(city);
        }
        city.set_id(id);
        //then set this city as the one on stage
        onStageSync(id);
        EventBus.getDefault().post(new CityAddedEvent(city));
    }
    /**
     * This is the runnable that will send the work in a background thread
     */
    private class RunnableAddCity implements Runnable {
        City city;

        public RunnableAddCity(City city) {
            //
            this.city = city;
        }

        @Override
        public void run() {
            addCitySync(city);
        }
    }

    // /******************************************************************************************/
    /** Method name: onStage
     /* Description : Put onStage a city **********/
    /* Param: long newCityOnStageId
    /******************************************************************************************/

    /**
     * Should only be called from a background thread (So only by another Service's method)
     * Don't ever call this method from the UI Thread
     */
    public void onStageSync(long newCityOnStageId) {
        MyLog.e(TAG,"onStageSync new:"+newCityOnStageId);
        ForecastDatabase.getInstance().getCityDao().setOutOfStage();
        ForecastDatabase.getInstance().getCityDao().setOnStage(newCityOnStageId);
    }

    /**
     * Should be called by the View
     */
    @Override
    public void onStage(long newCityOnStageId) {
        MyApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new RunnableOnStage(newCityOnStageId));
    }

    /**
     * This is the runnable that will send the work in a background thread
     */
    private class RunnableOnStage implements Runnable {
        long newCityOnStageId;

        public RunnableOnStage(long newCityOnStageId) {
            //
            this.newCityOnStageId = newCityOnStageId;
        }

        @Override
        public void run() {
            onStageSync(newCityOnStageId);
        }
    }

    /** ****************************************************************************************/
    /** Delete City from database **************************************************************************/
    /******************************************************************************************/

    /**
     * Delete a city from the database
     * @param city
     */
    @Override
    public void deleteCityAsync(City city) {
        MyLog.d(TAG, "deleteCityAsync() called with: " + "city = [" + city + "]");
        deleteCityByIdAsynch(city.get_id());
    }
    /******************************************************************************************/
    /** Method name: deleteCityById
     /* Description : Delete the city with the specific id **********/
    /* Param: long cityIdToDelete
    /******************************************************************************************/
    /**
     * Should only be called from a background thread (So only by another Service's method)
     * Don't ever call this method from the UI Thread
     */
    public void deleteCityByIdSync(long cityIdToDelete) {
        try{
            ForecastDatabase.getInstance().getCityDao().delete(cityIdToDelete);
            //then manage the on stage element
            List<City> cities=ForecastDatabase.getInstance().getCityDao().loadAll();
            if(cities!=null&&cities.size()>0){
                onStageSync(cities.get(0).get_id());
            }
        }catch(Exception e){
            MyLog.d(TAG, "deleteCityByIdSync failed silently",e);
            ExceptionManager.manage(new ExceptionManaged(ConnectedDataCommunication.class,R.string.generic_crud_failure,e));
        }
    }

    /**
     * Should be called by the View
     */
    @Override
    public void deleteCityByIdAsynch(long cityIdToDelete) {
        MyLog.d(TAG, "deleteCityByIdAsynch() called with: " + "cityId = [" + cityIdToDelete + "]");
        MyApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit( new RunnableDeleteCityById(cityIdToDelete));
}

    /**
     * This is the runnable that will send the work in a background thread
     */
    private class RunnableDeleteCityById implements Runnable {
        long cityIdToDelete;

        public RunnableDeleteCityById(long cityIdToDelete) {
            //
            this.cityIdToDelete = cityIdToDelete;
        }

        @Override
        public void run() {
            deleteCityByIdSync(cityIdToDelete);
        }
    }


    /***********************************************************
     *  LiveData part
     **********************************************************/

    /**
     * Load All the cities in the base
     *
     * @return
     */
    @Override
    public LiveData<List<City>> loadAllLiveDate() {
        return ForecastDatabase.getInstance().getCityDao().loadAllLiveData();
    }

    @Override
    public LiveData<List<City>> loadOnStageCities() {
        return ForecastDatabase.getInstance().getCityDao().loadOnStageCities();
    }
    /**
     * Should be called by the View
     */
    @Override
    public LiveData<Integer> countCities() {
        return ForecastDatabase.getInstance().getCityDao().count();
    }



}

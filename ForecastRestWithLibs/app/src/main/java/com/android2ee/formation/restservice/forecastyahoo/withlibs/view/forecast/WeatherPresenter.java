/**
 * <ul>
 * <li>WeatherPresenter</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast</li>
 * <li>09/03/2016</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.CitiesLoadedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.MotherPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by Mathias Seguy - Android2EE on 09/03/2016.
 */

@Deprecated
public class WeatherPresenter extends MotherPresenter implements WeatherPresenterIntf {
    /***********************************************************
     * Presenter Pattern
     **********************************************************/
    /**
     * The View
     */
    private WeatherViewIntf weatherView =null;
    /***********************************************************
     *  Attributes
     **********************************************************/
    ArrayList<City> cities;
    /***********************************************************
     *  Constructor
    **********************************************************/
    /**
     * Empty constructor should not be used
     */
    private WeatherPresenter(){
        //avoid using this constructor
    }
    /**
     * The constructor
     * @param weatherView
     * @return
     */
    public WeatherPresenter(WeatherViewIntf weatherView) {
        this.weatherView = weatherView;
        cities=new ArrayList<>();
    }

    /***********************************************************
     *  Business methods
     **********************************************************/
    /**
     * Get the list of cities to display
     *
     * @return
     */
    @Override
    public ArrayList<City> getCities() {
        return cities;
    }
    /**
     * Delete the city passed in parameter
     *
     * @param cityToDelete
     */
    @Override
    public void deleteCity(City cityToDelete) {
        //first delete from database
//        MyApplication.instance.getServiceManager().getCityService().deleteCityAsync(cityToDelete);
        //then delete from current list
        cities.remove(cityToDelete);
        //then manage the consequence of the deletion
        if(cities.size()==0){
            //Launch the search city activity
            weatherView.launchCityActivity(true);
        }else{
            //prevent the view
            weatherView.cityDeleted(cityToDelete);
        }
    }
    /**
     * Load the cities
     *
     * @return the list of cities we want to disaply the weather
     */
    @Override
    public void loadCities() {
//        MyApplication.instance.getServiceManager().getCityService().loadCitiesAsync();
    }
    /**
     * Is called by the service when the cities are loaded
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CitiesLoadedEvent event){
        //track entrance
        MyLog.e("WeatherPresenter", "onEvent() has been called");
        if(event.getCities().size()==0){
            weatherView.launchCityActivity(true);
        }else {
            cities.clear();
            for (City city : event.getCities()) {
                MyLog.e("WeatherPresenter", "onEvent(CitiesLoadedEvent) has found a new city "+city.getName());
                cities.add(city);
            }
            //prevent the view
            MyLog.e("WeatherPresenter", "weatherView.citiesLoaded has been called");
            weatherView.citiesLoaded();
        }
    }


}

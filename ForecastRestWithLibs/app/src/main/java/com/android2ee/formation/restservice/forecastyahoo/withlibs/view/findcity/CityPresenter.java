/**
 * <ul>
 * <li>CityPresenter</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.view.findcity</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.findcity;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.CityAddedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.FindCitiesResponseEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.MotherPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by Mathias Seguy - Android2EE on 08/03/2016.
 * The presenter managed the data (here the cities list)
 * And the actions on them
 */
@Deprecated
public class CityPresenter extends MotherPresenter implements CityPresenterIntf {
    private static final String TAG = "CityPresenter";
    /***********************************************************
     * Presenter Pattern
     **********************************************************/
    /**
     * The View
     */
    private CityViewIntf cityView=null;

    /***********************************************************
     *  Attributes
     **********************************************************/

    /**
     * The arraylist that contains the cities found
     */
    private ArrayList<City> cities = null;

    /***********************************************************
     *  Constructor
     **********************************************************/
    /**
     * Empty constructor should not be used
     */
    private CityPresenter(){
        //avoid using this constructor
    }
    /**
     * The constructor
     * @param cityView
     * @return
     */
    public CityPresenter(CityViewIntf cityView) {
        this.cityView = cityView;
        cities =new ArrayList<>();
    }
    /***********************************************************
     *  Implementing the CityPresenterIntf
     **********************************************************/
    /**
     * This method is called when you need to search for a city
     * The connection state is managed by the service, activity don't have to deal with that when
     * requesting data
     */
    @Override
    public void searchCity(String cityName) {
        MyLog.e("CityActivity ", "searchCity called ");
        // Call the service
        MyApplication.instance.getServiceManager().getCityService()
                .findCityByNameAsync(cityName);
    }

    /**
     * This method is called when a city is selected
     *
     * @param position
     *            The position of the city
     */
    @Override
    public void selectCity(int position) {
        //Ok so store the city
//        MyApplication.instance.getServiceManager().getCityService().addCityAsync(cities.get(position));
    }

    /**
     * @return the list of cities to display
     */
    @Override
    public ArrayList<City> getCities() {
        return cities;
    }

    /**
     * Reload cities because configuration changed
     *
     * @return the cities in cach, could be null
     */
    @Override
    public void reloadCities() {
        //Ok reload the city from the cache of the CityService
//        MyApplication.instance.getServiceManager().getCityService().reloadFindCitiesResponse();
    }

    /******************************************************************************************/
    /** FindCitiesCallBack and CityAddedCallBack **********************************************/
    /******************************************************************************************/

    /**
     * This method is called when the CityService returns the List of City to display
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FindCitiesResponseEvent event){
        MyLog.e(TAG, "FindCitiesResponseEvent received ");
        this.cities.clear();
        // if returned cities are not null, fill the list with them
        if (event != null
                && event.getFindCitiesResponse()!=null
                && event.getFindCitiesResponse().getCities()!=null) {
            for (City city : event.getFindCitiesResponse().getCities()) {
                MyLog.e(TAG, "Found " + city);
                this.cities.add(city);
            }
            cityView.updateCities();
        }
    }


    /**
     * This method is called when the CityService has added the selected city in dao
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CityAddedEvent event){
        MyLog.e("CityActivity ", "cityAdded=" + event.getCity());
        // then finish
        cityView.finishView();
    }
}

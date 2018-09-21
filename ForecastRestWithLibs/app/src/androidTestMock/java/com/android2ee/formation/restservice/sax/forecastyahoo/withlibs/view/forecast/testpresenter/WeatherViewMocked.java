/**
 * <ul>
 * <li>WeatherViewMocked</li>
 * <li>com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.injector.view.forecast</li>
 * <li>11/04/2016</li>
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

package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view.forecast.testpresenter;

import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.clientside.current.City;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.utils.MyLog;

/**
 * Created by Mathias Seguy - Android2EE on 11/04/2016.
 * This "view" is used to test the Presenter WeatherPresenter
 */
public class WeatherViewMocked implements WeatherViewIntf {
    private static final String TAG = "WeatherViewMocked";
    /***********************************************************
    *  Attributes
    **********************************************************/
    WeatherPresenterTest callBack;
    WeatherPresenter presenter;
    /***********************************************************
     *  Constructor
     **********************************************************/
    public WeatherViewMocked(WeatherPresenterTest callBack) {
        this.callBack = callBack;
    }
    public void setPresenter(WeatherPresenter presenter){
        this.presenter=presenter;
    }
    /***********************************************************
    *  Business Methods
    **********************************************************/
    /**
     * The cities have been loaded, you have to update your view
     */
    @Override
    public void citiesLoaded() {
        //track entrance
        MyLog.e(TAG, "citiesLoaded() has been called");
        callBack.loadCitiesCallBack(presenter);
    }

    /**
     * This city has been deleted
     *
     * @param deletedCity
     */
    @Override
    public void cityDeleted(City deletedCity) {

        callBack.cityDeleted(presenter);
    }

    /**
     * When there is no city in the Database, you have to launch the search activity from here     *
     *
     * @param finish true if the WeatherView has to die
     */
    @Override
    public void launchCityActivity(boolean finish) {
        callBack.launchCityActivity(presenter);
    }
}

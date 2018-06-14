/**
 * <ul>
 * <li>WeatherCityPresenter</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.fragment</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.fragment;

import android.util.Log;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.CityForecastLoadedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.WeatherLoadedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.exception.ExceptionManaged;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.exception.ExceptionManager;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.forecast.CityForecast;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.MotherPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Mathias Seguy - Android2EE on 09/03/2016.
 */
@Deprecated
public class WeatherCityPresenter extends MotherPresenter implements WeatherCityPresenterIntf {
    private static final String TAG = "WeatherCityPresenter";
    /***********************************************************
     *  Data set
     **********************************************************/
    /**
     * The CityForecast displayed by the view
     */
    CityForecast cityForecast=null;
    /***
     * The weather disaplyed by the view
     */
    Weather weather=null;
    /**
     * The cityId of the dataset displayed
     */
    int cityId=-1;
    /***********************************************************
     * Presenter Pattern
     **********************************************************/
    /**
     * The View
     */
    private WeatherCityViewIntf weatherCityView =null;
    /***********************************************************
     *  Constructor
     **********************************************************/
    /**
     * Empty constructor should not be used
     */
    private WeatherCityPresenter(){
        //avoid using this constructor
    }
    /**
     * The constructor
     * @param weatherCityView
     * @return
     */
    public WeatherCityPresenter(WeatherCityViewIntf weatherCityView) {
        this.weatherCityView = weatherCityView;
    }
    /***********************************************************
     *  Implementing WeatherCityPresenterIntf
     **********************************************************/
    /**
     * Load the Forecast for that city
     *
     * @param cityId
     */
    @Override
    public void loadForecast(int cityId) {
        if(this.cityId==-1){
            this.cityId=cityId;
        }else if(this.cityId != cityId){
            ExceptionManager.manage(new ExceptionManaged(WeatherCityPresenter.class, R.string.err_different_cityid_displayed_in_same_frag,new IllegalArgumentException()));
        }
        MyApplication.instance.getServiceManager().getForecastService().loadForecastAsync(cityId);
    }

    /**
     * Load the current weather for that city
     *
     * @param cityId
     */
    @Override
    public void loadCurrentWeather(int cityId) {
        if(this.cityId==-1){
            this.cityId=cityId;
        }else if(this.cityId != cityId){
            ExceptionManager.manage(new ExceptionManaged(WeatherCityPresenter.class, R.string.err_different_cityid_displayed_in_same_frag,new IllegalArgumentException()));
        }
        MyApplication.instance.getServiceManager().getWeatherService().loadCurrentWeatherAsync(cityId);
    }

    /**
     * get the current cityForecast to display
     *
     * @return
     */
    @Override
    public CityForecast getCityForecast() {
        return cityForecast;
    }

    /**
     * get the current weather to display
     *
     * @return
     */
    @Override
    public Weather getWeather() {
        return weather;
    }

    /***********************************************************
     *  Listening for services response
     **********************************************************/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CityForecastLoadedEvent event){
        Log.e(TAG, "CityForecastLoadedEvent event received = [" + event + "] and event.getCity="+event.getCityId()+" this.cityId="+cityId);
        //only update your view if it's your data
        if(event.getCityId()==cityId){
            cityForecast=event.getCityForecast();
            weatherCityView.updateForecast();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(WeatherLoadedEvent event){
        Log.e(TAG,"WeatherLoadedEvent event received = [" + event + "]");
        //only update your view if it's your data
        if(event.getCityId()==cityId){
            weather=event.getWeather();
            weatherCityView.updateCurrentWeather();
        }
    }
}

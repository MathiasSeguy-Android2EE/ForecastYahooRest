/**
 * <ul>
 * <li>NoConnectivityDataCommunication</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.com.noconnectivity</li>
 * <li>24/02/2016</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.com.noconnectivity;

import android.util.Log;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.com.DataCommunicationIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.FindCitiesResponse;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.forecast.CityForecast;

/**
 * Created by Mathias Seguy - Android2EE on 24/02/2016.
 */
public class NoConnectivityDataCommunication implements DataCommunicationIntf {
    @Override
    public FindCitiesResponse findCityByName(String cityName) {
        Log.i("NoConnectivityDataCom","Trying to make an Http call findTheCityByName but there is no connectivity");
        return null;
    }

    @Override
    public Weather findWeatherByCityId(long cityId) {
        Log.i("NoConnectivityDataCom","Trying to make an Http call findWeatherByCityId but there is no connectivity");
        return null;
    }

    @Override
    public CityForecast findForecastByCityId(long cityId) {
        Log.i("NoConnectivityDataCom","Trying to make an Http call findForecastByCityId but there is no connectivity");
        return null;
    }
}

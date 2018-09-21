/**
 * <ul>
 * <li>WebServiceIntf</li>
 * <li>com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.com</li>
 * <li>23/02/2016</li>
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
package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.com.retrofit;

import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.current.FindCitiesResponse;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.current.WeatherData;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.forecast.Forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mathias Seguy - Android2EE on 23/02/2016.
 */
public interface RetrofitServiceIntf {
    //find the city id
    @GET("find?type=like&sort=population&cnt=30&appid=5bdd3591cd56feae91bc8ac10c51ac8d")
    Call<FindCitiesResponse> findCityByName(@Query("q")String cityName);

    //find the weather
    @GET("weather?appid=5bdd3591cd56feae91bc8ac10c51ac8d")
    Call<WeatherData> findWeatherByCityServerId(@Query("id")long cityId);

    //find the forecast
    @GET("forecast?appid=5bdd3591cd56feae91bc8ac10c51ac8d")
    Call<Forecast> findForecastByCityId(@Query("id")long cityId);
}

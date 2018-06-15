/**
 * <ul>
 * <li>ConnectedDataCommunication</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.com.retrofit</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.com.retrofit;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.com.DataCommunicationIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.exception.ExceptionManaged;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.exception.ExceptionManager;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.FindCitiesResponse;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.forecast.CityForecast;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.WeatherData;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast.Forecast;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Mathias Seguy - Android2EE on 24/02/2016.
 * This class used retrofit to make Http calls
 */
public class ConnectedDataCommunication implements DataCommunicationIntf {
    private static final String TAG = "ConDataCom";

    /**
     * The call to find city using its name
     */
    Call<com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.FindCitiesResponse>  findCityByNameCall = null;
    /**
     * The call to find the weather of a city according the city Id
     */
    Call<WeatherData> findWeatherByCityIdCall = null;
    /**
     * The call to find the forecast of a city according the city Id
     */
    Call<Forecast> findForecastByCityIdCall = null;
    /**
     * The Retrofit service to make call
     */
    RetrofitServiceIntf webServiceComplex;

    /**
     * Constructor
     */
    public ConnectedDataCommunication() {
        MyLog.e(TAG, "ConnectedDataCommunication() called with: " + "");
        webServiceComplex = RetrofitBuilder.getComplexClient(MyApplication.instance);
    }

    @Override
    public com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.FindCitiesResponse findCityByName(String cityName) {
        MyLog.e(TAG, "findCityByName() called with: " + "cityName = [" + cityName + "]");
        try {
            findCityByNameCall=webServiceComplex.findCityByName(cityName);
            return new FindCitiesResponse(findCityByNameCall.execute().body());
        } catch (IOException e) {
            ExceptionManager.manage(new ExceptionManaged(ConnectedDataCommunication.class, R.string.datacom_findcity_ioexc,e));
        }
        return null;
    }


    @Override
    public Weather findWeatherByCityId(long cityId) {
        MyLog.e(TAG, "findWeatherByCityId() called with: " + "cityId = [" + cityId + "]");
        try {
            findWeatherByCityIdCall=webServiceComplex.findWeatherByCityId(cityId);
            return new Weather(findWeatherByCityIdCall.execute().body());
        } catch (IOException e) {
            ExceptionManager.manage(new ExceptionManaged(ConnectedDataCommunication.class, R.string.datacom_findcity_ioexc,e));
        }
        return null;
    }

    @Override
    public CityForecast findForecastByCityId(long cityId) {
        MyLog.e(TAG, "findForecastByCityId() called with: " + "cityId = [" + cityId + "]");
        try {
            findForecastByCityIdCall=webServiceComplex.findForecastByCityId(cityId);
            Forecast forecast=findForecastByCityIdCall.execute().body();
            MyLog.e(TAG,forecast.toString());
            int maxLogSize = 100;
            String longString=forecast.toString();
            for(int i = 0; i <= longString.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i+1) * maxLogSize;
                end = end > longString.length() ? longString.length() : end;
                MyLog.e(TAG, longString.substring(start, end));
            }
            MyLog.e(TAG,"Too long?");
            return new CityForecast(forecast);
        } catch (IOException e) {
            ExceptionManager.manage(new ExceptionManaged(ConnectedDataCommunication.class, R.string.datacom_findcity_ioexc,e));
        }
        return null;
    }

    @Override
    public com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.FindCitiesResponse getCitiesByName(String cityName) {
        MyLog.e(TAG, "findCityByName() called with: " + "cityName = [" + cityName + "]");
        try {
            findCityByNameCall=webServiceComplex.findCityByName(cityName);
            Response<com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.FindCitiesResponse> resp=findCityByNameCall.execute();
            if(resp.code()==200){
                return findCityByNameCall.execute().body();
            }else{
                ExceptionManager.manage(new ExceptionManaged(ConnectedDataCommunication.class, R.string.datacom_findcity_ioexc));
            }
        } catch (IOException e) {
            ExceptionManager.manage(new ExceptionManaged(ConnectedDataCommunication.class, R.string.datacom_findcity_ioexc,e));
        }
        return null;
    }

    @Override
    public WeatherData getWeatherByCityId(long cityId) {
        MyLog.e(TAG, "findWeatherByCityId() called with: " + "cityId = [" + cityId + "]");
        try {
            findWeatherByCityIdCall=webServiceComplex.findWeatherByCityId(cityId);
            return findWeatherByCityIdCall.execute().body();
        } catch (IOException e) {
            ExceptionManager.manage(new ExceptionManaged(ConnectedDataCommunication.class, R.string.datacom_findcity_ioexc,e));
        }
        return null;
    }

    @Override
    public Forecast getForecastByCityId(long cityId) {
        MyLog.e(TAG, "findForecastByCityId() called with: " + "cityId = [" + cityId + "]");
        try {
            findForecastByCityIdCall=webServiceComplex.findForecastByCityId(cityId);
            Forecast forecast=findForecastByCityIdCall.execute().body();
            MyLog.e(TAG,forecast.toString());
            int maxLogSize = 100;
            String longString=forecast.toString();
            for(int i = 0; i <= longString.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i+1) * maxLogSize;
                end = end > longString.length() ? longString.length() : end;
                MyLog.e(TAG, longString.substring(start, end));
            }
            MyLog.e(TAG,"Too long?");
            return forecast;
        } catch (IOException e) {
            ExceptionManager.manage(new ExceptionManaged(ConnectedDataCommunication.class, R.string.datacom_findcity_ioexc,e));
        }
        return null;
    }
}

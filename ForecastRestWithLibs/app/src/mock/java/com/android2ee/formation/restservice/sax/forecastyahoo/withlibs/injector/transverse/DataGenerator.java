/**
 * <ul>
 * <li>DataGenerator</li>
 * <li>com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model</li>
 * <li>06/03/2016</li>
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

package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.injector.transverse;

import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.Weather;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.current.FindCitiesResponse;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

/**
 * Created by Mathias Seguy - Android2EE on 06/03/2016.
 */
public class DataGenerator {
    /***********************************************************
     * Generate weather
     **********************************************************/
    private static String weatherServerSideStr=" {\"coord\":{\"lon\":1.44,\"lat\":43.6},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"base\":\"cmc stations\",\"main\":{\"temp\":277.884,\"pressure\":1003.19,\"humidity\":92,\"temp_min\":277.884,\"temp_max\":277.884,\"sea_level\":1022.9,\"grnd_level\":1003.19},\"wind\":{\"speed\":4.12,\"deg\":259.003},\"rain\":{\"3h\":0.165},\"clouds\":{\"all\":68},\"dt\":1457296231,\"sys\":{\"message\":0.0099,\"country\":\"FR\",\"sunrise\":1457245215,\"sunset\":1457286648},\"id\":2972315,\"name\":\"Toulouse\",\"cod\":200}";
    private static String weatherStr="{\"base\":\"cmc stations\",\"cityId\":2972315,\"clouds\":{\"cloudsCoveragePercents\":68},\"cod\":200,\"coordinates\":{\"lat\":43.6,\"lon\":1.44},\"ephemeris\":{\"cityId\":0,\"country\":\"FR\",\"message\":0.0099,\"sunrise\":1457245215,\"sunset\":1457286648,\"type\":0},\"name\":\"Toulouse\",\"rain\":{\"rainVolume3h\":0.165},\"snow\":{\"snowVolume3h\":0.0},\"timeStampUTC\":1457296231,\"weatherDetails\":{\"grnd_level\":1003.19,\"humidity\":92,\"pressure\":1003.19,\"sea_level\":1022.9,\"temp\":277.884,\"tempMax\":277.884,\"tempMin\":277.884},\"weatherMetaData\":[{\"description\":\"light rain\",\"icon\":\"10n\",\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":259.003,\"speed\":4.12}}";
    public static Weather getWeather(int cityId){
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Weather> adapter =
                moshi.adapter(Weather.class);
        try {
            Weather weather=adapter.fromJson(weatherStr);
//            weather.setCityId(cityId);
            MyLog.i("DataGenerator","Weather has been generated :"+weather);
            return weather;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    /***********************************************************
     *  Generates FindCitiesResponse
     **********************************************************/
    private static String findCitiesResponseStr="{\"cities\":[{\"cityId\":2972315,\"clouds\":{\"cloudsCoveragePercents\":0},\"coordinates\":{\"lat\":43.60426,\"lon\":1.44367},\"dateTimeUnix\":1460298488,\"ephemeris\":{\"cityId\":0,\"country\":\"FR\",\"message\":0.0,\"sunrise\":0,\"sunset\":0,\"type\":0},\"metaDataList\":[{\"description\":\"Sky is Clear\",\"icon\":\"01d\",\"main\":\"Clear\",\"weatherConditionId\":800}],\"name\":\"Toulouse\",\"weatherDetails\":{\"grnd_level\":0.0,\"humidity\":52,\"pressure\":1001.0,\"sea_level\":0.0,\"temp\":292.85,\"tempMax\":293.15,\"tempMin\":292.65},\"wind\":{\"deg\":130.0,\"speed\":9.3}}],\"cod\":200,\"count\":1,\"message\":\"like\"}";
    public static FindCitiesResponse getFindCitiesResponse(){
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<FindCitiesResponse> adapter =
                moshi.adapter(FindCitiesResponse.class);
        try {
            FindCitiesResponse findCitiesResponse=adapter.fromJson(findCitiesResponseStr);
            MyLog.i("DataGenerator","FindCitiesResponse has been generated :"+findCitiesResponse);
            return findCitiesResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

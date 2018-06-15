/**
 * <ul>
 * <li>DataGenerator</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.transverse;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.FindCitiesResponse;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.forecast.CityForecast;
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
            weather.setCityId(cityId);
            MyLog.i("DataGenerator","Weather has been generated :"+weather);
            return weather;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***********************************************************
     *  Generate city
     **********************************************************/
    private static String cityToulouse="{\"cityId\":2972315,\"clouds\":{\"cloudsCoveragePercents\":90},\"coordinates\":{\"lat\":43.60426,\"lon\":1.44367},\"dateTimeUnix\":1457381086,\"ephemeris\":{\"cityId\":0,\"country\":\"FR\",\"message\":0.0,\"sunrise\":0,\"sunset\":0,\"type\":0},\"metaDataList\":[{\"description\":\"overcast clouds\",\"icon\":\"04n\",\"main\":\"Clouds\",\"weatherConditionId\":804}],\"name\":\"Toulouse\",\"weatherDetails\":{\"grnd_level\":0.0,\"humidity\":81,\"pressure\":1011.0,\"sea_level\":0.0,\"temp\":278.47,\"tempMax\":279.15,\"tempMin\":277.95},\"wind\":{\"deg\":310.0,\"speed\":5.7}}";
    public static City getCity(){
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<City> adapter =
                moshi.adapter(City.class);
        try {
            City toulouse=adapter.fromJson(cityToulouse);
            MyLog.i("DataGenerator","City has been generated :"+toulouse);
            return toulouse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static City getCity(int cityID){
        return getCity(cityID,null);
    }
    public static City getCity(int cityID,String cityName){
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<City> adapter =
                moshi.adapter(City.class);
        try {
            City toulouse=adapter.fromJson(cityToulouse);
            MyLog.i("DataGenerator","City has been generated :"+toulouse);
            toulouse.setCityId(cityID);
            if(cityName!=null){
                toulouse.setName(cityName);
            }
            return toulouse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /***********************************************************
     *  Generates CityForecast
     **********************************************************/
    private static String cityForecastStr="{\"cityId\":2972315,\"cod\":\"200\",\"coordinates\":{\"lat\":43.60426,\"lon\":1.44367},\"country\":\"FR\",\"message\":0.0098,\"name\":\"Toulouse\",\"weatherForecasts\":[{\"clouds\":{\"cloudsCoveragePercents\":0},\"dateTimeStampUtc\":1457395200,\"dateTimeUtc\":\"2016-03-08 00:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1008.87,\"humidity\":87,\"pressure\":1008.87,\"sea_level\":1028.85,\"temp\":273.69,\"tempMax\":275.754,\"tempMin\":273.69},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10n\",\"id\":281,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":319.002,\"speed\":4.11}},{\"clouds\":{\"cloudsCoveragePercents\":8},\"dateTimeStampUtc\":1457406000,\"dateTimeUtc\":\"2016-03-08 03:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1010.45,\"humidity\":91,\"pressure\":1010.45,\"sea_level\":1030.4,\"temp\":270.61,\"tempMax\":272.555,\"tempMin\":270.61},\"weathers\":[{\"description\":\"clear sky\",\"icon\":\"02n\",\"id\":282,\"main\":\"Clear\",\"weatherConditionId\":800}],\"wind\":{\"deg\":300.503,\"speed\":3.17}},{\"clouds\":{\"cloudsCoveragePercents\":92},\"dateTimeStampUtc\":1457416800,\"dateTimeUtc\":\"2016-03-08 06:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1011.36,\"humidity\":91,\"pressure\":1011.36,\"sea_level\":1031.39,\"temp\":271.55,\"tempMax\":273.384,\"tempMin\":271.55},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10n\",\"id\":283,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":275.0,\"speed\":2.85}},{\"clouds\":{\"cloudsCoveragePercents\":64},\"dateTimeStampUtc\":1457427600,\"dateTimeUtc\":\"2016-03-08 09:00:00\",\"pod\":\"d\",\"weatherDetails\":{\"grnd_level\":1012.82,\"humidity\":92,\"pressure\":1012.82,\"sea_level\":1032.72,\"temp\":274.13,\"tempMax\":275.847,\"tempMin\":274.13},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10d\",\"id\":284,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":300.001,\"speed\":3.17}},{\"clouds\":{\"cloudsCoveragePercents\":12},\"dateTimeStampUtc\":1457438400,\"dateTimeUtc\":\"2016-03-08 12:00:00\",\"pod\":\"d\",\"weatherDetails\":{\"grnd_level\":1013.8,\"humidity\":94,\"pressure\":1013.8,\"sea_level\":1033.42,\"temp\":277.74,\"tempMax\":279.339,\"tempMin\":277.74},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10d\",\"id\":285,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":306.504,\"speed\":4.57}},{\"clouds\":{\"cloudsCoveragePercents\":48},\"dateTimeStampUtc\":1457449200,\"dateTimeUtc\":\"2016-03-08 15:00:00\",\"pod\":\"d\",\"weatherDetails\":{\"grnd_level\":1013.61,\"humidity\":77,\"pressure\":1013.61,\"sea_level\":1033.3,\"temp\":279.1,\"tempMax\":280.591,\"tempMin\":279.1},\"weathers\":[{\"description\":\"scattered clouds\",\"icon\":\"03d\",\"id\":286,\"main\":\"Clouds\",\"weatherConditionId\":802}],\"wind\":{\"deg\":307.004,\"speed\":5.47}},{\"clouds\":{\"cloudsCoveragePercents\":56},\"dateTimeStampUtc\":1457460000,\"dateTimeUtc\":\"2016-03-08 18:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1014.84,\"humidity\":67,\"pressure\":1014.84,\"sea_level\":1034.63,\"temp\":277.81,\"tempMax\":279.18,\"tempMin\":277.81},\"weathers\":[{\"description\":\"broken clouds\",\"icon\":\"04n\",\"id\":287,\"main\":\"Clouds\",\"weatherConditionId\":803}],\"wind\":{\"deg\":310.501,\"speed\":4.56}},{\"clouds\":{\"cloudsCoveragePercents\":68},\"dateTimeStampUtc\":1457470800,\"dateTimeUtc\":\"2016-03-08 21:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1015.86,\"humidity\":75,\"pressure\":1015.86,\"sea_level\":1035.72,\"temp\":275.68,\"tempMax\":276.934,\"tempMin\":275.68},\"weathers\":[{\"description\":\"broken clouds\",\"icon\":\"04n\",\"id\":288,\"main\":\"Clouds\",\"weatherConditionId\":803}],\"wind\":{\"deg\":304.507,\"speed\":3.31}},{\"clouds\":{\"cloudsCoveragePercents\":8},\"dateTimeStampUtc\":1457481600,\"dateTimeUtc\":\"2016-03-09 00:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1014.85,\"humidity\":90,\"pressure\":1014.85,\"sea_level\":1034.78,\"temp\":273.22,\"tempMax\":274.361,\"tempMin\":273.22},\"weathers\":[{\"description\":\"clear sky\",\"icon\":\"02n\",\"id\":289,\"main\":\"Clear\",\"weatherConditionId\":800}],\"wind\":{\"deg\":246.5,\"speed\":2.76}},{\"clouds\":{\"cloudsCoveragePercents\":56},\"dateTimeStampUtc\":1457492400,\"dateTimeUtc\":\"2016-03-09 03:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1012.28,\"humidity\":90,\"pressure\":1012.28,\"sea_level\":1032.24,\"temp\":271.65,\"tempMax\":272.681,\"tempMin\":271.65},\"weathers\":[{\"description\":\"broken clouds\",\"icon\":\"04n\",\"id\":290,\"main\":\"Clouds\",\"weatherConditionId\":803}],\"wind\":{\"deg\":247.002,\"speed\":2.33}},{\"clouds\":{\"cloudsCoveragePercents\":92},\"dateTimeStampUtc\":1457503200,\"dateTimeUtc\":\"2016-03-09 06:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1008.92,\"humidity\":85,\"pressure\":1008.92,\"sea_level\":1028.65,\"temp\":273.33,\"tempMax\":274.25,\"tempMin\":273.33},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10n\",\"id\":291,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":182.5,\"speed\":1.84}},{\"clouds\":{\"cloudsCoveragePercents\":92},\"dateTimeStampUtc\":1457514000,\"dateTimeUtc\":\"2016-03-09 09:00:00\",\"pod\":\"d\",\"weatherDetails\":{\"grnd_level\":1005.57,\"humidity\":88,\"pressure\":1005.57,\"sea_level\":1025.16,\"temp\":278.04,\"tempMax\":278.839,\"tempMin\":278.04},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10d\",\"id\":292,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":221.001,\"speed\":4.64}},{\"clouds\":{\"cloudsCoveragePercents\":92},\"dateTimeStampUtc\":1457524800,\"dateTimeUtc\":\"2016-03-09 12:00:00\",\"pod\":\"d\",\"weatherDetails\":{\"grnd_level\":1001.48,\"humidity\":96,\"pressure\":1001.48,\"sea_level\":1020.88,\"temp\":280.48,\"tempMax\":281.163,\"tempMin\":280.48},\"weathers\":[{\"description\":\"moderate rain\",\"icon\":\"10d\",\"id\":293,\"main\":\"Rain\",\"weatherConditionId\":501}],\"wind\":{\"deg\":247.007,\"speed\":9.52}},{\"clouds\":{\"cloudsCoveragePercents\":76},\"dateTimeStampUtc\":1457535600,\"dateTimeUtc\":\"2016-03-09 15:00:00\",\"pod\":\"d\",\"weatherDetails\":{\"grnd_level\":1002.65,\"humidity\":100,\"pressure\":1002.65,\"sea_level\":1022.06,\"temp\":280.74,\"tempMax\":281.308,\"tempMin\":280.74},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10d\",\"id\":294,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":296.005,\"speed\":8.86}},{\"clouds\":{\"cloudsCoveragePercents\":92},\"dateTimeStampUtc\":1457546400,\"dateTimeUtc\":\"2016-03-09 18:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1004.69,\"humidity\":97,\"pressure\":1004.69,\"sea_level\":1024.07,\"temp\":279.26,\"tempMax\":279.716,\"tempMin\":279.26},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10n\",\"id\":295,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":301.003,\"speed\":8.01}},{\"clouds\":{\"cloudsCoveragePercents\":92},\"dateTimeStampUtc\":1457557200,\"dateTimeUtc\":\"2016-03-09 21:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1006.77,\"humidity\":96,\"pressure\":1006.77,\"sea_level\":1026.34,\"temp\":278.85,\"tempMax\":279.191,\"tempMin\":278.85},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10n\",\"id\":296,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":309.507,\"speed\":7.37}},{\"clouds\":{\"cloudsCoveragePercents\":92},\"dateTimeStampUtc\":1457568000,\"dateTimeUtc\":\"2016-03-10 00:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1008.23,\"humidity\":95,\"pressure\":1008.23,\"sea_level\":1027.82,\"temp\":278.61,\"tempMax\":278.84,\"tempMin\":278.61},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10n\",\"id\":297,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":309.002,\"speed\":7.36}},{\"clouds\":{\"cloudsCoveragePercents\":92},\"dateTimeStampUtc\":1457578800,\"dateTimeUtc\":\"2016-03-10 03:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1009.03,\"humidity\":95,\"pressure\":1009.03,\"sea_level\":1028.72,\"temp\":277.93,\"tempMax\":278.04,\"tempMin\":277.93},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10n\",\"id\":298,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":310.505,\"speed\":7.6}},{\"clouds\":{\"cloudsCoveragePercents\":92},\"dateTimeStampUtc\":1457589600,\"dateTimeUtc\":\"2016-03-10 06:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1010.93,\"humidity\":98,\"pressure\":1010.93,\"sea_level\":1030.69,\"temp\":277.378,\"tempMax\":277.378,\"tempMin\":277.378},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10n\",\"id\":299,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":312.002,\"speed\":7.67}},{\"clouds\":{\"cloudsCoveragePercents\":92},\"dateTimeStampUtc\":1457600400,\"dateTimeUtc\":\"2016-03-10 09:00:00\",\"pod\":\"d\",\"weatherDetails\":{\"grnd_level\":1013.9,\"humidity\":98,\"pressure\":1013.9,\"sea_level\":1033.7,\"temp\":277.594,\"tempMax\":277.594,\"tempMin\":277.594},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10d\",\"id\":300,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":314.001,\"speed\":7.06}},{\"clouds\":{\"cloudsCoveragePercents\":88},\"dateTimeStampUtc\":1457611200,\"dateTimeUtc\":\"2016-03-10 12:00:00\",\"pod\":\"d\",\"weatherDetails\":{\"grnd_level\":1016.44,\"humidity\":99,\"pressure\":1016.44,\"sea_level\":1036.13,\"temp\":279.154,\"tempMax\":279.154,\"tempMin\":279.154},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10d\",\"id\":301,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":315.004,\"speed\":6.36}},{\"clouds\":{\"cloudsCoveragePercents\":92},\"dateTimeStampUtc\":1457622000,\"dateTimeUtc\":\"2016-03-10 15:00:00\",\"pod\":\"d\",\"weatherDetails\":{\"grnd_level\":1017.47,\"humidity\":97,\"pressure\":1017.47,\"sea_level\":1037.03,\"temp\":279.399,\"tempMax\":279.399,\"tempMin\":279.399},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10d\",\"id\":302,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":305.001,\"speed\":5.71}},{\"clouds\":{\"cloudsCoveragePercents\":92},\"dateTimeStampUtc\":1457632800,\"dateTimeUtc\":\"2016-03-10 18:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1019.08,\"humidity\":97,\"pressure\":1019.08,\"sea_level\":1038.75,\"temp\":279.188,\"tempMax\":279.188,\"tempMin\":279.188},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10n\",\"id\":303,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":304.501,\"speed\":5.05}},{\"clouds\":{\"cloudsCoveragePercents\":76},\"dateTimeStampUtc\":1457643600,\"dateTimeUtc\":\"2016-03-10 21:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1020.79,\"humidity\":97,\"pressure\":1020.79,\"sea_level\":1040.6,\"temp\":278.668,\"tempMax\":278.668,\"tempMin\":278.668},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10n\",\"id\":304,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":304.505,\"speed\":5.21}},{\"clouds\":{\"cloudsCoveragePercents\":8},\"dateTimeStampUtc\":1457654400,\"dateTimeUtc\":\"2016-03-11 00:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1021.45,\"humidity\":97,\"pressure\":1021.45,\"sea_level\":1041.34,\"temp\":277.024,\"tempMax\":277.024,\"tempMin\":277.024},\"weathers\":[{\"description\":\"clear sky\",\"icon\":\"02n\",\"id\":305,\"main\":\"Clear\",\"weatherConditionId\":800}],\"wind\":{\"deg\":301.001,\"speed\":4.78}},{\"clouds\":{\"cloudsCoveragePercents\":8},\"dateTimeStampUtc\":1457665200,\"dateTimeUtc\":\"2016-03-11 03:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1021.4,\"humidity\":96,\"pressure\":1021.4,\"sea_level\":1041.35,\"temp\":275.936,\"tempMax\":275.936,\"tempMin\":275.936},\"weathers\":[{\"description\":\"clear sky\",\"icon\":\"02n\",\"id\":306,\"main\":\"Clear\",\"weatherConditionId\":800}],\"wind\":{\"deg\":294.506,\"speed\":4.21}},{\"clouds\":{\"cloudsCoveragePercents\":24},\"dateTimeStampUtc\":1457676000,\"dateTimeUtc\":\"2016-03-11 06:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1021.86,\"humidity\":94,\"pressure\":1021.86,\"sea_level\":1041.87,\"temp\":274.967,\"tempMax\":274.967,\"tempMin\":274.967},\"weathers\":[{\"description\":\"few clouds\",\"icon\":\"02n\",\"id\":307,\"main\":\"Clouds\",\"weatherConditionId\":801}],\"wind\":{\"deg\":286.011,\"speed\":3.71}},{\"clouds\":{\"cloudsCoveragePercents\":20},\"dateTimeStampUtc\":1457686800,\"dateTimeUtc\":\"2016-03-11 09:00:00\",\"pod\":\"d\",\"weatherDetails\":{\"grnd_level\":1022.9,\"humidity\":100,\"pressure\":1022.9,\"sea_level\":1042.67,\"temp\":279.307,\"tempMax\":279.307,\"tempMin\":279.307},\"weathers\":[{\"description\":\"few clouds\",\"icon\":\"02d\",\"id\":308,\"main\":\"Clouds\",\"weatherConditionId\":801}],\"wind\":{\"deg\":292.005,\"speed\":3.56}},{\"clouds\":{\"cloudsCoveragePercents\":20},\"dateTimeStampUtc\":1457697600,\"dateTimeUtc\":\"2016-03-11 12:00:00\",\"pod\":\"d\",\"weatherDetails\":{\"grnd_level\":1022.71,\"humidity\":100,\"pressure\":1022.71,\"sea_level\":1042.3,\"temp\":282.285,\"tempMax\":282.285,\"tempMin\":282.285},\"weathers\":[{\"description\":\"few clouds\",\"icon\":\"02d\",\"id\":309,\"main\":\"Clouds\",\"weatherConditionId\":801}],\"wind\":{\"deg\":304.5,\"speed\":4.41}},{\"clouds\":{\"cloudsCoveragePercents\":0},\"dateTimeStampUtc\":1457708400,\"dateTimeUtc\":\"2016-03-11 15:00:00\",\"pod\":\"d\",\"weatherDetails\":{\"grnd_level\":1021.43,\"humidity\":79,\"pressure\":1021.43,\"sea_level\":1041.04,\"temp\":284.342,\"tempMax\":284.342,\"tempMin\":284.342},\"weathers\":[{\"description\":\"clear sky\",\"icon\":\"01d\",\"id\":310,\"main\":\"Clear\",\"weatherConditionId\":800}],\"wind\":{\"deg\":320.501,\"speed\":4.82}},{\"clouds\":{\"cloudsCoveragePercents\":92},\"dateTimeStampUtc\":1457719200,\"dateTimeUtc\":\"2016-03-11 18:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1021.97,\"humidity\":72,\"pressure\":1021.97,\"sea_level\":1041.6,\"temp\":283.075,\"tempMax\":283.075,\"tempMin\":283.075},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10n\",\"id\":311,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":325.004,\"speed\":4.21}},{\"clouds\":{\"cloudsCoveragePercents\":20},\"dateTimeStampUtc\":1457730000,\"dateTimeUtc\":\"2016-03-11 21:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1023.11,\"humidity\":83,\"pressure\":1023.11,\"sea_level\":1042.9,\"temp\":280.913,\"tempMax\":280.913,\"tempMin\":280.913},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10n\",\"id\":312,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":309.002,\"speed\":3.16}},{\"clouds\":{\"cloudsCoveragePercents\":76},\"dateTimeStampUtc\":1457740800,\"dateTimeUtc\":\"2016-03-12 00:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1023.41,\"humidity\":93,\"pressure\":1023.41,\"sea_level\":1043.26,\"temp\":279.257,\"tempMax\":279.257,\"tempMin\":279.257},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10n\",\"id\":313,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":284.504,\"speed\":2.71}},{\"clouds\":{\"cloudsCoveragePercents\":24},\"dateTimeStampUtc\":1457751600,\"dateTimeUtc\":\"2016-03-12 03:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1022.82,\"humidity\":92,\"pressure\":1022.82,\"sea_level\":1042.72,\"temp\":278.145,\"tempMax\":278.145,\"tempMin\":278.145},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10n\",\"id\":314,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":301.001,\"speed\":1.87}},{\"clouds\":{\"cloudsCoveragePercents\":48},\"dateTimeStampUtc\":1457762400,\"dateTimeUtc\":\"2016-03-12 06:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1022.92,\"humidity\":85,\"pressure\":1022.92,\"sea_level\":1042.85,\"temp\":276.368,\"tempMax\":276.368,\"tempMin\":276.368},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10n\",\"id\":315,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":287.503,\"speed\":2.41}},{\"clouds\":{\"cloudsCoveragePercents\":92},\"dateTimeStampUtc\":1457773200,\"dateTimeUtc\":\"2016-03-12 09:00:00\",\"pod\":\"d\",\"weatherDetails\":{\"grnd_level\":1023.63,\"humidity\":93,\"pressure\":1023.63,\"sea_level\":1043.38,\"temp\":280.932,\"tempMax\":280.932,\"tempMin\":280.932},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10d\",\"id\":316,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":276.503,\"speed\":2.46}},{\"clouds\":{\"cloudsCoveragePercents\":88},\"dateTimeStampUtc\":1457784000,\"dateTimeUtc\":\"2016-03-12 12:00:00\",\"pod\":\"d\",\"weatherDetails\":{\"grnd_level\":1022.78,\"humidity\":86,\"pressure\":1022.78,\"sea_level\":1042.4,\"temp\":283.632,\"tempMax\":283.632,\"tempMin\":283.632},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10d\",\"id\":317,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":282.502,\"speed\":4.26}},{\"clouds\":{\"cloudsCoveragePercents\":92},\"dateTimeStampUtc\":1457794800,\"dateTimeUtc\":\"2016-03-12 15:00:00\",\"pod\":\"d\",\"weatherDetails\":{\"grnd_level\":1021.17,\"humidity\":80,\"pressure\":1021.17,\"sea_level\":1040.63,\"temp\":284.616,\"tempMax\":284.616,\"tempMin\":284.616},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10d\",\"id\":318,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":290.005,\"speed\":5.46}},{\"clouds\":{\"cloudsCoveragePercents\":92},\"dateTimeStampUtc\":1457805600,\"dateTimeUtc\":\"2016-03-12 18:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1021.11,\"humidity\":87,\"pressure\":1021.11,\"sea_level\":1040.6,\"temp\":283.678,\"tempMax\":283.678,\"tempMin\":283.678},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10n\",\"id\":319,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":297.0,\"speed\":4.13}},{\"clouds\":{\"cloudsCoveragePercents\":80},\"dateTimeStampUtc\":1457816400,\"dateTimeUtc\":\"2016-03-12 21:00:00\",\"pod\":\"n\",\"weatherDetails\":{\"grnd_level\":1021.25,\"humidity\":96,\"pressure\":1021.25,\"sea_level\":1040.81,\"temp\":282.775,\"tempMax\":282.775,\"tempMin\":282.775},\"weathers\":[{\"description\":\"light rain\",\"icon\":\"10n\",\"id\":320,\"main\":\"Rain\",\"weatherConditionId\":500}],\"wind\":{\"deg\":285.005,\"speed\":3.42}}]}";
    public static CityForecast getCityForecast(int cityID){
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<CityForecast> adapter =
                moshi.adapter(CityForecast.class);
        try {
            CityForecast cityF=adapter.fromJson(cityForecastStr);
            MyLog.i("DataGenerator","City has been generated :"+cityF);
            cityF.setCityId(cityID);
            return cityF;
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

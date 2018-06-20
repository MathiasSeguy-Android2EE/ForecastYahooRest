package com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.database;

import android.util.Log;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.FindCitiesResponse;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.WeatherData;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast.Forecast;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast.WeatherForecastItem;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.PictureCacheDownloader;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 14/06/2018.
 */
public class DaoWrapper {
    /***********************************************************
     *  Singleton
     **********************************************************/
    private static DaoWrapper INSTANCE=null;
    public static DaoWrapper getInstance() {
        if(INSTANCE==null){
            INSTANCE=new DaoWrapper();
        }
        return INSTANCE;
    }
    private DaoWrapper(){};
    /***********************************************************
     *  Methods
     **********************************************************/
    /**
     * Save a City in Database
     * @param findCitiesRep
     */
    public void saveFindCitiesResponse(FindCitiesResponse findCitiesRep){
        CityDao cityDao=ForecastDatabase.getInstance().getCityDao();
        for (City city : findCitiesRep.getList()) {
            cityDao.insert(city);
        }
    }

    /**
     * Fully save a weatherData
     * @param weatherData
     */
    public void saveWeatherData(WeatherData weatherData){
        try {
            ForecastDatabase db=ForecastDatabase.getInstance();
            //save it
            long weatherDataId=db.getWeatherDataDao().insert(weatherData);
            weatherData.set_id(weatherDataId);
            //then persist the sub object
            //Persist Main
            weatherData.getMain().setWeatherDataId(weatherDataId);
            //important trick as you have 2 foreign key,
            //the on not used should be set to null
            weatherData.getMain().setWeatherForecastItemId(null);
            db.getMainDao().insert(weatherData.getMain());

            //Persist the sys object
            weatherData.getSys().setWeatherDataId(weatherDataId);
            db.getSysDao().insert(weatherData.getSys());

            //persist weather list
            WeatherDao weatherDao=db.getWeatherDao();
            for (Weather weather : weatherData.getWeather()) {
                weather.setWeatherDataId(weatherDataId);
                weather.setWeatherForecastItemId(null);
                weatherDao.insert(weather);
                //then download the picture
                if(PictureCacheDownloader.isPictureSavedOnDisk(weather.getIcon())){
                    //nothing to do
                }else{
                    //donwload and store it:
                    PictureCacheDownloader.savePicture(weather.getIcon(),
                            PictureCacheDownloader.downloadPicture("http://openweathermap.org/img/w/"+weather.getIcon()+".png"));
                }
            }
        } catch (Exception e) {
            Log.e("TAG", "EXCEPTION : ", e);
        }
    }

    public void saveForecast(Forecast forecast){
        ForecastDatabase db=ForecastDatabase.getInstance();
        //find the city id in the local db
        long cityId=db.getCityDao().loadLiveDataByName(forecast.getCity().getName()).get_id();
        //then  save each forecast
        WeatherForecastItemDao wfiDao=db.getWeatherForecastItemDao();
        MainDao mainDao=db.getMainDao();
        WeatherDao wDao=db.getWeatherDao();
        for (WeatherForecastItem weatherForecastItem : forecast.getWeatherForecastItem()) {
            weatherForecastItem.setCity_Id(cityId);
            saveWeatherForecastItem(weatherForecastItem,wfiDao,mainDao,wDao);
        }
    }

    /**
     * Save each WeatherForecastItem
     * @param weatherForecastItem
     * @param wfiDao
     * @param mainDao
     * @param wDao
     */
    private void saveWeatherForecastItem(WeatherForecastItem weatherForecastItem,
                                         WeatherForecastItemDao wfiDao,
                                         MainDao mainDao,
                                         WeatherDao wDao){
        //save it
        long weatherDataId=wfiDao.insert(weatherForecastItem);
        weatherForecastItem.set_id(weatherDataId);
        //then persist the sub object
        //Persist Main
        weatherForecastItem.getMain().setWeatherForecastItemId(weatherDataId);
        //important trick as you have 2 foreign key,
        //the on not used should be set to null
        weatherForecastItem.getMain().setWeatherDataId(null);
        mainDao.insert(weatherForecastItem.getMain());

        //persist weather list
        for (Weather weather : weatherForecastItem.getWeather()) {
            weather.setWeatherDataId(weatherDataId);
            weather.setWeatherForecastItemId(null);
            wDao.insert(weather);
        }
    }

}

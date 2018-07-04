package com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.database;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.PictureCacheDownloader;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.exception.ExceptionManaged;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.exception.ExceptionManager;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.calculated.WeatherOfTheDay;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.FindCitiesResponse;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.WeatherData;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast.Forecast;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast.WeatherForecastItem;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 14/06/2018.
 */
public class DaoWrapper {
    private static final String TAG = "DaoWrapper";
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
     *
     * @param findCitiesRep
     */
    public void saveFindCitiesResponse(FindCitiesResponse findCitiesRep) {
        try {
            CityDao cityDao = ForecastDatabase.getInstance().getCityDao();
            for (City city : findCitiesRep.getList()) {
                cityDao.insert(city);
            }
        } catch (Exception e) {
            ExceptionManager.manage(new ExceptionManaged(DaoWrapper.class, R.string.exc_database_cannot_open, e));
        }
    }

    /**
     * Fully save a weatherData
     * @param weatherData
     */
    public void saveWeatherData(WeatherData weatherData) {
        try {
            ForecastDatabase db = ForecastDatabase.getInstance();
            //save it
            long weatherDataId = db.getWeatherDataDao().insert(weatherData);
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
            WeatherDao weatherDao = db.getWeatherDao();
            for (Weather weather : weatherData.getWeather()) {
                weather.setWeatherDataId(weatherDataId);
                weather.setWeatherForecastItemId(null);
                weatherDao.insert(weather);
                //then download the picture
                if (PictureCacheDownloader.isPictureSavedOnDisk(weather.getIcon())) {
                    //nothing to do
                } else {
                    //donwload and store it:
                    PictureCacheDownloader.savePicture(weather.getIcon(),
                            PictureCacheDownloader.downloadPicture("http://openweathermap.org/img/w/" + weather.getIcon() + ".png"));
                }
            }
        } catch (Exception e) {
            ExceptionManager.manage(new ExceptionManaged(DaoWrapper.class, R.string.exc_database_cannot_open, e));
        }
    }

    public void saveForecast(Forecast forecast) {
        ForecastDatabase db = ForecastDatabase.getInstance();
        //find the city id in the local db
        long cityId = db.getCityDao().loadLiveDataByName(forecast.getCity().getName()).get_id();
        MyLog.e(TAG, "save forecast found cityId=" + cityId + " for the city :" + forecast.getCity().getName());
        //then  save each forecast
        WeatherForecastItemDao wfiDao = db.getWeatherForecastItemDao();
        MainDao mainDao = db.getMainDao();
        WeatherDao wDao = db.getWeatherDao();
        for (WeatherForecastItem weatherForecastItem : forecast.getWeatherForecastItem()) {
            weatherForecastItem.setCity_Id(cityId);
            weatherForecastItem.setDayStamp();
            saveWeatherForecastItem(weatherForecastItem, wfiDao, mainDao, wDao);
        }
        //Then manage the Weather Of The Day
        generateAndSaveWeatherOfTheDays(forecast.getWeatherForecastItem());
    }

    /**
     *
     * @param forecasts
     */
    private void generateAndSaveWeatherOfTheDays(List<WeatherForecastItem> forecasts) {
        MyLog.e(TAG, "generateAndSaveWeatherOfTheDays:" + forecasts.size());
        WeatherForecastItemDao wfiDao = ForecastDatabase.getInstance().getWeatherForecastItemDao();
        WeatherOfThDayDAO wotdDao = ForecastDatabase.getInstance().getWeatherOfTheDayDao();
        //retrieve the elements from database with the cityId and the HashOfTheDay
        //browse the data and create you object
        int currentDayHash = -1;
        long cityId = -1;
        int i = 0;
        List<WeatherForecastItem> forecastOfTheCurrentDay = new ArrayList<>(8);
        WeatherOfTheDay wotd;
        Long existingWotdId;
        //Browse the list to grab the dayHash to generate
        for (WeatherForecastItem forecast : forecasts) {
            if (currentDayHash != forecast.getDayHash()) {
                //changed day detection
                //So first create the WOTD according to the list of file you have
                //load them from database
                currentDayHash = forecast.getDayHash();
                cityId=forecast.getCity_Id();
                forecastOfTheCurrentDay = wfiDao.loadByCityIdAndHashDay(cityId, currentDayHash);
                MyLog.e(TAG, "we have found:" + forecastOfTheCurrentDay.size() + "[" + currentDayHash + "]and cityId="+cityId);
                //calculate and store for this elements list
                try{
                    wotd=createWeatherOfTheDay(forecastOfTheCurrentDay);
                    existingWotdId=wotdDao.loadIdByDayHashAndCity(currentDayHash,cityId);
                    if(existingWotdId==null){
                        MyLog.e(TAG, "insertion of WOTD case");
                        wotdDao.insert(wotd);
                    }else{
                        MyLog.e(TAG, "updating of WOTD case "+existingWotdId);
                        wotd.set_id_wotd(existingWotdId);
                        wotdDao.update(wotd);
                    }
                }catch (Exception e){
                    ExceptionManager.manage(new ExceptionManaged(DaoWrapper.class, R.string.exc_database_cannot_open, e));
                }
            }
            MyLog.e(TAG, "we have skip forecast with hash:" + currentDayHash + "[" + i + "]");
            i++;
        }
    }

    private WeatherOfTheDay createWeatherOfTheDay(List<WeatherForecastItem> forecasts){
        if(forecasts!=null&&forecasts.size()!=0) {
            MyLog.e(TAG, "createWeatherOfTheDay not empty list");
            WeatherForecastItem current=forecasts.get(0);
            WeatherOfTheDay wotd = new WeatherOfTheDay();
            //global attribute
            wotd.setCity_Id(current.getCity_Id());
            wotd.setDayHash(current.getDayHash());
            //initialization
            if(current.getClouds()!=null){
                wotd.setClouds(current.getClouds().getAll());
            }
            if(current.getWind()!=null) {
                wotd.setWindSpeed(current.getWind().getSpeed());
                wotd.setWindDegree(current.getWind().getDeg());
            }
            if(current.getRain()!=null) {
                wotd.setRain(current.getRain().get3h());
            }
            if(current.getSnow()!=null) {
                wotd.setSnow(current.getSnow().get3h());
            }
            if(current.getMain()!=null) {
                wotd.setTemp(current.getMain().getTemp());
                wotd.setPressure(current.getMain().getPressure());
                wotd.setHumidity(current.getMain().getHumidity());
                wotd.setTempMax(current.getMain().getTemp());
                wotd.setTempMin(current.getMain().getTemp());
            }
            //TODO find an algo for weather (icon/main/desc)
            //First element is consumed, keep going with others
            for (int i = 1; i < forecasts.size(); i++) {
                MyLog.e(TAG, "createWeatherOfTheDay managing:" + i + " element");
                current=forecasts.get(i);
                //elements based on an average calculation
                if(current.getClouds()!=null){
                    wotd.setClouds(average(current.getClouds().getAll(),wotd.getClouds(),i));
                }
                if(current.getWind()!=null) {
                    wotd.setWindSpeed(average(current.getWind().getSpeed(), wotd.getWindSpeed(), i));
                    wotd.setWindDegree(average(current.getWind().getDeg(), wotd.getWindDegree(), i));
                }

                if(current.getMain()!=null) {
                    wotd.setPressure(average(current.getMain().getPressure(), wotd.getPressure(), i));
                    wotd.setHumidity(average(current.getMain().getHumidity(), wotd.getHumidity(), i));
                }
                //elements based on sum
                if(current.getSnow()!=null) {
                    wotd.setSnow(wotd.getSnow()+current.getSnow().get3h());
                }
                if(current.getRain()!=null) {
                    wotd.setRain(wotd.getRain()+current.getRain().get3h());
                }
                //elements based on Min/Max
                if(current.getMain()!=null) {
                    wotd.setTempMin(Math.min(wotd.getTempMin(), current.getMain().getTemp()));
                    wotd.setTempMax(Math.max(wotd.getTempMax(), current.getMain().getTemp()));
                }
                //others temp? icon ? desc ? main
            }
            MyLog.e(TAG, "createWeatherOfTheDay Have created:" + wotd + "]");
            return wotd;
        }else{
            MyLog.e(TAG, "createWeatherOfTheDay Empty list");
            MyLog.e(TAG, "createWeatherOfTheDay Have created: null");
            return null;
        }

    }

    private float average(float newElement, float currentAverage, int currentElements){
        return (currentAverage*currentElements+newElement)/(currentElements+1);
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
                                         WeatherDao wDao) {
        try {
            //first check if update or insert depending on the field dt
            Long existingElementId = wfiDao.loadIdByDateTimeAndCity(weatherForecastItem.getDt(),weatherForecastItem.getCity_Id());
            if (existingElementId != null) {
                /**
                 * Update case: Delete the object and its sub object
                 */
                wfiDao.delete(existingElementId);
            }
            /**
             * The insert case
             */
            //save it
            long weatherDataId = wfiDao.insert(weatherForecastItem);

//            MyLog.e(TAG, "saveWeatherForecastItem has saved" + weatherDataId + " for the cityId set to :" + weatherForecastItem.getCity_Id());
            weatherForecastItem.set_id(weatherDataId);
            //then persist the sub object
            //Persist Main
            //important trick as you have 2 foreign key,
            //the one not used should be set to null
            weatherForecastItem.getMain().setWeatherForecastItemId(weatherDataId);
            weatherForecastItem.getMain().setWeatherDataId(null);
            mainDao.insert(weatherForecastItem.getMain());

            //persist weather list
            for (Weather weather : weatherForecastItem.getWeather()) {
                weather.setWeatherDataId(null);
                weather.setWeatherForecastItemId(weatherDataId);
                wDao.insert(weather);
            }

        } catch (Exception e) {
            ExceptionManager.manage(new ExceptionManaged(DaoWrapper.class, R.string.exc_database_cannot_open, e));
//            MyLog.e(TAG,"A fuck occured",e);
        }
    }

}

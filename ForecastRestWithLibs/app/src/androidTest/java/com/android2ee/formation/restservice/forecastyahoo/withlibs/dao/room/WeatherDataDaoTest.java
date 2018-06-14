package com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.room;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.database.ForecastDatabase;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.database.WeatherDao;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.DataGeneratorSimple;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.WeatherData;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast.WeatherForecastItem;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;


/**
 * Created by Created by Mathias Seguy alias Android2ee on 13/06/2018.
 * https://stackoverflow.com/questions/44270688/unit-testing-room-and-livedata
 */
@RunWith(AndroidJUnit4.class)
public class WeatherDataDaoTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private static final String TAG = "WeatherDataDaoTest";
    private ForecastDatabase db;
    Observer<List<WeatherData>> weatherDataObserver;
    Observer<List<WeatherForecastItem>> weatherForecastItemObserver;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, ForecastDatabase.class)
                .allowMainThreadQueries().build();
        //populate with the data set
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void testPopulate(){
        //declare the observer
        weatherDataObserver=new Observer<List<WeatherData>>() {
            @Override
            public void onChanged(@Nullable List<WeatherData> weatherData) {
                if(weatherData.size()==0){
                    //first call
                }else{
                    Log.e(TAG,"Toto est un roi");
                    Assert.assertEquals(1,weatherData.size());
                }
            }
        };
        weatherForecastItemObserver=new Observer<List<WeatherForecastItem>>() {
            @Override
            public void onChanged(@Nullable List<WeatherForecastItem> weatherForecastItems) {
                if(weatherForecastItems.size()==0){
                    //first call
                }else{
                    Log.e(TAG,"Toto est un roi deux");
                    Assert.assertEquals(1,weatherForecastItems.size());
                }
            }
        };
        //declare the LiveData you listen,
        LiveData<List<WeatherData>> lvWeatherData=db.getWeatherDataDao().loadAllLiveData();
        LiveData<List<WeatherForecastItem>> lvWFI=db.getWeatherForecastItemDao().loadAllLiveData();
        //bind the observer to the live data
        lvWFI.observeForever(weatherForecastItemObserver);
        lvWeatherData.observeForever(weatherDataObserver);

        /**Make you insertion or DB stuff*/
        //create the City (no city no foreign key, no weatherData)
        City city= DataGeneratorSimple.getCity("Palaminy");
        long cityId=db.getCityDao().insert(city);
        //create your WeatherData object
        WeatherData wData= populateWithAWeatherData(cityId);
        //create your WeatherForecastItem object
        WeatherForecastItem wForecast= populateWithAWeatherForecastItem(cityId);
        //then check your insertion

        //Remove observer
        //it works because you allow the main thread query:allowMainThreadQueries
        lvWeatherData.removeObserver(weatherDataObserver);
        lvWFI.removeObserver(weatherForecastItemObserver);


    }

    /**
     * Populate the DB with a WeatherData object
     * @param cityId
     */
    private WeatherData populateWithAWeatherData(long cityId) {
        WeatherData weatherData=DataGeneratorSimple.getWeatherData(cityId);
        //in fact no need the fake object is already created with this id
        //but in real life you have to make it, so I keep it here
        weatherData.setCity_Id(cityId);
        //save it
        long weatherDataId=db.getWeatherDataDao().insert(weatherData);
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
        }
        return weatherData;
    }
    /**
     * Populate the DB with a WeatherData object
     * @param cityId
     */
    private WeatherForecastItem populateWithAWeatherForecastItem(long cityId) {
        WeatherForecastItem weatherForecastItem=DataGeneratorSimple.getWeatherForecastItem(cityId);
        //in fact no need the fake object is already created with this id
        //but in real life you have to make it, so I keep it here
        weatherForecastItem.setCity_Id(cityId);
        //save it
        long weatherDataId=db.getWeatherForecastItemDao().insert(weatherForecastItem);
        //then persist the sub object
        //Persist Main
        weatherForecastItem.getMain().setWeatherForecastItemId(weatherDataId);
        //important trick as you have 2 foreign key,
        //the on not used should be set to null
        weatherForecastItem.getMain().setWeatherDataId(null);
        db.getMainDao().insert(weatherForecastItem.getMain());

        //persist weather list
        WeatherDao weatherDao=db.getWeatherDao();
        for (Weather weather : weatherForecastItem.getWeather()) {
            weather.setWeatherDataId(weatherDataId);
            weather.setWeatherForecastItemId(null);
            weatherDao.insert(weather);
        }
        return weatherForecastItem;
    }

}

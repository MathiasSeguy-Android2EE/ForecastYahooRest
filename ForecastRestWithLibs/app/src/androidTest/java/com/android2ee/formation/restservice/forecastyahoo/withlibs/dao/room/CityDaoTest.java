package com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.room;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.ForecastDatabase;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.DataGeneratorSimple;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Marion Aubard on 14/06/2018.
 */
@RunWith(AndroidJUnit4.class)
public class CityDaoTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private static final String TAG = "CityDaoTest";
    private ForecastDatabase db;

    private static final String CITY_PALAMINY = "Palaminy";
    private static final String CITY_SAINT_QUENTIN = "Saint-Quentin";

    Observer<List<City>> cityObserver;
    Observer<City> oneCityObserver;

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
        cityObserver = new Observer<List<City>>() {
            @Override
            public void onChanged(@Nullable List<City> cities) {
                if(cities.size() == 0){
                    //first call
                } else{
                    MyLog.e(TAG,"Toto est un roi");
                    Assert.assertEquals(1, cities.size());
                }
            }
        };

        //declare the LiveData you listen,
        LiveData<List<City>> lvCities = db.getCityDao().loadAllLiveData();

        //bind the observer to the live data
        lvCities.observeForever(cityObserver);

        /**Make you insertion or DB stuff*/
        //create the City (no city no foreign key, no weatherData)
        City city = DataGeneratorSimple.getCity("Palaminy");
        db.getCityDao().insert(city);

        //Remove observer
        //it works because you allow the main thread query:allowMainThreadQueries
        lvCities.removeObserver(cityObserver);
    }

    @Test
    public void testPopulateAll(){
        //declare the observer
        cityObserver = new Observer<List<City>>() {
            @Override
            public void onChanged(@Nullable List<City> cities) {
                if(cities.size() == 0){
                    //first call
                } else{
                    MyLog.e(TAG,"Toto est un roi");
                    Assert.assertEquals(2, cities.size());
                }
            }
        };

        //declare the LiveData you listen,
        LiveData<List<City>> lvCities = db.getCityDao().loadAllLiveData();

        //bind the observer to the live data
        lvCities.observeForever(cityObserver);

        /**Make you insertion or DB stuff*/
        //create the City (no city no foreign key, no weatherData)
        City city1 = DataGeneratorSimple.getCity(CITY_SAINT_QUENTIN);
        City city2 = DataGeneratorSimple.getCity(CITY_PALAMINY);
        List<City> cities = Arrays.asList(city1, city2);
        db.getCityDao().insertAll(cities);

        //Remove observer
        //it works because you allow the main thread query:allowMainThreadQueries
        lvCities.removeObserver(cityObserver);
    }

    @Test
    public void testLoadLiveDataById(){
        //declare the observer
        oneCityObserver = new Observer<City>() {
            @Override
            public void onChanged(@Nullable City city) {
                Assert.assertEquals(CITY_PALAMINY, city.getName());
            }
        };

        /**Make you insertion or DB stuff*/
        //create the City (no city no foreign key, no weatherData)
        City city = DataGeneratorSimple.getCity("Palaminy");
        long id = db.getCityDao().insert(city);

        //declare the LiveData you listen,
        LiveData<City> lvCity = db.getCityDao().loadLiveDataById(id);

        //bind the observer to the live data
        lvCity.observeForever(oneCityObserver);


        //Remove observer
        //it works because you allow the main thread query:allowMainThreadQueries
        lvCity.removeObserver(oneCityObserver);
    }

    @Test
    public void testDelete() {
        //declare the observer
        cityObserver = new Observer<List<City>>() {
            @Override
            public void onChanged(@Nullable List<City> cities) {
                if (cities.size() == 1){
                    //first call
                } else if(cities.size() == 0){
                    MyLog.e(TAG,"Toto est un roi");
                    Assert.assertEquals(0, cities.size());
                }
            }
        };

        //declare the LiveData you listen,
        LiveData<List<City>> lvCities = db.getCityDao().loadAllLiveData();

        /**Make you insertion or DB stuff*/
        //create the City (no city no foreign key, no weatherData)
        City city = DataGeneratorSimple.getCity(CITY_PALAMINY);
        long id = db.getCityDao().insert(city);
        city.set_id(id);

        //bind the observer to the live data
        lvCities.observeForever(cityObserver);

        db.getCityDao().delete(city);

        //Remove observer
        //it works because you allow the main thread query:allowMainThreadQueries
        lvCities.removeObserver(cityObserver);
    }

    @Test
    public void testDeleteById() {
        //declare the observer
        cityObserver = new Observer<List<City>>() {
            @Override
            public void onChanged(@Nullable List<City> cities) {
                if (cities.size() == 1){
                    //first call
                } else if(cities.size() == 0){
                    MyLog.e(TAG,"Toto est un roi");
                    Assert.assertEquals(0, cities.size());
                }
            }
        };

        //declare the LiveData you listen,
        LiveData<List<City>> lvCities = db.getCityDao().loadAllLiveData();

        /**Make you insertion or DB stuff*/
        //create the City (no city no foreign key, no weatherData)
        City city = DataGeneratorSimple.getCity(CITY_PALAMINY);
        long id = db.getCityDao().insert(city);

        //bind the observer to the live data
        lvCities.observeForever(cityObserver);

        db.getCityDao().delete(id);

        //Remove observer
        //it works because you allow the main thread query:allowMainThreadQueries
        lvCities.removeObserver(cityObserver);
    }

    @Test
    public void testUpdate() {
        //declare the observer
        cityObserver = new Observer<List<City>>() {
            @Override
            public void onChanged(@Nullable List<City> cities) {
                if (cities.size() == 1){
                    //first call
                    City city = cities.get(0);
                    if (CITY_PALAMINY.equals(city.getName())) {
                        MyLog.e(TAG,"Toto est le roi de " + CITY_PALAMINY);
                        Assert.assertEquals(CITY_PALAMINY, city.getName());
                    }
                    else if (CITY_SAINT_QUENTIN.equals(city.getName())) {
                        MyLog.e(TAG,"Toto est le roi de " + CITY_SAINT_QUENTIN);
                        Assert.assertEquals(CITY_SAINT_QUENTIN, city.getName());
                    }
                }
            }
        };

        //declare the LiveData you listen,
        LiveData<List<City>> lvCities = db.getCityDao().loadAllLiveData();

        /**Make you insertion or DB stuff*/
        //create the City (no city no foreign key, no weatherData)
        City city = DataGeneratorSimple.getCity(CITY_PALAMINY);
        long id = db.getCityDao().insert(city);
        city.set_id(id);

        //bind the observer to the live data
        lvCities.observeForever(cityObserver);

        city.setName(CITY_SAINT_QUENTIN);
        db.getCityDao().update(city);

        //Remove observer
        //it works because you allow the main thread query:allowMainThreadQueries
        lvCities.removeObserver(cityObserver);
    }
}

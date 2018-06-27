package com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Weather;

import java.util.List;

@Dao
public interface WeatherDao {
    /***********************************************************
     *  LiveData request
     **********************************************************/
    @Query("SELECT * FROM weather ")
    LiveData<List<Weather>> loadAllLiveData();

    @Query("SELECT * FROM weather WHERE _id IN (:id)")
    LiveData<Weather> loadLiveDataById(long id);

    @Query("SELECT * FROM weather WHERE weatherForecastItemId IN (:weatherForecastItemId)")
    LiveData<List<Weather>> loadLiveDataWeatherForWeatherForecastItem(long weatherForecastItemId);

    @Query("SELECT * FROM weather WHERE weatherForecastItemId IN (:weatherForecastItemId) LIMIT 1")
    LiveData<Weather> loadLiveDataWeatherForWeatherForecastItemUnique(long weatherForecastItemId);
    @Query("SELECT * FROM weather WHERE weatherDataId IN (:weatherDataId)")
    LiveData<List<Weather>> loadLiveDataWeatherForWeatherData(long weatherDataId);

    /***********************************************************
     *  Insert
     **********************************************************/
    @Insert
    long[] insertAll(List<Weather> weathers);

    @Insert
    long insert(Weather weather);

    /***********************************************************
     *  Update
     **********************************************************/
    @Update
    int update(Weather weather);

    /***********************************************************
     *  Delete
     **********************************************************/
    @Delete
    int delete(Weather weather);

    @Query("DELETE  FROM weather WHERE _id IN (:id)")
    int delete(long id);
    /***********************************************************
     *  For the examples
     **********************************************************/
//    @Query("SELECT * FROM battle WHERE title LIKE :title LIMIT 1")
//    Battle findByName(String title);
//    @Query("SELECT * FROM battle WHERE _id IN (:ids)")
//    List<Battle> loadAllByIds(int[] ids);
//    @Insert
//    long[] insertAll(Battle... battles);
}

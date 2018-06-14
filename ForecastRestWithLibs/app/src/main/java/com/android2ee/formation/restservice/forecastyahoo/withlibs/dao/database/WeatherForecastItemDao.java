package com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast.WeatherForecastItem;

import java.util.List;

@Dao
public interface WeatherForecastItemDao {
    /***********************************************************
     *  LiveData request
     **********************************************************/
    @Query("SELECT * FROM weather_forecast_item ")
    LiveData<List<WeatherForecastItem>> loadAllLiveData();

    @Query("SELECT * FROM weather_forecast_item WHERE _id IN (:id)")
    LiveData<WeatherForecastItem> loadLiveDataById(long id);

    @Query("SELECT * FROM weather_forecast_item WHERE cityId IN (:cityId)")
    LiveData<List<WeatherForecastItem>> loadLiveDataByCityId(long cityId);

    /***********************************************************
     *  Insert
     **********************************************************/
    @Insert
    long[] insertAll(List<WeatherForecastItem> weatherForecastItems);

    @Insert
    long insert(WeatherForecastItem weatherForecastItem);

    /***********************************************************
     *  Update
     **********************************************************/
    @Update
    int update(WeatherForecastItem weatherForecastItem);

    /***********************************************************
     *  Delete
     **********************************************************/
    @Delete
    int delete(WeatherForecastItem weatherForecastItem);

    @Query("DELETE  FROM weather_forecast_item WHERE _id IN (:id)")
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

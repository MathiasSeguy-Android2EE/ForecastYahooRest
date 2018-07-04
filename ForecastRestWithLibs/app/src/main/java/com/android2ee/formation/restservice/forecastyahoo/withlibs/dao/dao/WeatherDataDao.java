package com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.current.WeatherData;

import java.util.List;

@Dao
public interface WeatherDataDao {
    /***********************************************************
     *  LiveData request
     **********************************************************/
    @Query("SELECT * FROM weather_data_current ")
    LiveData<List<WeatherData>> loadAllLiveData();

    @Query("SELECT * FROM weather_data_current WHERE _id IN (:id)")
    LiveData<WeatherData> loadLiveDataById(long id);

    @Query("SELECT * FROM weather_data_current WHERE cityId IN (:cityId)")
    LiveData<List<WeatherData>> loadLiveDataByCityId(long cityId);
    @Query("SELECT * FROM weather_data_current WHERE cityId IN (:cityServerId) ORDER BY dateTime DESC LIMIT 1 ")
    LiveData<WeatherData> loadLiveDataCurrentByCityId(long cityServerId);
    /***********************************************************
     *  Insert
     **********************************************************/
    @Insert
    long[] insertAll(List<WeatherData> weatherData);

    @Insert
    long insert(WeatherData weatherData);

    /***********************************************************
     *  Update
     **********************************************************/
    @Update
    int update(WeatherData weatherData);

    /***********************************************************
     *  Delete
     **********************************************************/
    @Delete
    int delete(WeatherData weatherData);

    @Query("DELETE  FROM weather_data_current WHERE _id IN (:id)")
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

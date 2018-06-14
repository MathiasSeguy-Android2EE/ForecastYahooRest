package com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Main;

import java.util.List;

@Dao
public interface MainDao {
    /***********************************************************
     *  LiveData request
     **********************************************************/
    @Query("SELECT * FROM main_temp ")
    LiveData<List<Main>> loadAllLiveData();

    @Query("SELECT * FROM main_temp WHERE _id IN (:id)")
    LiveData<Main> loadLiveDataById(long id);

    @Query("SELECT * FROM main_temp WHERE weatherForecastItemId IN (:weatherForecastItemId)")
    LiveData<List<Main>> loadLiveDataWeatherForWeatherForecastItem(long weatherForecastItemId);

    @Query("SELECT * FROM main_temp WHERE weatherDataId IN (:weatherDataId)")
    LiveData<List<Main>> loadLiveDataWeatherForWeatherData(long weatherDataId);

    /***********************************************************
     *  Insert
     **********************************************************/
    @Insert
    long[] insertAll(List<Main> mains);

    @Insert
    long insert(Main main);

    /***********************************************************
     *  Update
     **********************************************************/
    @Update
    int update(Main main);

    /***********************************************************
     *  Delete
     **********************************************************/
    @Delete
    int delete(Main main);

    @Delete
    int deleteAll(List<Main> mainList);

    @Query("DELETE  FROM main_temp WHERE _id IN (:id)")
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

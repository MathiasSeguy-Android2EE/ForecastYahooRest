package com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.Sys;

import java.util.List;

@Dao
public interface SysDao {
    /***********************************************************
     *  LiveData request
     **********************************************************/
    @Query("SELECT * FROM sys_current ")
    LiveData<List<Sys>> loadAllLiveData();

    @Query("SELECT * FROM sys_current WHERE _id IN (:id)")
    LiveData<Sys> loadLiveDataById(long id);

//    @Query("SELECT * FROM sys_current WHERE weatherForecastItemId IN (:weatherForecastItemId)")
//    LiveData<List<Sys>> loadLiveDataMainForWeatherForecastItem(long weatherForecastItemId);

    @Query("SELECT * FROM sys_current WHERE weatherDataId IN (:weatherDataId)")
    LiveData<Sys> loadLiveDataSysByWeatherDataId(long weatherDataId);

    /***********************************************************
     *  Insert
     **********************************************************/
    @Insert
    long[] insertAll(List<Sys> sys);

    @Insert
    long insert(Sys sys);

    /***********************************************************
     *  Update
     **********************************************************/
    @Update
    int update(Sys sys);

    /***********************************************************
     *  Delete
     **********************************************************/
    @Delete
    int delete(Sys city);

    @Query("DELETE  FROM sys_current WHERE _id IN (:id)")
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

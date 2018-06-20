package com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.City;

import java.util.List;

@Dao
public interface CityDao {
    /***********************************************************
     *  LiveData request
     **********************************************************/
    @Query("SELECT * FROM city ")
    LiveData<List<City>> loadAllLiveData();

    @Query("SELECT * FROM city WHERE on_stage = 1")
    LiveData<List<City>> loadOnStageCities();

    @Query("SELECT * FROM city WHERE _id IN (:id)")
    LiveData<City> loadLiveDataById(long id);

    @Query("SELECT _id FROM city WHERE serverId IN (:serverid)")
    Long loadLiveDataByServerId(long serverid);

    @Query("SELECT * FROM city WHERE name LIKE :name")
    City loadLiveDataByName(String name);
    /***********************************************************
     *  Normal Query
     **********************************************************/
    @Query("SELECT * FROM city ")
    List<City> loadAll();
    @Query("SELECT COUNT(*) FROM city")
    LiveData<Integer> count();
    @Query("UPDATE city SET on_stage = 1 WHERE _id = :id")
    void setOnStage(long id);
    @Query("UPDATE city SET on_stage = 0 ")
    void setOutOfStage();

    /***********************************************************
     *  Insert
     **********************************************************/
    @Insert
    long[] insertAll(List<City> cities);

    @Insert
    long insert(City city);

    /***********************************************************
     *  Update
     **********************************************************/
    @Update
    int update(City city);

    /***********************************************************
     *  Delete
     **********************************************************/
    @Delete
    int delete(City city);

    @Query("DELETE  FROM city WHERE _id IN (:id)")
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

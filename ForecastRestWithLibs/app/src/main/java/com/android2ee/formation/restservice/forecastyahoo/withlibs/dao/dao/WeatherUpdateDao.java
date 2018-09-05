package com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.application.WeatherUpdate;

import java.util.List;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 04/09/2018.
 */
@Dao
public interface WeatherUpdateDao {
    /***********************************************************
     *  LiveData request
     **********************************************************/
    @Query("SELECT * FROM weather_update")
    LiveData<List<WeatherUpdate>> loadAll();

    @Query("SELECT * FROM weather_update ORDER BY _id_update DESC LIMIT 1")
    LiveData<WeatherUpdate> loadLastTry();

    @Query("SELECT * FROM weather_update WHERE succeed = 1  ORDER BY _id_update DESC LIMIT 1")
    LiveData<WeatherUpdate> loadLastSuccess();

    @Query("DELETE FROM weather_update")
    int deleteAll();
    @Insert
    long insert(WeatherUpdate weatherUpdate);
}

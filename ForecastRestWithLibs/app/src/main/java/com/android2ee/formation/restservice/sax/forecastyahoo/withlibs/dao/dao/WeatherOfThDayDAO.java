package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.dao.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.calculated.WeatherOfTheDay;

import java.util.List;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 04/07/2018.
 */
@Dao
public interface WeatherOfThDayDAO {

    /***********************************************************
     *  LiveData request
     **********************************************************/
    @Query("SELECT * FROM weather_of_the_day ")
    LiveData<List<WeatherOfTheDay>> loadAllLiveData();

    @Query("SELECT * FROM weather_of_the_day WHERE _id_wotd IN (:id)")
    LiveData<WeatherOfTheDay> loadLiveDataById(long id);

    @Query("SELECT * FROM weather_of_the_day WHERE cityId IN (:cityId)")
    LiveData<List<WeatherOfTheDay>> loadLiveDataByCityId(long cityId);

    @Query("SELECT _id_wotd FROM weather_of_the_day WHERE day_hash IN (:hashDay) AND cityId IN (:cityId)")
    Long loadIdByDayHashAndCity(int hashDay,long cityId);

    @Query("SELECT * FROM weather_of_the_day WHERE day_hash IN (:hashDay) AND cityId IN (:cityId)")
    WeatherOfTheDay loadByDayHashAndCity(int hashDay,long cityId);
    /***********************************************************
     *  Insert
     **********************************************************/
    @Insert
    long[] insertAll(List<WeatherOfTheDay> weatherForecastItems);

    @Insert
    long insert(WeatherOfTheDay weatherForecastItem);

    /***********************************************************
     *  Update
     **********************************************************/
    @Update
    int update(WeatherOfTheDay weatherForecastItem);

    /***********************************************************
     *  Delete
     **********************************************************/
    @Delete
    int delete(WeatherOfTheDay weatherForecastItem);

    @Query("DELETE  FROM weather_of_the_day WHERE _id_wotd IN (:id)")
    int delete(long id);
}

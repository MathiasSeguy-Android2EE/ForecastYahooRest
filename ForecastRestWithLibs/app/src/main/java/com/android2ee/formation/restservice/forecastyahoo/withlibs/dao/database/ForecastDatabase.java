package com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Main;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.Sys;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.WeatherData;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast.WeatherForecastItem;

@Database(entities = {Weather.class, Sys.class, WeatherForecastItem.class, City.class, Main.class, WeatherData.class}, version = 2)
@TypeConverters({WeatherConverter.class})
public abstract class ForecastDatabase extends RoomDatabase {
    /***********************************************************
     *  Attributes
     **********************************************************/
    private static final String DB_NAME = "forecast.db";
    /***********************************************************
    *  Singleton Pattern
    **********************************************************/
    private static volatile ForecastDatabase instance;
    public static synchronized ForecastDatabase getInstance() {
        if (instance == null) {
            instance = create(MyApplication.instance);
        }
        return instance;
    }
    private static ForecastDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                ForecastDatabase.class,
                DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }
    /***********************************************************
     *  Accessing DAO
     **********************************************************/
    public abstract WeatherDao getWeatherDao();
    public abstract MainDao getMainDao();
    public abstract WeatherForecastItemDao getWeatherForecastItemDao();
    public abstract WeatherDataDao getWeatherDataDao();
    public abstract CityDao getCityDao();
    public abstract SysDao getSysDao();
}
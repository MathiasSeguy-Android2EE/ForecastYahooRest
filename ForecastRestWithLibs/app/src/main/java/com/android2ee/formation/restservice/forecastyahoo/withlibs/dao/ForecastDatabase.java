package com.android2ee.formation.restservice.forecastyahoo.withlibs.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.dao.CityDao;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.dao.MainDao;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.dao.SysDao;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.dao.WeatherDao;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.dao.WeatherDataDao;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.dao.WeatherForecastItemDao;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.dao.WeatherOfThDayDAO;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.Main;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.calculated.WeatherOfTheDay;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.current.Sys;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.current.WeatherData;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.forecast.WeatherForecastItem;

@Database(entities = {Weather.class, Sys.class, WeatherForecastItem.class, City.class, Main.class, WeatherData.class, WeatherOfTheDay.class}, version = 2)
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
    public abstract WeatherOfThDayDAO getWeatherOfTheDayDao();
}
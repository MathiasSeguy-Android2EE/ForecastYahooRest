package com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.database;

import android.arch.persistence.room.TypeConverter;
import android.text.TextUtils;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Clouds;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Coord;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Rain;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Snow;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Wind;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 13/06/2018.
 */

public class WeatherConverter {
    /***********************************************************
     *  Clouds
     **********************************************************/
    @TypeConverter
    public static String cloudsToString(Clouds clouds) {
        return clouds == null ? null : Integer.toString(clouds.getAll());
    }

    @TypeConverter
    public static Clouds stringToClouds(String wind) {
        if (TextUtils.isEmpty(wind)) {
            return null;
        } else {
            String[] split = wind.split(":");
            return new Clouds(Integer.parseInt(split[0]));
        }
    }

    /***********************************************************
     *  Coord
     **********************************************************/
    @TypeConverter
    public static String coordToString(Coord coord) {
        return coord == null ? null : coord.getLat() + ":" + coord.getLon();
    }

    @TypeConverter
    public static Coord stringToCoord(String coord) {
        if (TextUtils.isEmpty(coord)) {
            return null;
        } else {
            String[] split = coord.split(":");
            return new Coord(Float.parseFloat(split[0]), Float.parseFloat(split[1]));
        }
    }
    /***********************************************************
     *  rain
     **********************************************************/
    @TypeConverter
    public static Float rainToFloat(Rain rain) {
        return rain == null ? null : rain.get3h();
    }

    @TypeConverter
    public static Rain floatToRain(Float rain) {
        if (rain!=null) {
            return new Rain(rain);
        }else{
            return null;
        }
    }
    /***********************************************************
     *  Snow
     **********************************************************/
    @TypeConverter
    public static Float snowToFloat(Snow snow) {
        return snow == null ? null : snow.get3h();
    }

    @TypeConverter
    public static Snow floatToSnow(Float snow) {
        if (snow!=null) {
            return new Snow(snow);
        }else{
            return null;
        }
    }

    /***********************************************************
     *  Wind
     **********************************************************/
    @TypeConverter
    public static String windToString(Wind wind) {
        return wind == null ? null : wind.getSpeed() + ":" + wind.getDeg();
    }

    @TypeConverter
    public static Wind stringToWind(String wind) {
        if (TextUtils.isEmpty(wind)) {
            return null;
        } else {
            String[] split = wind.split(":");
            return new Wind(Float.parseFloat(split[0]), Float.parseFloat(split[1]));
        }
    }

}
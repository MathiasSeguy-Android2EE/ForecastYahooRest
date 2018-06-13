package com.android2ee.formation.restservice.forecastyahoo.withlibs.dao;

import android.arch.persistence.room.TypeConverter;
import android.text.TextUtils;

public class WeatherConverter {
    @TypeConverter
    public static String coordToString(Coord coord) {
        return coord == null ? null : coord.getLatitude() + ":" + coord.getLongitude();
    }

    @TypeConverter
    public static Coord stringToCoord(String coord) {
        if (TextUtils.isEmpty(coord)) {
            return null;
        } else {
            String[] split = coord.split(":");
            return new Coord(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
        }
    }

    @TypeConverter
    public static String mainToString(Main main) {
        return main == null ? null : main.getTemp() + ":" + main.getPressure() + ":" + main.getHumidity() + ":" + main.getTempMin() + ":" + main.getTempMax();
    }

    @TypeConverter
    public static Main stringToMain(String main) {
        if (TextUtils.isEmpty(main)) {
            return null;
        } else {
            String[] split = main.split(":");
            return new Main(Double.parseDouble(split[0]), Double.parseDouble(split[1]), Integer.parseInt(split[2]), Double.parseDouble(split[3]), Double.parseDouble(split[4]));
        }
    }
}

package com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.current.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.Coord;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.Main;
import com.squareup.moshi.Json;

@Entity(tableName="city_current")
public class City {
    @Ignore
    private static final String TAG = "City";

    /***********************************************************
     *  Attributes
     **********************************************************/
    @PrimaryKey(autoGenerate = true)
    private long _id;

    @ColumnInfo(name="id")
    @Json(name="id")
    private long id;

    @ColumnInfo(name="name")
    private String name;

    @ColumnInfo(name="date")
    @Json(name="dt")
    private long date;

    @ColumnInfo(name="coord")
    @Json(name="coord")
    private Coord coord;

    @ColumnInfo(name="main")
    @Json(name="main")
    private Main main;
}

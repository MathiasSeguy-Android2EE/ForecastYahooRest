package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.forecast;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Relation;
import android.support.annotation.Nullable;

import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.Weather;
import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 28/06/2018.
 */
public class WeatherForecatsItemWithMainAndWeathers {
    @Embedded
    WeatherForecastItem forecastItem;
    @Relation(parentColumn = "_id",
            entityColumn = "weatherForecastItemId")
    List<Weather> weathers= new ArrayList();

    ///Flat the Main Object
    @ColumnInfo(name="_id_m")
    private long _id_m;
    @ColumnInfo(name="temp")
    private float temp;
    @ColumnInfo(name="pressure")
    private float pressure;
    @ColumnInfo(name="humidity")
    private int humidity;
    @Json(name = "temp_min")
    @ColumnInfo(name="temp_min")
    private float tempMin;
    @Json(name = "temp_max")
    @ColumnInfo(name="temp_max")
    private float tempMax;
    @ColumnInfo(name="sea_level")
    private float sea_level;
    @ColumnInfo(name="grnd_level")
    private float grnd_level;

    //IgnoredElement

    @ColumnInfo(name="main_temp._id")
    @Ignore
    private long _id2;

    @ColumnInfo(name="weatherDataId")
    @Nullable
    @Ignore
    private Long weatherDataId;
    @ColumnInfo(name="weatherForecastItemId")
    @Nullable
    @Ignore
    private Long weatherForecastItemId;
    /***********************************************************
     *  Constructor
     *  https://stackoverflow.com/questions/46956109/room-related-entities-usable-public-constructor
     **********************************************************/
    @Ignore
    public WeatherForecatsItemWithMainAndWeathers(WeatherForecastItem forecastItem, List<Weather> weathers) {
        this.forecastItem = forecastItem;
        this.weathers = weathers;
    }
    @Ignore
    public WeatherForecatsItemWithMainAndWeathers() {
    }
    public WeatherForecatsItemWithMainAndWeathers(WeatherForecastItem forecastItem) {
        this.forecastItem = forecastItem;
    }

    @Ignore
    public WeatherForecatsItemWithMainAndWeathers(List<Weather> weathers) {
        this.weathers = weathers;
    }

    public WeatherForecastItem getForecastItem() {
        return forecastItem;
    }

    public void setForecastItem(WeatherForecastItem forecastItem) {
        this.forecastItem = forecastItem;
    }

    public List<Weather> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<Weather> weathers) {
        this.weathers = weathers;
    }

    public long get_id_m() {
        return _id_m;
    }

    public void set_id_m(long _id_m) {
        this._id_m = _id_m;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public float getTempMin() {
        return tempMin;
    }

    public void setTempMin(float tempMin) {
        this.tempMin = tempMin;
    }

    public float getTempMax() {
        return tempMax;
    }

    public void setTempMax(float tempMax) {
        this.tempMax = tempMax;
    }

    public float getSea_level() {
        return sea_level;
    }

    public void setSea_level(float sea_level) {
        this.sea_level = sea_level;
    }

    public float getGrnd_level() {
        return grnd_level;
    }

    public void setGrnd_level(float grnd_level) {
        this.grnd_level = grnd_level;
    }
}

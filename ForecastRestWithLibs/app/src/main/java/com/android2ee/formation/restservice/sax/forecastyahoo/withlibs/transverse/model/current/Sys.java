
package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.current;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.squareup.moshi.Json;

/**
 * The system class having country, sunrise and sunset
 */
@Entity(tableName="sys_current",
        indices = {@Index(value = {"weatherDataId"},unique = false)},
        foreignKeys = @ForeignKey(entity = WeatherData.class,
                parentColumns = "_id",
                childColumns = "weatherDataId",
                onDelete = ForeignKey.CASCADE))
public class Sys{
    @PrimaryKey(autoGenerate = true)
    private long _id;
    @ColumnInfo(name="type")
    private int type;
    @Json(name = "cityId")
    @ColumnInfo(name="cityId")
    private long cityId;
    @ColumnInfo(name="message")
    private float message;
    @ColumnInfo(name="country")
    private String country;
    @ColumnInfo(name="sunrise")
    private int sunrise;
    @ColumnInfo(name="sunset")
    private int sunset;

    @ColumnInfo(name="weatherDataId")
    private Long weatherDataId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Sys() {
    }

    /**
     * 
     * @param message
     * @param cityId
     * @param sunset
     * @param sunrise
     * @param type
     * @param country
     */
    public Sys(int type, int cityId, float message, String country, int sunrise, int sunset) {
        this.type = type;
        this.cityId = cityId;
        this.message = message;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    /**
     * 
     * @return
     *     The type
     */
    public int getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The id
     */
    public long getCityId() {
        return cityId;
    }

    /**
     * 
     * @param cityId
     *     The id
     */
    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    /**
     * 
     * @return
     *     The message
     */
    public float getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(float message) {
        this.message = message;
    }

    /**
     * 
     * @return
     *     The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * 
     * @param country
     *     The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 
     * @return
     *     The sunrise
     */
    public int getSunrise() {
        return sunrise;
    }

    /**
     * 
     * @param sunrise
     *     The sunrise
     */
    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    /**
     * 
     * @return
     *     The sunset
     */
    public int getSunset() {
        return sunset;
    }

    /**
     * 
     * @param sunset
     *     The sunset
     */
    public void setSunset(int sunset) {
        this.sunset = sunset;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public Long getWeatherDataId() {
        return weatherDataId;
    }

    public void setWeatherDataId(Long weatherDataId) {
        this.weatherDataId = weatherDataId;
    }
}

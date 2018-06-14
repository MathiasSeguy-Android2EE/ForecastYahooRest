
package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Clouds;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Coord;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Main;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Rain;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Snow;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Wind;
import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName="weather_data_current",
        indices = {@Index(value = {"cityId"},unique = false)},
        foreignKeys = {
                @ForeignKey(entity = City.class,
                        parentColumns = "_id",
                        childColumns = "cityId")}
)
public class WeatherData{
    @PrimaryKey(autoGenerate = true)
    private long _id;
    @ColumnInfo(name="coord")
    private Coord coord;
    @ColumnInfo(name="base")
    private String base;
    @ColumnInfo(name="wind")
    private Wind wind;
    @ColumnInfo(name="clouds")
    private Clouds clouds;
    @ColumnInfo(name="rain")
    private Rain rain;
    @ColumnInfo(name="snow")
    private Snow snow;
    //When the weather object have been calculated (server side)
    @Json(name = "dt")
    @ColumnInfo(name="dt")
    private long timeStampUTC;
    @Json(name = "id")
    @ColumnInfo(name="cityId")
    private long city_Id;
    @ColumnInfo(name="name")
    private String name;
    @ColumnInfo(name="cod")
    private int cod;
    @Ignore
    private Sys sys;
    @Ignore
    private Main main;
    @Ignore
    private List<Weather> weather = new ArrayList<Weather>();


    /**
     * No args constructor for use in serialization
     * 
     */
    public WeatherData() {
    }

    /**
     * 
     * @param cityId
     * @param timeStampUTC
     * @param clouds
     * @param coord
     * @param wind
     * @param cod
     * @param sys
     * @param name
     * @param base
     * @param weather
     * @param rain
     * @param main
     */
    public WeatherData(Coord coord, List<Weather> weather, String base, Main main, Wind wind, Clouds clouds, Rain rain, int timeStampUTC, Sys sys, int cityId, String name, int cod) {
        this.coord = coord;
        this.weather = weather;
        this.base = base;
        this.main = main;
        this.wind = wind;
        this.clouds = clouds;
        this.rain = rain;
        this.timeStampUTC = timeStampUTC;
        this.sys = sys;
        this.city_Id = cityId;
        this.name = name;
        this.cod = cod;
    }

    /**
     * 
     * @return
     *     The coord
     */
    public Coord getCoord() {
        return coord;
    }

    /**
     * 
     * @param coord
     *     The coord
     */
    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    /**
     * 
     * @return
     *     The weather
     */
    public List<Weather> getWeather() {
        return weather;
    }

    /**
     * 
     * @param weather
     *     The weather
     */
    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    /**
     * 
     * @return
     *     The base
     */
    public String getBase() {
        return base;
    }

    /**
     * 
     * @param base
     *     The base
     */
    public void setBase(String base) {
        this.base = base;
    }

    /**
     * 
     * @return
     *     The main
     */
    public Main getMain() {
        return main;
    }

    /**
     * 
     * @param main
     *     The main
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     * 
     * @return
     *     The wind
     */
    public Wind getWind() {
        return wind;
    }

    /**
     * 
     * @param wind
     *     The wind
     */
    public void setWind(Wind wind) {
        this.wind = wind;
    }

    /**
     * 
     * @return
     *     The clouds
     */
    public Clouds getClouds() {
        return clouds;
    }

    /**
     * 
     * @param clouds
     *     The clouds
     */
    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    /**
     * 
     * @return
     *     The rain
     */
    public Rain getRain() {
        return rain;
    }

    /**
     * 
     * @param rain
     *     The rain
     */
    public void setRain(Rain rain) {
        this.rain = rain;
    }

    /**
     * 
     * @return
     *     The dt
     */
    public long getTimeStampUTC() {
        return timeStampUTC;
    }

    /**
     * 
     * @param timeStampUTC
     *     The dt
     */
    public void setTimeStampUTC(long timeStampUTC) {
        this.timeStampUTC = timeStampUTC;
    }

    /**
     * 
     * @return
     *     The sys
     */
    public Sys getSys() {
        return sys;
    }

    /**
     * 
     * @param sys
     *     The sys
     */
    public void setSys(Sys sys) {
        this.sys = sys;
    }

    /**
     * 
     * @return
     *     The id
     */
    public long getCityId() {
        return city_Id;
    }

    /**
     * 
     * @param cityId
     *     The id
     */
    public void setCityId(long cityId) {
        this.city_Id = cityId;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The cod
     */
    public int getCod() {
        return cod;
    }

    /**
     * 
     * @param cod
     *     The cod
     */
    public void setCod(int cod) {
        this.cod = cod;
    }


    public Snow getSnow() {
        return snow;
    }

    public void setSnow(Snow snow) {
        this.snow = snow;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getCity_Id() {
        return city_Id;
    }

    public void setCity_Id(long city_Id) {
        this.city_Id = city_Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeatherData)) return false;

        WeatherData that = (WeatherData) o;

        if (get_id() != that.get_id()) return false;
        if (getTimeStampUTC() != that.getTimeStampUTC()) return false;
        if (getCity_Id() != that.getCity_Id()) return false;
        if (getCod() != that.getCod()) return false;
        if (getCoord() != null ? !getCoord().equals(that.getCoord()) : that.getCoord() != null)
            return false;
        if (getBase() != null ? !getBase().equals(that.getBase()) : that.getBase() != null)
            return false;
        if (getWind() != null ? !getWind().equals(that.getWind()) : that.getWind() != null)
            return false;
        if (getClouds() != null ? !getClouds().equals(that.getClouds()) : that.getClouds() != null)
            return false;
        if (getRain() != null ? !getRain().equals(that.getRain()) : that.getRain() != null)
            return false;
        if (getSnow() != null ? !getSnow().equals(that.getSnow()) : that.getSnow() != null)
            return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null)
            return false;
        if (getSys() != null ? !getSys().equals(that.getSys()) : that.getSys() != null)
            return false;
        if (getMain() != null ? !getMain().equals(that.getMain()) : that.getMain() != null)
            return false;
        return getWeather() != null ? getWeather().equals(that.getWeather()) : that.getWeather() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (get_id() ^ (get_id() >>> 32));
        result = 31 * result + (getCoord() != null ? getCoord().hashCode() : 0);
        result = 31 * result + (getBase() != null ? getBase().hashCode() : 0);
        result = 31 * result + (getWind() != null ? getWind().hashCode() : 0);
        result = 31 * result + (getClouds() != null ? getClouds().hashCode() : 0);
        result = 31 * result + (getRain() != null ? getRain().hashCode() : 0);
        result = 31 * result + (getSnow() != null ? getSnow().hashCode() : 0);
        result = 31 * result + (int) (getTimeStampUTC() ^ (getTimeStampUTC() >>> 32));
        result = 31 * result + (int) (getCity_Id() ^ (getCity_Id() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + getCod();
        result = 31 * result + (getSys() != null ? getSys().hashCode() : 0);
        result = 31 * result + (getMain() != null ? getMain().hashCode() : 0);
        result = 31 * result + (getWeather() != null ? getWeather().hashCode() : 0);
        return result;
    }
}

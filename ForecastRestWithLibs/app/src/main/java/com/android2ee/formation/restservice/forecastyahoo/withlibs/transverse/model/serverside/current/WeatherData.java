
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
    @ColumnInfo(name="visibility")
    private int visibility;
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
    public WeatherData(Coord coord, List<Weather> weather, String base, Main main, Wind wind, Clouds clouds, Rain rain, int timeStampUTC, Sys sys, int cityId, String name, int cod, int visibility) {
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
        this.visibility = visibility;
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

    /**
     *
     * @return
     *     The visibility
     */
    public int getVisibility() {
        return visibility;
    }

    /**
     *
     * @param visibility
     *     The visibility
     */
    public void setVisibility(int visibility) {
        this.visibility = visibility;
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
        if (o == null || getClass() != o.getClass()) return false;

        WeatherData that = (WeatherData) o;

        if (_id != that._id) return false;
        if (timeStampUTC != that.timeStampUTC) return false;
        if (city_Id != that.city_Id) return false;
        if (cod != that.cod) return false;
        if (visibility != that.visibility) return false;
        if (coord != null ? !coord.equals(that.coord) : that.coord != null) return false;
        if (base != null ? !base.equals(that.base) : that.base != null) return false;
        if (wind != null ? !wind.equals(that.wind) : that.wind != null) return false;
        if (clouds != null ? !clouds.equals(that.clouds) : that.clouds != null) return false;
        if (rain != null ? !rain.equals(that.rain) : that.rain != null) return false;
        if (snow != null ? !snow.equals(that.snow) : that.snow != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (sys != null ? !sys.equals(that.sys) : that.sys != null) return false;
        if (main != null ? !main.equals(that.main) : that.main != null) return false;
        return weather != null ? weather.equals(that.weather) : that.weather == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (_id ^ (_id >>> 32));
        result = 31 * result + (coord != null ? coord.hashCode() : 0);
        result = 31 * result + (base != null ? base.hashCode() : 0);
        result = 31 * result + (wind != null ? wind.hashCode() : 0);
        result = 31 * result + (clouds != null ? clouds.hashCode() : 0);
        result = 31 * result + (rain != null ? rain.hashCode() : 0);
        result = 31 * result + (snow != null ? snow.hashCode() : 0);
        result = 31 * result + (int) (timeStampUTC ^ (timeStampUTC >>> 32));
        result = 31 * result + (int) (city_Id ^ (city_Id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + cod;
        result = 31 * result + visibility;
        result = 31 * result + (sys != null ? sys.hashCode() : 0);
        result = 31 * result + (main != null ? main.hashCode() : 0);
        result = 31 * result + (weather != null ? weather.hashCode() : 0);
        return result;
    }
}

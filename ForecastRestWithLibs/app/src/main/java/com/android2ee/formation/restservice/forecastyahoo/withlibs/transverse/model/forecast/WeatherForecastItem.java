
package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.forecast;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.Clouds;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.Main;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.Rain;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.Snow;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.Wind;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.DayHashCreator;
import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;

/**
 * The is the representation of the weather forecast (almost the same as WeatherData)
 */
@Entity(tableName="weather_forecast_item",
        indices = {@Index(value = {"cityId"},unique = false)},
        foreignKeys = {
                @ForeignKey(entity = com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.current.City.class,
                        parentColumns = "_id",
                        childColumns = "cityId",
                        onDelete = ForeignKey.CASCADE)}
)
public class WeatherForecastItem {
    @PrimaryKey(autoGenerate = true)
    private long _id;
    @ColumnInfo(name="dataTime")
    private long dt;
    @ColumnInfo(name="clouds")
    private Clouds clouds;
    @ColumnInfo(name="wind")
    private Wind wind;
    @ColumnInfo(name="rain")
    private Rain rain;
    @ColumnInfo(name="snow")
    private Snow snow;
    @ColumnInfo(name="cityId")
    private long city_Id;
    @Ignore
    private Sys sys;
    @Json(name = "dt_txt")
    @Ignore
    private String dtTxt;
    @ColumnInfo(name="day_hash")
    private int dayHash=-1;
    @Ignore
    private Main main;
    @Ignore
    private java.util.List<Weather> weather = new ArrayList<Weather>();


    /**
     * No args constructor for use in serialization
     * 
     */
    public WeatherForecastItem() {
    }

    /**
     *
     * @param clouds
     * @param dt
     * @param wind
     * @param sys
     * @param dtTxt
     * @param weather
     * @param main
     */
    public WeatherForecastItem(long dt, Main main, java.util.List<Weather> weather, Clouds clouds, Wind wind, Sys sys, String dtTxt) {
        this.dt = dt;
        this.main = main;
        this.weather = weather;
        this.clouds = clouds;
        this.wind = wind;
        this.sys = sys;
        this.dtTxt = dtTxt;
        setDayStamp();
    }
    public WeatherForecastItem(long dt, Main main, List<Weather> weather, Clouds clouds, Wind wind, Rain rain, Snow snow, Sys sys, String dtTxt) {
        this.dt = dt;
        this.main = main;
        this.weather = weather;
        this.clouds = clouds;
        this.wind = wind;
        this.rain = rain;
        this.snow = snow;
        this.sys = sys;
        this.dtTxt = dtTxt;
        setDayStamp();
    }
    public void setDayStamp(){
        dayHash= DayHashCreator.getTempKeyFromDay(dt);
    }
    public void setDtAndSyncDayHash(long dt){
        this.dt = dt;
        setDayStamp();
    }

    /**
     * 
     * @return
     *     The dt
     */
    public long getDt() {
        return dt;
    }

    /**
     * 
     * @param dt
     *     The dt
     */
    public void setDt(long dt) {
        this.dt = dt;

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


    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Snow getSnow() {
        return snow;
    }

    public void setSnow(Snow snow) {
        this.snow = snow;
    }

    /**
     * 
     * @return
     *     The weather
     */
    public java.util.List<Weather> getWeather() {
        return weather;
    }

    /**
     * 
     * @param weather
     *     The weather
     */
    public void setWeather(java.util.List<Weather> weather) {
        this.weather = weather;
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
     *     The dtTxt
     */
    public String getDtTxt() {
        return dtTxt;
    }

    public int getDayHash() {
        if(dayHash==-1){
            setDayStamp();
        }
        return dayHash;
    }

    public void setDayHash(int dayHash) {
        this.dayHash = dayHash;
    }

    /**
     * 
     * @param dtTxt
     *     The dt_txt
     */
    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
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
    public String toString() {
        final StringBuffer sb = new StringBuffer("List{");
        sb.append("clouds=").append(clouds);
        sb.append(", dt=").append(dt);
        sb.append(", main=").append(main);
        sb.append(", weather=").append(weather);
        sb.append(", wind=").append(wind);
        sb.append(", sys=").append(sys);
        sb.append(", dtTxt='").append(dtTxt).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeatherForecastItem)) return false;

        WeatherForecastItem that = (WeatherForecastItem) o;

        if (get_id() != that.get_id()) return false;
        if (getDt() != that.getDt()) return false;
        if (getCity_Id() != that.getCity_Id()) return false;
        if (getClouds() != null ? !getClouds().equals(that.getClouds()) : that.getClouds() != null)
            return false;
        if (getWind() != null ? !getWind().equals(that.getWind()) : that.getWind() != null)
            return false;
        if (getRain() != null ? !getRain().equals(that.getRain()) : that.getRain() != null)
            return false;
        if (getSnow() != null ? !getSnow().equals(that.getSnow()) : that.getSnow() != null)
            return false;
        if (getSys() != null ? !getSys().equals(that.getSys()) : that.getSys() != null)
            return false;
        if (getDtTxt() != null ? !getDtTxt().equals(that.getDtTxt()) : that.getDtTxt() != null)
            return false;
        if (getMain() != null ? !getMain().equals(that.getMain()) : that.getMain() != null)
            return false;
        return getWeather() != null ? getWeather().equals(that.getWeather()) : that.getWeather() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (get_id() ^ (get_id() >>> 32));
        result = 31 * result + (int) (getDt() ^ (getDt() >>> 32));
        result = 31 * result + (getClouds() != null ? getClouds().hashCode() : 0);
        result = 31 * result + (getWind() != null ? getWind().hashCode() : 0);
        result = 31 * result + (getRain() != null ? getRain().hashCode() : 0);
        result = 31 * result + (getSnow() != null ? getSnow().hashCode() : 0);
        result = 31 * result + (int) (getCity_Id() ^ (getCity_Id() >>> 32));
        result = 31 * result + (getSys() != null ? getSys().hashCode() : 0);
        result = 31 * result + (getDtTxt() != null ? getDtTxt().hashCode() : 0);
        result = 31 * result + (getMain() != null ? getMain().hashCode() : 0);
        result = 31 * result + (getWeather() != null ? getWeather().hashCode() : 0);
        return result;
    }
}

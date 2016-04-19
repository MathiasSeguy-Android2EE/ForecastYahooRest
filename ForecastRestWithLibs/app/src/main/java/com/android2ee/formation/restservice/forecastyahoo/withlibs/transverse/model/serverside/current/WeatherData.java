
package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current;

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
public class WeatherData{
    private Coord coord;
    private List<Weather> weather = new ArrayList<Weather>();
    private String base;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private Rain rain;
    private Snow snow;

    //When the weather object have been calculated (server side)
    @Json(name = "dt")
    private int timeStampUTC;
    private Sys sys;
    @Json(name = "id")
    private int cityId;
    private String name;
    private int cod;


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
        this.cityId = cityId;
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
    public int getTimeStampUTC() {
        return timeStampUTC;
    }

    /**
     * 
     * @param timeStampUTC
     *     The dt
     */
    public void setTimeStampUTC(int timeStampUTC) {
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
    public int getCityId() {
        return cityId;
    }

    /**
     * 
     * @param cityId
     *     The id
     */
    public void setCityId(int cityId) {
        this.cityId = cityId;
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
}

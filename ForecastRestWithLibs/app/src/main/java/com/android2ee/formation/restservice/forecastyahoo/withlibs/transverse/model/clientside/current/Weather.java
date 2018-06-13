
package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current;

import android.util.Log;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Clouds;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Coordinates;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Ephemeris;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Rain;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Snow;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.WeatherDetails;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Wind;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.weathermetadata.WeatherMetaData_Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.WeatherData;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Is the WeatherData class on server side
 */
public class Weather extends SugarRecord {
    private static final String TAG = "Weather";
    private Coordinates coordinates;
    @Ignore
    private List<WeatherMetaData_Weather> weatherMetaData = null;
    /**Because of SugarOrm bad management of one to many relationship*/

    /**
     *
     * @return
     *     The weather
     */
    public List<WeatherMetaData_Weather> getWeatherMetaData() {
        if(weatherMetaData==null){
            //load them
            weatherMetaData= WeatherMetaData_Weather.find(WeatherMetaData_Weather.class, "WEATHER = ?", Long.toString(getId()));
        }
        return weatherMetaData;
    }

    /**
     *
     * @param weatherMetaData
     *     The weather
     */
    public void setWeatherMetaData(List<WeatherMetaData_Weather> weatherMetaData) {
        this.weatherMetaData = weatherMetaData;
    }

    private String base;
    private WeatherDetails weatherDetails;
    private Wind wind;
    private Clouds clouds;
    private Rain rain;
    private Snow snow;
    private Ephemeris ephemeris;
    private int cityId;
    /**
     * City name
     */
    private String name;
    private int cod;
    //When the weather object have been calculated (server side)
    private int timeStampUTC;
    @Ignore
    private transient DateTime timeStamp=null;
    public DateTime getTimeStamp(){
        if(timeStamp==null){
            timeStamp=new DateTime(timeStampUTC*1000L);
        }
        Log.e(TAG, "getTimeStamp() returned: " + timeStamp+" and  "+timeStampUTC);
        return timeStamp;
    }



    /**
     * No args constructor for use in serialization
     *
     */
    public Weather(WeatherData weatherServerSide) {
        if(weatherServerSide!=null) {
            coordinates = new Coordinates(weatherServerSide.getCoord());
            if (weatherServerSide.getWeather() != null) {
                ArrayList<WeatherMetaData_Weather> weatherMetaDataTemp = new ArrayList<WeatherMetaData_Weather>(weatherServerSide.getWeather().size());
                for (com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Weather w : weatherServerSide.getWeather()) {
                    weatherMetaDataTemp.add(new WeatherMetaData_Weather(w));
                }
                setWeatherMetaData(weatherMetaDataTemp);
            }
            base = weatherServerSide.getBase();
            weatherDetails = new WeatherDetails(weatherServerSide.getMain());
            wind = new Wind(weatherServerSide.getWind());
            clouds = new Clouds(weatherServerSide.getClouds());
            rain = new Rain(weatherServerSide.getRain());
            snow = new Snow(weatherServerSide.getSnow());
            timeStampUTC=weatherServerSide.getTimeStampUTC();
            ephemeris=new Ephemeris(weatherServerSide.getSys());
            cityId=weatherServerSide.getCityId();
            name=weatherServerSide.getName();
            cod=weatherServerSide.getCod();
        }
    }
    /**
     * No args constructor for use in serialization
     *
     */
    public Weather() {
    }

    /**
     *  @param coordinates
     * @param weatherMetaData
     * @param base
     * @param weatherDetails
     * @param wind
     * @param clouds
     * @param rain
     * @param timeStampUTC
     * @param ephemeris
     * @param cityId
     * @param name
     * @param cod
     */
    public Weather(Coordinates coordinates, List<WeatherMetaData_Weather> weatherMetaData, String base, WeatherDetails weatherDetails, Wind wind, Clouds clouds, Rain rain, int timeStampUTC, Ephemeris ephemeris, int cityId, String name, int cod) {
        this.coordinates = coordinates;
        this.weatherMetaData = weatherMetaData;
        this.base = base;
        this.weatherDetails = weatherDetails;
        this.wind = wind;
        this.clouds = clouds;
        this.rain = rain;
        this.timeStampUTC = timeStampUTC;
        this.ephemeris = ephemeris;
        this.cityId = cityId;
        this.name = name;
        this.cod = cod;
    }

    /**
     *
     * @return
     *     The coord
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     *
     * @param coordinates
     *     The coord
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
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
    public WeatherDetails getWeatherDetails() {
        return weatherDetails;
    }

    /**
     *
     * @param weatherDetails
     *     The main
     */
    public void setWeatherDetails(WeatherDetails weatherDetails) {
        this.weatherDetails = weatherDetails;
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
    public Ephemeris getEphemeris() {
        return ephemeris;
    }

    /**
     * 
     * @param ephemeris
     *     The sys
     */
    public void setEphemeris(Ephemeris ephemeris) {
        this.ephemeris = ephemeris;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Weather{");
        sb.append("base='").append(base).append('\'');
        sb.append(", coordinates=").append(coordinates);
//        sb.append(", weatherMetaData=").append(getWeatherMetaData());
        sb.append(", weatherDetails=").append(weatherDetails);
        sb.append(", wind=").append(wind);
        sb.append(", clouds=").append(clouds);
        sb.append(", rain=").append(rain);
        sb.append(", snow=").append(snow);
        sb.append(", timeStampUTC=").append(timeStampUTC);
        sb.append(", timeStamp=").append(timeStamp);
        sb.append(", ephemeris=").append(ephemeris);
        sb.append(", cityId=").append(cityId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", cod=").append(cod);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Weather weather = (Weather) o;

        if (getCityId() != weather.getCityId()) return false;
        if (getCod() != weather.getCod()) return false;
        if (getTimeStampUTC() != weather.getTimeStampUTC()) return false;
        if (getCoordinates() != null ? !getCoordinates().equals(weather.getCoordinates()) : weather.getCoordinates() != null)
            return false;
        if (getWeatherMetaData() != null ? !getWeatherMetaData().equals(weather.getWeatherMetaData()) : weather.getWeatherMetaData() != null)
            return false;
        if (getBase() != null ? !getBase().equals(weather.getBase()) : weather.getBase() != null)
            return false;
        if (getWeatherDetails() != null ? !getWeatherDetails().equals(weather.getWeatherDetails()) : weather.getWeatherDetails() != null)
            return false;
        if (getWind() != null ? !getWind().equals(weather.getWind()) : weather.getWind() != null)
            return false;
        if (getClouds() != null ? !getClouds().equals(weather.getClouds()) : weather.getClouds() != null)
            return false;
        if (getRain() != null ? !getRain().equals(weather.getRain()) : weather.getRain() != null)
            return false;
        if (getSnow() != null ? !getSnow().equals(weather.getSnow()) : weather.getSnow() != null)
            return false;
        if (getEphemeris() != null ? !getEphemeris().equals(weather.getEphemeris()) : weather.getEphemeris() != null)
            return false;
        if (getName() != null ? !getName().equals(weather.getName()) : weather.getName() != null)
            return false;
        return !(getTimeStamp() != null ? !getTimeStamp().equals(weather.getTimeStamp()) : weather.getTimeStamp() != null);
    }

    @Override
    public int hashCode() {
        int result = getCoordinates() != null ? getCoordinates().hashCode() : 0;
        result = 31 * result + (getWeatherMetaData() != null ? getWeatherMetaData().hashCode() : 0);
        result = 31 * result + (getBase() != null ? getBase().hashCode() : 0);
        result = 31 * result + (getWeatherDetails() != null ? getWeatherDetails().hashCode() : 0);
        result = 31 * result + (getWind() != null ? getWind().hashCode() : 0);
        result = 31 * result + (getClouds() != null ? getClouds().hashCode() : 0);
        result = 31 * result + (getRain() != null ? getRain().hashCode() : 0);
        result = 31 * result + (getSnow() != null ? getSnow().hashCode() : 0);
        result = 31 * result + (getEphemeris() != null ? getEphemeris().hashCode() : 0);
        result = 31 * result + getCityId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + getCod();
        result = 31 * result + getTimeStampUTC();
        result = 31 * result + (getTimeStamp() != null ? getTimeStamp().hashCode() : 0);
        return result;
    }
}

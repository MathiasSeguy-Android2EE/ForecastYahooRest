
package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.newest;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Clouds;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Ephemeris;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Rain;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Snow;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Wind;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.weathermetadata.WeatherMetaData;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.WeatherData;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast.WeatherForecastItem;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Is the WeatherData class on server side
 */
@Deprecated
public class WeatherDay extends SugarRecord {
    private static final String TAG = "WeatherDay";
    /**
     * To know if it's a forecast or the current weather
     */
    boolean forecastCase;
    /**
     * should know what is it
     */
    private String base;
    /**
     * average pressure
     */
    private float pressure;
    /**
     * average humidity
     */
    private int humidity;
    /**
     * Minimal temperature
     */
    private float tempMin;
    /**
     * maximal temperature
     */
    private float tempMax;
    /**
     * Average wind
     */
    private Wind wind;
    /**
     * Average cloud covering
     */
    private Clouds clouds;
    /**
     * Global rain of the day
     */
    private Rain rain;
    /**
     * Global snow of the day
     */
    private Snow snow;
    /**
     * Ephemeris
     */
    private Ephemeris ephemeris;
    /**
     * Id of the associated city
     */
    private int cityId;
    /**
     * Should know what it is
     */
    private int cod;
    /**
     * Problem using that because it's not set server side
     * should find a way
     * //TODO
     */
    WeatherMetaData weatherMetaData;
    /**
     * The list of the weather each 3h
     */
    ArrayList<Weather3H> weather3Hs;
    //When the weather object have been calculated (server side)
    private int timeStampUTC;
    @Ignore
    private transient DateTime timeStamp=null;
    public DateTime getTimeStamp(){
        if(timeStamp==null){
            timeStamp=new DateTime(timeStampUTC*1000L);
        }
        MyLog.e(TAG, "getTimeStamp() returned: " + timeStamp+" and  "+timeStampUTC);
        return timeStamp;
    }
      /**
     * use for the current weather
     *
     */
    public WeatherDay(WeatherData weatherServerSide) {
        if(weatherServerSide!=null) {
            base = weatherServerSide.getBase();
            pressure=weatherServerSide.getMain().getPressure();
            humidity=weatherServerSide.getMain().getHumidity();
            tempMin=weatherServerSide.getMain().getTempMin();
            tempMax=weatherServerSide.getMain().getTempMax();
            wind = new Wind(weatherServerSide.getWind());
            clouds = new Clouds(weatherServerSide.getClouds());
            rain = new Rain(weatherServerSide.getRain());
            snow = new Snow(weatherServerSide.getSnow());
            timeStampUTC= (int) weatherServerSide.getTimeStampUTC();
            ephemeris=new Ephemeris(weatherServerSide.getSys());
            cityId= (int) weatherServerSide.getCityId();
//            weatherMetaData=new WeatherMetaData(weatherServerSide.getMain().
        }
    }


    /**
     * Use the forecast weather
     */
    public WeatherDay(com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast.Forecast forecast){

        if(forecast!=null){
            cityId=forecast.getCity().getId();
            forecastCase=true;
            weather3Hs=new ArrayList<>(forecast.getWeatherForecastItem().size());
            for(WeatherForecastItem wList:forecast.getWeatherForecastItem()){
                weather3Hs.add(new Weather3H(wList));
            }
        }else{
            weather3Hs=new ArrayList<>();
        }
    }
    /**
     * No args constructor for use in serialization
     *
     */
    public WeatherDay() {
    }



    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public Ephemeris getEphemeris() {
        return ephemeris;
    }

    public void setEphemeris(Ephemeris ephemeris) {
        this.ephemeris = ephemeris;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
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

    public static String getTAG() {
        return TAG;
    }

       public float getTempMax() {
        return tempMax;
    }

    public void setTempMax(float tempMax) {
        this.tempMax = tempMax;
    }

    public float getTempMin() {
        return tempMin;
    }

    public void setTempMin(float tempMin) {
        this.tempMin = tempMin;
    }

    public void setTimeStamp(DateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getTimeStampUTC() {
        return timeStampUTC;
    }

    public void setTimeStampUTC(int timeStampUTC) {
        this.timeStampUTC = timeStampUTC;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Weather{");
        sb.append("base='").append(base).append('\'');
        sb.append(", pressure=").append(pressure);
        sb.append(", humidity=").append(humidity);
        sb.append(", tempMin=").append(tempMin);
        sb.append(", tempMax=").append(tempMax);
        sb.append(", wind=").append(wind);
        sb.append(", clouds=").append(clouds);
        sb.append(", rain=").append(rain);
        sb.append(", snow=").append(snow);
        sb.append(", ephemeris=").append(ephemeris);
        sb.append(", cityId=").append(cityId);
        sb.append(", cod=").append(cod);
        sb.append(", timeStampUTC=").append(timeStampUTC);
        sb.append(", timeStamp=").append(timeStamp);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeatherDay weather = (WeatherDay) o;

        if (Float.compare(weather.getPressure(), getPressure()) != 0) return false;
        if (getHumidity() != weather.getHumidity()) return false;
        if (Float.compare(weather.getTempMin(), getTempMin()) != 0) return false;
        if (Float.compare(weather.getTempMax(), getTempMax()) != 0) return false;
        if (getCityId() != weather.getCityId()) return false;
        if (getCod() != weather.getCod()) return false;
        if (getTimeStampUTC() != weather.getTimeStampUTC()) return false;
        if (getBase() != null ? !getBase().equals(weather.getBase()) : weather.getBase() != null)
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
        return getTimeStamp() != null ? getTimeStamp().equals(weather.getTimeStamp()) : weather.getTimeStamp() == null;

    }

    @Override
    public int hashCode() {
        int result = (getBase() != null ? getBase().hashCode() : 0);
        result = 31 * result + (getPressure() != +0.0f ? Float.floatToIntBits(getPressure()) : 0);
        result = 31 * result + getHumidity();
        result = 31 * result + (getTempMin() != +0.0f ? Float.floatToIntBits(getTempMin()) : 0);
        result = 31 * result + (getTempMax() != +0.0f ? Float.floatToIntBits(getTempMax()) : 0);
        result = 31 * result + (getWind() != null ? getWind().hashCode() : 0);
        result = 31 * result + (getClouds() != null ? getClouds().hashCode() : 0);
        result = 31 * result + (getRain() != null ? getRain().hashCode() : 0);
        result = 31 * result + (getSnow() != null ? getSnow().hashCode() : 0);
        result = 31 * result + (getEphemeris() != null ? getEphemeris().hashCode() : 0);
        result = 31 * result + getCityId();
        result = 31 * result + getCod();
        result = 31 * result + getTimeStampUTC();
        result = 31 * result + (getTimeStamp() != null ? getTimeStamp().hashCode() : 0);
        return result;
    }
}

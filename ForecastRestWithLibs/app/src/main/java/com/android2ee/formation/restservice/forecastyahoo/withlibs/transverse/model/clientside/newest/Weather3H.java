/**
 * <ul>
 * <li>WeatherForecastDay</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.newest</li>
 * <li>02/08/2016</li>
 * <p/>
 * <li>======================================================</li>
 * <p/>
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 * <p/>
 * /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br>
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 * Belongs to <strong>Mathias Seguy</strong></br>
 * ***************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * <p/>
 * *****************************************************************************************************************</br>
 * Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 * Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br>
 * Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */

package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.newest;

import android.util.Log;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Clouds;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Rain;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Snow;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Wind;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.forecast.WeatherForecast;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.weathermetadata.WeatherMetaData;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast.WeatherForecastItem;
import com.orm.dsl.Ignore;

import org.joda.time.DateTime;

/**
 * Created by Mathias Seguy - Android2EE on 02/08/2016.
 */
@Deprecated
public class Weather3H {
    private static final String TAG = "Weather3H";
    /**
     * To know if it represents a Forecast or not
     */
    boolean forecastCase;
    /**
     * average pressure
     */
    private float pressure;
    /**
     * average humidity
     */
    private int humidity;
    /**
     * Current temperature
     */
    private float temp;
    /**
     * Minimal temperature
     */
    private float tempMin;
    /**
     * maximal temperature
     */
    private float tempMax;
    /**
     *  wind
     */
    private Wind wind;
    /**
     *  cloud covering
     */
    private Clouds clouds;
    /**
     *  rain of the period
     */
    private Rain rain;
    /**
     *  snow of the period
     */
    private Snow snow;
    /**
     * Id of the associated city
     */
    private int cityId;
    /**
     * Weather condition id
     */

    private int id;
    /**
     * weather.main Group of weather parameters (Rain, Snow, Extreme etc.)
     * */
    private String main;
    /**
     * Weather condition within the group
     */
    private String description;

    /**
     *Weather icon id
     */
    private String icon;
    /**
     * Should know what it is
     */
    private int cod;
   public Weather3H(WeatherForecast weatherForecast)
        {
        if(weatherForecast!=null) {
          clouds= weatherForecast.getClouds();
            humidity=weatherForecast.getWeatherDetails().getHumidity();
            pressure=weatherForecast.getWeatherDetails().getPressure();
            temp=weatherForecast.getWeatherDetails().getTemp();
            tempMax=weatherForecast.getWeatherDetails().getTempMax();
            tempMin=weatherForecast.getWeatherDetails().getTempMin();
            wind=weatherForecast.getWind();
        }
    }

    public Weather3H(WeatherForecastItem weatherForecastItem)
    {
        forecastCase=true;
        if(weatherForecastItem !=null) {
            clouds= new Clouds(weatherForecastItem.getClouds());
            humidity= weatherForecastItem.getMain().getHumidity();
            pressure= weatherForecastItem.getMain().getPressure();
            temp= weatherForecastItem.getMain().getTemp();
            tempMax= weatherForecastItem.getMain().getTempMax();
            tempMin= weatherForecastItem.getMain().getTempMin();
            wind=new Wind(weatherForecastItem.getWind());
            //TODO
            //rain and snow ??
           //code
        }
    }
    /**
     * Problem using that because it's not set server side
     * should find a way
     * //TODO
     */
    WeatherMetaData weatherMetaData;
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

    public boolean isForecastCase() {
        return forecastCase;
    }

    public void setForecastCase(boolean forecastCase) {
        this.forecastCase = forecastCase;
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

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
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

    public WeatherMetaData getWeatherMetaData() {
        return weatherMetaData;
    }

    public void setWeatherMetaData(WeatherMetaData weatherMetaData) {
        this.weatherMetaData = weatherMetaData;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Weather3H weather3H = (Weather3H) o;

        if (isForecastCase() != weather3H.isForecastCase()) return false;
        if (Float.compare(weather3H.getPressure(), getPressure()) != 0) return false;
        if (getHumidity() != weather3H.getHumidity()) return false;
        if (Float.compare(weather3H.getTemp(), getTemp()) != 0) return false;
        if (Float.compare(weather3H.getTempMin(), getTempMin()) != 0) return false;
        if (Float.compare(weather3H.getTempMax(), getTempMax()) != 0) return false;
        if (getCityId() != weather3H.getCityId()) return false;
        if (getCod() != weather3H.getCod()) return false;
        if (getTimeStampUTC() != weather3H.getTimeStampUTC()) return false;
        if (getWind() != null ? !getWind().equals(weather3H.getWind()) : weather3H.getWind() != null)
            return false;
        if (getClouds() != null ? !getClouds().equals(weather3H.getClouds()) : weather3H.getClouds() != null)
            return false;
        if (getRain() != null ? !getRain().equals(weather3H.getRain()) : weather3H.getRain() != null)
            return false;
        if (getSnow() != null ? !getSnow().equals(weather3H.getSnow()) : weather3H.getSnow() != null)
            return false;
        if (getWeatherMetaData() != null ? !getWeatherMetaData().equals(weather3H.getWeatherMetaData()) : weather3H.getWeatherMetaData() != null)
            return false;
        return getTimeStamp() != null ? getTimeStamp().equals(weather3H.getTimeStamp()) : weather3H.getTimeStamp() == null;

    }

    @Override
    public int hashCode() {
        int result = (isForecastCase() ? 1 : 0);
        result = 31 * result + (getPressure() != +0.0f ? Float.floatToIntBits(getPressure()) : 0);
        result = 31 * result + getHumidity();
        result = 31 * result + (getTemp() != +0.0f ? Float.floatToIntBits(getTemp()) : 0);
        result = 31 * result + (getTempMin() != +0.0f ? Float.floatToIntBits(getTempMin()) : 0);
        result = 31 * result + (getTempMax() != +0.0f ? Float.floatToIntBits(getTempMax()) : 0);
        result = 31 * result + (getWind() != null ? getWind().hashCode() : 0);
        result = 31 * result + (getClouds() != null ? getClouds().hashCode() : 0);
        result = 31 * result + (getRain() != null ? getRain().hashCode() : 0);
        result = 31 * result + (getSnow() != null ? getSnow().hashCode() : 0);
        result = 31 * result + getCityId();
        result = 31 * result + getCod();
        result = 31 * result + (getWeatherMetaData() != null ? getWeatherMetaData().hashCode() : 0);
        result = 31 * result + getTimeStampUTC();
        result = 31 * result + (getTimeStamp() != null ? getTimeStamp().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Weather3H{");
        sb.append("cityId=").append(cityId);
        sb.append(", forecast=").append(forecastCase);
        sb.append(", pressure=").append(pressure);
        sb.append(", humidity=").append(humidity);
        sb.append(", temp=").append(temp);
        sb.append(", tempMin=").append(tempMin);
        sb.append(", tempMax=").append(tempMax);
        sb.append(", wind=").append(wind);
        sb.append(", clouds=").append(clouds);
        sb.append(", rain=").append(rain);
        sb.append(", snow=").append(snow);
        sb.append(", cod=").append(cod);
        sb.append(", weatherMetaData=").append(weatherMetaData);
        sb.append(", timeStampUTC=").append(timeStampUTC);
        sb.append(", timeStamp=").append(timeStamp);
        sb.append('}');
        return sb.toString();

    }
}

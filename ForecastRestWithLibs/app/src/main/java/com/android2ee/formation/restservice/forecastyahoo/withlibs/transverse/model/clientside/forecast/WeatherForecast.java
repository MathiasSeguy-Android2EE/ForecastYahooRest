/**
 * <ul>
 * <li>WeatherForecast</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.forecast</li>
 * <li>05/03/2016</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.forecast;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Clouds;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.WeatherDetails;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Wind;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.weathermetadata.WeatherMetaData_WeatherForecast;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast.List;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.ArrayList;

/**
 * Created by Mathias Seguy - Android2EE on 05/03/2016.
 */
public class WeatherForecast  extends SugarRecord {

    private int dateTimeStampUtc;
    private WeatherDetails weatherDetails;
    private Clouds clouds;
    private Wind wind;
    private String pod;
    private String dateTimeUtc;
    /**Because of SugarOrm bad management of one to many relationship*/
    public CityForecast cityForecast;
    @Ignore
    private java.util.List<WeatherMetaData_WeatherForecast> weathers = null;
    public java.util.List<WeatherMetaData_WeatherForecast> getWeathers() {
        if(weathers==null){
            //load them
            weathers= WeatherMetaData_WeatherForecast.find(WeatherMetaData_WeatherForecast.class, "WEATHER_FORECAST = ?", Long.toString(getId()));
        }
        return weathers;
    }
    public void setWeathers(java.util.List<WeatherMetaData_WeatherForecast> weathers) {
        for(WeatherMetaData_WeatherForecast weather:weathers){
            WeatherMetaData_WeatherForecast.save(weather);
        }
        this.weathers = weathers;
    }

    public WeatherForecast() {
    }

    public WeatherForecast(List worecastForecast){
        dateTimeStampUtc=worecastForecast.getDt();
        weatherDetails=new WeatherDetails(worecastForecast.getMain());
        //SugarOrm trick to save the weathers
        if(worecastForecast.getWeather()!=null) {
            ArrayList weathersTemp = new ArrayList<>(worecastForecast.getWeather().size());
            for (Weather w : worecastForecast.getWeather()) {
                weathersTemp.add(new WeatherMetaData_WeatherForecast(w));
            }
            setWeathers(weathersTemp);
        }
        clouds=new Clouds(worecastForecast.getClouds());
        wind=new Wind(worecastForecast.getWind());
        pod=worecastForecast.getSys().getPod();
        dateTimeUtc=worecastForecast.getDtTxt();
    }



    public CityForecast getCityForecast() {
        return cityForecast;
    }
    public void setCityForecast(CityForecast cityForecast) {
        this.cityForecast = cityForecast;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public int getDateTimeStampUtc() {
        return dateTimeStampUtc;
    }

    public void setDateTimeStampUtc(int dateTimeStampUtc) {
        this.dateTimeStampUtc = dateTimeStampUtc;
    }

    public String getDateTimeUtc() {
        return dateTimeUtc;
    }

    public void setDateTimeUtc(String dateTimeUtc) {
        this.dateTimeUtc = dateTimeUtc;
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public WeatherDetails getWeatherDetails() {
        return weatherDetails;
    }

    public void setWeatherDetails(WeatherDetails weatherDetails) {
        this.weatherDetails = weatherDetails;
    }



    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WeatherForecast{");
        sb.append("clouds=").append(clouds);
        sb.append(", dateTimeStampUtc=").append(dateTimeStampUtc);
        sb.append(", weatherDetails=").append(weatherDetails);
//        sb.append(", weathers=").append(getWeathers());
        sb.append(", wind=").append(wind);
        sb.append(", pod='").append(pod).append('\'');
        sb.append(", dateTimeUtc='").append(dateTimeUtc).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeatherForecast that = (WeatherForecast) o;

        if (getDateTimeStampUtc() != that.getDateTimeStampUtc()) return false;
        if (getWeatherDetails() != null ? !getWeatherDetails().equals(that.getWeatherDetails()) : that.getWeatherDetails() != null)
            return false;
        if (getClouds() != null ? !getClouds().equals(that.getClouds()) : that.getClouds() != null)
            return false;
        if (getWind() != null ? !getWind().equals(that.getWind()) : that.getWind() != null)
            return false;
        if (getPod() != null ? !getPod().equals(that.getPod()) : that.getPod() != null)
            return false;
        if (getDateTimeUtc() != null ? !getDateTimeUtc().equals(that.getDateTimeUtc()) : that.getDateTimeUtc() != null)
            return false;

        return !(getWeathers() != null ? !getWeathers().equals(that.getWeathers()) : that.getWeathers() != null);

    }

    @Override
    public int hashCode() {
        int result = getDateTimeStampUtc();
        result = 31 * result + (getWeatherDetails() != null ? getWeatherDetails().hashCode() : 0);
        result = 31 * result + (getClouds() != null ? getClouds().hashCode() : 0);
        result = 31 * result + (getWind() != null ? getWind().hashCode() : 0);
        result = 31 * result + (getPod() != null ? getPod().hashCode() : 0);
        result = 31 * result + (getDateTimeUtc() != null ? getDateTimeUtc().hashCode() : 0);
        result = 31 * result + (getCityForecast() != null ? getCityForecast().hashCode() : 0);
        result = 31 * result + (getWeathers() != null ? getWeathers().hashCode() : 0);
        return result;
    }
}

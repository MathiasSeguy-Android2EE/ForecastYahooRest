/**
 * <ul>
 * <li>CityForecast</li>
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

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Coordinates;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast.WeatherList;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mathias Seguy - Android2EE on 05/03/2016.
 */
public class CityForecast extends SugarRecord {
    private int cityId;
    private String name;
    private Coordinates coordinates;
    private String country;
    private String cod;
    private float message;
    /**Because of SugarOrm bad management of one to many relationship*/
    @Ignore
    private List<WeatherForecast> weatherForecasts = null;
    public List<WeatherForecast> getWeatherForecasts() {
        if(weatherForecasts==null){
            //load them
            weatherForecasts=WeatherForecast.find(WeatherForecast.class, "CITY_FORECAST = ?", Long.toString(getId()));
        }
        return weatherForecasts;
    }

    public void setWeatherForecasts(ArrayList<WeatherForecast> weatherForecasts) {
        this.weatherForecasts = weatherForecasts;
    }

    public CityForecast() {
    }

    public CityForecast(com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast.Forecast forecast) {
        if(forecast!=null) {
            cityId = forecast.getCity().getId();
            name = forecast.getCity().getName();
            coordinates = new Coordinates(forecast.getCity().getCoord());
            country=forecast.getCity().getCountry();
            cod=forecast.getCod();
            message=forecast.getMessage();
            if(forecast.getWeatherList()!=null) {
                ArrayList<WeatherForecast> weatherForecastsTemp = new ArrayList<WeatherForecast>(forecast.getWeatherList().size());
                for (WeatherList elem : forecast.getWeatherList()) {
                    weatherForecastsTemp.add(new WeatherForecast(elem));
                }
                setWeatherForecasts(weatherForecastsTemp);
            }
        }
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public float getMessage() {
        return message;
    }

    public void setMessage(float message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CityForecast{");
        sb.append("cityId=").append(cityId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", coordinates=").append(coordinates);
        sb.append(", country='").append(country).append('\'');
        sb.append(", cod='").append(cod).append('\'');
        sb.append(", message=").append(message);
//        sb.append(", weatherForecasts=").append(getWeatherForecasts());
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityForecast that = (CityForecast) o;

        if (getCityId() != that.getCityId()) return false;
        if (Float.compare(that.getMessage(), getMessage()) != 0) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null)
            return false;
        if (getCoordinates() != null ? !getCoordinates().equals(that.getCoordinates()) : that.getCoordinates() != null)
            return false;
        if (getCountry() != null ? !getCountry().equals(that.getCountry()) : that.getCountry() != null)
            return false;
        if (getCod() != null ? !getCod().equals(that.getCod()) : that.getCod() != null)
            return false;
        return !(getWeatherForecasts() != null ? !getWeatherForecasts().equals(that.getWeatherForecasts()) : that.getWeatherForecasts() != null);

    }

    @Override
    public int hashCode() {
        int result = getCityId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getCoordinates() != null ? getCoordinates().hashCode() : 0);
        result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
        result = 31 * result + (getCod() != null ? getCod().hashCode() : 0);
        result = 31 * result + (getMessage() != +0.0f ? Float.floatToIntBits(getMessage()) : 0);
        result = 31 * result + (getWeatherForecasts() != null ? getWeatherForecasts().hashCode() : 0);
        return result;
    }
}

/**
 * <ul>
 * <li>WeatherMetaData_Weather</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.weathermetadata</li>
 * <li>07/03/2016</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.weathermetadata;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.Weather;

/**
 * Created by Mathias Seguy - Android2EE on 07/03/2016.
 * This class has been created because of sugarOrm one to many relationship management
 * So instead of polluting WeatherMetadat we creat subclass with just the fields to make the link
 */
@Deprecated
public class WeatherMetaData_Weather extends WeatherMetaData {
    Weather weather;

    /**
     * No args constructor for use in serialization
     */
    public WeatherMetaData_Weather() {
    }

    public WeatherMetaData_Weather(com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Weather weather) {
        super(weather);
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        //do not compare the weather (infinite loop) and you don't care for the equality
        return true;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getWeather() != null ? getWeather().hashCode() : 0);
        return result;
    }
}

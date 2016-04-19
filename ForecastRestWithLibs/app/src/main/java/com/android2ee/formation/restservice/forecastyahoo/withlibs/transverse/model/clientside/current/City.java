/**
 * <ul>
 * <li>CityDao</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model</li>
 * <li>26/02/2016</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Clouds;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Coordinates;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.WeatherDetails;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Wind;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.weathermetadata.WeatherMetaData_City;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mathias Seguy - Android2EE on 26/02/2016.
 */
public class City extends SugarRecord {
    /***********************************************************
     * Attributes
     **********************************************************/
    private int cityId;
    private String name;
    private Coordinates coordinates;
    private WeatherDetails weatherDetails;
    private int dateTimeUnix;
    private transient DateTime timeStamp=null;
    public DateTime getTimeStamp(){
        if(timeStamp==null){
            timeStamp=new DateTime(dateTimeUnix*1000);
        }
        return timeStamp;
    }
    private Wind wind;
    private Ephemeris ephemeris;
    private Clouds clouds;
    @Ignore
    private List<WeatherMetaData_City> metaDataList = null;
    /**Because of SugarOrm bad management of one to many relationship*/
    public List<WeatherMetaData_City> getMetaDataList() {
        if(metaDataList==null){
            //load them
            metaDataList= WeatherMetaData_City.find(WeatherMetaData_City.class, "CITY = ?", Long.toString(getId()));
        }
        return metaDataList;
    }

    public void setMetaDataList(List<WeatherMetaData_City> metaDataList) {
        this.metaDataList = metaDataList;
    }
    /***********************************************************
     *  Constructor
     **********************************************************/
    /**
     * No args constructor for use in serialization
     *
     */
    public City() {
    }
    /**
     * No args constructor for use in serialization
     *
     */
    public City(com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.City city) {
        if(city!=null) {
            cityId = city.getId();
            name = city.getName();
            coordinates = new Coordinates(city.getCoord());
            weatherDetails = new WeatherDetails(city.getMain());
            dateTimeUnix = city.getDt();
            wind = new Wind(city.getWind());
            ephemeris = new Ephemeris(city.getSys());
            clouds = new Clouds(city.getClouds());
            if(city.getWeather()!=null) {
                ArrayList<WeatherMetaData_City> weathersTemp =new ArrayList<>(city.getWeather().size());
                for (com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Weather w : city.getWeather()) {
                    weathersTemp.add(new WeatherMetaData_City(w));
                }
                setMetaDataList(weathersTemp);
            }
        }
    }

    /***********************************************************
     * Getters/setters
     **********************************************************/

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

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public int getDateTimeUnix() {
        return dateTimeUnix;
    }

    public void setDateTimeUnix(int dateTimeUnix) {
        this.dateTimeUnix = dateTimeUnix;
    }

    public Ephemeris getEphemeris() {
        return ephemeris;
    }

    public void setEphemeris(Ephemeris ephemeris) {
        this.ephemeris = ephemeris;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        final StringBuffer sb = new StringBuffer("City{");
        sb.append("cityId=").append(cityId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", coord=").append(coordinates);
        sb.append(", weatherDetails=").append(weatherDetails);
        sb.append(", dateTimeUnix=").append(dateTimeUnix);
        sb.append(", wind=").append(wind);
        sb.append(", ephemeris=").append(ephemeris);
        sb.append(", clouds=").append(clouds);
//        sb.append(", metaDataList=").append(getMetaDataList());
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (getCityId() != city.getCityId()) return false;
        if (getDateTimeUnix() != city.getDateTimeUnix()) return false;
        if (getName() != null ? !getName().equals(city.getName()) : city.getName() != null)
            return false;
        if (getCoordinates() != null ? !getCoordinates().equals(city.getCoordinates()) : city.getCoordinates() != null)
            return false;
        if (getWeatherDetails() != null ? !getWeatherDetails().equals(city.getWeatherDetails()) : city.getWeatherDetails() != null)
            return false;
        if (getTimeStamp() != null ? !getTimeStamp().equals(city.getTimeStamp()) : city.getTimeStamp() != null)
            return false;
        if (getWind() != null ? !getWind().equals(city.getWind()) : city.getWind() != null)
            return false;
        if (getEphemeris() != null ? !getEphemeris().equals(city.getEphemeris()) : city.getEphemeris() != null)
            return false;
        if (getClouds() != null ? !getClouds().equals(city.getClouds()) : city.getClouds() != null)
            return false;
        return !(getMetaDataList() != null ? !getMetaDataList().equals(city.getMetaDataList()) : city.getMetaDataList() != null);

    }

    @Override
    public int hashCode() {
        int result = getCityId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getCoordinates() != null ? getCoordinates().hashCode() : 0);
        result = 31 * result + (getWeatherDetails() != null ? getWeatherDetails().hashCode() : 0);
        result = 31 * result + getDateTimeUnix();
        result = 31 * result + (getTimeStamp() != null ? getTimeStamp().hashCode() : 0);
        result = 31 * result + (getWind() != null ? getWind().hashCode() : 0);
        result = 31 * result + (getEphemeris() != null ? getEphemeris().hashCode() : 0);
        result = 31 * result + (getClouds() != null ? getClouds().hashCode() : 0);
        result = 31 * result + (getMetaDataList() != null ? getMetaDataList().hashCode() : 0);
        return result;
    }

}

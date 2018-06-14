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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.newest;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.Coordinates;
import com.orm.SugarRecord;

/**
 * Created by Mathias Seguy - Android2EE on 26/02/2016.
 */
@Deprecated
public class City extends SugarRecord {
    /***********************************************************
     * Attributes
     **********************************************************/
    private int cityId;
    private String name;
    private Coordinates coordinates;
    private String country;
    private float groundLevel;
    private float seaLevel;

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
            cityId = city.getServerId();
            name = city.getName();
            coordinates = new Coordinates(city.getCoord());
            country=city.getSys().getCountry();
            groundLevel=city.getMain().getGrnd_level();
            seaLevel=city.getMain().getSea_level();
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

    public float getGroundLevel() {
        return groundLevel;
    }

    public void setGroundLevel(float groundLevel) {
        this.groundLevel = groundLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(float seaLevel) {
        this.seaLevel = seaLevel;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("City{").append("\t\r\n");
        sb.append("cityId=").append(cityId).append("\t\r\n");
        sb.append("name='").append(name).append("\t\r\n");
        sb.append("coordinates=").append(coordinates).append("\t\r\n");
        sb.append("country='").append(country).append("\t\r\n");
        sb.append("groundLevel=").append(groundLevel).append("\t\r\n");
        sb.append("seaLevel=").append(seaLevel).append("\r\n");
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (getCityId() != city.getCityId()) return false;
        if (Float.compare(city.getGroundLevel(), getGroundLevel()) != 0) return false;
        if (Float.compare(city.getSeaLevel(), getSeaLevel()) != 0) return false;
        if (getName() != null ? !getName().equals(city.getName()) : city.getName() != null)
            return false;
        if (getCoordinates() != null ? !getCoordinates().equals(city.getCoordinates()) : city.getCoordinates() != null)
            return false;
        return getCountry() != null ? getCountry().equals(city.getCountry()) : city.getCountry() == null;

    }

    @Override
    public int hashCode() {
        int result = getCityId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getCoordinates() != null ? getCoordinates().hashCode() : 0);
        result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
        result = 31 * result + (getGroundLevel() != +0.0f ? Float.floatToIntBits(getGroundLevel()) : 0);
        result = 31 * result + (getSeaLevel() != +0.0f ? Float.floatToIntBits(getSeaLevel()) : 0);
        return result;
    }
}

/**
 * <ul>
 * <li>FindCitiesResponse</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model</li>
 * <li>25/02/2016</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mathias Seguy - Android2EE on 25/02/2016.
 */
public class FindCitiesResponse {
    String message;
    int cod;
    int count;
    List<City> cities;

    public FindCitiesResponse(com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.FindCitiesResponse findCities) {
        if(findCities!=null) {
            message = findCities.getMessage();
            cod = findCities.getCod();
            count = findCities.getCount();
            cities = new ArrayList<City>();
            for (com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.City c : findCities.getList()) {
                cities.add(new City(c));
            }
        }
    }
    public FindCitiesResponse() {
    }
    public FindCitiesResponse(List<City> cities, int cod, int count, String message) {
        this.cities = cities;
        this.cod = cod;
        this.count = count;
        this.message = message;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FindCitiesResponse{");
        sb.append("cities=").append(cities);
        sb.append(", message='").append(message).append('\'');
        sb.append(", cod=").append(cod);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FindCitiesResponse that = (FindCitiesResponse) o;

        if (getCod() != that.getCod()) return false;
        if (getCount() != that.getCount()) return false;
        if (getMessage() != null ? !getMessage().equals(that.getMessage()) : that.getMessage() != null)
            return false;
        return !(getCities() != null ? !getCities().equals(that.getCities()) : that.getCities() != null);

    }

    @Override
    public int hashCode() {
        int result = getMessage() != null ? getMessage().hashCode() : 0;
        result = 31 * result + getCod();
        result = 31 * result + getCount();
        result = 31 * result + (getCities() != null ? getCities().hashCode() : 0);
        return result;
    }
}

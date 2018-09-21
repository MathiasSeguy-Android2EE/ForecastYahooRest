/**
 * <ul>
 * <li>FindCitiesResponse</li>
 * <li>com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model</li>
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

package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.current;

import java.util.List;

/**
 * Created by Mathias Seguy - Android2EE on 25/02/2016.
 */
public class FindCitiesResponse {
    String message;
    int cod;
    int count;
    List<City> list;

    public FindCitiesResponse() {
    }

    public FindCitiesResponse(List<City> list, int cod, int count, String message) {
        this.list = list;
        this.cod = cod;
        this.count = count;
        this.message = message;
    }

    public List<City> getList() {
        return list;
    }

    public void setList(List<City> list) {
        this.list = list;
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
        sb.append("cities=").append(list);
        sb.append(", message='").append(message).append('\'');
        sb.append(", cod=").append(cod);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}

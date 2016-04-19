
package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast;

import com.squareup.moshi.Json;

public class Forecast {

    private City city;
    private String cod;
    private float message;
    private int cnt;
    @Json(name = "list")
    private java.util.List<com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast.List> list ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Forecast() {
    }

    public Forecast(City city, int cnt, String cod, java.util.List<List> list, float message) {
        this.city = city;
        this.cnt = cnt;
        this.cod = cod;
        this.list = list;
        this.message = message;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public java.util.List<List> getList() {
        return list;
    }

    public void setList(java.util.List<List> list) {
        this.list = list;
    }

    public float getMessage() {
        return message;
    }

    public void setMessage(float message) {
        this.message = message;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Forecast{");
        sb.append("city=").append(city);
        sb.append(", cod='").append(cod).append('\'');
        sb.append(", message=").append(message);
        sb.append(", cnt=").append(cnt);
        sb.append(", list=").append(list);
        sb.append('}');
        return sb.toString();
    }
}

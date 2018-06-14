
package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Coord;

@Deprecated
public class City {

    private int id;
    private String name;
    private Coord coord;
    private String country;
//    = new ArrayList<com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast.List>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public City() {
    }

    public City(Coord coord, String country, int id, String name) {
        this.coord = coord;
        this.country = country;
        this.id = id;
        this.name = name;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("City{");
        sb.append("coord=").append(coord);
        sb.append(", id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

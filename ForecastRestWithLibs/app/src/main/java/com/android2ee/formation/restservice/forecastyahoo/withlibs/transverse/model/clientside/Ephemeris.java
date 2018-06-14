
package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.Sys;
import com.orm.SugarRecord;

/**
 * The system class having country, sunrise and sunset
 * Is the class sys on server side
 */
public class Ephemeris extends SugarRecord {
    private int type;
    private int cityId;
    private float message;
    private String country;
    private int sunrise;
    private int sunset;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Ephemeris() {
    }
    /**
     * No args constructor for use in serialization
     *
     */
    public Ephemeris(Sys sys) {
        if(sys!=null) {
            type = sys.getType();
//            cityId = sys.getId();
            message = sys.getMessage();
            country = sys.getCountry();
            sunrise = sys.getSunrise();
            sunset = sys.getSunset();
        }
    }
    /**
     * 
     * @param message
     * @param cityId
     * @param sunset
     * @param sunrise
     * @param type
     * @param country
     */
    public Ephemeris(int type, int cityId, float message, String country, int sunrise, int sunset) {
        this.type = type;
        this.cityId = cityId;
        this.message = message;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    /**
     * 
     * @return
     *     The type
     */
    public int getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The id
     */
    public int getCityId() {
        return cityId;
    }

    /**
     * 
     * @param cityId
     *     The id
     */
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    /**
     * 
     * @return
     *     The message
     */
    public float getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(float message) {
        this.message = message;
    }

    /**
     * 
     * @return
     *     The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * 
     * @param country
     *     The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 
     * @return
     *     The sunrise
     */
    public int getSunrise() {
        return sunrise;
    }

    /**
     * 
     * @param sunrise
     *     The sunrise
     */
    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    /**
     * 
     * @return
     *     The sunset
     */
    public int getSunset() {
        return sunset;
    }

    /**
     * 
     * @param sunset
     *     The sunset
     */
    public void setSunset(int sunset) {
        this.sunset = sunset;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Ephemeris{");
        sb.append("cityId=").append(cityId);
        sb.append(", type=").append(type);
        sb.append(", message=").append(message);
        sb.append(", country='").append(country).append('\'');
        sb.append(", sunrise=").append(sunrise);
        sb.append(", sunset=").append(sunset);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ephemeris ephemeris = (Ephemeris) o;

        if (getType() != ephemeris.getType()) return false;
        if (getCityId() != ephemeris.getCityId()) return false;
        if (Float.compare(ephemeris.getMessage(), getMessage()) != 0) return false;
        if (getSunrise() != ephemeris.getSunrise()) return false;
        if (getSunset() != ephemeris.getSunset()) return false;
        return !(getCountry() != null ? !getCountry().equals(ephemeris.getCountry()) : ephemeris.getCountry() != null);

    }

    @Override
    public int hashCode() {
        int result = getType();
        result = 31 * result + getCityId();
        result = 31 * result + (getMessage() != +0.0f ? Float.floatToIntBits(getMessage()) : 0);
        result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
        result = 31 * result + getSunrise();
        result = 31 * result + getSunset();
        return result;
    }
}


package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.weathermetadata;

import com.orm.SugarRecord;

/**
 * Mode info weather condition code
 * Is the Weather class on server side
 */
@Deprecated
public class WeatherMetaData extends SugarRecord {
    /**
     * Weather condition id
     */
    private long weatherConditionId;
    /**
     * weather.main Group of weather parameters (Rain, Snow, Extreme etc.)
     * */
    private String main;
    /**
     * Weather condition within the group
     */
    private String description;

    /**
     *Weather icon id
     */
    private String icon;

    /**
     * No args constructor for use in serialization
     * 
     */
    public WeatherMetaData() {
    }

    /**
     * Constructor to use for wrapping serverside format of data to client side one
     * @param weather
     */
    public WeatherMetaData(com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Weather weather) {
        if(weather!=null) {
            weatherConditionId = weather.getId();
            main = weather.getMain();
            description = weather.getDescription();
            icon = weather.getIcon();
        }
    }
    /**
     * 
     * @param weatherConditionId
     * @param icon
     * @param description
     * @param main
     */
    public WeatherMetaData(int weatherConditionId, String main, String description, String icon) {
        this.weatherConditionId = weatherConditionId;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    /**
     * 
     * @return
     *     The id
     */
    public long getWeatherConditionId() {
        return weatherConditionId;
    }

    /**
     * 
     * @param weatherConditionId
     *     The id
     */
    public void setWeatherConditionId(int weatherConditionId) {
        this.weatherConditionId = weatherConditionId;
    }

    /**
     * 
     * @return
     *     The main
     */
    public String getMain() {
        return main;
    }

    /**
     * 
     * @param main
     *     The main
     */
    public void setMain(String main) {
        this.main = main;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 
     * @param icon
     *     The icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WeatherMetaData{");
        sb.append("description='").append(description).append('\'');
        sb.append(", weatherConditionId=").append(weatherConditionId);
        sb.append(", main='").append(main).append('\'');
        sb.append(", icon='").append(icon).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeatherMetaData that = (WeatherMetaData) o;

        if (getWeatherConditionId() != that.getWeatherConditionId()) return false;
        if (getMain() != null ? !getMain().equals(that.getMain()) : that.getMain() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        return !(getIcon() != null ? !getIcon().equals(that.getIcon()) : that.getIcon() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) getWeatherConditionId();
        result = 31 * result + (getMain() != null ? getMain().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getIcon() != null ? getIcon().hashCode() : 0);
        return result;
    }
}

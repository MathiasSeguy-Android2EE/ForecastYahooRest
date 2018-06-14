package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.WeatherData;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast.WeatherForecastItem;

/**
 * Mode info weather condition code
 */

@Entity(tableName = "weather",
        indices = {@Index(value = {"weatherDataId","weatherForecastItemId"},unique = false)},
        foreignKeys = {
                @ForeignKey(entity = WeatherData.class,
                        parentColumns = "_id",
                        childColumns = "weatherDataId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = WeatherForecastItem.class,
                        parentColumns = "_id",
                        childColumns = "weatherForecastItemId",
                        onDelete = ForeignKey.CASCADE)}
)
public class Weather {
    @PrimaryKey(autoGenerate = true)
    private long _id;
    /**
     * Weather condition id
     */
    @Ignore
    private int id;
    /**
     * weather.main Group of weather parameters (Rain, Snow, Extreme etc.)
     */
    @ColumnInfo(name = "main")
    private String main;
    /**
     * Weather condition within the group
     */
    @ColumnInfo(name = "description")
    private String description;

    /**
     * Weather icon id
     */
    @ColumnInfo(name = "icon")
    private String icon;

    /***********************************************************
     *  Foreign Key
     *important trick as you have 2 foreign key, but only one will be defined
     *for any instance, so put a Long instead of long and Nullable
     **********************************************************/
    @ColumnInfo(name="weatherDataId")
    @Nullable
    private Long weatherDataId;
    @ColumnInfo(name="weatherForecastItemId")
    @Nullable
    private Long weatherForecastItemId;

    /***********************************************************
     *  Constructors
     **********************************************************/
    /**
     * No args constructor for use in serialization
     */
    public Weather() {
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public Long getWeatherDataId() {
        return weatherDataId;
    }

    public void setWeatherDataId(Long weatherDataId) {
        this.weatherDataId = weatherDataId;
    }

    public Long getWeatherForecastItemId() {
        return weatherForecastItemId;
    }

    public void setWeatherForecastItemId(Long weatherForecastItemId) {
        this.weatherForecastItemId = weatherForecastItemId;
    }

    /**
     * @param id
     * @param icon
     * @param description
     * @param main
     */
    public Weather(int id, String main, String description, String icon) {
        this.id = id;

        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    /**
     * @return The id
     */
    @Ignore
    public int getId() {
        return id;
    }

    /**
     * @param id The id
     */
    @Ignore
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The main
     */
    public String getMain() {
        return main;
    }

    /**
     * @param main The main
     */
    public void setMain(String main) {
        this.main = main;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon The icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Weather{\r\n");
        sb.append("description='").append(description).append("\r\n\t");
        sb.append(", id=").append(id).append("\r\n\t");
        sb.append(", main='").append(main).append("\r\n\t");
        sb.append(", icon='").append(icon).append("\r\n\t");
        sb.append('}');
        return sb.toString();
    }
}

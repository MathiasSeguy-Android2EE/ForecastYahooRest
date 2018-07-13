package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.calculated;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.current.City;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 03/07/2018.
 *
 * This is the representation of the Weather of a day for a specific city
 */
@Entity(tableName="weather_of_the_day",
        indices = {@Index(value = {"cityId"},unique = false)},
        foreignKeys = {
                @ForeignKey(entity = City.class,
                        parentColumns = "_id",
                        childColumns = "cityId",
                        onDelete = ForeignKey.CASCADE)}
)public class WeatherOfTheDay {

    /**
     *
     */
    private static final float KELVIN_OFFSET_TO_CELSIUS = -273.15f;
    @PrimaryKey(autoGenerate = true)
    private long _id_wotd;

    /**
     * Associated city
     */
    @ColumnInfo(name = "cityId")
    private long city_Id;
    /**
     * Hash of the day
     */
    @ColumnInfo(name = "day_hash")
    private int dayHash = -1;
    @Ignore
    private Calendar calTemp;

    /**
     * Average of all the clouds of the day
     */
    @ColumnInfo(name = "clouds")
    private float clouds=0f;
    /**
     * Average of the wind of the wind
     */
    @ColumnInfo(name = "wind_speed")
    private float windSpeed=0f;
    /**
     * Average of the wind of the wind
     */
    @ColumnInfo(name = "wind_degree")
    private float windDegree=0f;
    /**
     * Sum of the wind of the rain
     */
    @ColumnInfo(name = "rain")
    private float rain=0f;
    /**
     * Sum of the wind of the snow
     */
    @ColumnInfo(name = "snow")
    private float snow=0f;
    /**
     * Recalculation of the main
     * temp == null
     * humlidity=average
     * temp_min=Min of temp
     * tempMax=Max of temp
     */
    @ColumnInfo(name = "temp")
    private float temp=KELVIN_OFFSET_TO_CELSIUS;
    @ColumnInfo(name = "pressure")
    private float pressure=0f;
    @ColumnInfo(name = "humidity")
    private float humidity=0f;
    @ColumnInfo(name = "temp_min")
    private float tempMin=KELVIN_OFFSET_TO_CELSIUS;
    @ColumnInfo(name = "temp_max")
    private float tempMax=KELVIN_OFFSET_TO_CELSIUS;
    /**
     * For those elements, the most found in the day based on the occurence of each
     */
    @ColumnInfo(name = "main")
    private String main;
    @ColumnInfo(name = "main_secondary")
    private String mainSecondary ;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "icon")
    private String icon;
    @ColumnInfo(name = "icon_secondary")
    private String iconSecondary;


    /***********************************************************
     *  Generated
     **********************************************************/
    public WeatherOfTheDay() {
    }

    public long get_id_wotd() {
        return _id_wotd;
    }

    public void set_id_wotd(long _id_wotd) {
        this._id_wotd = _id_wotd;
    }

    public long getCity_Id() {
        return city_Id;
    }

    public void setCity_Id(long city_Id) {
        this.city_Id = city_Id;
    }

    public int getDayHash() {
        return dayHash;
    }

    public Calendar getDayHashCalendar() {
        if(calTemp==null){
            calTemp=new GregorianCalendar();
            calTemp.set(Calendar.YEAR,dayHash/1000);
            calTemp.set(Calendar.DAY_OF_YEAR,dayHash%1000);
        }
        return calTemp;
    }

    public void setDayHash(int dayHash) {
        this.dayHash = dayHash;
    }

    public float getClouds() {
        return clouds;
    }

    public void setClouds(float clouds) {
        this.clouds = clouds;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(float windDegree) {
        this.windDegree = windDegree;
    }

    public float getRain() {
        return rain;
    }

    public void setRain(float rain) {
        this.rain = rain;
    }

    public float getSnow() {
        return snow;
    }

    public void setSnow(float snow) {
        this.snow = snow;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getTempMin() {
        return tempMin;
    }

    public void setTempMin(float tempMin) {
        this.tempMin = tempMin;
    }

    public float getTempMax() {
        return tempMax;
    }

    public void setTempMax(float tempMax) {
        this.tempMax = tempMax;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconSecondary() {
        return iconSecondary;
    }

    public void setIconSecondary(String iconSecondary) {
        this.iconSecondary = iconSecondary;
    }

    public String getMainSecondary() {
        return mainSecondary;
    }

    public void setMainSecondary(String mainSecondary) {
        this.mainSecondary = mainSecondary;
    }

    @Override
    public String toString() {
        return "WeatherOfTheDay{" +
                "_id_wotd=" + _id_wotd +
                ", city_Id=" + city_Id +
                ", dayHash=" + dayHash +
                ", calTemp=" + calTemp +
                ", clouds=" + clouds +
                ", windSpeed=" + windSpeed +
                ", windDegree=" + windDegree +
                ", rain=" + rain +
                ", snow=" + snow +
                ", temp=" + temp +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                ", main='" + main + '\'' +
                ", mainSecondary='" + mainSecondary + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", iconSecondary='" + iconSecondary + '\'' +
                '}';
    }
}

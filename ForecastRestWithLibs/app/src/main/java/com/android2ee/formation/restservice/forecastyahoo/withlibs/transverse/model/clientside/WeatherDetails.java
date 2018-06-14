
package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Main;
import com.orm.SugarRecord;

/**
 * This is the main weather data
 * Is Main on serverSide
 */
@Deprecated
public class WeatherDetails extends SugarRecord {

    private float temp;
    private float pressure;
    private int humidity;
    private float tempMin;
    private float tempMax;
    private float sea_level;
    private float grnd_level;

    /**
     * No args constructor for use in serialization
     * 
     */
    public WeatherDetails() {
    }
    /**
     * No args constructor for use in serialization
     *
     */
    public WeatherDetails(Main main) {
        if(main!=null) {
            temp = main.getTemp();
            pressure = main.getPressure();
            humidity = main.getHumidity();
            tempMin = main.getTempMin();
            tempMax = main.getTempMax();
            sea_level=main.getSea_level();
            grnd_level=main.getGrnd_level();
        }
    }
    /**
     * 
     * @param humidity
     * @param pressure
     * @param tempMax
     * @param temp
     * @param tempMin
     */
    public WeatherDetails(float temp, float pressure, int humidity, float tempMin, float tempMax) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
    }

    /**
     * 
     * @return
     *     The temp
     */
    public float getTemp() {
        return (temp-273.15f);
    }

    /**
     * 
     * @param temp
     *     The temp
     */
    public void setTemp(float temp) {
        this.temp = temp;
    }

    /**
     * 
     * @return
     *     The pressure
     */
    public float getPressure() {
        return pressure;
    }

    /**
     * 
     * @param pressure
     *     The pressure
     */
    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    /**
     * 
     * @return
     *     The humidity
     */
    public int getHumidity() {
        return humidity;
    }

    /**
     * 
     * @param humidity
     *     The humidity
     */
    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    /**
     * 
     * @return
     *     The tempMin
     */
    public float getTempMin() {
        return tempMin;
    }

    /**
     * 
     * @param tempMin
     *     The temp_min
     */
    public void setTempMin(float tempMin) {
        this.tempMin = tempMin;
    }

    /**
     * 
     * @return
     *     The tempMax
     */
    public float getTempMax() {
        return tempMax;
    }

    /**
     * 
     * @param tempMax
     *     The temp_max
     */
    public void setTempMax(float tempMax) {
        this.tempMax = tempMax;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WeatherDetails{");
        sb.append("grnd_level=").append(grnd_level);
        sb.append(", temp=").append(temp);
        sb.append(", pressure=").append(pressure);
        sb.append(", humidity=").append(humidity);
        sb.append(", tempMin=").append(tempMin);
        sb.append(", tempMax=").append(tempMax);
        sb.append(", sea_level=").append(sea_level);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeatherDetails that = (WeatherDetails) o;

        if (Float.compare(that.getTemp(), getTemp()) != 0) return false;
        if (Float.compare(that.getPressure(), getPressure()) != 0) return false;
        if (getHumidity() != that.getHumidity()) return false;
        if (Float.compare(that.getTempMin(), getTempMin()) != 0) return false;
        if (Float.compare(that.getTempMax(), getTempMax()) != 0) return false;
        if (Float.compare(that.sea_level, sea_level) != 0) return false;
        return Float.compare(that.grnd_level, grnd_level) == 0;

    }

    @Override
    public int hashCode() {
        int result = (getTemp() != +0.0f ? Float.floatToIntBits(getTemp()) : 0);
        result = 31 * result + (getPressure() != +0.0f ? Float.floatToIntBits(getPressure()) : 0);
        result = 31 * result + getHumidity();
        result = 31 * result + (getTempMin() != +0.0f ? Float.floatToIntBits(getTempMin()) : 0);
        result = 31 * result + (getTempMax() != +0.0f ? Float.floatToIntBits(getTempMax()) : 0);
        result = 31 * result + (sea_level != +0.0f ? Float.floatToIntBits(sea_level) : 0);
        result = 31 * result + (grnd_level != +0.0f ? Float.floatToIntBits(grnd_level) : 0);
        return result;
    }
}


package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside;

import com.orm.SugarRecord;

public class Wind extends SugarRecord {

    private float speed;
    private float deg;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Wind() {
    }
    public Wind(com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Wind wind) {
        if(wind!=null) {
            speed = wind.getSpeed();
            deg = wind.getDeg();
        }
    }
    /**
     * 
     * @param speed
     * @param deg
     */
    public Wind(float speed, float deg) {
        this.speed = speed;
        this.deg = deg;
    }

    /**
     * 
     * @return
     *     The speed
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * 
     * @param speed
     *     The speed
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * 
     * @return
     *     The deg
     */
    public float getDeg() {
        return deg;
    }

    /**
     * 
     * @param deg
     *     The deg
     */
    public void setDeg(float deg) {
        this.deg = deg;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Wind{");
        sb.append("deg=").append(deg);
        sb.append(", speed=").append(speed);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wind wind = (Wind) o;

        if (Float.compare(wind.getSpeed(), getSpeed()) != 0) return false;
        return Float.compare(wind.getDeg(), getDeg()) == 0;

    }

    @Override
    public int hashCode() {
        int result = (getSpeed() != +0.0f ? Float.floatToIntBits(getSpeed()) : 0);
        result = 31 * result + (getDeg() != +0.0f ? Float.floatToIntBits(getDeg()) : 0);
        return result;
    }
}

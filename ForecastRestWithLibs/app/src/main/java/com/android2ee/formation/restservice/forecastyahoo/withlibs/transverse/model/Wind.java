
package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model;

public class Wind {

    private float speed;
    private float deg;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Wind() {
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

}

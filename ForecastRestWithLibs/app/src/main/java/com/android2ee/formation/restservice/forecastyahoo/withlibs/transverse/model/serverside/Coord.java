/**
 * <ul>
 * <li>Coord</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model</li>
 * <li>23/02/2016</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside;

/**
 * Created by Mathias Seguy - Android2EE on 23/02/2016.
 */
public class Coord {
    private float lon;
    private float lat;

    /**
     * No args constructor for use in serialization
     *
     */
    public Coord() {
    }

    /**
     *
     * @param lon
     * @param lat
     */
    public Coord(float lon, float lat) {
        this.lon = lon;
        this.lat = lat;
    }

    /**
     *
     * @return
     * The lon
     */
    public float getLon() {
        return lon;
    }

    /**
     *
     * @param lon
     * The lon
     */
    public void setLon(float lon) {
        this.lon = lon;
    }

    /**
     *
     * @return
     * The lat
     */
    public float getLat() {
        return lat;
    }

    /**
     *
     * @param lat
     * The lat
     */
    public void setLat(float lat) {
        this.lat = lat;
    }

}

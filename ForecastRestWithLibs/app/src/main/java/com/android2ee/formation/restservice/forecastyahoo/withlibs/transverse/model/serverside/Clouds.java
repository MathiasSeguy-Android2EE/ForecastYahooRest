
package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside;

public class Clouds {
    /**Percent of clouds*/

    private int all;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Clouds() {
    }

    /**
     * 
     * @param all
     */
    public Clouds(int all) {
        this.all = all;
    }

    /**
     * 
     * @return
     *     The all
     */
    public int getAll() {
        return all;
    }

    /**
     * 
     * @param all
     *     The all
     */
    public void setAll(int all) {
        this.all = all;
    }

}

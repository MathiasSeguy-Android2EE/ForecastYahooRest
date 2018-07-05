
package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model;


import com.squareup.moshi.Json;

public class Snow {

    /**
     * snow volume for the last 3 hours
     */
    @Json(name="3h")
    private float _3h;

    /**
     * No args constructor for use in serialization
     *
     */
    public Snow() {
    }

    /**
     *
     * @param _3h
     */
    public Snow(float _3h) {
        this._3h = _3h;
    }

    /**
     * 
     * @return
     *     The _3h
     */
    public float get3h() {
        return _3h;
    }

    /**
     * 
     * @param _3h
     *     The 3h
     */
    public void set3h(float _3h) {
        this._3h = _3h;
    }

}

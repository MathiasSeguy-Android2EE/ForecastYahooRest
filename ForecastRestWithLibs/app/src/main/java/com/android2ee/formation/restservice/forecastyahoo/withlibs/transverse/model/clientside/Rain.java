
package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside;


import com.orm.SugarRecord;

@Deprecated
public class Rain extends SugarRecord {

    /**
     * rain volume for the last 3 hours
     */
    private float rainVolume3h;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Rain() {
    }
    public Rain(com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Rain rain) {
        if(rain!=null){
            rainVolume3h=rain.get3h();
        }
    }

    /**
     * 
     * @param rainVolume3h
     */
    public Rain(float rainVolume3h) {
        this.rainVolume3h = rainVolume3h;
    }

    /**
     * 
     * @return
     *     The _3h
     */
    public float get3h() {
        return rainVolume3h;
    }

    /**
     * 
     * @param _3h
     *     The 3h
     */
    public void set3h(float _3h) {
        this.rainVolume3h = _3h;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Rain{");
        sb.append("rainVolume3h=").append(rainVolume3h);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rain rain = (Rain) o;

        return Float.compare(rain.rainVolume3h, rainVolume3h) == 0;

    }

    @Override
    public int hashCode() {
        return (rainVolume3h != +0.0f ? Float.floatToIntBits(rainVolume3h) : 0);
    }
}


package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside;


import com.orm.SugarRecord;

public class Snow extends SugarRecord {

    /**
     * snow volume for the last 3 hours
     */
    private float snowVolume3h;

    /**
     * No args constructor for use in serialization
     *
     */
    public Snow() {
    }
    public Snow(com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Snow snow) {
        if(snow!=null){
            snowVolume3h=snow.get3h();
        }
    }
    /**
     *
     * @param snowVolume3h
     */
    public Snow(float snowVolume3h) {
        this.snowVolume3h = snowVolume3h;
    }

    /**
     * 
     * @return
     *     The _3h
     */
    public float get3h() {
        return snowVolume3h;
    }

    /**
     * 
     * @param _3h
     *     The 3h
     */
    public void set3h(float _3h) {
        this.snowVolume3h = _3h;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Snow{");
        sb.append("snowVolume3h=").append(snowVolume3h);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Snow snow = (Snow) o;

        return Float.compare(snow.snowVolume3h, snowVolume3h) == 0;

    }

    @Override
    public int hashCode() {
        return (snowVolume3h != +0.0f ? Float.floatToIntBits(snowVolume3h) : 0);
    }
}

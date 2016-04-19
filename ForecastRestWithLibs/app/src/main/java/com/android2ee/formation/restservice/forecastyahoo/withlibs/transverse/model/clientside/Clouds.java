
package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside;

import com.orm.SugarRecord;

public class Clouds extends SugarRecord {
    /**Percent of clouds*/

    private int cloudsCoveragePercents;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Clouds() {
    }

    /**
     * 
     * @param cloudsCoveragePercents
     */
    public Clouds(int cloudsCoveragePercents) {
        this.cloudsCoveragePercents = cloudsCoveragePercents;
    }

    public Clouds(com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Clouds clouds){
        if(clouds!=null){
            cloudsCoveragePercents=clouds.getAll();
        }
    }
    /**
     * 
     * @return
     *     The all
     */
    public int getCloudsCoveragePercents() {
        return cloudsCoveragePercents;
    }

    /**
     * 
     * @param cloudsCoveragePercents
     *     The all
     */
    public void setCloudsCoveragePercents(int cloudsCoveragePercents) {
        this.cloudsCoveragePercents = cloudsCoveragePercents;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Clouds{");
        sb.append("cloudsCoveragePercents=").append(cloudsCoveragePercents);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Clouds clouds = (Clouds) o;

        return getCloudsCoveragePercents() == clouds.getCloudsCoveragePercents();

    }

    @Override
    public int hashCode() {
        return getCloudsCoveragePercents();
    }
}

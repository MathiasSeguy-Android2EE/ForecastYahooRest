
package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast;

@Deprecated
public class Sys {

    private String pod;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Sys() {
    }

    /**
     * 
     * @param pod
     */
    public Sys(String pod) {
        this.pod = pod;
    }

    /**
     * 
     * @return
     *     The pod
     */
    public String getPod() {
        return pod;
    }

    /**
     * 
     * @param pod
     *     The pod
     */
    public void setPod(String pod) {
        this.pod = pod;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Sys{");
        sb.append("pod='").append(pod).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

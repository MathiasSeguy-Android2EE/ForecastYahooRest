
package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside;

/**
 * Mode info weather condition code
 */
public class Weather {
    /**
     * Weather condition id
     */

    private int id;
    /**
     * weather.main Group of weather parameters (Rain, Snow, Extreme etc.)
     * */
    private String main;
    /**
     * Weather condition within the group
     */
    private String description;

    /**
     *Weather icon id
     */
    private String icon;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Weather() {
    }

    /**
     * 
     * @param id
     * @param icon
     * @param description
     * @param main
     */
    public Weather(int id, String main, String description, String icon) {
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    /**
     * 
     * @return
     *     The id
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The main
     */
    public String getMain() {
        return main;
    }

    /**
     * 
     * @param main
     *     The main
     */
    public void setMain(String main) {
        this.main = main;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 
     * @param icon
     *     The icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Weather{\r\n");
        sb.append("description='").append(description).append("\r\n\t");
        sb.append(", id=").append(id).append("\r\n\t");
        sb.append(", main='").append(main).append("\r\n\t");
        sb.append(", icon='").append(icon).append("\r\n\t");
        sb.append('}');
        return sb.toString();
    }
}

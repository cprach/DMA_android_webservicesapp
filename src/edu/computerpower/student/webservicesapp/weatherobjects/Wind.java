
package edu.computerpower.student.webservicesapp.weatherobjects;

import java.util.HashMap;
import java.util.Map;

public class Wind {

    private Double speed;
    private Double gust;
    private Double deg;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The speed
     */
    public Double getSpeed() {
        return speed;
    }

    /**
     * 
     * @param speed
     *     The speed
     */
    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    /**
     * 
     * @return
     *     The gust
     */
    public Double getGust() {
        return gust;
    }

    /**
     * 
     * @param gust
     *     The gust
     */
    public void setGust(Double gust) {
        this.gust = gust;
    }

    /**
     * 
     * @return
     *     The deg
     */
    public Double getDeg() {
        return deg;
    }

    /**
     * 
     * @param deg
     *     The deg
     */
    public void setDeg(Double deg) {
        this.deg = deg;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

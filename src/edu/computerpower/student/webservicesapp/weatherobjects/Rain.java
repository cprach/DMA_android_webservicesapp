
package edu.computerpower.student.webservicesapp.weatherobjects;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Rain {

    private Integer _3h;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The _3h
     */
    public Integer get3h() {
        return _3h;
    }

    /**
     * 
     * @param _3h
     *     The 3h
     */
    public void set3h(Integer _3h) {
        this._3h = _3h;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

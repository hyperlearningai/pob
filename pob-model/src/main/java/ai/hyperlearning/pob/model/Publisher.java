package ai.hyperlearning.pob.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Publisher Model
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public class Publisher implements Serializable {

    private static final long serialVersionUID = 5745021834926792359L;
    private String id;
    private boolean enabled;
    private String publisherClass;
    private Map<String, Object> properties = new LinkedHashMap<>();

    public Publisher() {

    }

    public Publisher(String id, boolean enabled, String publisherClass,
            Map<String, Object> properties) {
        this.id = id;
        this.enabled = enabled;
        this.publisherClass = publisherClass;
        this.properties = properties;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPublisherClass() {
        return publisherClass;
    }

    public void setPublisherClass(String publisherClass) {
        this.publisherClass = publisherClass;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Publisher other = (Publisher) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Publisher [" 
                + "id=" + id + ", " 
                + "enabled=" + enabled + ", "
                + "publisherClass=" + publisherClass + ", "
                + "properties=" + properties 
                + "]";
    }

}

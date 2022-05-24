package ai.hyperlearning.pob.data.publishers;

import java.util.Map;

import ai.hyperlearning.pob.data.publishers.exceptions.OpportunityPublishingException;
import ai.hyperlearning.pob.model.Opportunity;

/**
 * Procurement Opportunity Publisher
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public abstract class OpportunityPublisher {
    
    private Map<String, Object> properties;
    
    protected OpportunityPublisher(Map<String, Object> properties) {
        this.properties = properties;
    }
    
    public Map<String, Object> getProperties() {
        return properties;
    }
    
    public abstract void publish(Opportunity opportunity) 
            throws OpportunityPublishingException;

}

package ai.hyperlearning.pob.data.publishers;

import java.util.Map;

import ai.hyperlearning.pob.core.utils.JsonUtils;
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
    
    /**
     * The properties map will be loaded from application.yml
     * and injected into the publisher implementation class
     * by the main POB data pipeline.
     * @param properties
     */
    
    protected OpportunityPublisher(Map<String, Object> properties) {
        this.properties = properties;
    }
    
    public Map<String, Object> getProperties() {
        return properties;
    }
    
    public abstract void publish(Opportunity opportunity) 
            throws OpportunityPublishingException;
    
    /**
     * Default message builder
     * @param template
     * @param opportunity
     * @return
     */
    
    public String buildMessage(String template, 
            Opportunity opportunity) {
        
        return template
                .replace(CommonPublisherProperties
                            .JSON_PLACEHOLDER_OPPORTUNITY_TITLE, 
                        JsonUtils.cleanValueString(
                                opportunity.getTitle()))
                .replace(CommonPublisherProperties.
                            JSON_PLACEHOLDER_OPPORTUNITY_BUYER, 
                        JsonUtils.cleanValueString(
                                opportunity.getBuyer()))
                .replace(CommonPublisherProperties.
                            JSON_PLACEHOLDER_OPPORTUNITY_FRAMEWORK_NAME, 
                        JsonUtils.cleanValueString(
                                opportunity.getFramework().getName()))
                .replace(CommonPublisherProperties.
                            JSON_PLACEHOLDER_OPPORTUNITY_DATE_PUBLISHED, 
                        opportunity.getDatePublished() != null ? 
                                opportunity.getDatePublished().toString() : 
                                    CommonPublisherProperties.
                                        UNKNOWN_TEXT_VALUE)
                .replace(CommonPublisherProperties.
                            JSON_PLACEHOLDER_OPPORTUNITY_DATE_CLOSING, 
                        opportunity.getDateClosing() != null ? 
                                opportunity.getDateClosing().toString() : 
                                    CommonPublisherProperties.
                                        UNKNOWN_TEXT_VALUE)
                .replace(CommonPublisherProperties.
                            JSON_PLACEHOLDER_OPPORTUNITY_SUMMARY, 
                        JsonUtils.cleanValueString(
                                opportunity.getSummary()))
                .replace(CommonPublisherProperties.
                            JSON_PLACEHOLDER_OPPORTUNITY_URL, 
                        opportunity.getUrl() != null ? 
                                opportunity.getUrl() : 
                                    CommonPublisherProperties.
                                        UNKNOWN_TEXT_VALUE);
        
    }

}

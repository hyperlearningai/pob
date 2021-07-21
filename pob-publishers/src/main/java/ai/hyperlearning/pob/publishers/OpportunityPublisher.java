package ai.hyperlearning.pob.publishers;

import java.util.Map;

import ai.hyperlearning.pob.model.Opportunity;

/**
 * Opportunity Publisher Abstract Class
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public abstract class OpportunityPublisher {
	
	private Map<String, Object> properties;
	
	protected OpportunityPublisher(Map<String, Object> properties) {
		this.properties = properties;
	}
	
	public abstract boolean publish(Opportunity opportunity);
	
	public Map<String, Object> getProperties() {
		return properties;
	}

}

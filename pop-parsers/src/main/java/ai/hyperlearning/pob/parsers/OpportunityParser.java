package ai.hyperlearning.pob.parsers;

import java.util.Set;

import ai.hyperlearning.pob.model.Opportunity;

/**
 * Opportunity Parser Interface
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public abstract class OpportunityParser {
	
	private String frameworkId;
	
	protected OpportunityParser(String frameworkId) {
		this.frameworkId = frameworkId;
	}
	
	public abstract Set<Opportunity> parse();
	
	public String getFrameworkId() {
		return frameworkId;
	}

}

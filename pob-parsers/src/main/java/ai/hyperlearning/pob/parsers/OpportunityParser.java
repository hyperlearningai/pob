package ai.hyperlearning.pob.parsers;

import java.util.Set;

import ai.hyperlearning.pob.model.Framework;
import ai.hyperlearning.pob.model.Opportunity;

/**
 * Opportunity Parser Abstract Class
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public abstract class OpportunityParser {
	
	private Framework framework;
	
	protected OpportunityParser(Framework framework) {
		this.framework = framework;
	}
	
	public abstract Set<Opportunity> parse();
	
	public Framework getFramework() {
		return framework;
	}

}

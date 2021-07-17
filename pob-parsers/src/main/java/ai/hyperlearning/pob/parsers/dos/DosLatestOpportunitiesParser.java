package ai.hyperlearning.pob.parsers.dos;

import java.util.Set;

import ai.hyperlearning.pob.model.Framework;
import ai.hyperlearning.pob.model.Opportunity;
import ai.hyperlearning.pob.parsers.OpportunityParser;

/**
 * Custom Opportunity Parser - DOS Framework
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public class DosLatestOpportunitiesParser extends OpportunityParser {
	
	protected DosLatestOpportunitiesParser(Framework framework) {
		super(framework);
	}

	@Override
	public Set<Opportunity> parse() {
		return null;
	}

}

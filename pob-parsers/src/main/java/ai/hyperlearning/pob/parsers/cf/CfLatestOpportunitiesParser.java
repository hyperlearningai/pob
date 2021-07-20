package ai.hyperlearning.pob.parsers.cf;

import java.util.LinkedHashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.hyperlearning.pob.model.Framework;
import ai.hyperlearning.pob.model.Opportunity;
import ai.hyperlearning.pob.parsers.OpportunityParser;

/**
 * Custom Opportunity Parser - Contracts Finder Service
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public class CfLatestOpportunitiesParser extends OpportunityParser {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(
			CfLatestOpportunitiesParser.class);
	private static final String FORM_TOKEN_CSS_QUERY = "input[name=form_token]";
	
	public CfLatestOpportunitiesParser(Framework framework) {
		super(framework);
	}

	@Override
	public Set<Opportunity> parse() {
		
		Set<Opportunity> opportunities = new LinkedHashSet<Opportunity>();
		try {
			
			// Get the form token
			Document formDocument = Jsoup.connect(
					getFramework().getOpportunitiesUrl()).get();
			String formToken = formDocument.select(FORM_TOKEN_CSS_QUERY)
					.first().attr("value");
			LOGGER.debug("Parsed form token {}", formToken);
			
			// Submit a POST request with the relevant form data
			
			
			
			// Parse the search results
			
			
			
		} catch (Exception e) {
			LOGGER.error("Unable to parse the Contracts Finder "
					+ "Opportunities URL", e);
		}
		
		return opportunities;
		
	}

}

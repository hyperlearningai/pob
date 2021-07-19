package ai.hyperlearning.pob.parsers.dos;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.hyperlearning.pob.model.Framework;
import ai.hyperlearning.pob.model.Opportunity;
import ai.hyperlearning.pob.parsers.OpportunityParser;
import ai.hyperlearning.pob.utils.DateFormattingUtils;

/**
 * Custom Opportunity Parser - DOS Framework
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public class DosLatestOpportunitiesParser extends OpportunityParser {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(
			DosLatestOpportunitiesParser.class);
	private static final String SEARCH_RESULTS_CSS_QUERY = 
			"li.app-search-result";
	private static final String TITLE_CSS_QUERY = "a.govuk-link";
	
	protected DosLatestOpportunitiesParser(Framework framework) {
		super(framework);
	}

	@Override
	public Set<Opportunity> parse() {
		
		Set<Opportunity> opportunities = new LinkedHashSet<Opportunity>();
		try {
			
			// Get the HTML contents of the DOS Opportunities URL
			Document document = Jsoup.connect(
					getFramework().getOpportunitiesUrl()).get();
			
			// Parse and iterate opportunity search results
			Elements searchResults = document.select(SEARCH_RESULTS_CSS_QUERY);
			for (Element searchResult : searchResults) {
				
				// Parse URI
				String[] uriHref = searchResult.selectFirst(
						TITLE_CSS_QUERY).attr("href").split("/");
				String uri = uriHref[uriHref.length - 1];
				
				// Parse title
				String title = searchResult.selectFirst(
						TITLE_CSS_QUERY).text();
				
				// Parse buyer
				String buyer = searchResult.selectFirst("ul.govuk-list")
						.selectFirst("li").text()
						.replace("Organisation: ", "")
						.strip();
				
				// Parse summary
				String summary = searchResult.selectFirst("p.govuk-body")
						.text();
				
				// Parse URL
				String url = getFramework().getBaseUrl() + searchResult
						.selectFirst(TITLE_CSS_QUERY).attr("href");
				
				// Parse date published
				String datePublishedString = searchResult.select("ul.govuk-list")
						.get(2).selectFirst("li").text()
						.replace("Published: ", "")
						.strip();
				LocalDate datePublished = DateFormattingUtils
						.EEEEddMMMMyyyyToLocalDate(datePublishedString);
				
				// Parse date closing
				String dateClosingString = searchResult.select("ul.govuk-list")
						.get(2).select("li").get(2).text()
						.replace("Closing: ", "")
						.strip();
				LocalDate dateClosing = DateFormattingUtils
						.EEEEddMMMMyyyyToLocalDate(dateClosingString);
				
				// Create an opportunity object and add it to the set
				Opportunity opportunity = new Opportunity(uri, getFramework(), 
						title, buyer, summary, url, datePublished, dateClosing);
				opportunities.add(opportunity);
				
			}
			
		} catch (Exception e) {
			LOGGER.error("Unable to parse DOS Opportunities URL", e);
		}
		
		return opportunities;
		
	}

}

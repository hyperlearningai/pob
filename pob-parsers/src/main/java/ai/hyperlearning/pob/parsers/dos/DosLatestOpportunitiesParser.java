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
import ai.hyperlearning.pob.utils.StringUtils;

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
	private static final String GENERAL_CSS_QUERY = "ul.govuk-list";
	private static final String TITLE_CSS_QUERY = "a.govuk-link";
	private static final String SUMMARY_CSS_QUERY = "p.govuk-body";
	private static final String BUYER_TEXT_PREFIX = "Organisation: ";
	private static final String DATE_PUBLISHED_TEXT_PREFIX = "Published: ";
	private static final String DATE_CLOSING_TEXT_PREFIX = "Closing: ";
	
	public DosLatestOpportunitiesParser(Framework framework) {
		super(framework);
	}

	@Override
	public Set<Opportunity> parse() {
		
		Set<Opportunity> opportunities = new LinkedHashSet<Opportunity>();
		String opportunitiesUrl = null;
		try {
			
			// Construct the URL to GET with any required keyword filtering
			if ( getFramework().isFilter() && !getFramework()
					.getKeywords().isBlank() ) {
				String keywordsFilterRequestParams = StringUtils
						.keywordsToGetRequestParams(
								getFramework().getKeywords());
				opportunitiesUrl = getFramework().getOpportunitiesUrl()
						.replace("?q=&", 
								"?q=" + keywordsFilterRequestParams + "&");
			} else
				opportunitiesUrl = getFramework().getOpportunitiesUrl();
			
			// Get the HTML contents of the DOS Opportunities URL
			LOGGER.debug("Parsing {}", opportunitiesUrl);
			Document document = Jsoup.connect(opportunitiesUrl).get();
			
			// Parse and iterate opportunity search results
			Elements searchResults = document.select(SEARCH_RESULTS_CSS_QUERY);
			for (Element searchResult : searchResults) {
				
				// Parse URI (normally the opportunity URL if applicable)
				String uri = getFramework().getBaseUrl() + searchResult
						.selectFirst(TITLE_CSS_QUERY).attr("href");
				
				// Parse title
				String title = searchResult.selectFirst(
						TITLE_CSS_QUERY).text();
				
				// Parse buyer
				String buyer = searchResult.selectFirst(GENERAL_CSS_QUERY)
						.selectFirst("li").text()
						.replace(BUYER_TEXT_PREFIX, "")
						.strip();
				
				// Parse summary
				String summary = searchResult.selectFirst(SUMMARY_CSS_QUERY)
						.text();
				
				// Parse date published
				String datePublishedString = searchResult
						.select(GENERAL_CSS_QUERY)
						.get(2).selectFirst("li").text()
						.replace(DATE_PUBLISHED_TEXT_PREFIX, "")
						.strip();
				LocalDate datePublished = DateFormattingUtils
						.EEEEddMMMMyyyyToLocalDate(datePublishedString);
				
				// Parse date closing
				String dateClosingString = searchResult
						.select(GENERAL_CSS_QUERY)
						.get(2).select("li").get(2).text()
						.replace(DATE_CLOSING_TEXT_PREFIX, "")
						.strip();
				LocalDate dateClosing = DateFormattingUtils
						.EEEEddMMMMyyyyToLocalDate(dateClosingString);
				
				// Create an opportunity object and add it to the set
				Opportunity opportunity = new Opportunity(uri, uri, 
						getFramework(), title, buyer, summary, 
						datePublished, dateClosing);
				opportunities.add(opportunity);
				
			}
			
		} catch (Exception e) {
			LOGGER.error("Unable to parse the DOS Opportunities URL", e);
		}
		
		return opportunities;
		
	}

}

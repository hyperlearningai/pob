package ai.hyperlearning.pob.parsers.cf;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
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
 * Custom Opportunity Parser - Contracts Finder Service
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public class CfLatestOpportunitiesParser extends OpportunityParser {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(
			CfLatestOpportunitiesParser.class);
	private static final String FORM_TOKEN_CSS_QUERY = "input[name=form_token]";
	private static final String SEARCH_RESULTS_CSS_QUERY = 
			"div.search-result";
	private static final String GENERAL_CSS_QUERY = 
			"div.search-result-entry";
	private static final String BUYER_CSS_QUERY = 
			"div.search-result-sub-header";
	private static final String SUMMARY_CSS_QUERY = 
			"div.wrap-text";
	private static final String DATE_PUBLISHED_TEXT_PREFIX = 
			"Publication date";
	private static final String DATE_CLOSING_TEXT_PREFIX = 
			"Closing";
	
	public CfLatestOpportunitiesParser(Framework framework) {
		super(framework);
	}

	@Override
	public Set<Opportunity> parse() {
		
		Set<Opportunity> opportunities = new LinkedHashSet<Opportunity>();
		try {
			
			// Get the form token and cookies from the search form page
			Connection.Response formPageResponse = Jsoup.connect(
					getFramework().getOpportunitiesUrl())
					.method(Method.GET)
					.execute();
			Document formPageDocument = formPageResponse.parse();
			Map<String, String> formPageCookies = formPageResponse.cookies();
			String formPageToken = formPageDocument
					.select(FORM_TOKEN_CSS_QUERY)
					.first()
					.attr("value");
			LOGGER.debug("Parsed form token {}", formPageToken);
			
			// Submit a POST request with the relevant form 
			// data, form token, cookies and keyword filtering
			String keywords = null;
			if (getFramework().isFilter() && 
					!getFramework().getKeywords().isBlank())
				keywords = getFramework().getKeywords();
			else
				keywords = "";
			Connection.Response formPostResponse = Jsoup.connect(
					getFramework().getOpportunitiesUrl())
					.header("Content-Type", 
							"application/x-www-form-urlencoded;charset=UTF-8")
					.data("keywords", keywords)
					.data("location", "all_locations")
					.data("postcode", "")
					.data("postcode_distance", "5")
					.data("open", "1")
					.data("tender", "1")
					.data("planning", "1")
					.data("speculative", "1")
					.data("public_notice", "1")
					.data("supplychain_notice", "1")
					.data("published_from", DateFormattingUtils
							.getCurrentDateString("dd/MM/yyyy"))
					.data("sort", "notices.cf_published_date:DESC")
					.data("form_token", formPageToken)
					.data("adv_search", "")
					.cookies(formPageCookies)
					.method(Method.POST)
					.execute();
			
			// Parse the search results
			Document formPostResponseDocument = formPostResponse.parse();
			Elements searchResults = formPostResponseDocument.select(
					SEARCH_RESULTS_CSS_QUERY);
			for (Element searchResult : searchResults) {
				
				// Parse URI (normally the opportunity URL if applicable)
				String uri = searchResult.selectFirst("h2")
						.selectFirst("a").attr("href");
				
				// Parse title
				String title = searchResult.selectFirst("h2")
						.selectFirst("a").text();
				
				// Parse buyer
				String buyer = searchResult.selectFirst(BUYER_CSS_QUERY).text();
				
				// Parse summary
				String summary = searchResult.select(SUMMARY_CSS_QUERY)
						.get(1).text();
				
				// Parse search result entries for additional metadata
				String datePublishedString = null;
				String dateClosingString = null;
				Elements searchResultEntries = searchResult
						.select(GENERAL_CSS_QUERY);
				for (Element searchResultEntry : searchResultEntries) {
					String text = searchResultEntry.text();
					if ( text.contains(DATE_PUBLISHED_TEXT_PREFIX) )
						datePublishedString = text.replace(
								DATE_PUBLISHED_TEXT_PREFIX, "").strip();
					else if ( text.contains(DATE_CLOSING_TEXT_PREFIX) )
						dateClosingString = text.replace(
								DATE_CLOSING_TEXT_PREFIX, "").strip();
				}
				
				// Format date published
				LocalDate datePublished = datePublishedString != null ? 
						DateFormattingUtils.ddMMMMyyyyToLocalDate(
								datePublishedString) : null;
				
				// Format date closing
				LocalDate dateClosing = dateClosingString != null ? 
						DateFormattingUtils.ddMMMMyyyyToLocalDate(
								dateClosingString.split(",")[0]) : null;
				
				// Create an opportunity object and add it to the set
				Opportunity opportunity = new Opportunity(uri, uri, 
						getFramework(), title, buyer, summary, 
						datePublished, dateClosing);
				opportunities.add(opportunity);
				
			}
			
			
		} catch (Exception e) {
			LOGGER.error("Unable to parse the Contracts Finder "
					+ "Opportunities URL", e);
		}
		
		return opportunities;
		
	}

}

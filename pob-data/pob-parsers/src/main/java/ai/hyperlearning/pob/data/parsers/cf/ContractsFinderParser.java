package ai.hyperlearning.pob.data.parsers.cf;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import ai.hyperlearning.pob.core.utils.DateFormatUtils;
import ai.hyperlearning.pob.data.parsers.FrameworkParser;
import ai.hyperlearning.pob.data.parsers.exceptions.FrameworkParsingException;
import ai.hyperlearning.pob.model.Framework;
import ai.hyperlearning.pob.model.Opportunity;

/**
 * Framework Parser - Contracts Finder
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public class ContractsFinderParser extends FrameworkParser {
    
    private static final Logger LOGGER = 
            LoggerFactory.getLogger(ContractsFinderParser.class);
    
    // Parser properties
    private static final String OPPORTUNITIES_URL_PROPERTY_KEY = "opportunitiesUrl";
    
    // Contracts Finder web elements
    private static final String FORM_TOKEN_CSS_QUERY = "input[name=form_token]";
    private static final String SEARCH_RESULTS_CSS_QUERY = "div.search-result";
    private static final String GENERAL_CSS_QUERY = "div.search-result-entry";
    private static final String BUYER_CSS_QUERY = "div.search-result-sub-header";
    private static final String SUMMARY_CSS_QUERY = "div.wrap-text";
    private static final String DATE_PUBLISHED_TEXT_PREFIX = "Publication date";
    private static final String DATE_CLOSING_TEXT_PREFIX = "Closing";
    
    // Contracts Finder form data
    private static final String FORM_DATA_LOCATIONS = "all_locations";
    private static final String FORM_DATA_POSTCODE = "";
    private static final String FORM_DATA_POSTCODE_DISTANCE = "5";
    private static final String FORM_DATA_OPEN = "1";
    private static final String FORM_DATA_TENDER = "1";
    private static final String FORM_DATA_PLANNING = "1";
    private static final String FORM_DATA_SPECULATIVE = "1";
    private static final String FORM_DATA_PUBLIC_NOTICE = "1";
    private static final String FORM_DATA_SUPPLY_CHAIN_NOTICE = "1";
    private static final String FORM_DATA_SORT = "notices.cf_published_date:DESC";
    private static final String FORM_DATA_ADV_SEARCH = "";
    
    public ContractsFinderParser(Framework framework) {
        super(framework);
    }
    
    @Override
    public Set<Opportunity> parse() throws FrameworkParsingException {
        
        LOGGER.info("Started parsing the Contracts Finder framework.");
        Set<Opportunity> opportunities = new LinkedHashSet<>();
        
        try {
            
            // Get the form token and cookies from the search form page
            Connection.Response formPageResponse = Jsoup.connect(
                    (String) getFramework().getProperties()
                        .get(OPPORTUNITIES_URL_PROPERTY_KEY))
                    .method(Method.GET)
                    .execute();
            Document formPageDocument = formPageResponse.parse();
            Map<String, String> formPageCookies = formPageResponse.cookies();
            String formPageToken = formPageDocument
                    .select(FORM_TOKEN_CSS_QUERY)
                    .first()
                    .attr("value");
            LOGGER.debug("Extracted form token: {}", formPageToken);
            
            // Get the keyword filters if applicable
            String keywords = "";
            if (getFramework().isFilter() && 
                    !getFramework().getKeywords().isBlank())
                keywords = getFramework().getKeywords();
            
            // Submit a POST request with the relevant 
            // form data, form token, cookies and keyword filtering
            Connection.Response formPostResponse = Jsoup.connect(
                    (String) getFramework().getProperties()
                        .get(OPPORTUNITIES_URL_PROPERTY_KEY))
                    .header("Content-Type", 
                            MediaType.APPLICATION_FORM_URLENCODED_VALUE 
                                + ";charset=UTF-8")
                    .data("keywords", keywords)
                    .data("location", FORM_DATA_LOCATIONS)
                    .data("postcode", FORM_DATA_POSTCODE)
                    .data("postcode_distance", FORM_DATA_POSTCODE_DISTANCE)
                    .data("open", FORM_DATA_OPEN)
                    .data("tender", FORM_DATA_TENDER)
                    .data("planning", FORM_DATA_PLANNING)
                    .data("speculative", FORM_DATA_SPECULATIVE)
                    .data("public_notice", FORM_DATA_PUBLIC_NOTICE)
                    .data("supplychain_notice", FORM_DATA_SUPPLY_CHAIN_NOTICE)
                    .data("published_from", 
                            DateFormatUtils.getCurrentDateString("dd/MM/yyyy"))
                    .data("sort", FORM_DATA_SORT)
                    .data("form_token", formPageToken)
                    .data("adv_search", FORM_DATA_ADV_SEARCH)
                    .cookies(formPageCookies)
                    .method(Method.POST)
                    .execute();
            
            // Parse the search results
            Document formPostResponseDocument = formPostResponse.parse();
            Elements searchResults = formPostResponseDocument.select(
                    SEARCH_RESULTS_CSS_QUERY);
            for (Element searchResult : searchResults) {
                
                // Parse the URI (normally the opportunity URL if applicable)
                String uri = searchResult.selectFirst("h2")
                        .selectFirst("a").attr("href");
                
                // Parse the title
                String title = searchResult.selectFirst("h2")
                        .selectFirst("a").text();
                
                // Parse the buyer
                String buyer = searchResult.selectFirst(BUYER_CSS_QUERY).text();
                
                // Parse the summary
                String summary = searchResult.select(SUMMARY_CSS_QUERY)
                        .get(1).text();
                
                // Parse the search result entries for additional metadata
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
                
                // Format the date published
                LocalDate datePublished = datePublishedString != null ? 
                        DateFormatUtils.ddMMMMyyyyToLocalDate(
                                datePublishedString) : null;
                
                // Format the date closing
                LocalDate dateClosing = dateClosingString != null ? 
                        DateFormatUtils.ddMMMMyyyyToLocalDate(
                                dateClosingString.split(",")[0]) : null;
                
                // Create an opportunity object and add it to the set
                Opportunity opportunity = new Opportunity(uri, uri, 
                        getFramework(), title, buyer, summary, 
                        datePublished, dateClosing);
                opportunities.add(opportunity);
                
            }
            
            // Logging
            LOGGER.info("Finished parsing the Contracts Finder framework.");
            LOGGER.info("Number of opportunities parsed: {}", 
                    opportunities.size());
            return opportunities;
            
        } catch (Exception e) {
            String message = "An error was encountered whilst attempting to "
                    + "parse the Contracts Finder framework.";
            throw new FrameworkParsingException(message, e.getMessage());
        }
        
    }

}

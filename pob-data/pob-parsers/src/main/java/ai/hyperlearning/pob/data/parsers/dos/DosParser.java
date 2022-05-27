package ai.hyperlearning.pob.data.parsers.dos;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.hyperlearning.pob.core.utils.DateFormatUtils;
import ai.hyperlearning.pob.core.utils.HttpUtils;
import ai.hyperlearning.pob.data.parsers.FrameworkParser;
import ai.hyperlearning.pob.data.parsers.exceptions.FrameworkParsingException;
import ai.hyperlearning.pob.model.Framework;
import ai.hyperlearning.pob.model.Opportunity;

/**
 * Framework Parser - DOS Framework
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public class DosParser extends FrameworkParser {
    
    private static final Logger LOGGER = 
            LoggerFactory.getLogger(DosParser.class);
    
    // Parser properties
    private static final String OPPORTUNITIES_URL_PROPERTY_KEY = "opportunitiesUrl";
    
    // DOS framework web elements
    private static final String SEARCH_RESULTS_CSS_QUERY = "li.app-search-result";
    private static final String GENERAL_CSS_QUERY = "ul.govuk-list";
    private static final String TITLE_CSS_QUERY = "a.govuk-link";
    private static final String SUMMARY_CSS_QUERY = "p.govuk-body";
    private static final String BUYER_TEXT_PREFIX = "Organisation: ";
    private static final String DATE_PUBLISHED_TEXT_PREFIX = "Published: ";
    private static final String DATE_CLOSING_TEXT_PREFIX = "Closing: ";
    
    public DosParser(Framework framework) {
        super(framework);
    }
    
    @Override
    public Set<Opportunity> parse() throws FrameworkParsingException {
        
        LOGGER.info("Started parsing the DOS framework.");
        Set<Opportunity> opportunities = new LinkedHashSet<>();
        
        try {
            
            // Get the HTML contents of the DOS framework URL
            String frameworkUrl = generateUrl();
            LOGGER.debug("Parsing the DOS framework at URL: {}", frameworkUrl);
            Document document = Jsoup.connect(frameworkUrl).get();
            
            // Parse and iterate the framework opportunity search results
            Elements searchResults = document.select(SEARCH_RESULTS_CSS_QUERY);
            for (Element searchResult : searchResults) {
                
                // Parse the URI (normally the opportunity URL if applicable)
                String uri = getFramework().getBaseUrl() + searchResult
                        .selectFirst(TITLE_CSS_QUERY).attr("href");
                
                // Parse the title
                String title = searchResult.selectFirst(
                        TITLE_CSS_QUERY).text();
                
                // Parse the buyer
                String buyer = searchResult.selectFirst(GENERAL_CSS_QUERY)
                        .selectFirst("li").text()
                        .replace(BUYER_TEXT_PREFIX, "")
                        .strip();
                
                // Parse the summary
                String summary = searchResult.selectFirst(SUMMARY_CSS_QUERY)
                        .text();
                
                // Parse the date published
                String datePublishedString = searchResult
                        .select(GENERAL_CSS_QUERY)
                        .get(2).selectFirst("li").text()
                        .replace(DATE_PUBLISHED_TEXT_PREFIX, "")
                        .strip();
                LocalDate datePublished = DateFormatUtils
                        .EEEEddMMMMyyyyToLocalDate(datePublishedString);
                
                // Parse the date closing
                String dateClosingString = searchResult
                        .select(GENERAL_CSS_QUERY)
                        .get(2).select("li").get(2).text()
                        .replace(DATE_CLOSING_TEXT_PREFIX, "")
                        .strip();
                LocalDate dateClosing = DateFormatUtils
                        .EEEEddMMMMyyyyToLocalDate(dateClosingString);
                
                // Create an opportunity object and add it to the set
                Opportunity opportunity = new Opportunity(uri, uri, 
                        getFramework(), title, buyer, summary, 
                        datePublished, dateClosing);
                opportunities.add(opportunity);
                
            }
            
            // Logging and return
            LOGGER.info("Finished parsing the DOS framework.");
            LOGGER.info("Number of opportunities parsed: {}", 
                    opportunities.size());
            return opportunities;
            
        } catch (Exception e) {
            String message = "An error was encountered whilst attempting to "
                    + "parse the DOS framework.";
            throw new FrameworkParsingException(message, e.getMessage());
        }
        
    }
    
    /**
     * Generate the HTTP GET request URL with any required keyword filtering
     * @return
     */
    
    private String generateUrl() {
        
        String frameworkUrl = (String) getFramework().getProperties()
                .get(OPPORTUNITIES_URL_PROPERTY_KEY);
        if ( getFramework().isFilter() && 
                !getFramework().getKeywords().isBlank() ) {
            LOGGER.debug("DOS framework filtering enabled.");
            LOGGER.debug("Constructing the framework URL.");
            String keywords = getFramework().getKeywords();
            String keywordsQueryParams = 
                    HttpUtils.toGetQueryParams(keywords);
            frameworkUrl = ((String) getFramework().getProperties()
                    .get(OPPORTUNITIES_URL_PROPERTY_KEY))
                    .replace("?q=&", 
                            "?q=" + keywordsQueryParams + "&");
        }
        return frameworkUrl;
        
    }

}

package ai.hyperlearning.pob.data.publishers.microsoft;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.hyperlearning.pob.core.utils.JsonUtils;
import ai.hyperlearning.pob.data.publishers.CommonPublisherProperties;
import ai.hyperlearning.pob.data.publishers.OpportunityPublisher;
import ai.hyperlearning.pob.data.publishers.exceptions.OpportunityPublishingException;
import ai.hyperlearning.pob.model.Opportunity;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Microsoft Teams Publisher
 *
 * @author jillurquddus
 * @since 2.0.0
 */

public class MicrosoftTeamsPublisher extends OpportunityPublisher {
    
    private static final Logger LOGGER = 
            LoggerFactory.getLogger(MicrosoftTeamsPublisher.class);
 
    // Webhook properties
    private static final String WEBHOOK_PROPERTY_KEY = "webhook";
    
    // Request client
    private OkHttpClient client;
    
    // Request properties
    private static final String MEDIA_TYPE = 
            org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
    
    // Message content and formatting
    private static final String REQUEST_BODY_JSON_TEMPLATE = 
            "{"
            + "    \"@type\": \"MessageCard\","
            + "    \"@context\": \"http://schema.org/extensions\","
            + "    \"themeColor\": \"0076D7\","
            + "    \"summary\": \"" + CommonPublisherProperties.JSON_PLACEHOLDER_OPPORTUNITY_TITLE + "\","
            + "    \"sections\": [{"
            + "        \"activityTitle\": \"" + CommonPublisherProperties.JSON_PLACEHOLDER_OPPORTUNITY_TITLE + "\","
            + "        \"activitySubtitle\": \"New Opportunity\","
            + "        \"activityImage\": \"" + CommonPublisherProperties.POB_ICON + "\","
            + "        \"facts\": [{"
            + "            \"name\": \"Buyer\","
            + "            \"value\": \"" + CommonPublisherProperties.JSON_PLACEHOLDER_OPPORTUNITY_BUYER + "\""
            + "        }, {"
            + "            \"name\": \"Framework\","
            + "            \"value\": \"" + CommonPublisherProperties.JSON_PLACEHOLDER_OPPORTUNITY_FRAMEWORK_NAME + "\""
            + "        }, {"
            + "            \"name\": \"Date Published\","
            + "            \"value\": \"" + CommonPublisherProperties.JSON_PLACEHOLDER_OPPORTUNITY_DATE_PUBLISHED + "\""
            + "        }, {"
            + "            \"name\": \"Date Closing\","
            + "            \"value\": \"" + CommonPublisherProperties.JSON_PLACEHOLDER_OPPORTUNITY_DATE_CLOSING + "\""
            + "        }, {"
            + "            \"name\": \"Summary\","
            + "            \"value\": \"" + CommonPublisherProperties.JSON_PLACEHOLDER_OPPORTUNITY_SUMMARY + "\""
            + "        }],"
            + "        \"markdown\": true"
            + "    }],"
            + "    \"potentialAction\": [{"
            + "        \"@type\": \"OpenUri\","
            + "        \"name\": \"Learn More\","
            + "        \"targets\": [{"
            + "            \"os\": \"default\","
            + "            \"uri\": \"" + CommonPublisherProperties.JSON_PLACEHOLDER_OPPORTUNITY_URL + "\""
            + "        }]"
            + "    }]"
            + "}";
    
    public MicrosoftTeamsPublisher(Map<String, Object> properties) {
        super(properties);
        client = new OkHttpClient();
    }
    
    @Override
    public void publish(Opportunity opportunity) {
        
        LOGGER.info("Started the Microsoft Teams publisher.");
        Response response = null;
        try {
            
            // Get the Microsoft Teams publisher properties
            String webhook = (String) getProperties().get(WEBHOOK_PROPERTY_KEY);
            
            // Create the Microsoft Teams message as a JSON object
            String json = buildMessage(opportunity);
            
            // Create the HTTP POST request
            if (client == null)
                client = new OkHttpClient();
            RequestBody body = RequestBody.create(
                    json, MediaType.parse(MEDIA_TYPE));
            Request request = new Request.Builder()
                      .url(webhook)
                      .post(body)
                      .build();
            
            // Submit the POST request
            LOGGER.debug("POSTing opportunity to Microsoft Teams: \n{}", json);
            Call call = client.newCall(request);
            response = call.execute();
            
        } catch (Exception e) {
            
            String message = "An error was encountered whilst attempting to "
                    + "publish the opportunity to Microsoft Teams.";
            throw new OpportunityPublishingException(message, e.getMessage());
        
        } finally {
            
            // Close the response object to avoid connection leaks
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    LOGGER.warn("An error was encountered whilst attempting to "
                            + "close the OkHttpClient response object.", e);
                }
            }
            
        }
        
    }
    
    /**
     * Create the Microsoft Teams message JSON object
     * @param opportunity
     * @return
     */
    
    private String buildMessage(Opportunity opportunity) {
        
        return REQUEST_BODY_JSON_TEMPLATE
                .replace(CommonPublisherProperties
                            .JSON_PLACEHOLDER_OPPORTUNITY_TITLE, 
                        JsonUtils.cleanValueString(
                                opportunity.getTitle()))
                .replace(CommonPublisherProperties.
                            JSON_PLACEHOLDER_OPPORTUNITY_BUYER, 
                        JsonUtils.cleanValueString(
                                opportunity.getBuyer()))
                .replace(CommonPublisherProperties.
                            JSON_PLACEHOLDER_OPPORTUNITY_FRAMEWORK_NAME, 
                        JsonUtils.cleanValueString(
                                opportunity.getFramework().getName()))
                .replace(CommonPublisherProperties.
                            JSON_PLACEHOLDER_OPPORTUNITY_DATE_PUBLISHED, 
                        opportunity.getDatePublished() != null ? 
                                opportunity.getDatePublished().toString() : 
                                    CommonPublisherProperties.
                                        UNKNOWN_TEXT_VALUE)
                .replace(CommonPublisherProperties.
                            JSON_PLACEHOLDER_OPPORTUNITY_DATE_CLOSING, 
                        opportunity.getDateClosing() != null ? 
                                opportunity.getDateClosing().toString() : 
                                    CommonPublisherProperties.
                                        UNKNOWN_TEXT_VALUE)
                .replace(CommonPublisherProperties.
                            JSON_PLACEHOLDER_OPPORTUNITY_SUMMARY, 
                        JsonUtils.cleanValueString(
                                opportunity.getSummary()))
                .replace(CommonPublisherProperties.
                            JSON_PLACEHOLDER_OPPORTUNITY_URL, 
                        opportunity.getUrl() != null ? 
                                opportunity.getUrl() : 
                                    CommonPublisherProperties.
                                        UNKNOWN_TEXT_VALUE);
        
    }

}

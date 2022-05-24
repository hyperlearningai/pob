package ai.hyperlearning.pob.data.publishers.slack;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.hyperlearning.pob.core.utils.JsonUtils;
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
 * Slack Publisher
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public class SlackPublisher extends OpportunityPublisher {
    
    private static final Logger LOGGER = 
            LoggerFactory.getLogger(SlackPublisher.class);
    
    // Webhook properties
    private static final String CHANNEL_PROPERTY_KEY = "channel";
    private static final String WEBHOOK_PROPERTY_KEY = "webhook";
    
    // Request properties
    private static final String MEDIA_TYPE = 
            org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
    
    // Message properties and formatting
    private static final int MESSAGE_DASH_CHARACTER_LENGTH = 100;
    private static final String UNKNOWN_TEXT_VALUE = "Unknown";
    private static final String JSON_PLACEHOLDER_SLACK_CHANNEL = 
            "[SLACK_CHANNEL]";
    private static final String JSON_PLACEHOLDER_OPPORTUNITY_TITLE = 
            "[OPPORTUNITY_TITLE]";
    private static final String JSON_PLACEHOLDER_OPPORTUNITY_BUYER = 
            "[OPPORTUNITY_BUYER]";
    private static final String JSON_PLACEHOLDER_OPPORTUNITY_FRAMEWORK_NAME = 
            "[OPPORTUNITY_FRAMEWORK_NAME]";
    private static final String JSON_PLACEHOLDER_OPPORTUNITY_DATE_PUBLISHED = 
            "[OPPORTUNITY_DATE_PUBLISHED]";
    private static final String JSON_PLACEHOLDER_OPPORTUNITY_DATE_CLOSING = 
            "[OPPORTUNITY_DATE_CLOSING]";
    private static final String JSON_PLACEHOLDER_OPPORTUNITY_SUMMARY = 
            "[OPPORTUNITY_SUMMARY]";
    private static final String JSON_PLACEHOLDER_OPPORTUNITY_URL = 
            "[OPPORTUNITY_URL]";
    private static final String POST_REQUEST_JSON_BODY_TEMPLATE = 
            "{" + 
                    "\"channel\": \"" + JSON_PLACEHOLDER_SLACK_CHANNEL + "\", " +
                    "\"text\": \"" + 
                        "-".repeat(MESSAGE_DASH_CHARACTER_LENGTH) + "\\n" + 
                        "*New Opportunity: " + JSON_PLACEHOLDER_OPPORTUNITY_TITLE + "*\\n" +
                        "-".repeat(MESSAGE_DASH_CHARACTER_LENGTH) + "\\n" + 
                        "*Buyer:* " + JSON_PLACEHOLDER_OPPORTUNITY_BUYER + "\\n" + 
                        "*Framework:* " + JSON_PLACEHOLDER_OPPORTUNITY_FRAMEWORK_NAME + "\\n" + 
                        "*Date Published:* " + JSON_PLACEHOLDER_OPPORTUNITY_DATE_PUBLISHED + "\\n" + 
                        "*Date Closing:* " + JSON_PLACEHOLDER_OPPORTUNITY_DATE_CLOSING + "\\n" + 
                        "*Summary:* " + JSON_PLACEHOLDER_OPPORTUNITY_SUMMARY + "\\n" + 
                        "*Link:* " + JSON_PLACEHOLDER_OPPORTUNITY_URL + "\\n\\n" + 
                        "↓" + 
                    "\", " + 
                    "\"unfurl_links\": true" + 
            "}";
    
    public SlackPublisher(Map<String, Object> properties) {
        super(properties);
    }
    
    public void publish(Opportunity opportunity) {
        
        LOGGER.info("Started the Slack publisher.");
        Response response = null;
        try {
            
            // Get the Slack publisher properties
            String channel = (String) getProperties().get(CHANNEL_PROPERTY_KEY);
            String webhook = (String) getProperties().get(WEBHOOK_PROPERTY_KEY);
            
            // Create the Slack message as a JSON object
            String json = buildMessage(opportunity, channel);
            
            // Create the HTTP client and POST request body
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(
                    json, MediaType.parse(MEDIA_TYPE));
            Request request = new Request.Builder()
                      .url(webhook)
                      .post(body)
                      .build();
            
            // Submit the POST request and get the Slack webhook response
            LOGGER.debug("POSTing opportunity to Slack channel: \n{}", json);
            Call call = client.newCall(request);
            response = call.execute();
            
        } catch (Exception e) {
            
            String message = "An error was encountered whilst attempting to "
                    + "publish the opportunity to Slack.";
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
     * Create the Slack message JSON object
     * @param opportunity
     * @param channel
     * @param webhook
     * @return
     */
    
    private String buildMessage(Opportunity opportunity, String channel) {
        
        return POST_REQUEST_JSON_BODY_TEMPLATE
                .replace(JSON_PLACEHOLDER_SLACK_CHANNEL, channel)
                .replace(JSON_PLACEHOLDER_OPPORTUNITY_TITLE, 
                        JsonUtils.cleanValueString(
                                opportunity.getTitle()))
                .replace(JSON_PLACEHOLDER_OPPORTUNITY_BUYER, 
                        JsonUtils.cleanValueString(
                                opportunity.getBuyer()))
                .replace(JSON_PLACEHOLDER_OPPORTUNITY_FRAMEWORK_NAME, 
                        JsonUtils.cleanValueString(
                                opportunity.getFramework().getName()))
                .replace(JSON_PLACEHOLDER_OPPORTUNITY_DATE_PUBLISHED, 
                        opportunity.getDatePublished() != null ? 
                                opportunity.getDatePublished().toString() : 
                                    UNKNOWN_TEXT_VALUE)
                .replace(JSON_PLACEHOLDER_OPPORTUNITY_DATE_CLOSING, 
                        opportunity.getDateClosing() != null ? 
                                opportunity.getDateClosing().toString() : 
                                    UNKNOWN_TEXT_VALUE)
                .replace(JSON_PLACEHOLDER_OPPORTUNITY_SUMMARY, 
                        JsonUtils.cleanValueString(
                                opportunity.getSummary()))
                .replace(JSON_PLACEHOLDER_OPPORTUNITY_URL, 
                        opportunity.getUrl() != null ? 
                                opportunity.getUrl() : UNKNOWN_TEXT_VALUE);
        
    }

}

package ai.hyperlearning.pob.data.publishers.slack;

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
    
    // Request client
    private OkHttpClient client;
    
    // Request properties
    private static final String MEDIA_TYPE = 
            org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
    
    // Message content and formatting
    private static final String JSON_PLACEHOLDER_SLACK_CHANNEL = 
            "[SLACK_CHANNEL]";
    private static final String REQUEST_BODY_JSON_TEMPLATE = 
            "{" + 
                    "\"channel\": \"" + JSON_PLACEHOLDER_SLACK_CHANNEL + "\", " +
                    "\"text\": \"" + 
                        CommonPublisherProperties.MESSAGE_CONTENT_TEMPLATE + 
                    "\", " + 
                    "\"unfurl_links\": true" + 
            "}";
    
    public SlackPublisher(Map<String, Object> properties) {
        super(properties);
        client = new OkHttpClient();
    }
    
    @Override
    public void publish(Opportunity opportunity) {
        
        LOGGER.info("Started the Slack publisher.");
        Response response = null;
        try {
            
            // Get the Slack publisher properties
            String channel = (String) getProperties().get(CHANNEL_PROPERTY_KEY);
            String webhook = (String) getProperties().get(WEBHOOK_PROPERTY_KEY);
            
            // Create the Slack message as a JSON object
            String json = buildMessage(opportunity, channel);
            
            // Create the HTTP POST request
            if (client == null)
                client = new OkHttpClient();
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
     * @return
     */
    
    private String buildMessage(Opportunity opportunity, String channel) {
        
        return REQUEST_BODY_JSON_TEMPLATE
                .replace(JSON_PLACEHOLDER_SLACK_CHANNEL, channel)
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

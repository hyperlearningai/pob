package ai.hyperlearning.pob.data.publishers.google;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * Google Chat Publisher
 *
 * @author jillurquddus
 * @since 2.0.0
 */

public class GoogleChatPublisher extends OpportunityPublisher {
    
    private static final Logger LOGGER = 
            LoggerFactory.getLogger(GoogleChatPublisher.class);
    
    // Webhook properties
    private static final String WEBHOOK_PROPERTY_KEY = "webhook";
    
    // Request client
    private OkHttpClient client;
    
    // Request properties
    private static final String MEDIA_TYPE = 
            org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
    
    // Message content and formatting
    private static final String REQUEST_BODY_JSON_TEMPLATE = 
            "{" + 
                    "\"text\": \"" + 
                        CommonPublisherProperties.MESSAGE_CONTENT_TEMPLATE + 
                    "\"" + 
            "}";
    
    public GoogleChatPublisher(Map<String, Object> properties) {
        super(properties);
        client = new OkHttpClient();
    }
    
    @Override
    public void publish(Opportunity opportunity) {
        
        LOGGER.info("Started the Google Chat publisher.");
        Response response = null;
        try {
            
            // Get the Google Chat publisher properties
            String webhook = (String) getProperties().get(WEBHOOK_PROPERTY_KEY);
            
            // Create the Google Chat message as a JSON object
            String json = buildMessage(REQUEST_BODY_JSON_TEMPLATE, opportunity);
            
            // Create the HTTP POST request
            if (client == null)
                client = new OkHttpClient();
            RequestBody body = RequestBody.create(
                    json, MediaType.parse(MEDIA_TYPE));
            Request request = new Request.Builder()
                      .url(webhook)
                      .post(body)
                      .build();
            
            // Submit the POST request and get the Google Chat webhook response
            LOGGER.debug("POSTing opportunity to Google Chat: \n{}", json);
            Call call = client.newCall(request);
            response = call.execute();
            
        } catch (Exception e) {
            
            String message = "An error was encountered whilst attempting to "
                    + "publish the opportunity to Google Chat.";
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

}

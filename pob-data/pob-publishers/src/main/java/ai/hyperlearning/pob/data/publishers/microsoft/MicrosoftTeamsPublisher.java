package ai.hyperlearning.pob.data.publishers.microsoft;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.hyperlearning.pob.data.publishers.OpportunityPublisher;
import ai.hyperlearning.pob.data.publishers.exceptions.OpportunityPublishingException;
import ai.hyperlearning.pob.model.Opportunity;
import okhttp3.OkHttpClient;
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
    
    // Request client
    private OkHttpClient client;
    
    // Request properties
    private static final String MEDIA_TYPE = 
            org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
    
    public MicrosoftTeamsPublisher(Map<String, Object> properties) {
        super(properties);
        client = new OkHttpClient();
    }
    
    @Override
    public void publish(Opportunity opportunity) {
        
        LOGGER.info("Started the Microsoft Teams publisher.");
        Response response = null;
        try {
            
            
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

}

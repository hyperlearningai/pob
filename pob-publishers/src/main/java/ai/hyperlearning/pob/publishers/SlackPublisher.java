package ai.hyperlearning.pob.publishers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class SlackPublisher {
	
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(SlackPublisher.class);

	private static final int MESSAGE_DASH_CHARACTER_LENGTH = 100;
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
	
	@SuppressWarnings("deprecation")
	public static int sendMessage(String channel, String webhook, 
			Opportunity opportunity) {
		
		// Create the message as a JSON object
		String json = POST_REQUEST_JSON_BODY_TEMPLATE
				.replace(JSON_PLACEHOLDER_SLACK_CHANNEL, channel)
				.replace(JSON_PLACEHOLDER_OPPORTUNITY_TITLE, opportunity.getTitle())
				.replace(JSON_PLACEHOLDER_OPPORTUNITY_BUYER, opportunity.getBuyer())
				.replace(JSON_PLACEHOLDER_OPPORTUNITY_FRAMEWORK_NAME, opportunity.getFramework().getName())
				.replace(JSON_PLACEHOLDER_OPPORTUNITY_DATE_PUBLISHED, opportunity.getDatePublished().toString())
				.replace(JSON_PLACEHOLDER_OPPORTUNITY_DATE_CLOSING, opportunity.getDateClosing().toString())
				.replace(JSON_PLACEHOLDER_OPPORTUNITY_SUMMARY, opportunity.getSummary().replace("\"", "'").strip())
				.replace(JSON_PLACEHOLDER_OPPORTUNITY_URL, opportunity.getUrl());
		
		// Create the HTTP client and POST request object
		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(
			      MediaType.parse("application/json"), json);
		Request request = new Request.Builder()
			      .url(webhook)
			      .post(body)
			      .build();
		
		// Submit the POST request and get the Slack webhook response
		try {
			
			LOGGER.debug("POSTing opportunity to Slack channel: \n{}", json);
			Call call = client.newCall(request);
			Response response = call.execute();
			return response.code();
			
		} catch (IOException e) {
			LOGGER.error("Error encountered when publishing to Slack", e);
			return 404;
		}
		
	}
	
}
package ai.hyperlearning.pob.publishers;

import java.io.IOException;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;

import ai.hyperlearning.pob.model.Opportunity;


/**
 * Slack Publisher
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public class SlackPublisher {

	@SuppressWarnings("deprecation")
	public static int sendMessage(String channel, String username, 
			String emoji, String webhook, Opportunity opportunity) {
		
		// Create the message
		String message = "<b>Title: </b>" + opportunity.getTitle() + "\n"
				+ "<b>Buyer: </b>" + opportunity.getBuyer() + "\n"
				+ "<b>Date Published: </b>" + opportunity.getDatePublished().toString() + "\n"
				+ "<b>Date Closing: </b>" + opportunity.getDateClosing().toString() + "\n"
				+ "<b>Summary: </b>" + opportunity.getSummary() + "\n"
				+ "<b>Link: </b>" + opportunity.getUrl() + "\n";
		
		// Create the payloads to POST
		Payload payload = Payload.builder()
				.channel(channel)
				.username(username)
				.iconEmoji(emoji)
				.text(message)
				.build();
		
		// Send the message and get the webhook response
		try {
			
			WebhookResponse webHookResponse = Slack.getInstance().send(
					webhook, payload);
			return webHookResponse.getCode();
			
		} catch (IOException e) {
			return 404;
		}
		
	}
	
}

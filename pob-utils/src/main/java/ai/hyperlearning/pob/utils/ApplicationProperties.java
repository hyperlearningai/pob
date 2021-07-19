package ai.hyperlearning.pob.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import ai.hyperlearning.pob.model.Framework;

/**
 * POB Application Properties
 *
 * @author jillurquddus
 * @since 0.0.1
 */

@Component
@PropertySource(value = "classpath:pob.yaml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "application")
@Validated
public class ApplicationProperties {
	
	@Value("${application.publishers.slack.channel}")
	private String slackChannel;
	
	@Value("${application.publishers.slack.username}")
	private String slackUsername;
	
	@Value("${application.publishers.slack.emoji}")
	private String slackEmoji;
	
	@Value("${application.publishers.slack.webhook}")
	private String slackWebhook;
	
	private List<Framework> frameworks;
	
	public String getSlackChannel() {
		return slackChannel;
	}

	public void setSlackChannel(String slackChannel) {
		this.slackChannel = slackChannel;
	}

	public String getSlackUsername() {
		return slackUsername;
	}

	public void setSlackUsername(String slackUsername) {
		this.slackUsername = slackUsername;
	}

	public String getSlackEmoji() {
		return slackEmoji;
	}

	public void setSlackEmoji(String slackEmoji) {
		this.slackEmoji = slackEmoji;
	}

	public String getSlackWebhook() {
		return slackWebhook;
	}

	public void setSlackWebhook(String slackWebhook) {
		this.slackWebhook = slackWebhook;
	}

	public List<Framework> getFrameworks() {
		return frameworks;
	}

	public void setFrameworks(List<Framework> frameworks) {
		this.frameworks = frameworks;
	}

}

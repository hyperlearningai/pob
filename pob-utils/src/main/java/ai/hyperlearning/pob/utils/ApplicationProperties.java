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
	
	@Value("${application.publishers.slack.enabled}")
	private boolean slackEnabled;
	
	@Value("${application.publishers.slack.channel}")
	private String slackChannel;
	
	@Value("${application.publishers.slack.webhook}")
	private String slackWebhook;
	
	private List<Framework> frameworks;
	
	public boolean isSlackEnabled() {
		return slackEnabled;
	}

	public void setSlackEnabled(boolean slackEnabled) {
		this.slackEnabled = slackEnabled;
	}

	public String getSlackChannel() {
		return slackChannel;
	}

	public void setSlackChannel(String slackChannel) {
		this.slackChannel = slackChannel;
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

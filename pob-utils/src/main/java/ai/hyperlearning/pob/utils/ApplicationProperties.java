package ai.hyperlearning.pob.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import ai.hyperlearning.pob.model.Framework;
import ai.hyperlearning.pob.model.Publisher;

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
	
	@Value("${application.server.port}")
	private int serverPort;
	
	private List<Framework> frameworks;
	private List<Publisher> publishers;
	
	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public List<Framework> getFrameworks() {
		return frameworks;
	}

	public void setFrameworks(List<Framework> frameworks) {
		this.frameworks = frameworks;
	}

	public List<Publisher> getPublishers() {
		return publishers;
	}

	public void setPublishers(List<Publisher> publishers) {
		this.publishers = publishers;
	}

}

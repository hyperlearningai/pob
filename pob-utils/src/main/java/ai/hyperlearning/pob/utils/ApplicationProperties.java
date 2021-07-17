package ai.hyperlearning.pob.utils;

import java.util.List;
import java.util.Map;

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
	
	private List<Map<String, Object>> properties;
	private List<Framework> frameworks;
	
	public List<Map<String, Object>> getProperties() {
		return properties;
	}

	public void setProperties(List<Map<String, Object>> properties) {
		this.properties = properties;
	}

	public List<Framework> getFrameworks() {
		return frameworks;
	}

	public void setFrameworks(List<Framework> frameworks) {
		this.frameworks = frameworks;
	}

}

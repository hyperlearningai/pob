package ai.hyperlearning.pob.apps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

import ai.hyperlearning.pob.utils.ApplicationProperties;

/**
 * Pob Web Server Factory Configuration
 *
 * @author jillurquddus
 * @since 0.0.1
 */

@Component
public class PobWebServerFactoryConfig implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
	
	@Autowired
    private ApplicationProperties applicationProperties;

	@Override
	public void customize(ConfigurableWebServerFactory factory) {
		factory.setPort(applicationProperties.getServerPort());
	}

}

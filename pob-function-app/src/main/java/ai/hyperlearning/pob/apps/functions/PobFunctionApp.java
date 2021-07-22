package ai.hyperlearning.pob.apps.functions;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import ai.hyperlearning.pob.utils.ApplicationProperties;

/**
 * Pob Cloud Function Spring Boot Application
 *
 * @author jillurquddus
 * @since 0.0.1
 */

@ComponentScan(basePackages = "ai.hyperlearning.pob")
@EnableConfigurationProperties(value = ApplicationProperties.class)
@SpringBootApplication
public class PobFunctionApp {
	
	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(PobFunctionApp.class)
			.properties("spring.config.name:pob")
			.build()
			.run(args);
    }

}

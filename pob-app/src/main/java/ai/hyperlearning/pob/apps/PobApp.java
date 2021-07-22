package ai.hyperlearning.pob.apps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import ai.hyperlearning.pob.utils.ApplicationProperties;

/**
 * Pob Spring Boot Application
 *
 * @author jillurquddus
 * @since 0.0.1
 */

@ComponentScan(basePackages = "ai.hyperlearning.pob")
@EnableConfigurationProperties(value = ApplicationProperties.class)
@SpringBootApplication
public class PobApp implements CommandLineRunner {
	
	@Autowired
	private Pob pob;
	
	/**
	 * Spring Boot Application Builder
	 * @param args
	 * @throws Exception
	 */
	
	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(PobApp.class)
			.properties("spring.config.name:pob")
			.build()
			.run(args);
	}
	
	public void run(String... args) {
		
		// Run parsers for all registered frameworks
		pob.runParsers();
		
		// Run all registered publishers
		pob.runPublishers();
		
	}

}

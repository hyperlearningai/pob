package ai.hyperlearning.pob.apps;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import ai.hyperlearning.pob.jpa.repositories.FrameworkRepository;
import ai.hyperlearning.pob.jpa.repositories.OpportunityRepository;
import ai.hyperlearning.pob.model.Framework;
import ai.hyperlearning.pob.model.Opportunity;
import ai.hyperlearning.pob.publishers.SlackPublisher;
import ai.hyperlearning.pob.utils.ApplicationProperties;

/**
 * Parser Pipeline Spring Boot Application
 *
 * @author jillurquddus
 * @since 0.0.1
 */

@ComponentScan(basePackages = "ai.hyperlearning.pob")
@EnableConfigurationProperties(value = ApplicationProperties.class)
@SpringBootApplication
public class PobApp implements CommandLineRunner {
	
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(PobApp.class);
	
	@Autowired
    private ApplicationProperties applicationProperties;
	
	@Autowired
	private FrameworkRepository frameworkRepository;
	
	@Autowired
	private OpportunityRepository opportunityRepository;
	
	private Set<Opportunity> newOpportunities;
	private Set<Opportunity> unpublishedNewOpportunities;
	private Set<Opportunity> unindexedNewOpportunities;
	private List<Framework> frameworks;
	
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
		
		LOGGER.info("Starting POB");
		runParserPipeline();
		runSlackPublisher();
		LOGGER.info("Closing POB.");
		System.exit(0);
		
	}
	
	@SuppressWarnings("unchecked")
	private void runParserPipeline() {
		
		LOGGER.info("Starting the POB parser pipeline app.");
		try {
			
			// Parse, persist and iterate through all frameworks from pob.yaml
			frameworks = applicationProperties.getFrameworks();
			frameworkRepository.saveAll(frameworks);
			for (Framework framework : frameworks) {
				
				// Execute the relevant parser class
				Class<?> parserClass = Class.forName(
						framework.getParserFullyQualifiedClassName());
				Object parserInstance = parserClass.getDeclaredConstructor(
						Framework.class).newInstance(framework);
				Method parserMethod = parserClass.getDeclaredMethod("parse");
				newOpportunities = (Set<Opportunity>) 
						parserMethod.invoke(parserInstance);
				
				// Persist the parsed opportunities to storage
				opportunityRepository.saveAll(newOpportunities);
				
				// TO DO - Confirm whether saveAll() automatically deals with 
				// constraint violations. If not, then use the following code
				// for (Opportunity newOpportunity : newOpportunities) {
				// 	if (opportunityRepository.findByUriAndFrameworkId(
				// 			newOpportunity.getUri(), 
				// 			framework.getId()).isEmpty()) {
				// 		opportunityRepository.save(newOpportunity);
				// 	}
				// }
				
			}
			
		} catch (Exception e) {
			LOGGER.error("Error encountered when running the POB "
					+ "parser pipeline app", e);
		} finally {
			LOGGER.info("Closing the POB parser pipeline app.");
		}
		
	}
	
	private void runSlackPublisher() {
		
		LOGGER.info("Starting the POB Slack publisher app.");
		int slackWebhookResponseCode = SlackPublisher.sendMessage(
				applicationProperties.getSlackChannel(), 
				applicationProperties.getSlackUsername(), 
				applicationProperties.getSlackEmoji(), 
				applicationProperties.getSlackWebhook(), 
				newOpportunities.iterator().next());
		System.out.println(slackWebhookResponseCode);
		LOGGER.info("Closing the POB Slack publisher app.");
		
	}

}

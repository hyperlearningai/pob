package ai.hyperlearning.pob.services;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.hyperlearning.pob.jpa.repositories.FrameworkRepository;
import ai.hyperlearning.pob.jpa.repositories.OpportunityRepository;
import ai.hyperlearning.pob.model.Framework;
import ai.hyperlearning.pob.model.Opportunity;
import ai.hyperlearning.pob.model.Publisher;
import ai.hyperlearning.pob.utils.ApplicationProperties;

/**
 * Pob Service
 *
 * @author jillurquddus
 * @since 0.0.1
 */

@Service
public class Pob {
	
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(Pob.class);
	
	@Autowired
    private ApplicationProperties applicationProperties;
	
	@Autowired
	private FrameworkRepository frameworkRepository;
	
	@Autowired
	private OpportunityRepository opportunityRepository;
	
	public Pob() {
		LOGGER.info("Starting POB");
	}
	
	@PostConstruct
	public void logRegisteredFrameworks() {
		LOGGER.debug("Registered the following frameworks:\n{}", 
				applicationProperties.getFrameworks());
	}
	
	@PostConstruct
	public void logRegisteredPublishers() {
		LOGGER.debug("Registered the following publishers:\n{}", 
				applicationProperties.getPublishers());
	}
	
	/**
	 * Run parsers for all registered frameworks 
	 * and persist new opportunities to storage
	 */
	
	@SuppressWarnings("unchecked")
	public void runParsers() {
		
		LOGGER.info("Running parsers for all registered frameworks.");
		try {
			
			// Parse all registered frameworks
			List<Framework> frameworks = applicationProperties.getFrameworks();
			long enabledFrameworksCount = frameworks.stream()
					.filter(f -> f.isEnabled())
					.count();
			if (!frameworks.isEmpty() && enabledFrameworksCount > 0) {
				
				// Persist all registered frameworks to storage
				frameworkRepository.saveAll(frameworks);
				
				// Iterate through all registered frameworks
				for (Framework framework : frameworks) {
					if (framework.isEnabled()) {
						
						// Execute the parse method of the 
						// relevant parser class
						Class<?> parserClass = Class.forName(
								framework.getParserFullyQualifiedClassName());
						Object parserInstance = parserClass
								.getDeclaredConstructor(Framework.class)
								.newInstance(framework);
						Method parserMethod = parserClass
								.getDeclaredMethod("parse");
						Set<Opportunity> newOpportunities = (Set<Opportunity>) 
								parserMethod.invoke(parserInstance);
						LOGGER.debug("Parsed {} opportunities from the "
								+ "'{}' framework", newOpportunities.size(), 
								framework.getName());
						
						// Persist the parsed opportunities to storage
						if (!newOpportunities.isEmpty())
							opportunityRepository.saveAll(newOpportunities);
						
						// TO DO - Confirm whether saveAll() automatically 
						// deals with constraint violations. If not, then 
						// use the following code
						// for (Opportunity newOpportunity : newOpportunities) {
						// 	if (opportunityRepository.findByUriAndFrameworkId(
						// 			newOpportunity.getUri(), 
						// 			framework.getId()).isEmpty()) {
						// 		opportunityRepository.save(newOpportunity);
						// 	}
						// }
						
					}
					
				}
				
			}
			
		} catch (Exception e) {
			LOGGER.error("Error encountered when running parsers "
					+ "for all registered frameworks", e);
		} finally {
			LOGGER.info("Finished running parsers "
					+ "for all registered frameworks.");
		}
		
	}
	
	/**
	 * Publish all unpublished opportunities to the relevent
	 * publication channels
	 */
	
	public void runPublishers() {
		
		LOGGER.info("Running all registered publishers");
		try {
			
			// Identify all registered publishers
			List<Publisher> publishers = 
					applicationProperties.getPublishers();
			long enabledPublishersCount = publishers.stream()
					.filter(p -> p.isEnabled())
					.count();
			if ( !publishers.isEmpty() && enabledPublishersCount > 0 ) {
			
				// Get all unpublished opportunities and iterate through them
				List<Opportunity> unpublishedOpportunities = 
						opportunityRepository.findAllWhereNotPublished();
				LOGGER.debug("Found {} unpublished opportunities.", 
						unpublishedOpportunities.size());
				for (Opportunity unpublishedOpportunity : 
					unpublishedOpportunities) {
					
					// Iterate through all registered publishers
					int publishedCount = 0;
					for (Publisher publisher : publishers) {
						if (publisher.isEnabled()) {
							
							// Execute the publish method of the 
							// relevant publisher class
							Class<?> publisherClass = Class.forName(publisher
									.getPublisherFullyQualifiedClassName());
							Object publisherInstance = publisherClass
									.getDeclaredConstructor(Map.class)
									.newInstance(publisher.getProperties());
							Method publisherMethod = publisherClass
									.getDeclaredMethod("publish", 
											Opportunity.class);
							boolean published =  (boolean) publisherMethod
									.invoke(publisherInstance, 
											unpublishedOpportunity);
							if (published)
								publishedCount++;
							
						}
						
					}
					
					// Update the opportunity record in storage to indicate 
					// that it has been fully published
					if (publishedCount == enabledPublishersCount) {
						unpublishedOpportunity.setPublished(true);
						opportunityRepository.save(unpublishedOpportunity);
						LOGGER.debug("Fully published opportunity {}", 
								unpublishedOpportunity.getFramework().getId() + 
								"-" + unpublishedOpportunity.getUri());
					}
					
					// Pause between new publications
					TimeUnit.SECONDS.sleep(15);
					
				}
				
			}
			
		} catch (Exception e) {
			LOGGER.error("Error encountered when running all "
					+ "registered publishers", e);
		} finally {
			LOGGER.info("Finished running all registered publishers");
		}
		
	}

}

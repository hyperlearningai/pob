package ai.hyperlearning.pob.data.pipelines.main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ai.hyperlearning.pob.core.jpa.repositories.FrameworkRepository;
import ai.hyperlearning.pob.core.jpa.repositories.OpportunityRepository;
import ai.hyperlearning.pob.data.parsers.RegisteredFrameworks;
import ai.hyperlearning.pob.data.publishers.OpportunityPublisher;
import ai.hyperlearning.pob.data.publishers.RegisteredPublishers;
import ai.hyperlearning.pob.model.Framework;
import ai.hyperlearning.pob.model.Opportunity;
import ai.hyperlearning.pob.model.Publisher;

/**
 * Main POB Pipeline
 *
 * @author jillurquddus
 * @since 0.0.1
 */

@Service
public class MainPipeline {
    
    private static final Logger LOGGER = 
            LoggerFactory.getLogger(MainPipeline.class);
    
    // Delay between publications in seconds
    private static final int PUBLICATION_DELAY = 10;
    
    @Autowired
    private RegisteredFrameworks registeredFrameworks;
    
    @Autowired
    private RegisteredPublishers registeredPublishers;
    
    @Autowired
    private FrameworkRepository frameworkRepository;
    
    @Autowired
    private OpportunityRepository opportunityRepository;
    
    @Value("${pipelines.main.enabled:true}")
    private Boolean enabled;
    
    // Map to hold publisher implementation objects
    private Map<String, OpportunityPublisher> publisherImplementations;
    
    @PostConstruct
    public void logRegisteredFrameworks() {
        LOGGER.info("Registered the following frameworks:\n{}", 
                registeredFrameworks.getFrameworks());
    }
    
    @PostConstruct
    public void logRegisteredPublishers() {
        LOGGER.info("Registered the following publishers:\n{}", 
                registeredPublishers.getPublishers());
    }
    
    /**
     * Run the main pipeline
     */
    
    public void run() {
        if (Boolean.TRUE.equals(enabled)) {
            LOGGER.info("Started the POB Main Pipeline.");
            runRegisteredParsers();
            runRegisteredPublishers();
            LOGGER.info("Finished the POB Main Pipeline.");
        } else {
            LOGGER.warn("The POB Main Pipeline is not enabled. Skipping.");
        }
    }
    
    /**
     * Run parsers for all the registered frameworks and persist new 
     * opportunities to the RDBMS
     */
    
    private void runRegisteredParsers() {
        
        LOGGER.info("Running parsers for all registered frameworks.");
        try {
            
            // Get all registered and enabled frameworks
            List<Framework> frameworks = registeredFrameworks.getFrameworks();
            long enabledFrameworksCount = frameworks.stream()
                    .filter(Framework::isEnabled)
                    .count();
            LOGGER.debug("Found {} enabled frameworks.", 
                    enabledFrameworksCount);
            if (!frameworks.isEmpty() && enabledFrameworksCount > 0) {
                
                // Persist all registered frameworks to the RDBMS
                LOGGER.debug("Persisting all registered frameworks "
                        + "to the RDBMS.");
                frameworkRepository.saveAll(frameworks);
                
                // Iterate through all registered frameworks
                for (Framework framework : frameworks) {
                        
                    // Execute the parse method of the relevant parser class
                    LOGGER.debug("Parsing framework: {}", framework.getName());
                    Set<Opportunity> parsedOpportunities = 
                            executeParserMethod(framework);
                    
                    // Identify which of these parsed opportunities are new
                    if (!parsedOpportunities.isEmpty()) {
                        
                        // Get the list of parsed opportunity IDs
                        Set<String> parsedOpportunityIds = 
                                parsedOpportunities
                                    .stream()
                                    .map(Opportunity::getId)
                                    .collect(Collectors.toSet());
                        
                        // Identify whether these IDs and associated
                        // opportunities already exist in the RDBMS
                        List<Opportunity> knownOpportunities = 
                                (List<Opportunity>) opportunityRepository
                                .findAllById(parsedOpportunityIds);
                        
                        // Remove known opportunities from the parsed
                        // opportunities, leaving a set containing
                        // only new opportunities
                        parsedOpportunities.removeAll(knownOpportunities);
                        LOGGER.debug("Found {} new opportunities from the "
                                + "opportunities parsed from the {} "
                                + "framework", parsedOpportunities.size(), 
                                framework.getName());
                        
                        // Persist the parsed new opportunities to storage
                        if (!parsedOpportunities.isEmpty())
                            opportunityRepository.saveAll(
                                    parsedOpportunities);
                        
                    }
                    
                    // Logging
                    LOGGER.debug("Finished parsing framework: {}", 
                            framework.getName());
                    
                }
                
            }
            
            // Logging
            LOGGER.info("Finished running parsers for all "
                    + "registered frameworks.");
            
        } catch (Exception e) {
            LOGGER.error("An error was encountered whilst attempting "
                    + "to run the parsers for all registered frameworks.", e);
        }
        
    }
    
    /**
     * Execute the parse method of the relevant framework parser class
     * @param framework
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws ClassNotFoundException
     */
    
    @SuppressWarnings("unchecked")
    private Set<Opportunity> executeParserMethod(Framework framework) 
            throws InstantiationException, IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException, ClassNotFoundException {
        Class<?> parserClass = Class.forName(
                framework.getParserClass());
        Object parserInstance = parserClass
                .getDeclaredConstructor(Framework.class)
                .newInstance(framework);
        Method parserMethod = parserClass
                .getDeclaredMethod("parse");
        return (Set<Opportunity>) 
                parserMethod.invoke(parserInstance);
    }
    
    /**
     * Run all registered publishers in order to publish all unpublished 
     * opportunities to the relevant publication channels
     */
    
    private void runRegisteredPublishers() {
        
        LOGGER.info("Running all registered publishers.");
        try {
            
            // Get all registered and enabled publishers
            List<Publisher> publishers = registeredPublishers.getPublishers();
            long enabledPublishersCount = publishers.stream()
                    .filter(Publisher::isEnabled)
                    .count();
            LOGGER.debug("Found {} enabled publishers.", 
                    enabledPublishersCount);
            if ( !publishers.isEmpty() && enabledPublishersCount > 0 ) {
            
                // Get all unpublished opportunities and iterate through them
                List<Opportunity> unpublishedOpportunities = 
                        opportunityRepository.findNonPublished();
                LOGGER.debug("Found {} unpublished opportunities.", 
                        unpublishedOpportunities.size());
                if ( !unpublishedOpportunities.isEmpty() ) {
                    
                    // Instantiate all registered publisher implementations
                    instantiatePublisherImplementations(publishers);
                    
                    // Execute all registered publisher implementations
                    for (Opportunity unpublishedOpportunity : 
                        unpublishedOpportunities) {
                        
                        // Pause between publishing new opportunities
                        executePublishers(unpublishedOpportunity, publishers, 
                                enabledPublishersCount);
                        TimeUnit.SECONDS.sleep(PUBLICATION_DELAY);
                        
                    }
                    
                }
                
            }
            
            // Logging
            LOGGER.info("Finished running all registered publishers.");
            
        } catch (InterruptedException ie) {
            LOGGER.warn("Main pipeline interrupted during "
                    + "publication stage.", ie);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            LOGGER.error("An error was encountered whilst attempting "
                    + "to run all registered publishers.", e);
        }
        
    }
    
    /**
     * Instantiate all registered and enabled publisher implementations
     * @param publishers
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    
    private void instantiatePublisherImplementations(
            List<Publisher> publishers) 
            throws ClassNotFoundException, InstantiationException, 
            IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, 
            SecurityException {
        publisherImplementations = new LinkedHashMap<>();
        for (Publisher publisher : publishers) {
            if (publisher.isEnabled()) {
                Class<?> publisherClass = Class.forName(publisher
                        .getPublisherClass());
                Object publisherInstance = publisherClass
                        .getDeclaredConstructor(Map.class)
                        .newInstance(publisher.getProperties());
                publisherImplementations.put(publisher.getId(), 
                        (OpportunityPublisher) publisherInstance);
            }
        }
    }
    
    /**
     * Execute all registered publishers for a given opporunity
     * @param unpublishedOpportunity
     * @param publishers
     * @param enabledPublishersCount
     */
    
    private void executePublishers(Opportunity unpublishedOpportunity, 
            List<Publisher> publishers, long enabledPublishersCount) {
        
        try {
            
            // Iterate through all registered and enabled publishers
            int publishedCount = 0;
            for (Publisher publisher : publishers) {
                if (publisher.isEnabled()) {
                    
                    // Get the publisher implementation object
                    OpportunityPublisher publisherInstance = 
                            publisherImplementations.get(publisher.getId());
                    
                    // Execute the publisher implementation
                    publisherInstance.publish(unpublishedOpportunity);
                    publishedCount++;
                    
                }
                
            }
            
            // Update the opportunity record in the RDBMS to 
            // indicate that it has been fully published
            if (publishedCount == enabledPublishersCount) {
                unpublishedOpportunity.setPublished(true);
                opportunityRepository.save(unpublishedOpportunity);
                LOGGER.debug("Fully published opportunity {}.", 
                        unpublishedOpportunity.getFramework().getId()
                        + "-" + unpublishedOpportunity.getUri());
            }
            
        } catch (Exception e) {
            LOGGER.error("An error was encountered whilst "
                    + "attempting to publish the opportunity {}.", 
                    unpublishedOpportunity.getFramework().getId()
                    + "-" + unpublishedOpportunity.getUri(), e);
        }
        
    }

}

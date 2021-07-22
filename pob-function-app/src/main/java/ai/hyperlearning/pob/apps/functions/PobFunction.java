package ai.hyperlearning.pob.apps.functions;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ai.hyperlearning.pob.services.Pob;

/**
 * Pob Cloud Function
 *
 * @author jillurquddus
 * @since 0.0.1
 */

@Component
public class PobFunction implements Function<String, Boolean> {

	@Autowired
	private Pob pob;
	
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(PobFunction.class);

	@Override
	public Boolean apply(String input) {
		
		// Log the trigger timer metadata
		LOGGER.info("POB Cloud Function triggered: {}", input);
		
		// Run parsers for all registered frameworks
		pob.runParsers();
		
		// Run all registered publishers
		pob.runPublishers();
		
		// Return true
		return true;
		
	}

}

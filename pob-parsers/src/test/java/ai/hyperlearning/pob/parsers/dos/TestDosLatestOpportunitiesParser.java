package ai.hyperlearning.pob.parsers.dos;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ai.hyperlearning.pob.model.Framework;
import ai.hyperlearning.pob.model.Opportunity;
import ai.hyperlearning.pob.utils.ApplicationProperties;

/**
 * Test Custom DOS Framework Parser
 *
 * @author jillurquddus
 * @since 0.0.1
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
@EnableConfigurationProperties(value = ApplicationProperties.class)
public class TestDosLatestOpportunitiesParser {
	
	@Autowired
    private ApplicationProperties applicationProperties;
	
	@Test
	void testParse() {
		Framework dosFramework = applicationProperties.getFrameworks().get(0);
		DosLatestOpportunitiesParser dosLatestOpportunitiesParser = 
				new DosLatestOpportunitiesParser(dosFramework);
		Set<Opportunity> opportunities = dosLatestOpportunitiesParser.parse();
		assertFalse(opportunities.isEmpty());
	}

}

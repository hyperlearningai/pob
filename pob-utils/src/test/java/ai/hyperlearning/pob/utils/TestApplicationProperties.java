package ai.hyperlearning.pob.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * POB Test Application Properties
 *
 * @author jillurquddus
 * @since 0.0.1
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
@EnableConfigurationProperties(value = ApplicationProperties.class)
class TestApplicationProperties {
	
	@Autowired
    private ApplicationProperties applicationProperties;
	
	@Test
	void testYamlApplicationPropertiesComplexLists() {
		assertEquals("dos", applicationProperties
				.getFrameworks()
				.get(0)
				.getId());
	}

}

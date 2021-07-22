package ai.hyperlearning.pob.apps.functions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.microsoft.azure.functions.ExecutionContext;

import ai.hyperlearning.pob.utils.ApplicationProperties;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.logging.Logger;

/**
 * Test Pob Cloud Function
 *
 * @author jillurquddus
 * @since 0.0.1
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
@EnableConfigurationProperties(value = ApplicationProperties.class)
public class TestPobFunction {
	
	@Test
	public void test() {
		boolean result = new PobFunction().apply("2021-12-31 12:00");
		assertThat(result).isTrue();
	}
	
	@Test
    public void start() {
		FunctionInvoker<String, Boolean> handler = 
				new FunctionInvoker<>(PobFunction.class);
		boolean result = handler.handleRequest("2021-12-31 12:00", 
				new ExecutionContext() {
			
			@Override
            public Logger getLogger() {
                return Logger.getLogger(TestPobFunction.class.getName());
            }
			
			@Override
            public String getInvocationId() {
                return "id1";
            }
			
			@Override
            public String getFunctionName() {
                return "pobFunction";
            }
			
		});
		
		handler.close();
		assertThat(result).isTrue();
		
	}

}

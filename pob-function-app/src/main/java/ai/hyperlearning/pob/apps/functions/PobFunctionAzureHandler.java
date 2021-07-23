package ai.hyperlearning.pob.apps.functions;

import org.springframework.cloud.function.adapter.azure.FunctionInvoker;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.TimerTrigger;

/**
 * Pob Cloud Function - Azure Handler
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public class PobFunctionAzureHandler extends FunctionInvoker<String, Boolean> {
	
	/**
	 * Azure handler for the POB function.
	 * FunctionInvoker links the Azure Function with the Spring Cloud Function.
	 * FunctionInvoker also provides the handleRequest() method.
	 * Finally the function name annotation uses the name of the PobFunction
	 * component which is a Spring Bean with the same class name but starting
	 * with a lowercase character.
	 * @param timerInfo
	 * @param context
	 */
	
	@FunctionName("pobFunction")
	public void runPob(
			@TimerTrigger(name = "keepAliveTrigger", schedule = "0 0/20 * * * *") String timerInfo,
			ExecutionContext context) {
		context.getLogger().info("POB Function triggered: " + timerInfo);
		handleRequest(timerInfo, context);
		context.getLogger().info("POB Function finished");
	}

}

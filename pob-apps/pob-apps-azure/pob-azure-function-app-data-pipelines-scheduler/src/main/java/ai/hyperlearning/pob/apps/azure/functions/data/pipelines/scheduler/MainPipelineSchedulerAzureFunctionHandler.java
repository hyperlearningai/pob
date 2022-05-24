package ai.hyperlearning.pob.apps.azure.functions.data.pipelines.scheduler;

import org.springframework.cloud.function.adapter.azure.FunctionInvoker;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.TimerTrigger;

/**
 * Main Pipeline Function - Azure Handler
 *
 * @author jillurquddus
 * @since 2.0.0
 */

public class MainPipelineSchedulerAzureFunctionHandler 
        extends FunctionInvoker<String, Void> {
    
    /**
     * Azure handler for the POB Main Pipeline function.
     * FunctionInvoker links the Azure Function with the Spring Cloud Function.
     * FunctionInvoker also provides the handleRequest() method.
     * Finally the function name annotation uses the name of the 
     * mainPipelineFunction component which is a Spring Bean with the same 
     * class name but starting with a lowercase character.
     * @param timerInfo
     * @param context
     */
    
    @FunctionName("mainPipelineFunction")
    public void run(
            @TimerTrigger(name = "keepAliveTrigger", schedule = "0 0/30 * * * *") String timerInfo,
            ExecutionContext context) {
        context.getLogger().info(
                "POB Main Pipeline Function triggered: " + timerInfo);
        handleRequest(timerInfo, context);
        context.getLogger().info(
                "POB Main Pipeline Function finished.");
    }

}

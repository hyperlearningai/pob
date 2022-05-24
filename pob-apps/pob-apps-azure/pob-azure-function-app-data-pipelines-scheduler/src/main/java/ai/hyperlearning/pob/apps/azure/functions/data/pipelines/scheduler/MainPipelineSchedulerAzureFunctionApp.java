package ai.hyperlearning.pob.apps.azure.functions.data.pipelines.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * Microsoft Azure Main Pipeline Scheduler Serverless Function - Spring Boot Application
 *
 * @author jillurquddus
 * @since 2.0.0
 */

@ComponentScan(basePackages = {"ai.hyperlearning.pob"})
@EntityScan("ai.hyperlearning.pob.model")
@SpringBootApplication
public class MainPipelineSchedulerAzureFunctionApp {
    
    public static void main(String[] args) {
        SpringApplication.run(MainPipelineSchedulerAzureFunctionApp.class, args);
    }

}

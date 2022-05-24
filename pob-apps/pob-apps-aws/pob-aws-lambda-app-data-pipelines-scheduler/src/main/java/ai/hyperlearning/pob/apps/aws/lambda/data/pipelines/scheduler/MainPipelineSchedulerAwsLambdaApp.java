package ai.hyperlearning.pob.apps.aws.lambda.data.pipelines.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * AWS Main Pipeline Scheduler Lambda Application - Spring Boot Application
 *
 * @author jillurquddus
 * @since 2.0.0
 */

@ComponentScan(basePackages = {"ai.hyperlearning.pob"})
@EntityScan("ai.hyperlearning.pob.model")
@SpringBootApplication
public class MainPipelineSchedulerAwsLambdaApp {
    
    public static void main(String[] args) {
        SpringApplication.run(MainPipelineSchedulerAwsLambdaApp.class, args);
    }

}

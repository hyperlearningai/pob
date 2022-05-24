package ai.hyperlearning.pob.apps.spring.data.pipelines.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main Data Pipeline Scheduler - Spring Boot Application
 *
 * @author jillurquddus
 * @since 2.0.0
 */

@ComponentScan(basePackages = {"ai.hyperlearning.pob"})
@EntityScan("ai.hyperlearning.pob.model")
@EnableScheduling
@SpringBootApplication
public class MainPipelineSchedulerApp {
    
    public static void main(String[] args) {
        SpringApplication.run(MainPipelineSchedulerApp.class, args);
    }

}

package ai.hyperlearning.pob.data.pipelines.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Main POB Pipeline Scheduler
 *
 * @author jillurquddus
 * @since 2.0.0
 */

@Service
@ConditionalOnExpression("'${pipelines.main.enabled}'.equals('true') and "
        + "'${pipelines.main.scheduler.enabled}'.equals('true')")
public class MainPipelineScheduler {
    
    private static final Logger LOGGER =
            LoggerFactory.getLogger(MainPipelineScheduler.class);
    
    @Autowired
    private MainPipelineFunction mainPipelineFunction;
    
    @Scheduled(cron = "${pipelines.main.scheduler.cron}")
    public void run() {
        LOGGER.info("POB Main Pipeline - Scheduled Run");
        mainPipelineFunction.accept(null);
    }

}

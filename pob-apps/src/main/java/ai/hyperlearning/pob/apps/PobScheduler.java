package ai.hyperlearning.pob.apps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Pob Scheduler
 *
 * @author jillurquddus
 * @since 0.0.1
 */

@Component
@EnableScheduling
public class PobScheduler {
	
	@Autowired
	private Pob pob;
	
	@Scheduled(cron = "${application.scheduler.cron}")
	public void runPob() {
		
		// Run parsers for all registered frameworks
		pob.runParsers();
		
		// Run all registered publishers
		pob.runPublishers();
		
		
	}

}

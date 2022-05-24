package ai.hyperlearning.pob.data.pipelines.main;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Main POB Pipeline Function
 *
 * @author jillurquddus
 * @since 2.0.0
 */

@Component
public class MainPipelineFunction implements Consumer<String> {
    
    private static final Logger LOGGER =
            LoggerFactory.getLogger(MainPipelineFunction.class);
    
    @Autowired
    private MainPipeline mainPipeline;

    @Override
    public void accept(String message) {
        LOGGER.info("Invoking the POB Main Pipeline.");
        mainPipeline.run();
    }

}

package ai.hyperlearning.pob.data.publishers;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import ai.hyperlearning.pob.core.io.yaml.YamlPropertySourceFactory;
import ai.hyperlearning.pob.model.Publisher;

/**
 * POB Registered Publishers
 *
 * @author jillurquddus
 * @since 0.0.1
 */

@Component
@PropertySource(value = "classpath:application.yaml", 
    factory = YamlPropertySourceFactory.class)
@ConfigurationProperties
@Validated
public class RegisteredPublishers {
    
    private List<Publisher> publishers;
    
    public List<Publisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<Publisher> publishers) {
        this.publishers = publishers;
    }

}

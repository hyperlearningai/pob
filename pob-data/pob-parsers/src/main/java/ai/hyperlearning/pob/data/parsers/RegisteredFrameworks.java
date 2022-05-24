package ai.hyperlearning.pob.data.parsers;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import ai.hyperlearning.pob.core.io.yaml.YamlPropertySourceFactory;
import ai.hyperlearning.pob.model.Framework;

/**
 * POB Registered Frameworks
 *
 * @author jillurquddus
 * @since 0.0.1
 */

@Component
@PropertySource(value = "classpath:application.yml", 
    factory = YamlPropertySourceFactory.class)
@ConfigurationProperties
@Validated
public class RegisteredFrameworks {
    
    private List<Framework> frameworks;
    
    public List<Framework> getFrameworks() {
        return frameworks;
    }

    public void setFrameworks(List<Framework> frameworks) {
        this.frameworks = frameworks;
    }

}

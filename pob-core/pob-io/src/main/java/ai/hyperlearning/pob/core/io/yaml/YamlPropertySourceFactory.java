package ai.hyperlearning.pob.core.io.yaml;

import java.io.IOException;
import java.util.Properties;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

/**
 * Custom YAML properties file processor
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public class YamlPropertySourceFactory implements PropertySourceFactory {
    
    @Override
    public PropertySource<?> createPropertySource(String name, 
            @NotNull EncodedResource encodedResource) throws IOException {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(encodedResource.getResource());
        Properties properties = factory.getObject();
        return new PropertiesPropertySource(
                encodedResource.getResource().getFilename(), properties);
    }

}

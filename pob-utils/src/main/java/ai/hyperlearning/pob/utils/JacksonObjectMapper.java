package ai.hyperlearning.pob.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Override Object Mapper Bean
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public class JacksonObjectMapper {
	
	@Bean
	@Primary
	public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
	    ObjectMapper objectMapper = builder.build();
	    objectMapper.configure(
	    		SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	    return objectMapper;
	}

}

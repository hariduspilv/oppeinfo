package ee.hitsa.ois;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class TestConfiguration {

    public static final String USER_ID = "48403150000";

    @Autowired
    ObjectMapper objectMapper;

    @Bean
    RestTemplateBuilder builder() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        return new RestTemplateBuilder().basicAuthorization(USER_ID, "undefined")
                .additionalInterceptors((request, body, execution) -> {
                    request.getHeaders().add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
                    return execution.execute(request, body);
                }).additionalMessageConverters(converter);
    }
}

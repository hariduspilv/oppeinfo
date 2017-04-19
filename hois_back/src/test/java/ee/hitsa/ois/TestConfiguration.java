package ee.hitsa.ois;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
public class TestConfiguration {

    public static final String USER_ID = "48403150000";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TestConfigurationService testConfigurationService;

    @Bean
    RestTemplateBuilder builder() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        return new RestTemplateBuilder()
                .additionalInterceptors((request, body, execution) -> {
                    request.getHeaders().add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
                    if (!StringUtils.isEmpty(testConfigurationService.getSessionCookie())) {
                        request.getHeaders().add(HttpHeaders.COOKIE, testConfigurationService.getSessionCookie());
                    } else {
                        return new BasicAuthorizationInterceptor(USER_ID, "undefined").intercept(request, body, execution);
                    }
                    return execution.execute(request, body);
                }).additionalMessageConverters(converter);
    }
}

package ee.hitsa.ois;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.web.WebMvcRegistrationsAdapter;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.security.HoisUserDetailsService;

@EntityScan(basePackageClasses = { Application.class, Jsr310JpaConverters.class })
@EnableCaching
@EnableJpaAuditing
@SpringBootApplication
public class Application {

    @Autowired
    private CacheManager cacheManager;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void postConstruct() {
        Cache c = cacheManager.getCache("classifier");
        if(c != null) {
            c.clear();
        }
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
              return null;
            }
            return HoisUserDetails.fromPrincipal(authentication).getUsername();
        };
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfig();
    }

    @Bean CustomRequestMappingHandler requestMappingHandler() {
        return new CustomRequestMappingHandler();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer configureJackson2ObjectMapperBuilder() {
        return jacksonObjectMapperBuilder ->  {
            jacksonObjectMapperBuilder.serializerByType(LocalDate.class, new JsonSerializer<LocalDate>() {
                @Override
                public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers)
                        throws IOException, JsonProcessingException {
                    gen.writeString(LocalDateTime.of(value, LocalTime.MIN).toInstant(ZoneOffset.UTC).toString());
                }
            });

            jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
                @Override
                public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
                        throws IOException, JsonProcessingException {
                    gen.writeString(value.toInstant(ZoneOffset.UTC).toString());
                }
            });
        };
    }

    @Bean @Autowired
    public DomainClassConverter<FormattingConversionService> domainClassConverter(FormattingConversionService conversionService) {
        return new DomainClassConverter<>(conversionService);
    }

    static class WebMvcConfig extends WebMvcConfigurerAdapter {
        @Autowired
        private ConversionService conversionService;
        @Autowired
        private HoisUserDetailsService hoisUserDetailsService;

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
            argumentResolvers.add(new WithEntityMethodArgumentResolver(conversionService));
            argumentResolvers.add(new HoisUserDetailsArgumentResolver(hoisUserDetailsService));

            // ISO string to LocalDate
            ((ConverterRegistry)conversionService).addConverter(String.class, LocalDate.class, s -> {
                LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.parse(s), ZoneId.systemDefault());
                return localDateTime.toLocalDate();
            });
            // ISO string to LocalDateTime
            ((ConverterRegistry)conversionService).addConverter(String.class, LocalDateTime.class, s -> {
                LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.parse(s), ZoneId.systemDefault());
                return localDateTime;
            });
        }
    }

    static class CustomRequestMappingHandler extends WebMvcRegistrationsAdapter {

        private final RequestMappingHandlerAdapter adapter = new VersionedRequestMappingHandlerAdapter();

        @Override
        public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
            return adapter;
        }
    }

    static class VersionedRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {

        @Override
        protected ServletInvocableHandlerMethod createInvocableHandlerMethod(HandlerMethod handlerMethod) {
            return new VersionedInvocableHandlerMethod(handlerMethod);
        }
    }
}

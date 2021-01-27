package ee.AD.demo;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;

@EnableScheduling
@SpringBootApplication
public class DemoApplication {
    
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
    RestTemplate restTemplate() throws Exception {
        RestTemplate template = new RestTemplate();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        template.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        return template;
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
                    gen.writeString(value.atZone(ZoneId.systemDefault()).toInstant().toString());
                }
            });

            jacksonObjectMapperBuilder.serializerByType(LocalTime.class, new JsonSerializer<LocalTime>() {
                @Override
                public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializers)
                        throws IOException, JsonProcessingException {
                    gen.writeString(value.toString());
                }
            });

            jacksonObjectMapperBuilder.deserializerByType(LocalDate.class, new JsonDeserializer<LocalDate>() {
                @Override
                public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                    LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.parse(p.getText()), ZoneOffset.UTC);
                    return localDateTime.toLocalDate();
                }
            });

            jacksonObjectMapperBuilder.deserializerByType(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                @Override
                public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                    return LocalDateTime.ofInstant(Instant.parse(p.getText()), ZoneOffset.UTC);
                }
            });

            jacksonObjectMapperBuilder.deserializerByType(LocalTime.class, new JsonDeserializer<LocalTime>() {
                @Override
                public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                    try {
                        return LocalDateTime.ofInstant(Instant.parse(p.getText()), ZoneOffset.UTC).toLocalTime();
                    } catch (@SuppressWarnings("unused") Exception e) {}
                    return LocalTimeDeserializer.INSTANCE.deserialize(p, ctxt);
                }
            });

            jacksonObjectMapperBuilder.deserializerByType(String.class, new JsonDeserializer<String>() {
                @Override
                public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                    String value = p.getText();
                    return StringUtils.hasText(value) ? value.trim() : null;
                }
            });
        };
    }

}

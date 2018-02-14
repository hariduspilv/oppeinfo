package ee.hitsa.ois.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import ee.hois.moodle.Config;

@Component
@Validated
@ConfigurationProperties("moodle")
public class MoodleProperties extends Config {
}

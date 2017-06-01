package ee.hitsa.ois.config;

import ee.hitsa.ois.validation.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@ConfigurationProperties("sais")
public class SaisProperties {

    @NotEmpty
    private String endpoint;
    @NotEmpty
    private String consumer;
    @NotEmpty
    private String producer;
    @NotEmpty
    private String useridprefix;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getUseridprefix() {
        return useridprefix;
    }

    public void setUseridprefix(String useridprefix) {
        this.useridprefix = useridprefix;
    }

}

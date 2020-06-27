package ee.hitsa.ois.config;

import ee.sk.mid.MidClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MobileIdConfiguration {

    @Value("${mobileid.relyingPartyUuid}")
    private String relyingPartyUuid;
    @Value("${mobileid.relyingPartyName}")
    private String relyingPartyName;
    @Value("${mobileid.applicationProviderHost}")
    private String applicationProviderHost;
    @Value("${mobileid.pollingTimeoutSeconds}")
    private int pollingTimeoutSeconds;

    @Bean
    public MidClient mobileIdClient() {
        return MidClient.newBuilder()
                .withRelyingPartyUUID(relyingPartyUuid)
                .withRelyingPartyName(relyingPartyName)
                .withHostUrl(applicationProviderHost)
                .withLongPollingTimeoutSeconds(pollingTimeoutSeconds)
                .build();
    }
}

package ee.hitsa.ois;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import ee.hitsa.ois.mock.MockEhisClient;
import ee.hitsa.ois.mock.MockEhisLogService;
import ee.hitsa.ois.mock.MockEkisClient;
import ee.hitsa.ois.mock.MockEkisLogService;
import ee.hitsa.ois.mock.MockEkisService;
import ee.hitsa.ois.mock.MockSaisClient;
import ee.hitsa.ois.mock.MockSaisLogService;
import ee.hitsa.ois.service.ehis.EhisLogService;
import ee.hitsa.ois.service.ekis.EkisLogService;
import ee.hitsa.ois.service.ekis.EkisService;
import ee.hitsa.ois.service.sais.SaisLogService;
import ee.hois.soap.ekis.client.EkisClient;
import ee.hois.xroad.ehis.service.EhisClient;
import ee.hois.xroad.sais2.service.SaisClient;

@Configuration
@Profile("test")
public class MockServicesConfiguration {

    @Bean
    public EkisService ekisService() {
        // do not update entities after ekis call
        return new MockEkisService();
    }

    @Bean
    public EkisClient ekisClient() {
        // do not access ekis endpoint
        return new MockEkisClient();
    }

    @Bean
    public EhisClient ehisClient() {
        // do not access ehis endpoint
        return new MockEhisClient();
    }

    @Bean
    public SaisClient saisClient() {
        // do not access sais endpoint
        return new MockSaisClient();
    }

    @Bean
    public EkisLogService ekisLogService() {
        // do not store log entires
        return new MockEkisLogService();
    }

    @Bean
    public EhisLogService ehisLogService() {
        // do not store log entires
        return new MockEhisLogService();
    }

    @Bean
    public SaisLogService saisLogService() {
        // do not store log entires
        return new MockSaisLogService();
    }
}

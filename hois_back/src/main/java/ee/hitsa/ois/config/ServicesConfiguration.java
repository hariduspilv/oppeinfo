package ee.hitsa.ois.config;

import javax.annotation.PostConstruct;
import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import ee.hitsa.ois.services.EkisSoapService;
import ee.hois.moodle.MoodleClient;
import ee.hois.soap.dds.service.DigiDocServiceClient;
import ee.hois.soap.ekis.client.EkisClient;
import ee.hois.xroad.ariregister.service.AriregisterClient;
import ee.hois.xroad.ehis.service.EhisClient;
import ee.hois.xroad.handler.CustomValidationEventHandler;
import ee.hois.xroad.kutseregister.service.KutseregisterClient;
import ee.hois.xroad.rtip.service.RtipClient;
import ee.hois.xroad.sais2.service.SaisClient;

/**
 * Configuration file for services hois offers (ekis) and consumes (ehis, ekis, sais)
 *
 * By default CXF JAX-WS servlet path is /services/*
 *
 * http://cxf.apache.org/docs/springboot.html
 *
 */
@Configuration
public class ServicesConfiguration {

    @Autowired
    private Bus bus;

    @Autowired
    private EkisSoapService ekisSoapService;

    /**
     * Ekis service endpoint
     *
     * @return
     */
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, ekisSoapService);
        endpoint.publish("/ekis");
        return endpoint;
    }

    @PostConstruct
    public void postConstruct() {
        bus.setProperty("jaxb-reader-validation-event-handler", new CustomValidationEventHandler());
        //bus.setProperty("mtom-enabled", Boolean.TRUE);
    }
    
    /**
     * Ehis client
     *
     * @return
     */
    @Bean
    @Profile("!test")
    public EhisClient ehisClient() {
        return new EhisClient();
    }

    /**
     * Ekis client
     *
     * @return
     */
    @Bean
    @Profile("!test")
    public EkisClient ekisClient() {
        return new EkisClient();
    }

    /**
     * Kutseregister client
     *
     * @return
     */
    @Bean
    public KutseregisterClient kutseregisterClient() {
        return new KutseregisterClient();
    }

    /**
     * Rtip client
     *
     * @return
     */
    @Bean
    public RtipClient rtipClient() {
        return new RtipClient();
    }

    /**
     * Sais client
     *
     * @return
     */
    @Bean
    @Profile("!test")
    public SaisClient saisClient() {
        return new SaisClient();
    }

    /**
     * DigiDocService client
     *
     * @return
     */
    @Bean
    public DigiDocServiceClient ddsClient() {
        return new DigiDocServiceClient();
    }

    /**
     * Moodle client
     */
    @Bean
    public MoodleClient moodleClient() {
        return new MoodleClient();
    }
    
    /**
     * Ariregister client
     */
    @Bean
    public AriregisterClient ariregisterClient() {
        return new AriregisterClient();
    }
}

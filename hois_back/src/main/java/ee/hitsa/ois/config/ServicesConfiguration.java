package ee.hitsa.ois.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ee.hitsa.ois.services.EkisSoapService;
import ee.hois.soap.ekis.client.EkisClient;
import ee.hois.xroad.ehis.service.EhisClient;
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

    /**
     * Ehis client
     *
     * @return
     */
    @Bean
    public EhisClient ehisClient() {
        return new EhisClient();
    }

    /**
     * Ekis client
     *
     * @return
     */
    @Bean
    public EkisClient ekisClient() {
        return new EkisClient();
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
    public SaisClient saisClient() {
        return new SaisClient();
    }
}

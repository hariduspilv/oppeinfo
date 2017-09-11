package ee.hitsa.ois.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ee.hitsa.ois.services.EkisSoapService;

/**
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

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, ekisSoapService);
        endpoint.publish("/ekis");
        return endpoint;
    }

}

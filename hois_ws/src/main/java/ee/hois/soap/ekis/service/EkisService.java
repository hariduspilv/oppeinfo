package ee.hois.soap.ekis.service;

import java.lang.invoke.MethodHandles;
import java.net.URL;

import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.hois.soap.ekis.service.generated.EkisTahvel;
import ee.hois.soap.ekis.service.generated.EkisTahvelPort;
import ee.hois.soap.ekis.service.generated.EnforceContract;
import ee.hois.soap.ekis.service.generated.EnforceContractResponse;
import ee.hois.soap.ekis.service.generated.EnforceDirective;
import ee.hois.soap.ekis.service.generated.EnforceDirectiveResponse;
import ee.hois.soap.ekis.service.generated.RejectDirective;
import ee.hois.soap.ekis.service.generated.RejectDirectiveResponse;


public class EkisService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final QName SERVICE_NAME = new QName("http://tahvel.hois.ee/ekis", "EkisTahvel");
    private static final String WSDL = "/wsdl/ekis/ekis.wsdl";

    private EkisTahvelPort port;

    public EkisService() {
        URL wsdlUrl = EkisService.class.getResource(WSDL);
        EkisTahvel ss = new EkisTahvel(wsdlUrl, SERVICE_NAME);
        port = ss.getEkisTahvelSOAP();
    }

    public EkisService(String mockEndpoint, URL wsdlUrl) {
        QName service = new QName(mockEndpoint, "EkisTahvel");
        EkisTahvel ss = new EkisTahvel(wsdlUrl, service);
        port = ss.getEkisTahvelSOAP();
    }

    public RejectDirectiveResponse rejectDirective(RejectDirective request) {
        log.info("EkisService: rejectDirective");
        return port.rejectDirective(request);
    }

    public EnforceDirectiveResponse enforceDirective(EnforceDirective request) {
        log.info("EkisService: enforceDirective");
        return port.enforceDirective(request);
    }

    public EnforceContractResponse enforceContract(EnforceContract request) {
        log.info("EkisService: enforceContract");
        return port.enforceContract(request);
    }
}

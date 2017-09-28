package ee.hois.soap.ekis;

import ee.hois.soap.ekis.service.generated.EkisTahvelPort;
import ee.hois.soap.ekis.service.generated.EnforceContract;
import ee.hois.soap.ekis.service.generated.EnforceContractResponse;
import ee.hois.soap.ekis.service.generated.EnforceDirective;
import ee.hois.soap.ekis.service.generated.EnforceDirectiveResponse;
import ee.hois.soap.ekis.service.generated.RejectDirective;
import ee.hois.soap.ekis.service.generated.RejectDirectiveResponse;

@javax.jws.WebService(
        serviceName = "EkisTahvel",
        portName = "EkisTahvelSOAP",
        targetNamespace = "http://tahvel.hois.ee/ekis",
        wsdlLocation = "wsdl/ekis/ekis.wsdl",
        endpointInterface = "ee.hois.soap.ekis.service.generated.EkisTahvelPort")
public class EkisMockService implements EkisTahvelPort {

    @Override
    public RejectDirectiveResponse rejectDirective(RejectDirective parameters) {
        return new RejectDirectiveResponse();
    }

    @Override
    public EnforceDirectiveResponse enforceDirective(EnforceDirective parameters) {
        return new EnforceDirectiveResponse();
    }

    @Override
    public EnforceContractResponse enforceContract(EnforceContract parameters) {
        return new EnforceContractResponse();
    }

}

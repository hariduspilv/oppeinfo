package ee.hois.soap.dds.service;

import java.util.Map;

import javax.xml.ws.BindingProvider;

import ee.hois.soap.LogContext;
import ee.hois.soap.SoapHandler;
import ee.hois.soap.dds.generated.DigiDocService;
import ee.hois.soap.dds.generated.DigiDocServicePortType;

public class DigiDocServiceClient {

    private final DigiDocService service = new DigiDocService(DigiDocService.class.getResource("/wsdl/dds/dds_literal.wsdl"));

    public MobileAuthenticateResponse mobileAuthenticate(MobileAuthenticateRequest request) {
        DigiDocServicePortType port = initializePort(null, "mobileAuthenticate");

        // port.mobileAuthenticate(idCode, countryCode, phoneNo, language, serviceName, messageToDisplay, spChallenge, messagingMode, asyncConfiguration, returnCertData, returnRevocationData, sesscode, status, userIDCode, userGivenname, userSurname, userCountry, userCN, certificateData, challengeID, challenge, revocationData);
        return null;
    }

    public GetMobileAuthenticateStatusResponse getMobileAuthenticateStatus(GetMobileAuthenticateStatusRequest request) {
        DigiDocServicePortType port = initializePort(null, "getMobileAuthenticateStatus");
        // port.getMobileAuthenticateStatus(sesscode, waitSignature, status, signature);
        return null;
    }

    private DigiDocServicePortType initializePort(String endpoint, String queryName) {
        DigiDocServicePortType port = service.getDigiDocService();
        Map<String, Object> context = ((BindingProvider) port).getRequestContext();
        context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
        context.put(SoapHandler.LOG_CONTEXT, new LogContext(null, queryName));
        return port;
    }
}

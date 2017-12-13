package ee.hois.soap.dds.service;

import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import ee.hois.soap.LogContext;
import ee.hois.soap.SoapHandler;
import ee.hois.soap.dds.generated.DigiDocService;
import ee.hois.soap.dds.generated.DigiDocServicePortType;

public class DigiDocServiceClient {

    private final DigiDocService service = new DigiDocService(DigiDocService.class.getResource("/wsdl/dds/dds_literal.wsdl"));

    public MobileAuthenticateResponse mobileAuthenticate(MobileAuthenticateRequest request, String endpoint) {
        Holder<Integer> sesscode = new Holder<>();
        Holder<String> status = new Holder<>();
        Holder<String> userIDCode = new Holder<>();
        Holder<String> userGivenname = new Holder<>();
        Holder<String> userSurname = new Holder<>();
        Holder<String> userCountry = new Holder<>();
        Holder<String> userCN = new Holder<>();
        Holder<String> certificateData = request.isReturnCertData() ? new Holder<>() : null;
        Holder<String> challengeID = new Holder<>();
        Holder<String> challenge = new Holder<>();
        Holder<String> revocationData = request.isReturnRevocationData() ? new Holder<>() : null;
        
        DigiDocServicePortType port = initializePort(endpoint, "mobileAuthenticate");
        port.mobileAuthenticate(request.getIdCode(), request.getCountryCode(), request.getPhoneNo(), request.getLanguage(), 
                request.getServiceName(), request.getMessageToDisplay(), request.getSpChallenge(), request.getMessagingMode(), 
                request.getAsyncConfiguration(), request.isReturnCertData(), request.isReturnRevocationData(), 
                sesscode, status, userIDCode, userGivenname, userSurname, userCountry, userCN, certificateData, 
                challengeID, challenge, revocationData);
        
        MobileAuthenticateResponse response = new MobileAuthenticateResponse();
        response.setSesscode(sesscode.value);
        response.setStatus(status.value);
        response.setUserIDCode(userIDCode.value);
        response.setUserGivenname(userGivenname.value);
        response.setUserSurname(userSurname.value);
        response.setUserCountry(userCountry.value);
        response.setUserCN(userCN.value);
        if (certificateData != null) {
            response.setCertificateData(certificateData.value);
        }
        response.setChallengeID(challengeID.value);
        response.setChallenge(challenge.value);
        if (revocationData != null) {
            response.setRevocationData(revocationData.value);
        }
        return response;
    }

    public GetMobileAuthenticateStatusResponse getMobileAuthenticateStatus(GetMobileAuthenticateStatusRequest request, String endpoint) {
        Holder<String> status = new Holder<>();
        Holder<String> signature = new Holder<>();
        
        DigiDocServicePortType port = initializePort(endpoint, "getMobileAuthenticateStatus");
        port.getMobileAuthenticateStatus(request.getSesscode(), request.isWaitSignature(), status, signature);
        
        GetMobileAuthenticateStatusResponse response = new GetMobileAuthenticateStatusResponse();
        response.setStatus(status.value);
        response.setSignature(signature.value);
        return response;
    }

    private DigiDocServicePortType initializePort(String endpoint, String queryName) {
        DigiDocServicePortType port = service.getDigiDocService();
        Map<String, Object> context = ((BindingProvider) port).getRequestContext();
        context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
        context.put(SoapHandler.LOG_CONTEXT, new LogContext(null, queryName));
        return port;
    }
}

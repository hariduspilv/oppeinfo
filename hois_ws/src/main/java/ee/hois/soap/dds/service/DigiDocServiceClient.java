package ee.hois.soap.dds.service;

import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import ee.hois.soap.LogContext;
import ee.hois.soap.SoapHandler;
import ee.hois.soap.dds.generated.DataFileData;
import ee.hois.soap.dds.generated.DigiDocService;
import ee.hois.soap.dds.generated.DigiDocServicePortType;

public class DigiDocServiceClient {

    public static final String RESPONSE_SUCCESS = "OK";
    
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

    public StartSessionResponse startSession(String endpoint) {
        Holder<String> status = new Holder<>();
        Holder<Integer> sesscode = new Holder<>();
        
        DigiDocServicePortType port = initializePort(endpoint, "startSession");
        port.startSession("", "", true, new DataFileData(), status, sesscode, null);
        
        StartSessionResponse response = new StartSessionResponse();
        response.setStatus(status.value);
        response.setSesscode(sesscode.value);
        return response;
    }

    public String closeSession(Integer sesscode, String endpoint) {
        DigiDocServicePortType port = initializePort(endpoint, "closeSession");
        return port.closeSession(sesscode);
    }
    
    public CreateSignedDocResponse createSignedDoc(Integer sesscode, String endpoint) {
        Holder<String> status = new Holder<>();
        
        DigiDocServicePortType port = initializePort(endpoint, "createSignedDoc");
        port.createSignedDoc(sesscode, "BDOC", "2.1", "", status, null);
        
        CreateSignedDocResponse response = new CreateSignedDocResponse();
        response.setStatus(status.value);
        return response;
    }
    
    public AddDataFileResponse addDataFile(AddDataFileRequest request, String endpoint) {
        Holder<String> status = new Holder<>();
        
        DigiDocServicePortType port = initializePort(endpoint, "addDataFile");
        port.addDataFile(request.getSesscode(), request.getFileName(), request.getMimeType(), "EMBEDDED_BASE64", 
                request.getSize(), "", "", request.getContent(), status, null);
        
        AddDataFileResponse response = new AddDataFileResponse();
        response.setStatus(status.value);
        return response;
    }
    
    public MobileSignResponse mobileSign(MobileSignRequest request, String endpoint) {
        Holder<String> status = new Holder<>();
        Holder<String> statusCode = new Holder<>();
        Holder<String> challengeID = new Holder<>();
        
        DigiDocServicePortType port = initializePort(endpoint, "mobileSign");
        port.mobileSign(request.getSesscode(), request.getSignerIDCode(), request.getSignersCountry(), request.getSignerPhoneNo(), 
                request.getServiceName(), request.getAdditionalDataToBeDisplayed(), request.getLanguage(), request.getRole(), 
                request.getCity(), request.getStateOrProvince(), request.getPostalCode(), request.getCountryName(), 
                request.getSigningProfile(), request.getMessagingMode(), request.getAsyncConfiguration(), 
                request.isReturnDocInfo(), request.isReturnDocData(), 
                status, statusCode, challengeID);
        
        MobileSignResponse response = new MobileSignResponse();
        response.setStatus(status.value);
        response.setStatusCode(statusCode.value);
        response.setChallengeID(challengeID.value);
        return response;
    }
    
    public GetStatusInfoResponse getStatusInfo(Integer sesscode, String endpoint) {
        Holder<String> status = new Holder<>();
        Holder<String> statusCode = new Holder<>();
        
        DigiDocServicePortType port = initializePort(endpoint, "getStatusInfo");
        port.getStatusInfo(sesscode, false, false, status, statusCode, null);
        
        GetStatusInfoResponse response = new GetStatusInfoResponse();
        response.setStatus(status.value);
        response.setStatusCode(statusCode.value);
        return response;
    }
    
    public GetSignedDocResponse getSignedDoc(Integer sesscode, String endpoint) {
        Holder<String> status = new Holder<>();
        Holder<String> signedDocData = new Holder<>();
        
        DigiDocServicePortType port = initializePort(endpoint, "getSignedDoc");
        port.getSignedDoc(sesscode, status, signedDocData);
        
        GetSignedDocResponse response = new GetSignedDocResponse();
        response.setStatus(status.value);
        response.setSignedDocData(signedDocData.value);
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

package ee.hitsa.ois.service.security;

import java.lang.invoke.MethodHandles;
import java.util.UUID;

import javax.xml.soap.Detail;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.soap.SOAPFaultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.config.MobileIdProperties;
import ee.hois.soap.dds.service.DigiDocServiceClient;
import ee.hois.soap.dds.service.GetMobileAuthenticateStatusRequest;
import ee.hois.soap.dds.service.GetMobileAuthenticateStatusResponse;
import ee.hois.soap.dds.service.MobileAuthenticateRequest;
import ee.hois.soap.dds.service.MobileAuthenticateResponse;

@Service
public class MobileIdLoginService {

    public static final Integer GENERAL_ERROR = Integer.valueOf(100);
    public static final Integer NOT_MOBILE_ID_USER_ERROR = Integer.valueOf(301);

    private static final int SP_CHALLENGE_LENGTH = 10 * 2; // hex form: 2 chars per byte

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private DigiDocServiceClient ddsClient;
    @Autowired
    private MobileIdProperties properties;

    public MobileIdSession login(String mobileNumber) {
        MobileAuthenticateRequest request = new MobileAuthenticateRequest();
        request.setCountryCode("EE");
        request.setPhoneNo(mobileNumber);
        request.setLanguage("EST");
        request.setServiceName(properties.getServiceName());
        request.setMessageToDisplay(properties.getMessageToDisplay());
        String spChallenge = generateRandomSpChallenge();
        request.setSpChallenge(spChallenge);
        request.setMessagingMode("asynchClientServer");
        request.setReturnCertData(false);
        request.setReturnRevocationData(false);

        MobileIdSession session = new MobileIdSession();
        MobileAuthenticateResponse response;
        try {
            response = ddsClient.mobileAuthenticate(request, properties.getEndpoint());
        } catch (SOAPFaultException e) {
            logFault(e.getFault());
            try {
                session.setErrorCode(Integer.valueOf(e.getFault().getFaultString()));
            } catch (@SuppressWarnings("unused") NumberFormatException e2) {
                log.warn("Cannot parse SOAPFault error code");
                session.setErrorCode(GENERAL_ERROR);
            }
            return session;
        }
        if (!"OK".equals(response.getStatus())) {
            log.warn("response not OK: {}", response.getStatus());
            session.setErrorCode(GENERAL_ERROR);
            return session;
        }
        String challenge = response.getChallenge();
        if (challenge == null || !challenge.substring(0, SP_CHALLENGE_LENGTH).equalsIgnoreCase(spChallenge)) {
            log.warn("challenge ({}) first 10 bytes do not match spChallenge ({})", challenge, spChallenge);
            session.setErrorCode(GENERAL_ERROR);
            return session;
        }
        session.setSesscode(response.getSesscode());
        session.setUserIDCode(response.getUserIDCode());
        session.setUserGivenname(response.getUserGivenname());
        session.setUserSurname(response.getUserSurname());
        session.setChallengeID(response.getChallengeID());
        return session;
    }

    public MobileIdStatus status(int sesscode) {
        GetMobileAuthenticateStatusRequest request = new GetMobileAuthenticateStatusRequest();
        request.setSesscode(sesscode);
        request.setWaitSignature(false);

        MobileIdStatus status = new MobileIdStatus();
        GetMobileAuthenticateStatusResponse response;
        try {
            response = ddsClient.getMobileAuthenticateStatus(request, properties.getEndpoint());
        } catch (SOAPFaultException e) {
            logFault(e.getFault());
            return status;
        }
        status.setStatus(response.getStatus());
        return status;
    }

    private static void logFault(SOAPFault fault) {
        Detail detail = fault.getDetail();
        log.info("SOAPFault {} detail message: {}", fault.getFaultString(), (detail != null ? detail.getTextContent() : ""));
    }

    private static String generateRandomSpChallenge() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, SP_CHALLENGE_LENGTH);
    }
}

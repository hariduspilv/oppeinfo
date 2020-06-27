package ee.hitsa.ois.service.security;

import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.sk.mid.MidAuthentication;
import ee.sk.mid.MidAuthenticationHashToSign;
import ee.sk.mid.MidAuthenticationResponseValidator;
import ee.sk.mid.MidAuthenticationResult;
import ee.sk.mid.MidClient;
import ee.sk.mid.MidDisplayTextFormat;
import ee.sk.mid.MidHashType;
import ee.sk.mid.MidLanguage;
import ee.sk.mid.exception.MidNotMidClientException;
import ee.sk.mid.rest.dao.MidSessionStatus;
import ee.sk.mid.rest.dao.request.MidAuthenticationRequest;
import ee.sk.mid.rest.dao.response.MidAuthenticationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class MobileIdLoginService {

    @Autowired
    private MidClient client;

    @Value("${mobileid.authDisplayText}")
    private String authDisplayText;
    @Value("${mobileid.authDisplayTextEn}")
    private String authDisplayTextEn;

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public MobileIdSession startAuthentication(String idcode, String mobileNumber) {
        MidAuthenticationHashToSign authenticationHash = MidAuthenticationHashToSign.generateRandomHashOfDefaultType();

        MobileIdSession session = new MobileIdSession(idcode, mobileNumber, authenticationHash.getHashInBase64());
        session.setVerificationCode(authenticationHash.calculateVerificationCode());
        return session;
    }

    public MidAuthenticationResult authenticate(MobileIdSession session, Language lang) {
        MidAuthenticationHashToSign authenticationHash = authenticationHash(session.getAuthenticationHash());
        MidAuthenticationRequest request = midAuthenticationRequest(session.getIdcode(), session.getMobileNumber(),
                authenticationHash, lang);

        MidAuthenticationResult authenticationResult;
        try {
            MidAuthenticationResponse response = client.getMobileIdConnector().authenticate(request);
            MidSessionStatus sessionStatus = client.getSessionStatusPoller()
                    .fetchFinalAuthenticationSessionStatus(response.getSessionID());
            MidAuthentication authentication = client.createMobileIdAuthentication(sessionStatus, authenticationHash);

            MidAuthenticationResponseValidator validator = new MidAuthenticationResponseValidator();
            authenticationResult = validator.validate(authentication);
        } catch (MidNotMidClientException e) {
            log.info(e.getMessage());
            throw new ValidationFailedException("main.login.mobileid.notMobileIdUser");
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ValidationFailedException("main.login.error");
        }

        if (!authenticationResult.isValid()) {
            log.info(String.join(", ", authenticationResult.getErrors()));
            throw new ValidationFailedException("main.login.error");
        }
        return authenticationResult;
    }

    private MidAuthenticationHashToSign authenticationHash(String authenticationHashInBase64) {
        return new MidAuthenticationHashToSign
                .MobileIdAuthenticationHashToSignBuilder()
                .withHashInBase64(authenticationHashInBase64)
                .withHashType(MidHashType.SHA256)
                .build();
    }

    private MidAuthenticationRequest midAuthenticationRequest(String idcode, String mobileNumber,
            MidAuthenticationHashToSign authenticationHash, Language lang) {
        return MidAuthenticationRequest.newBuilder()
                .withPhoneNumber(mobileNumber)
                .withNationalIdentityNumber(idcode)
                .withHashToSign(authenticationHash)
                .withLanguage(Language.EN.equals(lang) ? MidLanguage.ENG : MidLanguage.EST)
                .withDisplayText(Language.EN.equals(lang) ? authDisplayTextEn : authDisplayText)
                .withDisplayTextFormat(MidDisplayTextFormat.GSM7)
                .build();
    }

}

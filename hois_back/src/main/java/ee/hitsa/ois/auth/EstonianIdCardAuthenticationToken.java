package ee.hitsa.ois.auth;

import java.security.cert.X509Certificate;

import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import ee.hitsa.ois.service.security.HoisUserDetails;

public class EstonianIdCardAuthenticationToken extends PreAuthenticatedAuthenticationToken {

    private X509Certificate certificate;

    public EstonianIdCardAuthenticationToken(Object personCode, X509Certificate certificate) {
        super(personCode, personCode);
        this.setCertificate(certificate);
    }

    public EstonianIdCardAuthenticationToken(HoisUserDetails userDetails) {
        super(userDetails, "");
    }
    
    public X509Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(X509Certificate certificate) {
        this.certificate = certificate;
    }

}

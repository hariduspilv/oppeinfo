package ee.hitsa.ois.auth;

import java.security.cert.X509Certificate;

import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class EstonianIdCardAuthenticationToken extends PreAuthenticatedAuthenticationToken {

    private X509Certificate certificate;

    public EstonianIdCardAuthenticationToken(Object personCode, X509Certificate certificate) {
        super(personCode, personCode);
        this.setCertificate(certificate);
    }

    public EstonianIdCardAuthenticationToken(String personCode) {
        super(personCode, personCode);
    }

    public X509Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(X509Certificate certificate) {
        this.certificate = certificate;
    }

}

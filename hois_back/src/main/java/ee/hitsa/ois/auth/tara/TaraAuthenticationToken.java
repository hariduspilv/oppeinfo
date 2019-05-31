package ee.hitsa.ois.auth.tara;

import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import ee.hitsa.ois.service.security.HoisUserDetails;

public class TaraAuthenticationToken extends PreAuthenticatedAuthenticationToken {

    public TaraAuthenticationToken(HoisUserDetails userDetails) {
        super(userDetails, "");
    }

}

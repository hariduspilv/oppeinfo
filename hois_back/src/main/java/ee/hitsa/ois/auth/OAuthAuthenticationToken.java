package ee.hitsa.ois.auth;

import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import ee.hitsa.ois.service.security.HoisUserDetails;

public class OAuthAuthenticationToken extends PreAuthenticatedAuthenticationToken {

    public OAuthAuthenticationToken(HoisUserDetails userDetails) {
        super(userDetails, "");
    }

}

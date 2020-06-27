package ee.hitsa.ois.auth;

import ee.hitsa.ois.service.security.HoisUserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class MobileIdAuthenticationToken extends PreAuthenticatedAuthenticationToken {

    public MobileIdAuthenticationToken(HoisUserDetails userDetails) {
        super(userDetails, "");
    }

}

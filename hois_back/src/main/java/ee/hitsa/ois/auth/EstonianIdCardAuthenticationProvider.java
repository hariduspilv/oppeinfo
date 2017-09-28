package ee.hitsa.ois.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.security.HoisUserDetailsService;

@Component
public class EstonianIdCardAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private HoisUserDetailsService hoisUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        EstonianIdCardAuthenticationToken token = (EstonianIdCardAuthenticationToken) authentication;
        HoisUserDetails hoisUserDetails = hoisUserDetailsService.loadUserByUsername(token.getPrincipal().toString());
        if (hoisUserDetails != null) {
            token.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EstonianIdCardAuthenticationToken.class.isAssignableFrom(authentication);
    }

}

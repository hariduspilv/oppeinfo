package ee.hitsa.ois.auth;

import java.security.cert.X509Certificate;

import org.digidoc4j.X509Cert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import ee.hitsa.ois.service.UserService;

@Component
public class EstonianIdCardAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication token) throws AuthenticationException {
        X509Certificate certificate = ((EstonianIdCardAuthenticationToken) token).getCertificate();
        String idcode = token.getPrincipal().toString();
        if (certificate == null) {
            throw new UsernameNotFoundException("No certificate for person with idcode : " + idcode);
        }
        X509Cert cert = new X509Cert(certificate);
        String lastname = cert.getSubjectName(X509Cert.SubjectName.SURNAME);
        String firstname = cert.getSubjectName(X509Cert.SubjectName.GIVENNAME);
        userService.createPersonUserIfNecessary(idcode, lastname, firstname);
        token.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(token);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EstonianIdCardAuthenticationToken.class.isAssignableFrom(authentication);
    }

}

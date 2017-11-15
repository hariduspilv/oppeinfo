package ee.hitsa.ois.auth;

import java.security.cert.X509Certificate;

import javax.persistence.EntityManager;

import org.digidoc4j.X509Cert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.service.UserService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.security.HoisUserDetailsService;
import ee.hitsa.ois.validation.EstonianIdCodeValidator;

@Component
public class EstonianIdCardAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private EntityManager em;
    @Autowired
    private HoisUserDetailsService hoisUserDetailsService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication token) throws AuthenticationException {
        String idcode = token.getPrincipal().toString();
        HoisUserDetails hoisUserDetails = null;
        try {
            hoisUserDetails = hoisUserDetailsService.loadUserByUsername(idcode);
        } catch(UsernameNotFoundException e) {
            // either person or user not found
            Person person = personRepository.findByIdcode(idcode);
            X509Certificate certificate = ((EstonianIdCardAuthenticationToken)token).getCertificate();
            if(certificate != null) {
                if(person == null) {
                    // create new person/user from certificate
                    person = new Person();
                    X509Cert cert = new X509Cert(certificate);
                    person.setLastname(cert.getSubjectName(X509Cert.SubjectName.SURNAME));
                    person.setFirstname(cert.getSubjectName(X509Cert.SubjectName.GIVENNAME));
                    person.setIdcode(idcode);
                    person.setBirthdate(EstonianIdCodeValidator.birthdateFromIdcode(idcode));
                    person.setSex(em.getReference(Classifier.class, EstonianIdCodeValidator.sexFromIdcode(idcode)));
                    person = personRepository.save(person);
                }
                hoisUserDetails = hoisUserDetailsService.getHoisUserDetails(userService.createUser(person));
            }
            if(hoisUserDetails == null) {
                throw e;
            }
        }
        token.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(token);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EstonianIdCardAuthenticationToken.class.isAssignableFrom(authentication);
    }

}

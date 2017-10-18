package ee.hitsa.ois;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.security.HoisUserDetailsService;
import ee.hitsa.ois.util.JpaQueryBuilder;

@Service
public class TestConfigurationService {

    @Autowired
    private EntityManager em;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private HoisUserDetailsService hoisUserDetailsService;

    private String sessionCookie;
    private String xsrfCookie;
    private User currentUser;

    public String getSessionCookie() {
        return sessionCookie;
    }

    public void setSessionCookie(String sessionCookie) {
        this.sessionCookie = sessionCookie;
    }

    public String getXsrfCookie() {
        return xsrfCookie;
    }

    public void setXsrfCookie(String xsrfCookie) {
        this.xsrfCookie = xsrfCookie;
    }

    public void userToRole(Role role, TestRestTemplate restTemplate) {
        userToRoleInSchool(role, null, restTemplate);
    }

    public void userToRoleInSchool(Role role, Long schoolId, TestRestTemplate restTemplate) {
        String userId = TestConfiguration.USER_ID;
        Person person = personRepository.findByIdcode(userId);
        if (person == null) {
            throw new UsernameNotFoundException("No person present with idcode : " + userId);
        }

        JpaQueryBuilder<User> qb = new JpaQueryBuilder<>(User.class, "u").sort("id");
        qb.requiredCriteria("u.person.id = :personId", "personId", person.getId());
        qb.requiredCriteria("u.role.code = :role", "role", role);
        qb.optionalCriteria("u.school.id = :schoolId", "schoolId", schoolId);
        List<User> usersWithRole = qb.select(em).setMaxResults(1).getResultList();

        if (usersWithRole.isEmpty()) {
            throw new AssertionFailedException("Cannot find role " + role.name() + " for user " + userId);
        }
        ResponseEntity<Object> userResponse = restTemplate.withBasicAuth(userId, "undefined").getForEntity("/user", null, Object.class);
        setHeaders(userResponse);

        if (getSessionCookie() != null && getXsrfCookie() != null) {
            currentUser = usersWithRole.get(0);
            restTemplate.postForEntity("/changeUser", Collections.singletonMap("id", currentUser.getId()), Object.class);
        }
    }

    public List<School> personSchools(Role role) {
        return em.createQuery("select u.school from User u where u.school.id is not null and u.role.code = ?1 and u.person.idcode = ?2 order by u.school.id", School.class)
                .setParameter(1, role.name())
                .setParameter(2, TestConfiguration.USER_ID)
                .getResultList();
    }

    private void setHeaders(ResponseEntity<Object> changeUserResponse) {
        HttpHeaders headers = changeUserResponse.getHeaders();
        headers.forEach((name, values) -> {
            if (name.equalsIgnoreCase("Set-Cookie")) {
                for (String value : values) {
                    if (value.contains("XSRF-TOKEN")) {
                        setXsrfCookie(value);
                    } else {
                        setSessionCookie(value);
                    }
                }
            }
        });
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public HoisUserDetails getHoisUserDetails() {
        return hoisUserDetailsService.getHoisUserDetails(currentUser);
    }
}

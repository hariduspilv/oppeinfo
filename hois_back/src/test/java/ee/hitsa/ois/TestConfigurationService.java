package ee.hitsa.ois;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.repository.UserRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.security.HoisUserDetailsService;

@Service
public class TestConfigurationService {

    @Autowired
    private UserRepository userRepository;
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
        HoisUserDetails hoisUserDetails = hoisUserDetailsService.loadUserByUsername(TestConfiguration.USER_ID);
        List<User> usersWithRole = userRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("person").get("id"), hoisUserDetails.getPersonId()));
            filters.add(cb.equal(root.get("role").get("code"), role.name()));
            filters.add(cb.equal(root.get("school").get("id"), schoolId != null ? schoolId : hoisUserDetails.getSchoolId()));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });

        if (!CollectionUtils.isEmpty(usersWithRole)) {
            ResponseEntity<Object> userResponse = restTemplate.withBasicAuth(TestConfiguration.USER_ID, "undefined")
                    .getForEntity("/user", null, Object.class);
            setHeaders(userResponse);

            if (getSessionCookie() != null && getXsrfCookie() != null) {
                currentUser = usersWithRole.stream().findFirst().get();
                restTemplate.postForEntity("/changeUser", Collections.singletonMap("id", currentUser.getId()), Object.class);
            }
        }
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

    public HoisUserDetails getHoisUserDetails() {
        return hoisUserDetailsService.getHoisUserDetails(currentUser);
    }

}

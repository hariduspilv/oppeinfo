package ee.hitsa.ois.service.security;

import ee.hitsa.ois.domain.School;
import ee.hitsa.ois.domain.User;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.security.Principal;
import java.util.Collection;
import java.util.List;


/**
 * DISCLAIMER: "undefined" is a placeholder before proper authentication is setup.
 * TODO: Setup security.
 */
public class HoisUserDetails extends org.springframework.security.core.userdetails.User {

    private Long userId;
    private transient School school;

    HoisUserDetails(User user, List<String> roles) {
        super(user.getPerson().getIdcode(), "undefined", getAuthorities(roles));
        this.userId = user.getId();
        this.school = user.getSchool();
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        return AuthorityUtils.createAuthorityList(roles.toArray(new String[roles.size()]));
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public static HoisUserDetails fromPrincipal(Principal principal) {
        if (principal instanceof PreAuthenticatedAuthenticationToken) {
            Object auth = ((Authentication) principal).getDetails();
            return (HoisUserDetails) auth;
        }
        Object auth = ((Authentication) principal).getPrincipal();
        return (HoisUserDetails) auth;
    }
}

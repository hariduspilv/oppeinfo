package ee.hitsa.ois.service.security;

import ee.hitsa.ois.domain.School;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.util.EntityUtil;

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
    private static final long serialVersionUID = -7947997673955215575L;

    private Long userId;
    private transient Long personId;
    private transient String role;
    private transient School school;

    HoisUserDetails(User user, List<String> roles) {
        super(user.getPerson().getIdcode(), "undefined", getAuthorities(roles));
        this.userId = user.getId();
        this.personId = user.getPerson().getId();
        this.role = EntityUtil.getCode(user.getRole());
        this.school = user.getSchool();
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        return AuthorityUtils.createAuthorityList(roles.toArray(new String[roles.size()]));
    }

    public Long getUserId() {
        return userId;
    }

    public Long getPersonId() {
        return personId;
    }

    public School getSchool() {
        return school;
    }

    public Long getSchoolId() {
        return EntityUtil.getNullableId(school);
    }

    public boolean isSchoolAdmin() {
        return school != null && "ROLL_A".equals(role);
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

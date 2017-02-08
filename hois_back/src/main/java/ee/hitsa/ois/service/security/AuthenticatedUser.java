package ee.hitsa.ois.service.security;

import ee.hitsa.ois.domain.School;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.UserProjection;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

public class AuthenticatedUser implements Serializable {

    private final String name;

    private final Long user;

    // rights?
    private String roleCode;

    private AuthenticatedSchool school;

    private String fullname;

    private Collection<GrantedAuthority> authorizedRoles;

    private List<UserProjection> users;

    public AuthenticatedUser(String name, Long user, String roleCode) {
        this.name = name;
        this.user = user;
        this.roleCode = roleCode;
    }

    public AuthenticatedUser(User user) {
        this(user.getPerson().getIdcode(), user.getId(), EntityUtil.getCode(user.getRole()));
    }

    public Long getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public AuthenticatedSchool getSchool() {
        return school;
    }

    public void setSchool(AuthenticatedSchool school) {
        this.school = school;
    }

    public void setSchool(School school) {
        this.school = school != null ? new AuthenticatedSchool(school) : null;
    }

    public Collection<String> getAuthorizedRoles() {
        return authorizedRoles != null ?
                authorizedRoles.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()) :
                Collections.emptySet();
    }

    public void setAuthorizedRoles(Collection<GrantedAuthority> authorizedRoles) {
        this.authorizedRoles = authorizedRoles;
    }

    public List<UserProjection> getUsers() {
        return users;
    }

    public void setUsers(List<UserProjection> users) {
        this.users = users;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}

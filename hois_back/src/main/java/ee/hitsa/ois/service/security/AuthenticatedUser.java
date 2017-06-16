package ee.hitsa.ois.service.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.UserProjection;

public class AuthenticatedUser implements Serializable {

    private final String name;
    private final Long user;
    private final Long student;
    // rights?
    private String roleCode;
    private AuthenticatedSchool school;
    private String fullname;
    private Collection<GrantedAuthority> authorizedRoles;
    private List<UserProjection> users;

    public AuthenticatedUser(String name, Long user, String roleCode, Long student) {
        this.name = name;
        this.user = user;
        this.roleCode = roleCode;
        this.student = student;
    }

    public AuthenticatedUser(User user) {
        this(user.getPerson().getIdcode(), user.getId(), EntityUtil.getCode(user.getRole()), EntityUtil.getNullableId(user.getStudent()));
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

    public Collection<String> getAuthorizedRoles() {
        return StreamUtil.toMappedSet(GrantedAuthority::getAuthority, authorizedRoles);
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

    public Long getStudent() {
        return student;
    }
}

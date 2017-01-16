package ee.hitsa.ois.service.security;

import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.UserProjection;
import ee.hitsa.ois.web.dto.SchoolWithoutLogo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public class AuthenticatedUser implements Serializable {

    private final String name;

    private final Long user;

    // todo: replace with id
    private SchoolWithoutLogo school;

    private String fullname;

    private Collection<GrantedAuthority> authorizedRoles;

    private List<UserProjection> users;

    public AuthenticatedUser(String name, Long user) {
        this.name = name;
        this.user = user;
    }

    public AuthenticatedUser(User user) {
        this(user.getPerson().getIdcode(), user.getId());
    }

    public Long getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public SchoolWithoutLogo getSchool() {
        return school;
    }

    public void setSchool(SchoolWithoutLogo school) {
        this.school = school;
    }

    public Collection<GrantedAuthority> getAuthorizedRoles() {
        return authorizedRoles;
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

    public void setFullname(String firstName, String lastName) {
        fullname = "" + firstName + " " + lastName;
    }
}

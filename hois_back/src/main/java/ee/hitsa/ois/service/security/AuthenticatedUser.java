package ee.hitsa.ois.service.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import ee.hitsa.ois.auth.LoginMethod;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.UserProjection;

public class AuthenticatedUser implements Serializable {

    private final String name;
    private final Long user;
    private final Long student;
    private final Long teacher;
    // rights?
    private String roleCode;
    private AuthenticatedSchool school;
    // for student only two flags from curriculum
    private Boolean vocational;
    private Boolean higher;
    private Boolean doctoral;
    private String fullname;
    private Collection<GrantedAuthority> authorizedRoles;
    private List<UserProjection> users;
    private LoginMethod loginMethod;
    private Integer sessionTimeoutInSeconds;

    public AuthenticatedUser(String name, Long user, String roleCode, Long student, Long teacher, Integer sessionTimeoutInSeconds) {
        this.name = name;
        this.user = user;
        this.roleCode = roleCode;
        this.student = student;
        this.teacher = teacher;
        this.sessionTimeoutInSeconds = sessionTimeoutInSeconds;
    }

    public AuthenticatedUser(User user, Integer sessionTimeoutInSeconds) {
        this(user.getPerson().getIdcode(), user.getId(), EntityUtil.getCode(user.getRole()),
                EntityUtil.getNullableId(user.getStudent()), EntityUtil.getNullableId(user.getTeacher()),
                sessionTimeoutInSeconds);
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

    public Boolean getVocational() {
        return vocational;
    }

    public void setVocational(Boolean vocational) {
        this.vocational = vocational;
    }

    public Boolean getHigher() {
        return higher;
    }

    public void setHigher(Boolean higher) {
        this.higher = higher;
    }

    public Boolean getDoctoral() {
        return doctoral;
    }

    public void setDoctoral(Boolean doctoral) {
        this.doctoral = doctoral;
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

    public Long getTeacher() {
        return teacher;
    }

    public LoginMethod getLoginMethod() {
        return loginMethod;
    }

    public void setLoginMethod(LoginMethod loginMethod) {
        this.loginMethod = loginMethod;
    }

    public Integer getSessionTimeoutInSeconds() {
        return sessionTimeoutInSeconds;
    }

    public void setSessionTimeoutInSeconds(Integer sessionTimeoutInSeconds) {
        this.sessionTimeoutInSeconds = sessionTimeoutInSeconds;
    }

}

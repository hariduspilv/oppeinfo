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
    private final Long person;
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
    private String type;
    private Collection<GrantedAuthority> authorizedRoles;
    private List<UserProjection> users;
    private LoginMethod loginMethod;
    private Integer sessionTimeoutInSeconds;
    private List<Long> teacherGroupIds;
    private Boolean isCurriculumTeacher;
    private List<String> committees;
    private List<Long> curriculums;
    private Boolean hasSchoolRole;
    
    private Boolean mustAgreeWithToS;

    public AuthenticatedUser(String name, Long person, Long user, String roleCode, Long student, Long teacher,
            Integer sessionTimeoutInSeconds) {
        this.name = name;
        this.person = person;
        this.user = user;
        this.roleCode = roleCode;
        this.student = student;
        this.teacher = teacher;
        this.sessionTimeoutInSeconds = sessionTimeoutInSeconds;
    }

    public AuthenticatedUser(User user, Integer sessionTimeoutInSeconds) {
        this(user.getPerson().getIdcode(), user.getPerson().getId(), user.getId(), EntityUtil.getCode(user.getRole()),
                EntityUtil.getNullableId(user.getStudent()), EntityUtil.getNullableId(user.getTeacher()),
                sessionTimeoutInSeconds);
    }

    public Long getPerson() {
        return person;
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

    public List<Long> getTeacherGroupIds() {
        return teacherGroupIds;
    }

    public void setTeacherGroupIds(List<Long> teacherGroupIds) {
        this.teacherGroupIds = teacherGroupIds;
    }

    public Boolean getIsCurriculumTeacher() {
        return isCurriculumTeacher;
    }

    public void setIsCurriculumTeacher(Boolean isCurriculumTeacher) {
        this.isCurriculumTeacher = isCurriculumTeacher;
    }

    public List<String> getCommittees() {
        return committees;
    }

    public void setCommittees(List<String> committees) {
        this.committees = committees;
    }

    public List<Long> getCurriculums() {
        return curriculums;
    }

    public void setCurriculums(List<Long> curriculums) {
        this.curriculums = curriculums;
    }

    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Boolean getHasSchoolRole() {
        return hasSchoolRole;
    }

    public void setHasSchoolRole(Boolean hasSchoolRole) {
        this.hasSchoolRole = hasSchoolRole;
    }

    public Boolean getMustAgreeWithToS() {
        return mustAgreeWithToS;
    }

    public void setMustAgreeWithToS(Boolean mustAgreeWithToS) {
        this.mustAgreeWithToS = mustAgreeWithToS;
    }

}

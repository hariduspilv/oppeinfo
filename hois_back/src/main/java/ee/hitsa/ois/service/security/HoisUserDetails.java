package ee.hitsa.ois.service.security;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import ee.hitsa.ois.auth.LoginMethod;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;

/**
 * DISCLAIMER: "undefined" is a placeholder before proper authentication is
 * setup. TODO: Setup security.
 */
public class HoisUserDetails extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = -7947997673955215575L;

    private Long userId;
    private Long personId;
    private String role;
    private Long schoolId;
    private Long studentId;
    private Long teacherId;
    private LoginMethod loginMethod;
    private String mobileNumber;

    HoisUserDetails(User user, List<String> roles) {
        super(PersonUtil.fullnameAndIdcode(user.getPerson()), "undefined", getAuthorities(roles));
        this.userId = EntityUtil.getId(user);
        this.personId = EntityUtil.getId(user.getPerson());
        this.role = EntityUtil.getCode(user.getRole());
        this.schoolId = EntityUtil.getNullableId(user.getSchool());
        this.studentId = EntityUtil.getNullableId(user.getStudent());
        this.teacherId = EntityUtil.getNullableId(user.getTeacher());
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        return AuthorityUtils.createAuthorityList(roles.toArray(new String[roles.size()]));
    }

    public boolean isExternalExpert() {
        return Role.ROLL_V.name().equals(role);
    }

    public boolean isMainAdmin() {
        return Role.ROLL_P.name().equals(role);
    }

    public boolean isRepresentative() {
        return Role.ROLL_L.name().equals(role);
    }

    public boolean isSchoolAdmin() {
        return schoolId != null && Role.ROLL_A.name().equals(role);
    }

    public boolean isStudent() {
        return schoolId != null && Role.ROLL_T.name().equals(role) && studentId != null;
    }

    public boolean isTeacher() {
        return Role.ROLL_O.name().equals(role) && teacherId != null;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getPersonId() {
        return personId;
    }

    public String getRole() {
        return role;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public LoginMethod getLoginMethod() {
        return loginMethod;
    }

    public void setLoginMethod(LoginMethod loginMethod) {
        this.loginMethod = loginMethod;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }
    
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    
    public Long getStudentId() {
        return studentId;
    }

    public Long getTeacherId() {
        return teacherId;
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

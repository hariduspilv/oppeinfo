package ee.hitsa.ois;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("test")
public class TestProperties {

    private Integer ldapSchool;
    private String ldapUsername;
    private String ldapPassword;
    
    public Integer getLdapSchool() {
        return ldapSchool;
    }
    public void setLdapSchool(Integer ldapSchool) {
        this.ldapSchool = ldapSchool;
    }
    
    public String getLdapUsername() {
        return ldapUsername;
    }
    public void setLdapUsername(String ldapUsername) {
        this.ldapUsername = ldapUsername;
    }
    
    public String getLdapPassword() {
        return ldapPassword;
    }
    public void setLdapPassword(String ldapPassword) {
        this.ldapPassword = ldapPassword;
    }
    
}

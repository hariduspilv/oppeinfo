package ee.hitsa.ois.web.commandobject;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class UsersSearchCommand {

    private Long school;

    private String idcode;
    
    private String uniqueCode;

    private String name;

    @ClassifierRestriction(MainClassCode.ROLL)
    private String role;

    private Long userRole;
    private Long occupation;
    
    /** used for declaration student search */
    private Boolean nextPeriod;

    public Long getSchool() {
        return school;
    }

    public void setSchool(Long school) {
        this.school = school;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getUserRole() {
        return userRole;
    }

    public void setUserRole(Long userRole) {
        this.userRole = userRole;
    }

    public Long getOccupation() {
        return occupation;
    }

    public void setOccupation(Long occupation) {
        this.occupation = occupation;
    }

    public Boolean getNextPeriod() {
        return nextPeriod;
    }

    public void setNextPeriod(Boolean nextPeriod) {
        this.nextPeriod = nextPeriod;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

}

package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class CertificateContentCommand {
    
    private Long student;
    private Long school;
    private String otherName;
    private String otherIdcode;
    
    @NotNull
    @ClassifierRestriction(MainClassCode.TOEND_LIIK)
    private String type;

    public Long getStudent() {
        return student;
    }

    public void setStudent(Long student) {
        this.student = student;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getOtherIdcode() {
        return otherIdcode;
    }

    public void setOtherIdcode(String otherIdcode) {
        this.otherIdcode = otherIdcode;
    }

    public Long getSchool() {
        return school;
    }

    public void setSchool(Long school) {
        this.school = school;
    }
}

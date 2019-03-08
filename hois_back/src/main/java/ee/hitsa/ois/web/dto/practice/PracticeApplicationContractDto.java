package ee.hitsa.ois.web.dto.practice;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class PracticeApplicationContractDto {

    private Boolean isHigher;
    private AutocompleteResult student;
    private AutocompleteResult enterprise;
    private String contactPersonName;
    private String contactPersonPhone;
    private String contactPersonEmail;
    private String supervisorName;
    private String supervisorPhone;
    private String supervisorEmail;
    
    public Boolean getIsHigher() {
        return isHigher;
    }
    public void setIsHigher(Boolean isHigher) {
        this.isHigher = isHigher;
    }
    public AutocompleteResult getStudent() {
        return student;
    }
    public void setStudent(AutocompleteResult student) {
        this.student = student;
    }
    public AutocompleteResult getEnterprise() {
        return enterprise;
    }
    public void setEnterprise(AutocompleteResult enterprise) {
        this.enterprise = enterprise;
    }
    public String getContactPersonName() {
        return contactPersonName;
    }
    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }
    public String getContactPersonPhone() {
        return contactPersonPhone;
    }
    public void setContactPersonPhone(String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }
    public String getContactPersonEmail() {
        return contactPersonEmail;
    }
    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }
    public String getSupervisorName() {
        return supervisorName;
    }
    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }
    public String getSupervisorPhone() {
        return supervisorPhone;
    }
    public void setSupervisorPhone(String supervisorPhone) {
        this.supervisorPhone = supervisorPhone;
    }
    public String getSupervisorEmail() {
        return supervisorEmail;
    }
    public void setSupervisorEmail(String supervisorEmail) {
        this.supervisorEmail = supervisorEmail;
    }
    
}

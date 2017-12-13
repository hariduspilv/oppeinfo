package ee.hitsa.ois.web.commandobject.scholarship;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.commandobject.OisFileForm;

public class ScholarshipStudentApplicationForm {
    private Long id;
    @NotEmpty
    @Size(max = 50)
    private String bankAccount;
    @Size(max = 4000)
    private String addInfo;
    private String bankAccountOwnerIdcode;
    private String bankAccountOwnerName;
    private Long familyMembers;
    private Long familyMembersAdult;
    private LocalDate scholarshipFrom;
    private LocalDate scholarshipThru;
    @ClassifierRestriction(MainClassCode.STIPTOETUS_HYVITAMINE_POHJUS)
    private String compensationReason;
    @ClassifierRestriction(MainClassCode.STIPTOETUS_HYVITAMINE)
    private String compensationFrequency;
    private Long routeKm;

    private List<OisFileForm> files;
    private List<ScholarshipApplicationFamilyForm> family;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public List<OisFileForm> getFiles() {
        return files;
    }

    public void setFiles(List<OisFileForm> files) {
        this.files = files;
    }

    public String getBankAccountOwnerIdcode() {
        return bankAccountOwnerIdcode;
    }

    public void setBankAccountOwnerIdcode(String bankAccountOwnerIdcode) {
        this.bankAccountOwnerIdcode = bankAccountOwnerIdcode;
    }

    public String getBankAccountOwnerName() {
        return bankAccountOwnerName;
    }

    public void setBankAccountOwnerName(String bankAccountOwnerName) {
        this.bankAccountOwnerName = bankAccountOwnerName;
    }

    public Long getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(Long familyMembers) {
        this.familyMembers = familyMembers;
    }

    public Long getFamilyMembersAdult() {
        return familyMembersAdult;
    }

    public void setFamilyMembersAdult(Long familyMembersAdult) {
        this.familyMembersAdult = familyMembersAdult;
    }

    public LocalDate getScholarshipFrom() {
        return scholarshipFrom;
    }

    public void setScholarshipFrom(LocalDate scholarshipFrom) {
        this.scholarshipFrom = scholarshipFrom;
    }

    public LocalDate getScholarshipThru() {
        return scholarshipThru;
    }

    public void setScholarshipThru(LocalDate scholarshipThru) {
        this.scholarshipThru = scholarshipThru;
    }

    public List<ScholarshipApplicationFamilyForm> getFamily() {
        return family;
    }

    public void setFamily(List<ScholarshipApplicationFamilyForm> family) {
        this.family = family;
    }

    public String getCompensationReason() {
        return compensationReason;
    }

    public void setCompensationReason(String compensationReason) {
        this.compensationReason = compensationReason;
    }

    public String getCompensationFrequency() {
        return compensationFrequency;
    }

    public void setCompensationFrequency(String compensationFrequency) {
        this.compensationFrequency = compensationFrequency;
    }

    public Long getRouteKm() {
        return routeKm;
    }

    public void setRouteKm(Long routeKm) {
        this.routeKm = routeKm;
    }

}

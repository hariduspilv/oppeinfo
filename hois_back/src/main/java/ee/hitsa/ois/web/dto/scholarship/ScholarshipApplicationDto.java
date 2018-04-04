package ee.hitsa.ois.web.dto.scholarship;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.scholarship.ScholarshipApplication;
import ee.hitsa.ois.enums.ScholarshipStatus;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.Required;

public class ScholarshipApplicationDto {

    private Long id;
    private BigDecimal credits;
    private BigDecimal averageMark;
    private BigDecimal lastPeriodMark;
    private BigDecimal saisPoints;
    private BigDecimal curriculumCompletion;
    private Long absences;
    private String studentGroupCode;
    @Size(max = 100)
    private String email;
    @Size(max = 100)
    private String phone;
    private String address;
    @Required
    @Size(max = 50)
    private String bankAccount;
    private String bankAccountOwnerIdcode;
    private String bankAccountOwnerName;
    @Size(max = 4000)
    private String addInfo;
    private Long familyMembers;
    private Long familyMembersAdult;
    private LocalDate scholarshipFrom;
    private LocalDate scholarshipThru;
    private String compensationReason;
    private String compensationFrequency;
    private String status;
    private BigDecimal routeKm;
    private List<ScholarshipFileDto> files;
    private List<ScholarshipApplicationFamilyDto> family;
    private Boolean canApply;

    public static ScholarshipApplicationDto of(ScholarshipApplication application) {
        ScholarshipApplicationDto dto = new ScholarshipApplicationDto();
        EntityUtil.bindToDto(application, dto, "files", "family");
        dto.setStudentGroupCode(application.getStudentGroup().getCode());
        dto.setFiles(
                StreamUtil.toMappedList(file -> ScholarshipFileDto.of(file), application.getScholarshipApplicationFiles()));
        dto.setFamily(StreamUtil.toMappedList(fam -> ScholarshipApplicationFamilyDto.of(fam),
                application.getScholarshipApplicationFamilies()));
        dto.setCanApply(Boolean
                .valueOf(ClassifierUtil.equals(ScholarshipStatus.STIPTOETUS_STAATUS_K, application.getStatus())));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public BigDecimal getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(BigDecimal averageMark) {
        this.averageMark = averageMark;
    }

    public BigDecimal getLastPeriodMark() {
        return lastPeriodMark;
    }

    public void setLastPeriodMark(BigDecimal lastPeriodMark) {
        this.lastPeriodMark = lastPeriodMark;
    }

    public BigDecimal getSaisPoints() {
        return saisPoints;
    }

    public void setSaisPoints(BigDecimal saisPoints) {
        this.saisPoints = saisPoints;
    }

    public BigDecimal getCurriculumCompletion() {
        return curriculumCompletion;
    }

    public void setCurriculumCompletion(BigDecimal curriculumCompletion) {
        this.curriculumCompletion = curriculumCompletion;
    }

    public Long getAbsences() {
        return absences;
    }

    public void setAbsences(Long absences) {
        this.absences = absences;
    }

    public String getStudentGroupCode() {
        return studentGroupCode;
    }

    public void setStudentGroupCode(String studentGroupCode) {
        this.studentGroupCode = studentGroupCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
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

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getRouteKm() {
        return routeKm;
    }

    public void setRouteKm(BigDecimal routeKm) {
        this.routeKm = routeKm;
    }

    public List<ScholarshipFileDto> getFiles() {
        return files;
    }

    public void setFiles(List<ScholarshipFileDto> files) {
        this.files = files;
    }

    public List<ScholarshipApplicationFamilyDto> getFamily() {
        return family;
    }

    public void setFamily(List<ScholarshipApplicationFamilyDto> family) {
        this.family = family;
    }

    public Boolean getCanApply() {
        return canApply;
    }

    public void setCanApply(Boolean canApply) {
        this.canApply = canApply;
    }
}

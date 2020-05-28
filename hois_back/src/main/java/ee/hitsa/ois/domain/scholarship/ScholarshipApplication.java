package ee.hitsa.ois.domain.scholarship;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;

@Entity
public class ScholarshipApplication extends BaseEntityWithId {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "scholarship_term_id", nullable = false, updatable = false)
    private ScholarshipTerm scholarshipTerm;
    private BigDecimal averageMark;
    private BigDecimal curriculumCompletion;
    private String address;
    private String addressAds;
    private String addressAdsOid;
    @Column(nullable = false)
    private String bankAccount;
    private BigDecimal lastPeriodMark;
    private Long absences;
    private String addInfo;
    private String bankAccountOwnerIdcode;
    private String bankAccountOwnerName;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier status;
    private LocalDate decisionDate;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false, updatable = false)
    private Student student;
    private String phone;
    private String email;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private StudentGroup studentGroup;
    @Column(nullable = false)
    private BigDecimal credits;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CurriculumVersion curriculumVersion;
    private Boolean isTeacherConfirmed;
    private LocalDate scholarshipFrom;
    private LocalDate scholarshipThru;
    private Long familyMembers;
    private Long familyMembersAdult;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier compensationReason;
    private BigDecimal routeKm;
    private String compensationAddInfo;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier compensationFrequency;
    private String rejectComment;
    
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private ScholarshipDecision scholarshipDecision;

    private BigDecimal wagMark;
    private BigDecimal lastPeriodWagMark;
    private Boolean isSais;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "scholarshipApplication")
    private List<ScholarshipApplicationFile> scholarshipApplicationFiles = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "scholarshipApplication")
    private List<ScholarshipApplicationFamily> scholarshipApplicationFamilies = new ArrayList<>();

    public ScholarshipTerm getScholarshipTerm() {
        return scholarshipTerm;
    }

    public void setScholarshipTerm(ScholarshipTerm scholarshipTerm) {
        this.scholarshipTerm = scholarshipTerm;
    }

    public BigDecimal getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(BigDecimal averageMark) {
        this.averageMark = averageMark;
    }

    public BigDecimal getCurriculumCompletion() {
        return curriculumCompletion;
    }

    public void setCurriculumCompletion(BigDecimal curriculumCompletion) {
        this.curriculumCompletion = curriculumCompletion;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressAds() {
        return addressAds;
    }

    public void setAddressAds(String addressAds) {
        this.addressAds = addressAds;
    }

    public String getAddressAdsOid() {
        return addressAdsOid;
    }

    public void setAddressAdsOid(String addressAdsOid) {
        this.addressAdsOid = addressAdsOid;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public BigDecimal getLastPeriodMark() {
        return lastPeriodMark;
    }

    public void setLastPeriodMark(BigDecimal lastPeriodMark) {
        this.lastPeriodMark = lastPeriodMark;
    }

    public Long getAbsences() {
        return absences;
    }

    public void setAbsences(Long absences) {
        this.absences = absences;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
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

    public Classifier getStatus() {
        return status;
    }

    public void setStatus(Classifier status) {
        this.status = status;
    }

    public LocalDate getDecisionDate() {
        return decisionDate;
    }

    public void setDecisionDate(LocalDate decisionDate) {
        this.decisionDate = decisionDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public CurriculumVersion getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(CurriculumVersion curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public Boolean getIsTeacherConfirmed() {
        return isTeacherConfirmed;
    }

    public void setIsTeacherConfirmed(Boolean isTeacherConfirmed) {
        this.isTeacherConfirmed = isTeacherConfirmed;
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

    public Classifier getCompensationReason() {
        return compensationReason;
    }

    public void setCompensationReason(Classifier compensationReason) {
        this.compensationReason = compensationReason;
    }

    public BigDecimal getRouteKm() {
        return routeKm;
    }

    public void setRouteKm(BigDecimal routeKm) {
        this.routeKm = routeKm;
    }

    public String getCompensationAddInfo() {
        return compensationAddInfo;
    }

    public void setCompensationAddInfo(String compensationAddInfo) {
        this.compensationAddInfo = compensationAddInfo;
    }

    public Classifier getCompensationFrequency() {
        return compensationFrequency;
    }

    public void setCompensationFrequency(Classifier compensationFrequency) {
        this.compensationFrequency = compensationFrequency;
    }

    public String getRejectComment() {
        return rejectComment;
    }

    public void setRejectComment(String rejectComment) {
        this.rejectComment = rejectComment;
    }

    public ScholarshipDecision getScholarshipDecision() {
        return scholarshipDecision;
    }

    public void setScholarshipDecision(ScholarshipDecision scholarshipDecision) {
        this.scholarshipDecision = scholarshipDecision;
    }

    public BigDecimal getWagMark() {
        return wagMark;
    }

    public void setWagMark(BigDecimal wagMark) {
        this.wagMark = wagMark;
    }

    public BigDecimal getLastPeriodWagMark() {
        return lastPeriodWagMark;
    }

    public void setLastPeriodWagMark(BigDecimal lastPeriodWagMark) {
        this.lastPeriodWagMark = lastPeriodWagMark;
    }

    public Boolean getIsSais() {
        return isSais;
    }

    public void setIsSais(Boolean isSais) {
        this.isSais = isSais;
    }

    public List<ScholarshipApplicationFile> getScholarshipApplicationFiles() {
        return scholarshipApplicationFiles;
    }

    public void setScholarshipApplicationFiles(List<ScholarshipApplicationFile> scholarshipApplicationFiles) {
        this.scholarshipApplicationFiles = scholarshipApplicationFiles;
    }

    public List<ScholarshipApplicationFamily> getScholarshipApplicationFamilies() {
        return scholarshipApplicationFamilies;
    }

    public void setScholarshipApplicationFamilies(List<ScholarshipApplicationFamily> scholarshipApplicationFamilies) {
        this.scholarshipApplicationFamilies = scholarshipApplicationFamilies;
    }
}

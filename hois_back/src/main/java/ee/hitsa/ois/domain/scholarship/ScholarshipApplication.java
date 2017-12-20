package ee.hitsa.ois.domain.scholarship;

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
    private Long averageMark;
    private Long curriculumCompletion;
    private String addressAds;
    private String address;
    @Column(nullable = false)
    private String bankAccount;
    private Long lastPeriodMark;
    private Long absences;
    private String addInfo;
    private String bankAccountOwnerIdcode;
    private String bankAccountOwnerName;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier status;
    private LocalDate decisionDate;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Student student;
    private String phone;
    private String email;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private StudentGroup studentGroup;
    @Column(nullable = false)
    private Long credits;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CurriculumVersion curriculumVersion;
    private Boolean isTeacherConfirmed;
    private LocalDate scholarshipFrom;
    private LocalDate scholarshipThru;
    private Long familyMembers;
    private Long familyMembersAdult;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier compensationReason;
    private Long routeKm;
    private String compensationAddInfo;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier compensationFrequency;

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

    public Long getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(Long averageMark) {
        this.averageMark = averageMark;
    }

    public Long getCurriculumCompletion() {
        return curriculumCompletion;
    }

    public void setCurriculumCompletion(Long curriculumCompletion) {
        this.curriculumCompletion = curriculumCompletion;
    }

    public String getAddressAds() {
        return addressAds;
    }

    public void setAddressAds(String addressAds) {
        this.addressAds = addressAds;
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

    public Long getLastPeriodMark() {
        return lastPeriodMark;
    }

    public void setLastPeriodMark(Long lastPeriodMark) {
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

    public Long getCredits() {
        return credits;
    }

    public void setCredits(Long credits) {
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

    public Long getRouteKm() {
        return routeKm;
    }

    public void setRouteKm(Long routeKm) {
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
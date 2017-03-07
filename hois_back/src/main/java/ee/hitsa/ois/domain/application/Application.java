package ee.hitsa.ois.domain.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.student.Student;

@Entity
public class Application extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = true)
    private Student student;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = true)
    private Classifier type;

    private LocalDateTime submited;

    @Column(name = "is_period")
    private Boolean period;

    private LocalDate startDate;
    private LocalDate endDate;

    @Size(max = 4000)
    @Column(name = "add_info")
    private String additionalInfo;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Classifier reason;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CurriculumVersion oldCurriculumVersion;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CurriculumVersion newCurriculumVersion;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Classifier oldStudyForm;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "old_fin_code", nullable = true)
    private Classifier oldFinancialSource;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "new_fin_code", nullable = true)
    private Classifier newFinancialSource;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "old_fin_specific_code", nullable = true)
    private Classifier oldFinancialSourceSpecification;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "new_fin_specific_code", nullable = true)
    private Classifier newFinancialSourceSpecification;

    @Column(name = "is_abroad")
    private Boolean abroad;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Classifier state;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, updatable = false, insertable = true)
    private Classifier ehisSchool;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Classifier abroadPurpose;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Classifier abroadProgramme;

    private Boolean needsRepresentativeConfirm = Boolean.FALSE;

    @Size(max = 255)
    private String abroadSchool;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "application_id", nullable = false, updatable = false, insertable = true)
    private Set<ApplicationFile> files = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "application_id", nullable = false, updatable = false, insertable = true)
    private Set<ApplicationPlannedSubject> plannedSubjects = new HashSet<>();

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Classifier getStatus() {
        return status;
    }

    public void setStatus(Classifier status) {
        this.status = status;
    }

    public Classifier getType() {
        return type;
    }

    public void setType(Classifier type) {
        this.type = type;
    }

    public LocalDateTime getSubmited() {
        return submited;
    }

    public void setSubmited(LocalDateTime submited) {
        this.submited = submited;
    }

    public Boolean getPeriod() {
        return period;
    }

    public void setPeriod(Boolean period) {
        this.period = period;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Classifier getReason() {
        return reason;
    }

    public void setReason(Classifier reason) {
        this.reason = reason;
    }

    public CurriculumVersion getOldCurriculumVersion() {
        return oldCurriculumVersion;
    }

    public void setOldCurriculumVersion(CurriculumVersion oldCurriculumVersion) {
        this.oldCurriculumVersion = oldCurriculumVersion;
    }

    public CurriculumVersion getNewCurriculumVersion() {
        return newCurriculumVersion;
    }

    public void setNewCurriculumVersion(CurriculumVersion newCurriculumVersion) {
        this.newCurriculumVersion = newCurriculumVersion;
    }

    public Classifier getOldStudyForm() {
        return oldStudyForm;
    }

    public void setOldStudyForm(Classifier oldStudyForm) {
        this.oldStudyForm = oldStudyForm;
    }

    public Classifier getOldFinancialSource() {
        return oldFinancialSource;
    }

    public void setOldFinancialSource(Classifier oldFinancialSource) {
        this.oldFinancialSource = oldFinancialSource;
    }

    public Classifier getNewFinancialSource() {
        return newFinancialSource;
    }

    public void setNewFinancialSource(Classifier newFinancialSource) {
        this.newFinancialSource = newFinancialSource;
    }

    public Classifier getOldFinancialSourceSpecification() {
        return oldFinancialSourceSpecification;
    }

    public void setOldFinancialSourceSpecification(Classifier oldFinancialSourceSpecification) {
        this.oldFinancialSourceSpecification = oldFinancialSourceSpecification;
    }

    public Classifier getNewFinancialSourceSpecification() {
        return newFinancialSourceSpecification;
    }

    public void setNewFinancialSourceSpecification(Classifier newFinancialSourceSpecification) {
        this.newFinancialSourceSpecification = newFinancialSourceSpecification;
    }

    public Boolean getAbroad() {
        return abroad;
    }

    public void setAbroad(Boolean abroad) {
        this.abroad = abroad;
    }

    public Classifier getState() {
        return state;
    }

    public void setState(Classifier state) {
        this.state = state;
    }

    public Classifier getEhisSchool() {
        return ehisSchool;
    }

    public void setEhisSchool(Classifier ehisSchool) {
        this.ehisSchool = ehisSchool;
    }

    public Classifier getAbroadPurpose() {
        return abroadPurpose;
    }

    public void setAbroadPurpose(Classifier abroadPurpose) {
        this.abroadPurpose = abroadPurpose;
    }

    public Classifier getAbroadProgramme() {
        return abroadProgramme;
    }

    public void setAbroadProgramme(Classifier abroadProgramme) {
        this.abroadProgramme = abroadProgramme;
    }

    public Boolean getNeedsRepresentativeConfirm() {
        return needsRepresentativeConfirm;
    }

    public void setNeedsRepresentativeConfirm(Boolean needsRepresentativeConfirm) {
        this.needsRepresentativeConfirm = needsRepresentativeConfirm;
    }

    public String getAbroadSchool() {
        return abroadSchool;
    }

    public void setAbroadSchool(String abroadSchool) {
        this.abroadSchool = abroadSchool;
    }

    public Set<ApplicationFile> getFiles() {
        return files;
    }

    public void setFiles(Set<ApplicationFile> files) {
        this.files = files;
    }

    public Set<ApplicationPlannedSubject> getPlannedSubjects() {
        return plannedSubjects;
    }

    public void setPlannedSubjects(Set<ApplicationPlannedSubject> plannedSubjects) {
        this.plannedSubjects = plannedSubjects;
    }

}

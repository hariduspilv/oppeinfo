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
import ee.hitsa.ois.domain.StudyPeriod;
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

    //TODO:should be submitted
    private LocalDateTime submited;

    @Column(name = "is_period")
    private Boolean period;

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudyPeriod studyPeriodStart;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudyPeriod studyPeriodEnd;

    @Size(max = 4000)
    private String addInfo;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Classifier reason;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CurriculumVersion oldCurriculumVersion;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CurriculumVersion newCurriculumVersion;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Classifier oldStudyForm;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Classifier oldFin;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Classifier newFin;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Classifier oldFinSpecific;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Classifier newFinSpecific;

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

    public StudyPeriod getStudyPeriodStart() {
        return studyPeriodStart;
    }

    public void setStudyPeriodStart(StudyPeriod studyPeriodStart) {
        this.studyPeriodStart = studyPeriodStart;
    }

    public StudyPeriod getStudyPeriodEnd() {
        return studyPeriodEnd;
    }

    public void setStudyPeriodEnd(StudyPeriod studyPeriodEnd) {
        this.studyPeriodEnd = studyPeriodEnd;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
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

    public Classifier getOldFin() {
        return oldFin;
    }

    public void setOldFin(Classifier oldFin) {
        this.oldFin = oldFin;
    }

    public Classifier getNewFin() {
        return newFin;
    }

    public void setNewFin(Classifier newFin) {
        this.newFin = newFin;
    }

    public Classifier getOldFinSpecific() {
        return oldFinSpecific;
    }

    public void setOldFinSpecific(Classifier oldFinSpecific) {
        this.oldFinSpecific = oldFinSpecific;
    }

    public Classifier getNewFinSpecific() {
        return newFinSpecific;
    }

    public void setNewFinSpecific(Classifier newFinSpecific) {
        this.newFinSpecific = newFinSpecific;
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

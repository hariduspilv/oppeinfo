package ee.hitsa.ois.domain.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.util.Period;
import ee.hitsa.ois.validation.ApplicationValidation.Akad;
import ee.hitsa.ois.validation.ApplicationValidation.Akadk;
import ee.hitsa.ois.validation.ApplicationValidation.Eksmat;
import ee.hitsa.ois.validation.ApplicationValidation.Finm;
import ee.hitsa.ois.validation.ApplicationValidation.Muu;
import ee.hitsa.ois.validation.ApplicationValidation.Okava;
import ee.hitsa.ois.validation.ApplicationValidation.Ovorm;
import ee.hitsa.ois.validation.ApplicationValidation.Valis;
import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.validation.PeriodRange;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.validation.StudyPeriodRange;

@Entity
@PeriodRange(groups = {Akad.class, Valis.class})
@DateRange(from = "startDate", thru = "endDate", groups = {Akad.class})
@StudyPeriodRange(from = "studyPeriodStart", thru = "studyPeriodEnd", groups = {Akad.class})
public class Application extends BaseEntityWithId implements Period {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    @Required(groups = {Akad.class, Akadk.class, Okava.class, Ovorm.class, Finm.class, Valis.class, Eksmat.class, Muu.class})
    private Student student;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @Required(groups = {Akad.class, Akadk.class, Okava.class, Ovorm.class, Finm.class, Valis.class, Eksmat.class, Muu.class})
    private Classifier status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    @Required(groups = {Akad.class, Akadk.class, Okava.class, Ovorm.class, Finm.class, Valis.class, Eksmat.class, Muu.class})
    private Classifier type;

    private LocalDateTime submitted;

    @Required(groups = {Akad.class, Valis.class})
    private Boolean isPeriod;

    @Required(groups = {Akadk.class})
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudyPeriod studyPeriodStart;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudyPeriod studyPeriodEnd;

    @Size(max = 4000)
    private String addInfo;

    @Size(max = 4000)
    private String rejectReason;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @Required(groups = {Akad.class, Eksmat.class})
    private Classifier reason;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @Required(groups = {Okava.class})
    private CurriculumVersion oldCurriculumVersion;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @Required(groups = {Okava.class})
    private CurriculumVersion newCurriculumVersion;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @Required(groups = {Okava.class, Ovorm.class})
    private Classifier oldStudyForm;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @Required(groups = {Okava.class, Ovorm.class})
    private Classifier newStudyForm;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @Required(groups = {Finm.class})
    private Classifier oldFin;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @Required(groups = {Finm.class})
    private Classifier newFin;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @Required(groups = {Finm.class})
    private Classifier oldFinSpecific;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @Required(groups = {Finm.class})
    private Classifier newFinSpecific;

    @Required(groups = {Valis.class})
    private Boolean isAbroad;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Classifier country;

    // used in foreign study application, when selecting Estonian school
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Classifier ehisSchool;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @Required(groups = {Valis.class})
    private Classifier abroadPurpose;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @Required(groups = {Valis.class})
    private Classifier abroadProgramme;

    @Required(groups = {Muu.class})
    private String otherText;

    private Boolean needsRepresentativeConfirm = Boolean.FALSE;

    @Size(max = 255)
    private String abroadSchool;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Application academicApplication;
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @Required(groups = {Akadk.class})
    private Directive directive;

    @OneToMany
    @JoinColumn(name = "application_id", nullable = false, updatable = false, insertable = false)
    private List<DirectiveStudent> directiveStudents;

//    @OneToMany
//    @JoinColumn(name = "academic_application_id", nullable = false, updatable = false, insertable = false)
//    private Set<Application> academicApplicationRevocations = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "application_id", nullable = false, updatable = false)
    private Set<ApplicationFile> files = new HashSet<>();

    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "application_id", nullable = false, updatable = false)
    @Required(groups = {Valis.class})
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

    public LocalDateTime getSubmitted() {
        return submitted;
    }

    public void setSubmitted(LocalDateTime submitted) {
        this.submitted = submitted;
    }

    public Classifier getCountry() {
        return country;
    }

    public void setCountry(Classifier country) {
        this.country = country;
    }

    @Override
    public Boolean getIsPeriod() {
        return isPeriod;
    }

    public void setIsPeriod(Boolean isPeriod) {
        this.isPeriod = isPeriod;
    }

    public Boolean getIsAbroad() {
        return isAbroad;
    }

    public void setIsAbroad(Boolean isAbroad) {
        this.isAbroad = isAbroad;
    }

    @Override
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public StudyPeriod getStudyPeriodStart() {
        return studyPeriodStart;
    }

    public void setStudyPeriodStart(StudyPeriod studyPeriodStart) {
        this.studyPeriodStart = studyPeriodStart;
    }

    @Override
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

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
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

    public Classifier getNewStudyForm() {
        return newStudyForm;
    }

    public void setNewStudyForm(Classifier newStudyForm) {
        this.newStudyForm = newStudyForm;
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

    public String getOtherText() {
        return otherText;
    }

    public void setOtherText(String otherText) {
        this.otherText = otherText;
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

    public Application getAcademicApplication() {
        return academicApplication;
    }

    public void setAcademicApplication(Application academicApplication) {
        this.academicApplication = academicApplication;
    }

    public Directive getDirective() {
        return directive;
    }

    public void setDirective(Directive directive) {
        this.directive = directive;
    }

    public List<DirectiveStudent> getDirectiveStudents() {
        return directiveStudents;
    }

    public void setDirectiveStudents(List<DirectiveStudent> directiveStudents) {
        this.directiveStudents = directiveStudents;
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

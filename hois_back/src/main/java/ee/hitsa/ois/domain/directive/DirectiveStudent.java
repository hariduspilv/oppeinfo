package ee.hitsa.ois.domain.directive;

import static ee.hitsa.ois.validation.DirectiveValidation.*;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.SaisApplication;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.curriculum.CurriculumGrade;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.student.StudentHistory;
import ee.hitsa.ois.util.Period;
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.validation.PeriodRange;

@PeriodRange(groups = {Akad.class, Valis.class})
@Entity
public class DirectiveStudent extends BaseEntityWithId implements Period {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Directive directive;
    @NotNull(groups = {Akad.class, Akadk.class, Eksmat.class, Ennist.class, Finm.class, Lopet.class, Okava.class, Okoorm.class, Ovorm.class, Valis.class})
    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;
    @NotNull(groups = {Akadk.class})
    private LocalDate startDate;
    private LocalDate endDate;
    @NotNull(groups = {Akad.class, Eksmat.class})
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier reason;
    @NotNull(groups = {Immat.class, Okoorm.class})
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier studyLoad;
    @NotNull(groups = {Immat.class, Lopet.class, Ovorm.class})
    @ManyToOne(fetch = FetchType.LAZY)
    private CurriculumVersion curriculumVersion;
    @NotNull(groups = {Immat.class, Okava.class, Ovorm.class})
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier studyForm;
    @NotNull(groups = {Ennist.class, Immat.class, Okava.class})
    @ManyToOne(fetch = FetchType.LAZY)
    private StudentGroup studentGroup;
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(groups = {Finm.class, Immat.class, Okoorm.class})
    private Classifier fin;
    @NotNull(groups = {Finm.class, Immat.class, Okoorm.class})
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier finSpecific;
    @NotNull(groups = Immat.class)
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier language;
    // temporary switched off
    // @NotNull(groups = Lopet.class)
    @ManyToOne(fetch = FetchType.LAZY)
    private CurriculumGrade curriculumGrade;
    private Boolean isPeriod;
    @ManyToOne(fetch = FetchType.LAZY)
    private StudyPeriod studyPeriodStart;
    @ManyToOne(fetch = FetchType.LAZY)
    private StudyPeriod studyPeriodEnd;
    @NotNull(groups = Valis.class)
    private Boolean isAbroad;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier ehisSchool;
    @NotNull(groups = Valis.class)
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier country;
    @NotNull(groups = Valis.class)
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier abroadPurpose;
    @NotNull(groups = Valis.class)
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier abroadProgramme;
    private String abroadSchool;
    @NotEmpty(groups = Immat.class)
    private String email;
    @NotNull(groups = Immat.class)
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier previousStudyLevel;
    @NotNull(groups = Lopet.class)
    private Boolean isCumLaude;
    private Boolean isOccupationExamPassed;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier stateLanguageEcts;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private Application application;
    @NotNull(groups = Immat.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private Person person;
    @ManyToOne(fetch = FetchType.LAZY)
    private StudentHistory studentHistory;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private SaisApplication saisApplication;

    public Directive getDirective() {
        return directive;
    }

    public void setDirective(Directive directive) {
        this.directive = directive;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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

    public Classifier getReason() {
        return reason;
    }

    public void setReason(Classifier reason) {
        this.reason = reason;
    }

    public Classifier getStudyLoad() {
        return studyLoad;
    }

    public void setStudyLoad(Classifier studyLoad) {
        this.studyLoad = studyLoad;
    }

    public CurriculumVersion getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(CurriculumVersion curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public Classifier getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(Classifier studyForm) {
        this.studyForm = studyForm;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Classifier getFin() {
        return fin;
    }

    public void setFin(Classifier fin) {
        this.fin = fin;
    }

    public Classifier getFinSpecific() {
        return finSpecific;
    }

    public void setFinSpecific(Classifier finSpecific) {
        this.finSpecific = finSpecific;
    }

    public Classifier getLanguage() {
        return language;
    }

    public void setLanguage(Classifier language) {
        this.language = language;
    }

    public CurriculumGrade getCurriculumGrade() {
        return curriculumGrade;
    }

    public void setCurriculumGrade(CurriculumGrade curriculumGrade) {
        this.curriculumGrade = curriculumGrade;
    }

    @Override
    public Boolean getIsPeriod() {
        return isPeriod;
    }

    public void setIsPeriod(Boolean isPeriod) {
        this.isPeriod = isPeriod;
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

    public Boolean getIsAbroad() {
        return isAbroad;
    }

    public void setIsAbroad(Boolean isAbroad) {
        this.isAbroad = isAbroad;
    }

    public Classifier getEhisSchool() {
        return ehisSchool;
    }

    public void setEhisSchool(Classifier ehisSchool) {
        this.ehisSchool = ehisSchool;
    }

    public Classifier getCountry() {
        return country;
    }

    public void setCountry(Classifier country) {
        this.country = country;
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

    public String getAbroadSchool() {
        return abroadSchool;
    }

    public void setAbroadSchool(String abroadSchool) {
        this.abroadSchool = abroadSchool;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Classifier getPreviousStudyLevel() {
        return previousStudyLevel;
    }

    public void setPreviousStudyLevel(Classifier previousStudyLevel) {
        this.previousStudyLevel = previousStudyLevel;
    }

    public Boolean getIsCumLaude() {
        return isCumLaude;
    }

    public void setIsCumLaude(Boolean isCumLaude) {
        this.isCumLaude = isCumLaude;
    }

    public Boolean getIsOccupationExamPassed() {
        return isOccupationExamPassed;
    }

    public void setIsOccupationExamPassed(Boolean isOccupationExamPassed) {
        this.isOccupationExamPassed = isOccupationExamPassed;
    }

    public Classifier getStateLanguageEcts() {
        return stateLanguageEcts;
    }

    public void setStateLanguageEcts(Classifier stateLanguageEcts) {
        this.stateLanguageEcts = stateLanguageEcts;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public StudentHistory getStudentHistory() {
        return studentHistory;
    }

    public void setStudentHistory(StudentHistory studentHistory) {
        this.studentHistory = studentHistory;
    }

    public SaisApplication getSaisApplication() {
        return saisApplication;
    }

    public void setSaisApplication(SaisApplication saisApplication) {
        this.saisApplication = saisApplication;
    }
}

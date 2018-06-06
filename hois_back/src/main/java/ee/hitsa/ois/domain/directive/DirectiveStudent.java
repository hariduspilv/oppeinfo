package ee.hitsa.ois.domain.directive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.curriculum.CurriculumGrade;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.sais.SaisApplication;
import ee.hitsa.ois.domain.scholarship.ScholarshipApplication;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.student.StudentHistory;
import ee.hitsa.ois.util.Period;
import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.validation.DirectiveValidation.Akad;
import ee.hitsa.ois.validation.DirectiveValidation.Akadk;
import ee.hitsa.ois.validation.DirectiveValidation.Eksmat;
import ee.hitsa.ois.validation.DirectiveValidation.Ennist;
import ee.hitsa.ois.validation.DirectiveValidation.Finm;
import ee.hitsa.ois.validation.DirectiveValidation.Immat;
import ee.hitsa.ois.validation.DirectiveValidation.Lopet;
import ee.hitsa.ois.validation.DirectiveValidation.Okava;
import ee.hitsa.ois.validation.DirectiveValidation.Okoorm;
import ee.hitsa.ois.validation.DirectiveValidation.Ovorm;
import ee.hitsa.ois.validation.DirectiveValidation.Stiptoet;
import ee.hitsa.ois.validation.DirectiveValidation.Stiptoetl;
import ee.hitsa.ois.validation.DirectiveValidation.Valis;
import ee.hitsa.ois.validation.PeriodRange;
import ee.hitsa.ois.validation.Required;

@DateRange(from = "startDate", thru = "endDate", groups = Stiptoet.class)
@PeriodRange(groups = {Akad.class, Valis.class})
@Entity
public class DirectiveStudent extends BaseEntityWithId implements Period {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Directive directive;
    @Required(groups = {Akad.class, Akadk.class, Eksmat.class, Ennist.class, Finm.class, Lopet.class, Okava.class, Okoorm.class, Ovorm.class, Valis.class})
    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;
    @Required(groups = {Akadk.class, Stiptoet.class})
    private LocalDate startDate;
    @Required(groups = Stiptoet.class)
    private LocalDate endDate;
    @Required(groups = {Akad.class, Eksmat.class, Stiptoetl.class})
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier reason;
    @Required(groups = {Okoorm.class}) // Immat is checked by hand (only higher)
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier studyLoad;
    @Required(groups = {Immat.class, Lopet.class, Okava.class})
    @ManyToOne(fetch = FetchType.LAZY)
    private CurriculumVersion curriculumVersion;
    @Required(groups = {Immat.class, Okava.class, Ovorm.class})
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier studyForm;
    @Required(groups = {Ennist.class, Immat.class, Okava.class})
    @ManyToOne(fetch = FetchType.LAZY)
    private StudentGroup studentGroup;
    @ManyToOne(fetch = FetchType.LAZY)
    @Required(groups = {Finm.class, Immat.class, Okoorm.class})
    private Classifier fin;
    @Required(groups = {Finm.class, Immat.class, Okoorm.class})
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier finSpecific;
    @Required(groups = Immat.class)
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier language;
    // temporary switched off
    // @Required(groups = Lopet.class)
    @ManyToOne(fetch = FetchType.LAZY)
    private CurriculumGrade curriculumGrade;
    private Boolean isPeriod;
    @ManyToOne(fetch = FetchType.LAZY)
    private StudyPeriod studyPeriodStart;
    @ManyToOne(fetch = FetchType.LAZY)
    private StudyPeriod studyPeriodEnd;
    @Required(groups = Ennist.class) // Immat is checked by hand
    private LocalDate nominalStudyEnd;
    @Required(groups = Valis.class)
    private Boolean isAbroad;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier ehisSchool;
    @Required(groups = Valis.class)
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier country;
    @Required(groups = Valis.class)
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier abroadPurpose;
    @Required(groups = Valis.class)
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier abroadProgramme;
    private String abroadSchool;
    private String email;
    @Required(groups = Immat.class)
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier previousStudyLevel;
    @Required(groups = Lopet.class)
    private Boolean isCumLaude;
    private Boolean isOccupationExamPassed;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier stateLanguageEcts;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private Application application;
    @Required(groups = Immat.class)
    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;
    @ManyToOne(fetch = FetchType.LAZY)
    private StudentHistory studentHistory;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private SaisApplication saisApplication;
    private Boolean canceled;
    private String bankAccount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private ScholarshipApplication scholarshipApplication;
    @Required(groups = Stiptoet.class)
    private BigDecimal amountPaid;
    @OneToMany(mappedBy = "directiveStudent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DirectiveStudentOccupation> occupations;

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

    public LocalDate getNominalStudyEnd() {
        return nominalStudyEnd;
    }

    public void setNominalStudyEnd(LocalDate nominalStudyEnd) {
        this.nominalStudyEnd = nominalStudyEnd;
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

    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public ScholarshipApplication getScholarshipApplication() {
        return scholarshipApplication;
    }

    public void setScholarshipApplication(ScholarshipApplication scholarshipApplication) {
        this.scholarshipApplication = scholarshipApplication;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public List<DirectiveStudentOccupation> getOccupations() {
        return occupations;
    }

    public void setOccupations(List<DirectiveStudentOccupation> occupations) {
        this.occupations = occupations;
    }
    
}

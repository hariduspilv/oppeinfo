package ee.hitsa.ois.domain.subject;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.NumberFormat;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.school.SchoolDepartment;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodPlan;

@Entity
public class Subject extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private School school;
    private String code;
    private String nameEt;
    private String nameEn;
    private String description;
    @NumberFormat
    private BigDecimal credits;

    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier assessment;

    private String assessmentDescription;
    private String objectivesEt;
    private String objectivesEn;
    private String outcomesEt;
    private String outcomesEn;
    private String descriptionEt;
    private String descriptionEn;
    private String studyLiterature;
    private String evaluationEt;
    private String evaluationEn;
    private String independentStudyEt;
    private String independentStudyEn;
    private String additionalInfo;
    private Boolean isPractice;

    @ManyToOne(fetch = FetchType.LAZY)
    private SchoolDepartment schoolDepartment;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier status;

    @OneToMany(mappedBy = "subject",cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<SubjectLanguage> subjectLanguages;

    @OneToMany(mappedBy = "primarySubject", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubjectConnect> subjectConnections;

    @OneToMany(mappedBy = "connectSubject")
    private Set<SubjectConnect> parentConnections;
    
    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    private Set<SubjectStudyPeriodPlan> subjectStudyPeriodPlans;
    
    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    private Set<SubjectStudyPeriod> subjectStudyPeriods;

    public Set<SubjectStudyPeriod> getSubjectStudyPeriods() {
        return subjectStudyPeriods;
    }

    public void setSubjectStudyPeriods(Set<SubjectStudyPeriod> subjectStudyPeriods) {
        this.subjectStudyPeriods = subjectStudyPeriods;
    }

    public Set<SubjectStudyPeriodPlan> getSubjectStudyPeriodPlans() {
        return subjectStudyPeriodPlans != null ? subjectStudyPeriodPlans : (subjectStudyPeriodPlans = new HashSet<>());
    }

    public void setSubjectStudyPeriodPlans(Set<SubjectStudyPeriodPlan> subjectStudyPeriodPlans) {
        this.subjectStudyPeriodPlans = subjectStudyPeriodPlans;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public Classifier getAssessment() {
        return assessment;
    }

    public void setAssessment(Classifier assessment) {
        this.assessment = assessment;
    }

    public String getAssessmentDescription() {
        return assessmentDescription;
    }

    public void setAssessmentDescription(String assessmentDescription) {
        this.assessmentDescription = assessmentDescription;
    }

    public String getObjectivesEt() {
        return objectivesEt;
    }

    public void setObjectivesEt(String objectivesEt) {
        this.objectivesEt = objectivesEt;
    }

    public String getObjectivesEn() {
        return objectivesEn;
    }

    public void setObjectivesEn(String objectivesEn) {
        this.objectivesEn = objectivesEn;
    }

    public String getOutcomesEt() {
        return outcomesEt;
    }

    public void setOutcomesEt(String outcomesEt) {
        this.outcomesEt = outcomesEt;
    }

    public String getOutcomesEn() {
        return outcomesEn;
    }

    public void setOutcomesEn(String outcomesEn) {
        this.outcomesEn = outcomesEn;
    }

    public String getDescriptionEt() {
        return descriptionEt;
    }

    public void setDescriptionEt(String descriptionEt) {
        this.descriptionEt = descriptionEt;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getStudyLiterature() {
        return studyLiterature;
    }

    public void setStudyLiterature(String studyLiterature) {
        this.studyLiterature = studyLiterature;
    }

    public String getEvaluationEt() {
        return evaluationEt;
    }

    public void setEvaluationEt(String evaluationEt) {
        this.evaluationEt = evaluationEt;
    }

    public String getEvaluationEn() {
        return evaluationEn;
    }

    public void setEvaluationEn(String evaluationEn) {
        this.evaluationEn = evaluationEn;
    }

    public String getIndependentStudyEt() {
        return independentStudyEt;
    }

    public void setIndependentStudyEt(String independentStudyEt) {
        this.independentStudyEt = independentStudyEt;
    }

    public String getIndependentStudyEn() {
        return independentStudyEn;
    }

    public void setIndependentStudyEn(String independentStudyEn) {
        this.independentStudyEn = independentStudyEn;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public SchoolDepartment getSchoolDepartment() {
        return schoolDepartment;
    }

    public void setSchoolDepartment(SchoolDepartment schoolDepartment) {
        this.schoolDepartment = schoolDepartment;
    }

    public Classifier getStatus() {
        return status;
    }

    public void setStatus(Classifier status) {
        this.status = status;
    }

    public Set<SubjectLanguage> getSubjectLanguages() {
        return subjectLanguages != null ? subjectLanguages : (subjectLanguages = new HashSet<>());
    }

    public void setSubjectLanguages(Set<SubjectLanguage> subjectLanguages) {
        this.subjectLanguages = subjectLanguages;
    }

    public Set<SubjectConnect> getSubjectConnections() {
        return subjectConnections != null ? subjectConnections : (subjectConnections = new HashSet<>());
    }

    public void setSubjectConnections(Set<SubjectConnect> subjectConnections) {
        Set<SubjectConnect> connections = getSubjectConnections();
        connections.clear();
        connections.addAll(subjectConnections);
    }

    public Set<SubjectConnect> getParentConnections() {
        return parentConnections != null ? parentConnections : (parentConnections = new HashSet<>());
    }

    public void setParentConnections(Set<SubjectConnect> parentConnections) {
        this.parentConnections = parentConnections;
    }

    public Boolean getIsPractice() {
        return isPractice;
    }

    public void setIsPractice(Boolean isPractice) {
        this.isPractice = isPractice;
    }
}

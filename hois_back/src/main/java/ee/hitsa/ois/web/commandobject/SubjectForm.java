package ee.hitsa.ois.web.commandobject;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ee.hitsa.ois.ClassifierJsonDeserializer;
import ee.hitsa.ois.EntityWithIdJsonDeserializer;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.SchoolDepartment;
import ee.hitsa.ois.domain.Subject;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SubjectForm extends VersionedCommand {

    @NotNull
    @Size(min = 1, max = 20)
    private String code;
    @NotNull
    @Size(min = 1, max = 255)
    private String nameEt;
    @NotNull
    @Size(min = 1, max = 255)
    private String nameEn;
    @Size(min = 1, max = 4000)
    private String description;

    @NotNull
    @DecimalMin("0.1")
    private BigDecimal credits;

    @NotNull
    @JsonDeserialize(using = ClassifierJsonDeserializer.class)
    private Classifier assessment;

    @Size(max = 10000)
    private String assessmentDescription;
    @NotNull
    @Size(min = 1, max = 10000)
    private String objectivesEt;
    @NotNull
    @Size(min = 1, max = 10000)
    private String objectivesEn;
    @NotNull
    @Size(min = 1, max = 10000)
    private String outcomesEt;
    @NotNull
    @Size(min = 1, max = 10000)
    private String outcomesEn;
    @Size(max = 4000)
    private String descriptionEt;
    @Size(max = 4000)
    private String descriptionEn;
    @Size(max = 4000)
    private String studyLiterature;
    @Size(max = 10000)
    private String evaluationEt;
    @Size(max = 10000)
    private String evaluationEn;
    @Size(max = 4000)
    private String independentStudyEt;
    @Size(max = 4000)
    private String independentStudyEn;
    @Size(max = 4000)
    private String additionalInfo;

    @JsonDeserialize(contentUsing = ClassifierJsonDeserializer.class)
    private Set<Classifier> languages = new HashSet<>();

    @JsonDeserialize(using = EntityWithIdJsonDeserializer.class)
    private SchoolDepartment schoolDepartment;

    @NotNull
    @JsonDeserialize(using = ClassifierJsonDeserializer.class)
    private Classifier status;

    @JsonDeserialize(contentUsing = EntityWithIdJsonDeserializer.class)
    private Set<Subject> mandatoryPrerequisiteSubjects;

    @JsonDeserialize(contentUsing = EntityWithIdJsonDeserializer.class)
    private Set<Subject> recommendedPrerequisiteSubjects;

    @JsonDeserialize(contentUsing = EntityWithIdJsonDeserializer.class)
    private Set<Subject> substituteSubjects;

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

    public Classifier getStatus() {
        return status;
    }

    public void setStatus(Classifier status) {
        this.status = status;
    }

    public Set<Classifier> getLanguages() {
        return languages != null ? languages : Collections.emptySet();
    }

    public void setLanguages(Set<Classifier> languages) {
        this.languages = languages;
    }

    public SchoolDepartment getSchoolDepartment() {
        return schoolDepartment;
    }

    public void setSchoolDepartment(SchoolDepartment schoolDepartment) {
        this.schoolDepartment = schoolDepartment;
    }

    public Set<Subject> getMandatoryPrerequisiteSubjects() {
        return mandatoryPrerequisiteSubjects != null ? mandatoryPrerequisiteSubjects : Collections.emptySet();
    }

    public void setMandatoryPrerequisiteSubjects(Set<Subject> mandatoryPrerequisiteSubjects) {
        this.mandatoryPrerequisiteSubjects = mandatoryPrerequisiteSubjects;
    }

    public Set<Subject> getRecommendedPrerequisiteSubjects() {
        return recommendedPrerequisiteSubjects != null ? recommendedPrerequisiteSubjects : Collections.emptySet();
    }

    public void setRecommendedPrerequisiteSubjects(Set<Subject> recommendedPrerequisiteSubjects) {
        this.recommendedPrerequisiteSubjects = recommendedPrerequisiteSubjects;
    }

    public Set<Subject> getSubstituteSubjects() {
        return substituteSubjects != null ? substituteSubjects : Collections.emptySet();
    }

    public void setSubstituteSubjects(Set<Subject> substituteSubjects) {
        this.substituteSubjects = substituteSubjects;
    }
}

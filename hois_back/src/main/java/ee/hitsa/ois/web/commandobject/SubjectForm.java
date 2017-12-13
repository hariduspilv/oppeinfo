package ee.hitsa.ois.web.commandobject;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class SubjectForm extends VersionedCommand {

    @NotEmpty
    @Size(max = 20)
    private String code;
    @NotEmpty
    @Size(max = 255)
    private String nameEt;
    @NotEmpty
    @Size(max = 255)
    private String nameEn;
    @Size(min = 1, max = 4000)
    private String description;

    @NotNull
    @DecimalMin("0.1")
    private BigDecimal credits;

    @NotEmpty
    @ClassifierRestriction(MainClassCode.HINDAMISVIIS)
    private String assessment;

    @Size(max = 10000)
    private String assessmentDescription;
    @NotEmpty
    @Size(max = 10000)
    private String objectivesEt;
    @NotEmpty
    @Size(max = 10000)
    private String objectivesEn;
    @NotEmpty
    @Size(max = 10000)
    private String outcomesEt;
    @NotEmpty
    @Size(max = 10000)
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
    
    @NotNull
    private Boolean isPractice = Boolean.FALSE;

    @ClassifierRestriction(MainClassCode.OPPEKEEL)
    private Set<String> languages = new HashSet<>();

    private AutocompleteResult schoolDepartment;

    @NotEmpty
    @ClassifierRestriction(MainClassCode.AINESTAATUS)
    private String status;

    private Set<EntityConnectionCommand> mandatoryPrerequisiteSubjects;

    private Set<EntityConnectionCommand> recommendedPrerequisiteSubjects;

    private Set<EntityConnectionCommand> substituteSubjects;

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

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<String> getLanguages() {
        return languages != null ? languages : Collections.emptySet();
    }

    public void setLanguages(Set<String> languages) {
        this.languages = languages;
    }

    public AutocompleteResult getSchoolDepartment() {
        return schoolDepartment;
    }

    public void setSchoolDepartment(AutocompleteResult schoolDepartment) {
        this.schoolDepartment = schoolDepartment;
    }

    public Set<EntityConnectionCommand> getMandatoryPrerequisiteSubjects() {
        return mandatoryPrerequisiteSubjects != null ? mandatoryPrerequisiteSubjects : Collections.emptySet();
    }

    public void setMandatoryPrerequisiteSubjects(Set<EntityConnectionCommand> mandatoryPrerequisiteSubjects) {
        this.mandatoryPrerequisiteSubjects = mandatoryPrerequisiteSubjects;
    }

    public Set<EntityConnectionCommand> getRecommendedPrerequisiteSubjects() {
        return recommendedPrerequisiteSubjects != null ? recommendedPrerequisiteSubjects : Collections.emptySet();
    }

    public void setRecommendedPrerequisiteSubjects(Set<EntityConnectionCommand> recommendedPrerequisiteSubjects) {
        this.recommendedPrerequisiteSubjects = recommendedPrerequisiteSubjects;
    }

    public Set<EntityConnectionCommand> getSubstituteSubjects() {
        return substituteSubjects != null ? substituteSubjects : Collections.emptySet();
    }

    public void setSubstituteSubjects(Set<EntityConnectionCommand> substituteSubjects) {
        this.substituteSubjects = substituteSubjects;
    }

    public Boolean getIsPractice() {
        return isPractice;
    }

    public void setIsPractice(Boolean isPractice) {
        this.isPractice = isPractice;
    }
}

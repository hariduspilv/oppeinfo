package ee.hitsa.ois.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ee.hitsa.ois.enums.SubjectConnection;
import ee.hitsa.ois.web.dto.AutocompleteResult;

import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Subject extends BaseEntityWithId {

    @JsonIgnore
    @ManyToOne(optional = false)
    private School school;
    private String code;
    private String nameEt;
    private String nameEn;
    private String description;
    @NumberFormat
    private BigDecimal credits;

    @ManyToOne
    private Classifier assessment;

    @Column(name = "assessment_descr")
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

    @ManyToOne
    private SchoolDepartment schoolDepartment;

    @ManyToOne(optional = false)
    private Classifier status;

    @JsonIgnore
    @OneToMany(mappedBy = "subject",cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<SubjectLanguage> subjectLanguages;

    @JsonIgnore
    @OneToMany(mappedBy = "primarySubject", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubjectConnect> subjectConnecions;

    @JsonIgnore
    @OneToMany(mappedBy = "connectSubject")
    private Set<SubjectConnect> parentConnections;

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
        return subjectLanguages != null ? subjectLanguages : Collections.emptySet();
    }

    public void setSubjectLanguages(Set<SubjectLanguage> subjectLanguages) {
        Set<SubjectLanguage> languages = getSubjectLanguages();
        languages.clear();
        languages.addAll(subjectLanguages);
    }

    public Set<Classifier> getLanguages() {
        return getSubjectLanguages().stream().map(SubjectLanguage::getLanguage).collect(Collectors.toSet());
    }

    public Set<SubjectConnect> getSubjectConnecions() {
        return subjectConnecions != null ? subjectConnecions : Collections.emptySet();
    }

    public void setSubjectConnecions(Set<SubjectConnect> subjectConnecions) {
        Set<SubjectConnect> connections = getSubjectConnecions();
        connections.clear();
        connections.addAll(subjectConnecions);
    }

    public Set<SubjectConnect> getParentConnections() {
        return parentConnections != null ? parentConnections : Collections.emptySet();
    }

    public void setParentConnections(Set<SubjectConnect> parentConnections) {
        this.parentConnections = parentConnections;
    }

    public Set<AutocompleteResult<Long>> getMandatoryPrerequisiteSubjects() {
        return subjectConnections(SubjectConnection.AINESEOS_EK);
    }

    public Set<AutocompleteResult<Long>> getRecommendedPrerequisiteSubjects() {
        return subjectConnections(SubjectConnection.AINESEOS_EV);
    }

    public Set<AutocompleteResult<Long>> getsubstituteSubjects() {
        return subjectConnections(SubjectConnection.AINESEOS_A);
    }

    public Set<AutocompleteResult<Long>> getPrimarySubjects() {
        return getParentConnections().stream()
                .filter(it -> it.getConnection().getCode().equals(SubjectConnection.AINESEOS_EK.name()))
                .map(it -> AutocompleteResult.of(it.getPrimarySubject()))
                .collect(Collectors.toSet());
    }

    private Set<AutocompleteResult<Long>> subjectConnections(SubjectConnection type) {
        return getSubjectConnecions().stream()
                .filter(it -> type.name().equals(it.getConnection().getCode()))
                .map(it -> AutocompleteResult.of(it.getConnectSubject()))
                .collect(Collectors.toSet());
    }
}

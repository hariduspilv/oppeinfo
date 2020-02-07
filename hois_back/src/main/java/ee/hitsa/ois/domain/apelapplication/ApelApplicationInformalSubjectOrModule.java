package ee.hitsa.ois.domain.apelapplication;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.validation.Conditional;
import ee.hitsa.ois.validation.Required;

@Entity
@Conditional(selected = "apelApplicationRecord.apelApplication.isVocational", values = {"true"}, required = {"curriculumVersionOmodule"})
@Conditional(selected = "apelApplicationRecord.apelApplication.isVocational", values = {"false"}, required = {"subject"})
public class ApelApplicationInformalSubjectOrModule extends BaseEntityWithId {
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private ApelApplicationRecord apelApplicationRecord;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private CurriculumVersionHigherModule curriculumVersionHmodule;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private CurriculumVersionOccupationModuleTheme curriculumVersionOmoduleTheme;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private CurriculumVersionOccupationModule curriculumVersionOmodule;

    @OneToMany(mappedBy="apelApplicationInformalSubjectOrModule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApelApplicationInformalSubjectOrModuleOutcomes> outcomes = new ArrayList<>();

    @Required
    private String skills;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier grade;
    
    private Boolean isOptional;
    private Boolean transfer = Boolean.FALSE;

    public ApelApplicationRecord getApelApplicationRecord() {
        return apelApplicationRecord;
    }

    public void setApelApplicationRecord(ApelApplicationRecord apelApplicationRecord) {
        this.apelApplicationRecord = apelApplicationRecord;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public CurriculumVersionHigherModule getCurriculumVersionHmodule() {
        return curriculumVersionHmodule;
    }

    public void setCurriculumVersionHmodule(CurriculumVersionHigherModule curriculumVersionHmodule) {
        this.curriculumVersionHmodule = curriculumVersionHmodule;
    }

    public CurriculumVersionOccupationModuleTheme getCurriculumVersionOmoduleTheme() {
        return curriculumVersionOmoduleTheme;
    }

    public void setCurriculumVersionOmoduleTheme(CurriculumVersionOccupationModuleTheme curriculumVersionOmoduleTheme) {
        this.curriculumVersionOmoduleTheme = curriculumVersionOmoduleTheme;
    }

    public CurriculumVersionOccupationModule getCurriculumVersionOmodule() {
        return curriculumVersionOmodule;
    }

    public void setCurriculumVersionOmodule(CurriculumVersionOccupationModule curriculumVersionOmodule) {
        this.curriculumVersionOmodule = curriculumVersionOmodule;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Classifier getGrade() {
        return grade;
    }

    public void setGrade(Classifier grade) {
        this.grade = grade;
    }

    public Boolean getIsOptional() {
        return isOptional;
    }

    public void setIsOptional(Boolean isOptional) {
        this.isOptional = isOptional;
    }

    public Boolean getTransfer() {
        return transfer;
    }

    public void setTransfer(Boolean transfer) {
        this.transfer = transfer;
    }

    public List<ApelApplicationInformalSubjectOrModuleOutcomes> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<ApelApplicationInformalSubjectOrModuleOutcomes> outcomes) {
        this.outcomes = outcomes;
    }
    
}

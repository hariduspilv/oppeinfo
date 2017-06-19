package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;

@Entity
public class DeclarationSubject extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private Declaration declaration;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private SubjectStudyPeriod subjectStudyPeriod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_version_hmodule_id")
    private CurriculumVersionHigherModule module;

    private Boolean isOptional;

    public Declaration getDeclaration() {
        return declaration;
    }
    public void setDeclaration(Declaration declaration) {
        this.declaration = declaration;
    }
    public SubjectStudyPeriod getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }
    public void setSubjectStudyPeriod(SubjectStudyPeriod subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }
    public CurriculumVersionHigherModule getModule() {
        return module;
    }
    public void setModule(CurriculumVersionHigherModule module) {
        this.module = module;
    }
    public Boolean getIsOptional() {
        return isOptional;
    }
    public void setIsOptional(Boolean isOptional) {
        this.isOptional = isOptional;
    }
}

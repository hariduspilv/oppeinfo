package ee.hitsa.ois.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
    private Boolean isMoodleRegistered;
    
    @OneToMany(mappedBy = "declarationSubject", fetch = FetchType.LAZY)
    private Set<MidtermTaskStudentResult> midtermTaskStudentResults;

    public Set<MidtermTaskStudentResult> getMidtermTaskStudentResults() {
        return midtermTaskStudentResults != null ? midtermTaskStudentResults : (midtermTaskStudentResults = new HashSet<>());
    }
    public void setMidtermTaskStudentResults(Set<MidtermTaskStudentResult> midtermTaskStudentResults) {
        this.midtermTaskStudentResults = midtermTaskStudentResults;
    }
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
    public Boolean getIsMoodleRegistered() {
        return isMoodleRegistered;
    }
    public void setIsMoodleRegistered(Boolean isMoodleRegistered) {
        this.isMoodleRegistered = isMoodleRegistered;
    }
    
}

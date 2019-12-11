package ee.hitsa.ois.domain.application;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.validation.ApplicationValidation.Valis;
import ee.hitsa.ois.validation.Conditional;

@Entity
@Conditional(selected = "subject", values = {"null"}, required = {"curriculumVersionOmodule"}, groups = {Valis.class})
@Conditional(selected = "curriculumVersionOmodule", values = {"null"}, required = {"subject"}, groups = {Valis.class})
public class ApplicationPlannedSubjectEquivalent extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private ApplicationPlannedSubject applicationPlannedSubject;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Subject subject;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private CurriculumVersionOccupationModule curriculumVersionOmodule;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private CurriculumVersionOccupationModuleTheme curriculumVersionOmoduleTheme;

    public ApplicationPlannedSubject getApplicationPlannedSubject() {
        return applicationPlannedSubject;
    }

    public void setApplicationPlannedSubject(ApplicationPlannedSubject applicationPlannedSubject) {
        this.applicationPlannedSubject = applicationPlannedSubject;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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
}

package ee.hitsa.ois.domain.application;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.validation.ApplicationValidation.Valis;

@Entity
public class ApplicationPlannedSubjectEquivalent extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private ApplicationPlannedSubject applicationPlannedSubject;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    @NotNull(groups = {Valis.class})
    private Subject subject;

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

}

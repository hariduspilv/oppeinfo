package ee.hitsa.ois.domain.curriculum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.subject.Subject;

@Entity
@Table(name="curriculum_version_hmodule_subject")
public class CurriculumVersionHigherModuleSubject extends BaseEntityWithId {
    
    @NotNull
    @Column(name="is_optional")
    private Boolean optional;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Subject subject;

    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}

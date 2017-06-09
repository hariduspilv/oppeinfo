package ee.hitsa.ois.domain.curriculum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.subject.Subject;

@Entity
@Table(name="curriculum_version_hmodule_subject")
public class CurriculumVersionHigherModuleSubject extends BaseEntityWithId {
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="curriculum_version_hmodule_id",  nullable = false, updatable = false)
    private CurriculumVersionHigherModule module;
    
    @Column(name="is_optional")
    private Boolean optional;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Subject subject;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_version_elective_module_id")
    private CurriculumVersionElectiveModule electiveModule;

    public CurriculumVersionElectiveModule getElectiveModule() {
        return electiveModule;
    }

    public void setElectiveModule(CurriculumVersionElectiveModule electiveModule) {
        this.electiveModule = electiveModule;
    }

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

    public CurriculumVersionHigherModule getModule() {
        return module;
    }

    public void setModule(CurriculumVersionHigherModule module) {
        this.module = module;
    }
}

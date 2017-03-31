package ee.hitsa.ois.domain.curriculum;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
@Table(name="curriculum_version_hmodule_speciality")
public class CurriculumVersionHigherModuleSpeciality extends BaseEntityWithId {
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_version_speciality_id", referencedColumnName = "id", nullable = false, updatable = false)
    private CurriculumVersionSpeciality speciality;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_version_hmodule_id", referencedColumnName = "id", nullable = false, updatable = false)
    private CurriculumVersionHigherModule module;

    public CurriculumVersionSpeciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(CurriculumVersionSpeciality speciality) {
        this.speciality = speciality;
    }

    public CurriculumVersionHigherModule getModule() {
        return module;
    }

    public void setModule(CurriculumVersionHigherModule module) {
        this.module = module;
    }
}

package ee.hitsa.ois.domain.curriculum;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ee.hitsa.ois.domain.BaseEntityWithId;


/*
 * Caused by: org.hibernate.MappingException: 
 * Repeated column in mapping for entity: 
 * ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSpeciality 
 * column: curriculum_version_hmodule_id (should be mapped with insert="false" update="false")

 */

@Entity
@Table(name="curriculum_version_hmodule_speciality")
public class CurriculumVersionHigherModuleSpeciality extends BaseEntityWithId {
    
    @ManyToOne
    @JoinColumn(name = "curriculum_version_speciality_id", referencedColumnName = "id")
    private CurriculumVersionSpeciality speciality;

    public CurriculumVersionSpeciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(CurriculumVersionSpeciality speciality) {
        this.speciality = speciality;
    }
}

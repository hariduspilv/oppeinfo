package ee.hitsa.ois.domain.curriculum;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class CurriculumVersionSpeciality extends BaseEntityWithId {
    
    @ManyToOne(optional = false)
    private CurriculumSpeciality curriculumSpeciality;

    public CurriculumSpeciality getCurriculumSpeciality() {
        return curriculumSpeciality;
    }
    public void setCurriculumSpeciality(CurriculumSpeciality curriculumSpeciality) {
        this.curriculumSpeciality = curriculumSpeciality;
    }
}

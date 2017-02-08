package ee.hitsa.ois.domain.curriculum;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class CurriculumVersionSpeciality extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = true)
    private CurriculumSpeciality curriculumSpeciality;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, updatable = false, insertable = true)
    private CurriculumVersion curriculumVersion;
    
    public CurriculumVersionSpeciality() {
    }
    
    public CurriculumVersion getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(CurriculumVersion curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public CurriculumSpeciality getCurriculumSpeciality() {
        return curriculumSpeciality;
    }
    public void setCurriculumSpeciality(CurriculumSpeciality curriculumSpeciality) {
        this.curriculumSpeciality = curriculumSpeciality;
    }
}

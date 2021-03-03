package ee.hitsa.ois.domain.curriculum;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class CurriculumModuleStudyField extends BaseEntityWithId {
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(insertable=false, nullable = false, updatable = false)
    private CurriculumModule curriculumModule;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private CurriculumStudyField curriculumStudyField;

    public CurriculumModule getCurriculumModule() {
        return curriculumModule;
    }

    public void setCurriculumModule(CurriculumModule curriculumModule) {
        this.curriculumModule = curriculumModule;
    }

    public CurriculumStudyField getCurriculumStudyField() {
        return curriculumStudyField;
    }

    public void setCurriculumStudyField(CurriculumStudyField curriculumStudyField) {
        this.curriculumStudyField = curriculumStudyField;
    }

}

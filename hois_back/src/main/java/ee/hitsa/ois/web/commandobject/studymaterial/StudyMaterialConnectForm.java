package ee.hitsa.ois.web.commandobject.studymaterial;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.web.dto.InsertedChangedVersionDto;

public class StudyMaterialConnectForm extends InsertedChangedVersionDto {

    @NotNull
    private Long studyMaterial;
    private Long journal;
    private Long subjectStudyPeriod;
    
    public Long getStudyMaterial() {
        return studyMaterial;
    }
    public void setStudyMaterial(Long studyMaterial) {
        this.studyMaterial = studyMaterial;
    }
    
    public Long getJournal() {
        return journal;
    }
    public void setJournal(Long journal) {
        this.journal = journal;
    }
    
    public Long getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }
    public void setSubjectStudyPeriod(Long subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }
    
}

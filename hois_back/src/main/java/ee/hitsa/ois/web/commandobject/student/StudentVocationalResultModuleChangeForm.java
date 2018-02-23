package ee.hitsa.ois.web.commandobject.student;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class StudentVocationalResultModuleChangeForm extends VersionedCommand {
    
    @NotNull
    private Long id;
    @NotNull
    private Long oldCurriculumVersionOmoduleId;
    @NotNull
    private Long curriculumVersionOmoduleId;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getOldCurriculumVersionOmoduleId() {
        return oldCurriculumVersionOmoduleId;
    }

    public void setOldCurriculumVersionOmoduleId(Long oldCurriculumVersionOmoduleId) {
        this.oldCurriculumVersionOmoduleId = oldCurriculumVersionOmoduleId;
    }

    public Long getCurriculumVersionOmoduleId() {
        return curriculumVersionOmoduleId;
    }

    public void setCurriculumVersionOmoduleId(Long curriculumVersionOmoduleId) {
        this.curriculumVersionOmoduleId = curriculumVersionOmoduleId;
    }
    
}

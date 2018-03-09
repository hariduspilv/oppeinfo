package ee.hitsa.ois.web.commandobject.student;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class StudentResultModuleChangeForm extends VersionedCommand {
    
    @NotNull
    private Long id;
    private Long oldCurriculumVersionModuleId;
    private Long curriculumVersionModuleId;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getOldCurriculumVersionModuleId() {
        return oldCurriculumVersionModuleId;
    }

    public void setOldCurriculumVersionModuleId(Long oldCurriculumVersionModuleId) {
        this.oldCurriculumVersionModuleId = oldCurriculumVersionModuleId;
    }

    public Long getCurriculumVersionModuleId() {
        return curriculumVersionModuleId;
    }

    public void setCurriculumVersionModuleId(Long curriculumVersionModuleId) {
        this.curriculumVersionModuleId = curriculumVersionModuleId;
    }
    
}

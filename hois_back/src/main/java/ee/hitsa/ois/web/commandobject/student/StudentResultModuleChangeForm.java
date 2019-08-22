package ee.hitsa.ois.web.commandobject.student;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class StudentResultModuleChangeForm extends VersionedCommand {

    @NotNull
    private Long id;
    private Long oldCurriculumVersionModuleId;
    private Long curriculumVersionModuleId;
    private Boolean oldIsOptional;
    private Boolean isOptional;

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

    public Boolean getOldIsOptional() {
        return oldIsOptional;
    }

    public void setOldIsOptional(Boolean oldIsOptional) {
        this.oldIsOptional = oldIsOptional;
    }

    public Boolean getIsOptional() {
        return isOptional;
    }

    public void setIsOptional(Boolean isOptional) {
        this.isOptional = isOptional;
    }

}

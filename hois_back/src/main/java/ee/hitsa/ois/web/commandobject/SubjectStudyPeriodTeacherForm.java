package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.NotNull;

public class SubjectStudyPeriodTeacherForm extends VersionedCommand {
    @NotNull
    private Long teacherId;
    @NotNull
    private Boolean isSignatory = Boolean.FALSE;

    public Long getTeacherId() {
        return teacherId;
    }
    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
    public Boolean getIsSignatory() {
        return isSignatory;
    }
    public void setIsSignatory(Boolean isSignatory) {
        this.isSignatory = isSignatory;
    }
}

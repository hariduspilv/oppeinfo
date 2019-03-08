package ee.hitsa.ois.web.commandobject.subject.studyperiod;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodCapacityDto;

public class SubjectStudyPeriodTeacherForm extends VersionedCommand {
    @NotNull
    private Long teacherId;
    @NotNull
    private Boolean isSignatory = Boolean.FALSE;
    private List<SubjectStudyPeriodCapacityDto> capacities;

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

    public List<SubjectStudyPeriodCapacityDto> getCapacities() {
        return capacities != null ? capacities : (capacities = new ArrayList<>());
    }

    public void setCapacities(List<SubjectStudyPeriodCapacityDto> capacities) {
        this.capacities = capacities;
    }

}

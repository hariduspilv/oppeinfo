package ee.hitsa.ois.web.commandobject;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import ee.hitsa.ois.domain.MidtermTask;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.MidtermTaskDto;

public class MidtermTaskUpdateForm {

    @Valid
    private Set<MidtermTaskDto> midtermTasks;
    /**
     * Is set in controller
     */
    private Boolean canBeEdited;

    public static MidtermTaskUpdateForm of(SubjectStudyPeriod subjectStudyPeriod) {
        MidtermTaskUpdateForm form = new MidtermTaskUpdateForm();
        form.setMidtermTasks(StreamUtil.toMappedSet(MidtermTaskDto::of, subjectStudyPeriod.getMidtermTasks()));
        return form;
    }
    
    public static MidtermTaskUpdateForm ofWithCopiedMidtermTasks(List<MidtermTask> midtermTasks) {
        MidtermTaskUpdateForm form = new MidtermTaskUpdateForm();
        form.setMidtermTasks(StreamUtil.toMappedSet(MidtermTaskDto::of, midtermTasks));
        return form;
    }

    public Boolean getCanBeEdited() {
        return canBeEdited;
    }

    public void setCanBeEdited(Boolean canBeEdited) {
        this.canBeEdited = canBeEdited;
    }

    public Set<MidtermTaskDto> getMidtermTasks() {
        return midtermTasks;
    }

    public void setMidtermTasks(Set<MidtermTaskDto> midtermTasks) {
        this.midtermTasks = midtermTasks;
    }
}

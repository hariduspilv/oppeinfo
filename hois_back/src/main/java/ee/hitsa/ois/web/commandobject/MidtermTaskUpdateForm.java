package ee.hitsa.ois.web.commandobject;

import java.util.Set;

import javax.validation.Valid;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.MidtermTaskDto;

public class MidtermTaskUpdateForm {
    
    private Long subjectStudyPeriod;

    @Valid
    private Set<MidtermTaskDto> midtermTasks;
    private Boolean canBeEdited;

    public Long getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }
    
    public static MidtermTaskUpdateForm of(SubjectStudyPeriod subjectStudyPeriod) {
        MidtermTaskUpdateForm form = new MidtermTaskUpdateForm();
        form.setSubjectStudyPeriod(EntityUtil.getId(subjectStudyPeriod));
        form.setMidtermTasks(StreamUtil.toMappedSet(MidtermTaskDto::of, subjectStudyPeriod.getMidtermTasks()));
        return form;
    }

    public Boolean getCanBeEdited() {
        return canBeEdited;
    }

    public void setCanBeEdited(Boolean canBeEdited) {
        this.canBeEdited = canBeEdited;
    }

    public void setSubjectStudyPeriod(Long subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }

    public Set<MidtermTaskDto> getMidtermTasks() {
        return midtermTasks;
    }

    public void setMidtermTasks(Set<MidtermTaskDto> midtermTasks) {
        this.midtermTasks = midtermTasks;
    }
}

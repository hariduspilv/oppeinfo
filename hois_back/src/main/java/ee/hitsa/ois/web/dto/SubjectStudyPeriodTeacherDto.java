package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodTeacherForm;

public class SubjectStudyPeriodTeacherDto extends SubjectStudyPeriodTeacherForm {

    private String name;
    private Short scheduleLoad;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getScheduleLoad() {
        return scheduleLoad;
    }

    public void setScheduleLoad(Short scheduleLoad) {
        this.scheduleLoad = scheduleLoad;
    }

    public static SubjectStudyPeriodTeacherDto of(SubjectStudyPeriodTeacher t) {
        SubjectStudyPeriodTeacherDto dto = new SubjectStudyPeriodTeacherDto();
        dto.setIsSignatory(t.getIsSignatory());
        dto.setName(t.getTeacher().getPerson().getFullname());
        dto.setTeacherId(t.getTeacher().getId());
        dto.setVersion(t.getVersion());
        dto.setScheduleLoad(t.getTeacher().getScheduleLoad());
        return dto;
    }
}

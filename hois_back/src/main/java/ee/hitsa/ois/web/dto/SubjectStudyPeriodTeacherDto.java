package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.subject.studyperiod.SubjectStudyPeriodTeacherForm;

public class SubjectStudyPeriodTeacherDto extends SubjectStudyPeriodTeacherForm {

    private String name;
    private Short scheduleLoad;
    private Short plannedLessons;

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

    public Short getPlannedLessons() {
        return plannedLessons;
    }

    public void setPlannedLessons(Short plannedLessons) {
        this.plannedLessons = plannedLessons;
    }

    public static SubjectStudyPeriodTeacherDto of(SubjectStudyPeriodTeacher t) {
        SubjectStudyPeriodTeacherDto dto = new SubjectStudyPeriodTeacherDto();
        dto.setIsSignatory(t.getIsSignatory());
        dto.setName(t.getTeacher().getPerson().getFullname());
        dto.setTeacherId(t.getTeacher().getId());
        dto.setVersion(t.getVersion());
        dto.setScheduleLoad(t.getTeacher().getScheduleLoad());
        dto.setCapacities(StreamUtil.toMappedList(c -> SubjectStudyPeriodCapacityDto.of(c), t.getCapacities()));
        return dto;
    }

    public static SubjectStudyPeriodTeacherDto of(SubjectStudyPeriodTeacher t, Short plannedLessons) {
        SubjectStudyPeriodTeacherDto dto = SubjectStudyPeriodTeacherDto.of(t);
        dto.setPlannedLessons(plannedLessons);
        return dto;
    }
}

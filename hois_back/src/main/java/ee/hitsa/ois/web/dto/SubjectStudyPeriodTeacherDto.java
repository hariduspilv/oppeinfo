package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacherCapacity;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.subject.studyperiod.SubjectStudyPeriodTeacherForm;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SubjectStudyPeriodTeacherDto extends SubjectStudyPeriodTeacherForm {

    private String name;
    private Short scheduleLoad;
    private Integer plannedLessons;
    private Long subject;

    // additional for container
    private Long sspId;

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

    public Integer getPlannedLessons() {
        return plannedLessons;
    }

    public void setPlannedLessons(Integer plannedLessons) {
        this.plannedLessons = plannedLessons;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public Long getSspId() {
        return sspId;
    }

    public void setSspId(Long sspId) {
        this.sspId = sspId;
    }

    public static SubjectStudyPeriodTeacherDto of(SubjectStudyPeriodTeacher t) {
        return of(t, false);
    }

    public static SubjectStudyPeriodTeacherDto of(SubjectStudyPeriodTeacher t, boolean onlyMainCapacity) {
        SubjectStudyPeriodTeacherDto dto = new SubjectStudyPeriodTeacherDto();
        dto.setIsSignatory(t.getIsSignatory());
        dto.setName(t.getTeacher().getPerson().getFullname());
        dto.setTeacherId(t.getTeacher().getId());
        dto.setVersion(t.getVersion());
        dto.setScheduleLoad(t.getTeacher().getScheduleLoad());
        if (onlyMainCapacity) {
            Map<String, SubjectStudyPeriodTeacherCapacity> mappedCap = StreamUtil.nullSafeSet(t.getCapacities()).stream()
                    .sorted(Comparator
                            .comparingInt((SubjectStudyPeriodTeacherCapacity cap) -> cap.getSubgroup() == null ? 0 : 1)
                            .thenComparing(Comparator.comparingInt(SubjectStudyPeriodTeacherCapacity::getHours).reversed()))
                    .collect(Collectors.toMap((SubjectStudyPeriodTeacherCapacity cap) ->
                                    EntityUtil.getCode(cap.getSubjectStudyPeriodCapacity().getCapacityType()), cap -> cap,
                            (o, n) -> o, LinkedHashMap::new));
            dto.setCapacities(StreamUtil.toMappedList(SubjectStudyPeriodCapacityDto::of, mappedCap.values()));
        } else {
            dto.setCapacities(StreamUtil.toMappedList(SubjectStudyPeriodCapacityDto::of, t.getCapacities()));
        }
        dto.setSubject(EntityUtil.getId(t.getSubjectStudyPeriod().getSubject()));
        dto.setSspId(EntityUtil.getId(t.getSubjectStudyPeriod()));
        return dto;
    }

    public static SubjectStudyPeriodTeacherDto of(SubjectStudyPeriodTeacher t, Integer plannedLessons) {
        SubjectStudyPeriodTeacherDto dto = SubjectStudyPeriodTeacherDto.of(t);
        dto.setPlannedLessons(plannedLessons);
        return dto;
    }
}

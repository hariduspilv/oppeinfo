package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;

import ee.hitsa.ois.domain.timetable.LessonTime;

public class LessonTimesDto {

    private LocalDate validFrom;
    private LessonTimeBuildingGroupDto lessonTimeBuildingGroup;

    public static LessonTimesDto of(LessonTime lessonTime) {
        LessonTimesDto dto = new LessonTimesDto();
        dto.setValidFrom(lessonTime.getLessonTimeBuildingGroup().getValidFrom());
        dto.setLessonTimeBuildingGroup(LessonTimeBuildingGroupDto.of(lessonTime.getLessonTimeBuildingGroup()));
        return dto;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LessonTimeBuildingGroupDto getLessonTimeBuildingGroup() {
        return lessonTimeBuildingGroup;
    }

    public void setLessonTimeBuildingGroup(LessonTimeBuildingGroupDto lessonTimeBuildingGroup) {
        this.lessonTimeBuildingGroup = lessonTimeBuildingGroup;
    }



}

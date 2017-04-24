package ee.hitsa.ois.web.commandobject.timetable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.timetable.LessonTimeBuildingGroupDto;

public class LessonTimeForm {

    private LocalDate validFrom;

    private List<LessonTimeBuildingGroupDto> lessonTimeBuildingGroups;

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public List<LessonTimeBuildingGroupDto> getLessonTimeBuildingGroups() {
        return lessonTimeBuildingGroups != null ? lessonTimeBuildingGroups : (lessonTimeBuildingGroups = new ArrayList<>());
    }

    public void setLessonTimeBuildingGroups(List<LessonTimeBuildingGroupDto> lessonTimeBuildingGroups) {
        this.lessonTimeBuildingGroups = lessonTimeBuildingGroups;
    }

}

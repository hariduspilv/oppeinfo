package ee.hitsa.ois.web.dto.timetable;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.timetable.LessonTimeBuildingGroup;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class LessonTimeBuildingGroupDto {


    private Set<AutocompleteResult> buildings;
    private Set<LessonTimeDto> lessonTimes;

    public static LessonTimeBuildingGroupDto of(LessonTimeBuildingGroup lessonTimeBuildingGroup) {
        LessonTimeBuildingGroupDto dto = EntityUtil.bindToDto(lessonTimeBuildingGroup, new LessonTimeBuildingGroupDto(), "buildings", "lessonTimes");
        dto.setBuildings(lessonTimeBuildingGroup.getBuildings().stream().map(AutocompleteResult::of).collect(Collectors.toSet()));
        dto.setLessonTimes(lessonTimeBuildingGroup.getLessonTimes().stream().map(LessonTimeDto::of).collect(Collectors.toSet()));

        return dto;
    }

    public Set<AutocompleteResult> getBuildings() {
        return buildings != null ? buildings : (buildings = new HashSet<>());
    }

    public void setBuildings(Set<AutocompleteResult> buildings) {
        this.buildings = buildings;
    }

    public Set<LessonTimeDto> getLessonTimes() {
        return lessonTimes != null ? lessonTimes : (lessonTimes = new HashSet<>());
    }

    public void setLessonTimes(Set<LessonTimeDto> lessonTimes) {
        this.lessonTimes = lessonTimes;
    }



}

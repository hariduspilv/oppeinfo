package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.timetable.LessonTimeBuildingGroup;

public class LessonTimeGroupsDto {

    /**
     * All groups have same validFrom
     */
    @NotNull
    private LocalDate validFrom;

    @Valid
    private Set<LessonTimeBuildingGroupDto> lessonTimeBuildingGroups = new HashSet<>();

    public static LessonTimeGroupsDto of(Set<LessonTimeBuildingGroup> lessonTimeBuildingGroups) {
        LessonTimeGroupsDto dto = new LessonTimeGroupsDto();
        if (dto.getValidFrom() == null) {
            lessonTimeBuildingGroups.stream().findAny().ifPresent(group -> dto.setValidFrom(group.getValidFrom()));
        }
        dto.setLessonTimeBuildingGroups(lessonTimeBuildingGroups.stream().map(LessonTimeBuildingGroupDto::of).collect(Collectors.toSet()));
        return dto;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public Set<LessonTimeBuildingGroupDto> getLessonTimeBuildingGroups() {
        return lessonTimeBuildingGroups;
    }

    public void setLessonTimeBuildingGroups(Set<LessonTimeBuildingGroupDto> lessonTimeBuildingGroups) {
        this.lessonTimeBuildingGroups = lessonTimeBuildingGroups;
    }
}

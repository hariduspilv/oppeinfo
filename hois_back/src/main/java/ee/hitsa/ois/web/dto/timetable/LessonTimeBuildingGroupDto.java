package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import ee.hitsa.ois.domain.timetable.LessonTimeBuildingGroup;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class LessonTimeBuildingGroupDto {

    private Long id;
    private LocalDate validFrom;
    private LocalDate validThru;

    @Required
    private Set<AutocompleteResult> buildings = new HashSet<>();
    @Valid
    private Set<LessonTimeDto> lessonTimes = new HashSet<>();

    public static LessonTimeBuildingGroupDto of(LessonTimeBuildingGroup lessonTimeBuildingGroup) {
        LessonTimeBuildingGroupDto dto = EntityUtil.bindToDto(lessonTimeBuildingGroup, new LessonTimeBuildingGroupDto(), "buildings", "lessonTimes");
        dto.setBuildings(lessonTimeBuildingGroup.getBuildings().stream().map(AutocompleteResult::of).collect(Collectors.toSet()));
        dto.setLessonTimes(lessonTimeBuildingGroup.getLessonTimes().stream().map(LessonTimeDto::of).collect(Collectors.toSet()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }

    public Set<AutocompleteResult> getBuildings() {
        return buildings;
    }

    public void setBuildings(Set<AutocompleteResult> buildings) {
        this.buildings = buildings;
    }

    public Set<LessonTimeDto> getLessonTimes() {
        return lessonTimes;
    }

    public void setLessonTimes(Set<LessonTimeDto> lessonTimes) {
        this.lessonTimes = lessonTimes;
    }
}

package ee.hitsa.ois.domain.timetable;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class LessonTimeBuildingGroup  extends BaseEntityWithId {

    @NotNull
    private LocalDate validFrom;

    private LocalDate validThru;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "lesson_time_building_group_id", nullable = false, updatable = false)
    private Set<LessonTime> lessonTimes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "lesson_time_building_group_id", nullable = false, updatable = false)
    private Set<LessonTimeBuilding> buildings = new HashSet<>();

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

    public Set<LessonTime> getLessonTimes() {
        return lessonTimes;
    }

    public void setLessonTimes(Set<LessonTime> lessonTimes) {
        this.lessonTimes = lessonTimes;
    }

    public Set<LessonTimeBuilding> getBuildings() {
        return buildings;
    }

    public void setBuildings(Set<LessonTimeBuilding> buildings) {
        this.buildings = buildings;
    }

}

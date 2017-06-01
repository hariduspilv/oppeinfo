package ee.hitsa.ois.domain.timetable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Building;

@Entity
public class LessonTimeBuilding extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Building building;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private LessonTimeBuildingGroup lessonTimeBuildingGroup;

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public LessonTimeBuildingGroup getLessonTimeBuildingGroup() {
        return lessonTimeBuildingGroup;
    }

    public void setLessonTimeBuildingGroup(LessonTimeBuildingGroup lessonTimeBuildingGroup) {
        this.lessonTimeBuildingGroup = lessonTimeBuildingGroup;
    }

}

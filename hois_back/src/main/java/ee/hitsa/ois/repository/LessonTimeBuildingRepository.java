package ee.hitsa.ois.repository;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.timetable.LessonTimeBuilding;

public interface LessonTimeBuildingRepository extends JpaRepository<LessonTimeBuilding, Long> {

    LessonTimeBuilding findFirstByBuildingIdInOrderByLessonTimeBuildingGroupValidFromDesc(Set<Long> buildings);

    LessonTimeBuilding findFirstByBuildingIdInAndLessonTimeBuildingGroupValidFromLessThanOrderByLessonTimeBuildingGroupValidFromDesc(
            Set<Long> buildingIds, LocalDate before);

}

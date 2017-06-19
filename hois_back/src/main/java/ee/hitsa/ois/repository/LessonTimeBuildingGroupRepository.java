package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.timetable.LessonTimeBuildingGroup;

public interface LessonTimeBuildingGroupRepository extends JpaRepository<LessonTimeBuildingGroup, Long> {

}

package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.timetable.LessonTimeBuildingGroup;

public interface LessonTimeBuildingGroupRepository extends JpaRepository<LessonTimeBuildingGroup, Long>, JpaSpecificationExecutor<LessonTimeBuildingGroup>  {

}

package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.timetable.LessonTime;

public interface LessonTimeRepository extends JpaRepository<LessonTime, Long>, JpaSpecificationExecutor<LessonTime>  {

}

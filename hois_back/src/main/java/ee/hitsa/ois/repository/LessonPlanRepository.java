package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.timetable.LessonPlan;

public interface LessonPlanRepository extends JpaRepository<LessonPlan, Long> {

}

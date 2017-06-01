package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.timetable.LessonPlanModule;

public interface LessonPlanModuleRepository extends JpaRepository<LessonPlanModule, Long> {

}

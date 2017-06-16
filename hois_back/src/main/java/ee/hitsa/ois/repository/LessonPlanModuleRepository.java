package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.timetable.LessonPlanModule;

public interface LessonPlanModuleRepository
        extends JpaRepository<LessonPlanModule, Long>, JpaSpecificationExecutor<LessonPlanModule> {

    LessonPlanModule findFirstByCurriculumVersionOccupationModuleId(Long id);

}

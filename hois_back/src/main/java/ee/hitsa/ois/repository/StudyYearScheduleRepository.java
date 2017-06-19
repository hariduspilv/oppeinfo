package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.school.StudyYearSchedule;

public interface StudyYearScheduleRepository extends JpaRepository<StudyYearSchedule, Long>, JpaSpecificationExecutor<StudyYearSchedule> {

}

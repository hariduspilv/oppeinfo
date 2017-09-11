package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.StudyYear;

public interface StudyYearRepository extends JpaRepository<StudyYear, Long>, JpaSpecificationExecutor<StudyYear> {
}

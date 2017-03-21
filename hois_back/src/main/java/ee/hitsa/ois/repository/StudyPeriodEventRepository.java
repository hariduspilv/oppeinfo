package ee.hitsa.ois.repository;

import ee.hitsa.ois.domain.StudyPeriodEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudyPeriodEventRepository extends JpaRepository<StudyPeriodEvent, Long>, JpaSpecificationExecutor<StudyPeriodEvent> {
}

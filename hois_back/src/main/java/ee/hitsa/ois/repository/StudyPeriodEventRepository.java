package ee.hitsa.ois.repository;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyPeriodEvent;
import ee.hitsa.ois.domain.StudyYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Set;

public interface StudyPeriodEventRepository extends JpaRepository<StudyPeriodEvent, Long>, JpaSpecificationExecutor<StudyPeriodEvent> {

    Set<StudyPeriodEvent> findAllByStudyYearAndStudyPeriodAndEventType(StudyYear studyYear, StudyPeriod studyPeriod, Classifier eventType);
}

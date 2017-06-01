package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.timetable.JournalCapacity;

public interface JournalCapacityRepository extends JpaRepository<JournalCapacity, Long> {

    Long deleteByStudyPeriodAndWeekNrNotIn(StudyPeriod studyPlan, List<Integer> weekNrs);
}

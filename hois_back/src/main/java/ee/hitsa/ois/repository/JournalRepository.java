package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.timetable.Journal;

public interface JournalRepository extends JpaRepository<Journal, Long>, JpaSpecificationExecutor<Journal> {
    List<Journal> findAllBySchool_idAndStudyYear_id(Long schoolId, Long studyYearId);
}

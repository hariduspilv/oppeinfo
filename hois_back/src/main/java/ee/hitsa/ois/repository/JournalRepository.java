package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.timetable.Journal;

public interface JournalRepository extends JpaRepository<Journal, Long>, JpaSpecificationExecutor<Journal> {
}

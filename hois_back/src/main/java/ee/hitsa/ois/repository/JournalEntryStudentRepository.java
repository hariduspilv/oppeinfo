package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.timetable.JournalEntryStudent;

public interface JournalEntryStudentRepository extends JpaRepository<JournalEntryStudent, Long>, JpaSpecificationExecutor<JournalEntryStudent> {

}

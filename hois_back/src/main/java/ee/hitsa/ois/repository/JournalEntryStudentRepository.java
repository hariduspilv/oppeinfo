package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.timetable.JournalEntryStudent;

public interface JournalEntryStudentRepository extends JpaRepository<JournalEntryStudent, Long> {

}

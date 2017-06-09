package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.timetable.JournalStudent;

public interface JournalStudentRepository extends JpaRepository<JournalStudent, Long> {

}

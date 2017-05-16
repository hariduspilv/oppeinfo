package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.timetable.JournalStudent;

public interface JournalStudentRepository extends JpaRepository<JournalStudent, Long>, JpaSpecificationExecutor<JournalStudent> {

}

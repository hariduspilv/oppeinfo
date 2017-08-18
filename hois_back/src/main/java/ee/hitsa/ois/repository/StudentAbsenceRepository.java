package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.student.StudentAbsence;

public interface StudentAbsenceRepository extends JpaRepository<StudentAbsence, Long> {
}

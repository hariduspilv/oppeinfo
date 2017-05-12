package ee.hitsa.ois.repository;

import ee.hitsa.ois.domain.teacher.TeacherQualification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherQualificationRepository extends JpaRepository<TeacherQualification, Long> {
}

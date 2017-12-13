package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.teacher.TeacherQualification;

public interface TeacherQualificationRepository extends JpaRepository<TeacherQualification, Long> {
}

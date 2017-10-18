package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.teacher.TeacherOccupation;

public interface TeacherOccupationRepository extends JpaRepository<TeacherOccupation, Long> {

    TeacherOccupation getOneByIdAndSchool_Id(Long id, Long schoolId);
}

package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.TeacherOccupation;

public interface TeacherOccupationRepository extends JpaRepository<TeacherOccupation, Long>, JpaSpecificationExecutor<TeacherOccupation> {

    TeacherOccupation getOneByIdAndSchool_Id(Long id, Long schoolId);
}

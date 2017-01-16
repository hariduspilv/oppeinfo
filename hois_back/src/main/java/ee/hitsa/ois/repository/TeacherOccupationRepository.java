package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import ee.hitsa.ois.domain.TeacherOccupation;

public interface TeacherOccupationRepository extends CrudRepository<TeacherOccupation, Long>, JpaSpecificationExecutor<TeacherOccupation> {
}

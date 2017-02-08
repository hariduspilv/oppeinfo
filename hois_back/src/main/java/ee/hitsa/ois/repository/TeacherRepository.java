package ee.hitsa.ois.repository;

import ee.hitsa.ois.domain.Teacher;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface TeacherRepository extends CrudRepository<Teacher, Long>, JpaSpecificationExecutor<Teacher> {
}

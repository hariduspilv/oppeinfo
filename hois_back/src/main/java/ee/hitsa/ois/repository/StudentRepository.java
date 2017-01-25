package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import ee.hitsa.ois.domain.Student;

public interface StudentRepository extends CrudRepository<Student, Long>, JpaSpecificationExecutor<Student> {

}

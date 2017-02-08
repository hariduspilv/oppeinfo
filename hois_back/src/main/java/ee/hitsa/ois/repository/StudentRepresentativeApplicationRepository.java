package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import ee.hitsa.ois.domain.StudentRepresentativeApplication;

public interface StudentRepresentativeApplicationRepository extends CrudRepository<StudentRepresentativeApplication, Long>, JpaSpecificationExecutor<StudentRepresentativeApplication> {

}

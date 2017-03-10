package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.school.SchoolDepartment;

public interface SchoolDepartmentRepository extends JpaRepository<SchoolDepartment, Long>, JpaSpecificationExecutor<SchoolDepartment> {

}

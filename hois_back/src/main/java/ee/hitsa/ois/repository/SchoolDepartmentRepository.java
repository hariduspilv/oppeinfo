package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.school.SchoolDepartment;

public interface SchoolDepartmentRepository extends JpaRepository<SchoolDepartment, Long> {

}

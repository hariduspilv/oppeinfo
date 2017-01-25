package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.curriculum.CurriculumDepartment;

public interface CurriculumDepartmentRepository  extends JpaRepository<CurriculumDepartment, Long>{

}

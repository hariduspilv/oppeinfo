package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.curriculum.CurriculumModule;

public interface CurriculumModuleRepository extends JpaRepository<CurriculumModule, Long>, JpaSpecificationExecutor<CurriculumModule> {

}

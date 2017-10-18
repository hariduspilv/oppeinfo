package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.curriculum.CurriculumModule;

public interface CurriculumModuleRepository extends JpaRepository<CurriculumModule, Long> {

}

package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;

public interface CurriculumVersionHigherModuleRepository extends JpaRepository<CurriculumVersionHigherModule, Long> {

}

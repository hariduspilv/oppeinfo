package ee.hitsa.ois.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;

public interface CurriculumVersionOccupationModuleRepository extends JpaRepository<CurriculumVersionOccupationModule, Long> {

    List<CurriculumVersionOccupationModule> findAllByIdIn(Set<Long> moduleIds);

}

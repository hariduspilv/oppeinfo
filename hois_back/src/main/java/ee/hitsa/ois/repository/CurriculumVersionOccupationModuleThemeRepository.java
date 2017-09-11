package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;

public interface CurriculumVersionOccupationModuleThemeRepository
        extends JpaRepository<CurriculumVersionOccupationModuleTheme, Long> {

    @Query(nativeQuery = true, value = "select exists"
            + "(select * "
            + "from curriculum_version_omodule_outcomes "
            + "where curriculum_module_outcomes_id in :outcomeIds)")
    public boolean existsByCurriculumModuleOutcomeIds(@Param("outcomeIds") List<Long> outcomeId);
    
    
}

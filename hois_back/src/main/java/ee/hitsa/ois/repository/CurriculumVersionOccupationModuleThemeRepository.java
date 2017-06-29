package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;

public interface CurriculumVersionOccupationModuleThemeRepository
        extends JpaRepository<CurriculumVersionOccupationModuleTheme, Long> {

}

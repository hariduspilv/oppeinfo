package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.curriculum.CurriculumSpeciality;

public interface CurriculumSpecialityRepository  extends JpaRepository<CurriculumSpeciality, Long> {

}

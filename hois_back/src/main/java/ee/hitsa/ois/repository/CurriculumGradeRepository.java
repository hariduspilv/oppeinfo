package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.curriculum.CurriculumGrade;

public interface CurriculumGradeRepository extends JpaRepository<CurriculumGrade, Long>{

}

package ee.hitsa.ois.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.curriculum.Curriculum;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {

    // TODO implement as fetch first id (count is costly operation)
    long count(Specification<Curriculum> spec);
}

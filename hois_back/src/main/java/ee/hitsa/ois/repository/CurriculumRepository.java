package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {

    // TODO implement as fetch first id (count is costly operation)
    long count(Specification<Curriculum> spec);

    List<AutocompleteResult<Long>> findAllBySchool_id(Long schoolId);
}

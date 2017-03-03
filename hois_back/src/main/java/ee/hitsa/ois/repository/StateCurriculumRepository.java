package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;

public interface StateCurriculumRepository extends JpaRepository<StateCurriculum, Long> {

    Page<StateCurriculum> findAll(Specification<StateCurriculum> spec, Pageable pageable);

    List<StateCurriculum> findAll(Specification<StateCurriculum> spec);

    // TODO implement as fetch first id (count is costly operation)
    long count(Specification<StateCurriculum> spec);

    List<StateCurriculum> findAll(Specification<StateCurriculum> spec, Sort sort);
}

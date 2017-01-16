package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.StateCurriculum;

public interface StateCurriculumRepository extends JpaRepository<StateCurriculum, Long> {

	public Page<StateCurriculum> findAll(Specification<StateCurriculum> spec, Pageable pageable);

	public List<StateCurriculum> findAll(Specification<StateCurriculum> spec);

	public long count(Specification<StateCurriculum> spec);
}

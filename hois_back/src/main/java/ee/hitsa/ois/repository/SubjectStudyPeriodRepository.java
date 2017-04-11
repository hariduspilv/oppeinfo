package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.subject.SubjectStudyPeriod;

public interface SubjectStudyPeriodRepository extends JpaRepository<SubjectStudyPeriod, Long> {

    // TODO remove if not used
    List<SubjectStudyPeriod> findAll(Specification<SubjectStudyPeriod> spec);
}

package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.school.School;

public interface SchoolRepository extends JpaRepository<School, Long>, JpaSpecificationExecutor<School> {
}

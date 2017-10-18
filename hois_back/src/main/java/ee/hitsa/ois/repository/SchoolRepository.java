package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.school.School;

public interface SchoolRepository extends JpaRepository<School, Long> {
}

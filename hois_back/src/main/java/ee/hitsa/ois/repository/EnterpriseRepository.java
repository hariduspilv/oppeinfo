package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.Enterprise;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

}

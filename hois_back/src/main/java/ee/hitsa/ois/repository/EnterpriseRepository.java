package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.Enterprise;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long>, JpaSpecificationExecutor<Enterprise> {

}

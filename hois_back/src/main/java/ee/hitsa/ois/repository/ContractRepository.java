package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    Contract findBySupervisorUrl(String uuid);

}

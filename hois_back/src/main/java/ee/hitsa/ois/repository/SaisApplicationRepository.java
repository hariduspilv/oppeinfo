package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.SaisApplication;

public interface SaisApplicationRepository extends JpaRepository<SaisApplication, Long>, JpaSpecificationExecutor<SaisApplication> {

    SaisApplication findByApplicationNr(String applicationNr);

}

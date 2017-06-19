package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.sais.SaisClassifier;

public interface SaisClassifierRepository extends JpaRepository<SaisClassifier, Long>, JpaSpecificationExecutor<SaisClassifier> {

}

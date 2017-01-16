package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.OisFile;

public interface OisFileRepository extends JpaRepository<OisFile, Long> {

}

package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.Certificate;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

}

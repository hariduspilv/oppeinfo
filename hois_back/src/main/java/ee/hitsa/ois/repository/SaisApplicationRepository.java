package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.sais.SaisApplication;

public interface SaisApplicationRepository extends JpaRepository<SaisApplication, Long> {

    SaisApplication findByApplicationNrAndSaisAdmissionCode(String applicationNr, String code);
}

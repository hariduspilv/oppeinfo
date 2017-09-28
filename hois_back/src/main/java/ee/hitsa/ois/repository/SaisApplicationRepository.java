package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.sais.SaisApplication;

public interface SaisApplicationRepository extends JpaRepository<SaisApplication, Long>, JpaSpecificationExecutor<SaisApplication> {

    // XXX not used?
    SaisApplication findByApplicationNrAndSaisAdmissionCodeAndSaisAdmissionCurriculumVersionCurriculumSchoolId(String applicationNr, String saisAdmissionCode, Long schoolId);

    SaisApplication findByApplicationNrAndSaisAdmissionCode(String applicationNr, String code);
}

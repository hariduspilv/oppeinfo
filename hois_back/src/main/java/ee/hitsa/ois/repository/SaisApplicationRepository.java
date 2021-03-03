package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.sais.SaisApplication;

public interface SaisApplicationRepository extends JpaRepository<SaisApplication, Long> {

    SaisApplication findByApplicationNrAndSaisAdmissionCodeAndSaisAdmissionCurriculumVersionCurriculumSchoolIdAndSaisIdIsNull(String applicationNr, String code, Long schoolId);
}

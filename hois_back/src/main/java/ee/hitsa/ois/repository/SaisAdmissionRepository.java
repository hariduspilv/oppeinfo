package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.sais.SaisAdmission;

public interface SaisAdmissionRepository extends JpaRepository<SaisAdmission, Long> {

    SaisAdmission findByCode(String code);
    SaisAdmission findByCodeAndCurriculumVersionCurriculumSchoolId(String code, Long schoolId);
    SaisAdmission findFirstByCurriculumVersionIdOrderByIdDesc(Long id);

}

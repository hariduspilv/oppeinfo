package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.sais.SaisAdmission;

public interface SaisAdmissionRepository extends JpaRepository<SaisAdmission, Long> {

    List<SaisAdmission> findByCode(String code);
    List<SaisAdmission> findByCodeAndCurriculumVersionCurriculumSchoolId(String code, Long schoolId);
    SaisAdmission findFirstByCurriculumVersionIdOrderByIdDesc(Long id);

}

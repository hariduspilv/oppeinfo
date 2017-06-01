package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.sais.SaisAdmission;

public interface SaisAdmissionRepository extends JpaRepository<SaisAdmission, Long>, JpaSpecificationExecutor<SaisAdmission> {

    List<SaisAdmission> findAllDistinctCodeByCurriculumVersionCurriculumSchoolId(Long schoolId);
    List<SaisAdmission> findAllByCodeIn(List<String> codes);
    SaisAdmission findByCode(String code);
    SaisAdmission findByCodeAndCurriculumVersionCurriculumSchoolId(String code, Long schoolId);
    SaisAdmission findFirstByCurriculumVersionIdOrderByIdDesc(Long id);

}

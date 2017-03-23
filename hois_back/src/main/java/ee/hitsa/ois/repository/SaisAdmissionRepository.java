package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.SaisAdmission;

public interface SaisAdmissionRepository extends JpaRepository<SaisAdmission, Long>, JpaSpecificationExecutor<SaisAdmission> {

    List<SaisAdmission> findAllDistinctCodeByCurriculumVersionCurriculumSchoolId(Long schoolId);

}

package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.curriculum.CurriculumVersion;

public interface CurriculumVersionRepository extends JpaRepository<CurriculumVersion, Long> {

    List<CurriculumVersion> findAllDistinctByModules_Subjects_Subject_id(Long subjectId);

    CurriculumVersion findByCodeAndCurriculumSchoolId(String curriculumVersionCode, Long schoolId);

    boolean existsByCurriculumSchoolIdAndCodeAndIdNot(Long schoolId, String code, Long curriculumId);
    boolean existsByCurriculumSchoolIdAndCode(Long schoolId, String paramValue);
}

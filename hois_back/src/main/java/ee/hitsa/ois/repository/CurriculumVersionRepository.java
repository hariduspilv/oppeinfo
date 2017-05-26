package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.curriculum.CurriculumVersion;

public interface CurriculumVersionRepository extends JpaRepository<CurriculumVersion, Long> {

    List<CurriculumVersion> findAllDistinctByModules_Subjects_Subject_id(Long subjectId);

    // TODO remove as useless (code is probably not unique in database)
    CurriculumVersion findByCode(String curriculumVersionCode);

    CurriculumVersion findByCodeAndCurriculumSchoolId(String curriculumVersionCode, Long schoolId);

    Boolean existsByCurriculumSchoolIdAndCodeAndCurriculumIdNot(Long schoolId, String code, Long curriculumId);
    Boolean existsByCurriculumSchoolIdAndCode(Long schoolId, String paramValue);
}

package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ee.hitsa.ois.domain.curriculum.CurriculumVersion;

public interface CurriculumVersionRepository extends JpaRepository<CurriculumVersion, Long> {

    // TODO check uniqueness with "exists" pattern
    @Query(nativeQuery = true, value =
            "select count(*) from curriculum_version "
            + "where curriculum_id in (select id from curriculum where school_id = ?1) "
            + "and code = ?2 and curriculum_id <> ?3")
    long countBySchoolAndOtherCurriculums(Long schoolId, String code, Long curriculumId);

    List<CurriculumVersion> findAllDistinctByModules_Subjects_Subject_id(Long subjectId);

    // TODO remove as useless (code is probably not unique in database)
    CurriculumVersion findByCode(String curriculumVersionCode);

    CurriculumVersion findByCodeAndCurriculumSchoolId(String curriculumVersionCode, Long schoolId);
}

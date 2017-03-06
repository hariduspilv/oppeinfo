package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public interface CurriculumVersionRepository extends JpaRepository<CurriculumVersion, Long> {

    //  and (cv.curriculum.validFrom is null or cv.curriculum.validFrom <= current_date) and (cv.curriculum.validThru is null or cv.curriculum.validThru >= current_date)
    @Query("select new ee.hitsa.ois.web.dto.AutocompleteResult(cv.id, concat(cv.code, ' ', cv.curriculum.nameEt), concat(cv.code, ' ', cv.curriculum.nameEn)) from CurriculumVersion cv where cv.curriculum.school.id=?1")
    List<AutocompleteResult> findAllForSelect(Long schoolId);
    
    @Query(nativeQuery = true, value = 
            "select count(*) from curriculum_version "
            + "where curriculum_id in (select id from curriculum where school_id = ?1) "
            + "and code = ?2 and curriculum_id <> ?3")
    long countBySchoolAndOtherCurriculums(Long schoolId, String code, Long curriculumId);

    List<CurriculumVersion> findAllDistinctByModules_Subjects_Subject_id(Long subjectId);
}

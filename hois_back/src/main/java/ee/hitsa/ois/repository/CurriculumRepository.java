package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.curriculum.Curriculum;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long>, JpaSpecificationExecutor<Curriculum> {

    boolean existsByMerCode(String paramValue);
    boolean existsByMerCodeAndIdNot(String paramValue, Long curriculumId);
    boolean existsBySchoolIdAndCode(Long schoolId, String paramValue);
    boolean existsBySchoolIdAndCodeAndIdNot(Long schoolId, String code, Long curriculumId);
    
    boolean existsBySchoolIdAndNameEnAndIdNot(Long schoolId, String paramValue, Long id);
    boolean existsBySchoolIdAndNameRuAndIdNot(Long schoolId, String paramValue, Long id);
    boolean existsBySchoolIdAndNameEtAndIdNot(Long schoolId, String paramValue, Long id);
    boolean existsBySchoolIdAndNameEn(Long schoolId, String paramValue);
    boolean existsBySchoolIdAndNameRu(Long schoolId, String paramValue);
    boolean existsBySchoolIdAndNameEt(Long schoolId, String paramValue);
}

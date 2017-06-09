package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.curriculum.Curriculum;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long>, JpaSpecificationExecutor<Curriculum> {

    Boolean existsByMerCode(String paramValue);
    Boolean existsByMerCodeAndIdNot(String paramValue, Long curriculumId);
    Boolean existsBySchoolIdAndCode(Long schoolId, String paramValue);
    Boolean existsBySchoolIdAndCodeAndIdNot(Long schoolId, String code, Long curriculumId);
    
    Boolean existsBySchoolIdAndNameEnAndIdNot(Long schoolId, String paramValue, Long id);
    Boolean existsBySchoolIdAndNameRuAndIdNot(Long schoolId, String paramValue, Long id);
    Boolean existsBySchoolIdAndNameEtAndIdNot(Long schoolId, String paramValue, Long id);
    Boolean existsBySchoolIdAndNameEn(Long schoolId, String paramValue);
    Boolean existsBySchoolIdAndNameRu(Long schoolId, String paramValue);
    Boolean existsBySchoolIdAndNameEt(Long schoolId, String paramValue);
}

package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.subject.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>, JpaSpecificationExecutor<Subject> {

    boolean existsBySchoolIdAndCode(Long schoolId, String paramValue);
    boolean existsBySchoolIdAndCodeAndIdNot(Long schoolId, String code, Long curriculumId);
}

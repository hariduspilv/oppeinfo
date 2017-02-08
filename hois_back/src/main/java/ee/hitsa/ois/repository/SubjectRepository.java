package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.Subject;
import ee.hitsa.ois.web.dto.AutocompleteResult;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long>, JpaSpecificationExecutor<Subject> {

    List<AutocompleteResult<Long>> findAllBySchool_idAndStatus_code(Long schoolId, String status);
}

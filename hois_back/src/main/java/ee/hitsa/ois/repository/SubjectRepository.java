package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import ee.hitsa.ois.domain.Subject;
import ee.hitsa.ois.web.dto.AutocompleteResult;

import java.util.List;

public interface SubjectRepository extends CrudRepository<Subject, Long>, JpaSpecificationExecutor<Subject> {

    List<AutocompleteResult<Long>> findAllBySchool_idAndStatus_code(Long schoolId, String status);
}

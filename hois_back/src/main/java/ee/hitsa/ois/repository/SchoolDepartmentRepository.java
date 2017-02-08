package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ee.hitsa.ois.domain.SchoolDepartment;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SchoolDepartmentDto;

public interface SchoolDepartmentRepository extends CrudRepository<SchoolDepartment, Long>, JpaSpecificationExecutor<SchoolDepartment> {

    @Query("select new ee.hitsa.ois.web.dto.SchoolDepartmentDto(sd.id, sd.version, sd.code, sd.nameEt, sd.nameEn, sd.validFrom, sd.validThru, sd.parentSchoolDepartment.id) from SchoolDepartment sd where sd.school.id=?1")
    List<SchoolDepartmentDto> findAllTree(Long schoolId, Sort sort);

    List<AutocompleteResult<Long>> findAllBySchool_id(Long schoolId);
}

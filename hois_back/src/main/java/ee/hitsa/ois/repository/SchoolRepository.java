package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.web.dto.SchoolWithoutLogo;

public interface SchoolRepository extends JpaRepository<School, Long>, JpaSpecificationExecutor<School> {

    @Query("select s from School s")
    List<SchoolWithoutLogo> findAllSchools();

    @Query("select s.logo from School s where s.id=?1")
    OisFile findSchoolLogo(Long schoolId);
}

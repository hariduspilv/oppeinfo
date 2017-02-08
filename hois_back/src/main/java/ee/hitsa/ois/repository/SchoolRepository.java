package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ee.hitsa.ois.domain.School;
import ee.hitsa.ois.web.dto.SchoolWithoutLogo;

public interface SchoolRepository extends JpaRepository<School, Long> {

    Page<School> findAll(Specification<School> spec, Pageable pageable);

    @Query("select s from School s")
    List<SchoolWithoutLogo> findAllSchools();
}

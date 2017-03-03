package ee.hitsa.ois.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ee.hitsa.ois.domain.student.StudentRepresentative;

public interface StudentRepresentativeRepository extends JpaRepository<StudentRepresentative, Long> {

    @Query("select sr from StudentRepresentative sr where sr.student.id=?1")
    Page<StudentRepresentative> findAllByStudent_id(Long studentId, Pageable pageable);
}

package ee.hitsa.ois.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ee.hitsa.ois.domain.student.StudentAbsence;

public interface StudentAbsenceRepository extends JpaRepository<StudentAbsence, Long> {

    @Query("select sa from StudentAbsence sa where sa.student.id=?1")
    Page<StudentAbsence> findAllByStudent_id(Long studentId, Pageable pageable);
}

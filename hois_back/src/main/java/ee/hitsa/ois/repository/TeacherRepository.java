package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import ee.hitsa.ois.domain.teacher.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long>, JpaSpecificationExecutor<Teacher> {

    Teacher findByPersonIdAndSchoolId(Long personId, Long schoolId);

    @Query(nativeQuery = true, value = ""
            + "select not exists("
                + "select * "
                + "from teacher t "
                + "join person p on p.id = t.person_id "
                + "where t.school_id = ?1 "
                + "and p.idcode = ?2)")
    boolean isUnique(Long schoolId, String personIdCode);
}

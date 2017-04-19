package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.student.StudentGroup;

public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long>, JpaSpecificationExecutor<StudentGroup> {

    List<StudentGroup> findAllBySchool_id(Long schoolId);
}

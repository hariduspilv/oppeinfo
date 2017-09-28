package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import ee.hitsa.ois.domain.protocol.ProtocolStudent;

public interface ProtocolStudentRepository extends JpaRepository<ProtocolStudent, Long>, JpaSpecificationExecutor<ProtocolStudent>  {

    @Query("select ps from ProtocolStudent ps where ps.student.id = ?1")
    List<ProtocolStudent> findByStudent(Long studentId);
}

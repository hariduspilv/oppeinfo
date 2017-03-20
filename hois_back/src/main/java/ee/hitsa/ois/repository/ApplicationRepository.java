package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import ee.hitsa.ois.domain.application.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long>, JpaSpecificationExecutor<Application> {

    @Query("select a from Application a where a.student.id=?1")
    Page<Application> findAllByStudent_id(Long studentId, Pageable pageable);

    List<Application> findDistinctTypeByStudentIdAndStatusCodeIn(Long studentId, List<String> statuses);
}

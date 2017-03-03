package ee.hitsa.ois.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import ee.hitsa.ois.domain.directive.Directive;

public interface DirectiveRepository extends JpaRepository<Directive, Long>, JpaSpecificationExecutor<Directive> {

    @Query("select d from Directive d join d.students s where s.student.id=?1")
    Page<Directive> findAllByStudent_id(Long studentId, Pageable pageable);

}

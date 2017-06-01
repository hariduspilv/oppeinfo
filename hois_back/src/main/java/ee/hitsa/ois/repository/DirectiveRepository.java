package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.directive.Directive;

public interface DirectiveRepository extends JpaRepository<Directive, Long>, JpaSpecificationExecutor<Directive> {

    Boolean existsByCanceledDirectiveId(Long canceledDirectiveId);
}

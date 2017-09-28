package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.directive.Directive;

public interface DirectiveRepository extends JpaRepository<Directive, Long> {
}

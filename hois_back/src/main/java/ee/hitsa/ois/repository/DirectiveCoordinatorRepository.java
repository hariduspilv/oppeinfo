package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.directive.DirectiveCoordinator;

public interface DirectiveCoordinatorRepository extends JpaRepository<DirectiveCoordinator, Long> {

}

package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.Declaration;

public interface DeclarationRepository extends JpaRepository<Declaration, Long>, JpaSpecificationExecutor<Declaration> {
}

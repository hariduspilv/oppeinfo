package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.DeclarationSubject;

public interface DeclarationSubjectRepository extends JpaRepository<DeclarationSubject, Long> {

}
package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.GeneralMessage;

public interface GeneralMessageRepository extends JpaRepository<GeneralMessage, Long>, JpaSpecificationExecutor<GeneralMessage> {
}

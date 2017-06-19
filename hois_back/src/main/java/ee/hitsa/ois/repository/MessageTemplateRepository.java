package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.MessageTemplate;

public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Long>, JpaSpecificationExecutor<MessageTemplate> {

}
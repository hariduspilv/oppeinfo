package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.Message;

public interface MessageRepository  extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {

}

package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}

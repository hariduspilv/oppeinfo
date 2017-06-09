package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.protocol.Protocol;

public interface ProtocolRepository extends JpaRepository<Protocol, Long> {

    Protocol findFirstByOrderByIdDesc();

}

package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.Committee;

public interface CommitteeRepository  extends JpaRepository<Committee, Long> {

    boolean existsByAddInfo(String addInfo);
}

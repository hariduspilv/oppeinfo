package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}

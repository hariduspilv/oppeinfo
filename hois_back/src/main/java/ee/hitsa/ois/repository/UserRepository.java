package ee.hitsa.ois.repository;

import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.web.dto.UserProjection;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<UserProjection> findDistinctByPerson_IdAndUserRightsIsNotNull(Long id);

    UserProjection findOneById(long id);
}

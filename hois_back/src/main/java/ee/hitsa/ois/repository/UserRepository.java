package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.web.dto.UserProjection;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    List<UserProjection> findDistinctByPerson_IdAndUserRightsIsNotNull(Long id);

    UserProjection findOneById(long id);

}

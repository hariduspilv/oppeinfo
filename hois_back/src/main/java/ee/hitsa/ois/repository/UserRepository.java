package ee.hitsa.ois.repository;

import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.web.dto.UserProjection;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    List<UserProjection> findByPerson_IdAndUserRightsIsNotNull(Long id);

    UserProjection findOneById(long id);
}

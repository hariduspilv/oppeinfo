package ee.hitsa.ois.repository;

import ee.hitsa.ois.domain.UserRights;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRolesRepository extends CrudRepository<UserRights, Long> {

    //@Query("select r.roleCode from Person, UserRights r, User u where u.userName=?1 and r.users.id=u.id")
    //@Query("select u.roleCode from Person p, UserRights r, User u where p.idcode=?1 and p.id = u.person.id and r.users.id = u.id")

    @Query("select ('ROLE_' || u.permission || '_' || u.object) from UserRights u where u.user.id = ?1")
    List<String> findRolesByUser(Long id);
}

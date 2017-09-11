package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.repository.UserRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.UserProjection;

@Transactional
@Service
public class UserService {

    @Autowired
    private EntityManager em;
    @Autowired
    private UserRepository userRepository;

    public User createUser(Person person, Role role, School school) {
        User user = new User();
        user.setPerson(person);
        user.setRole(em.getReference(Classifier.class, role.name()));
        user.setSchool(school);
        return EntityUtil.save(user, em);
    }

    public List<User> findAllValidSchoolUsersByRole(School school, Role role) {
        return userRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), EntityUtil.getId(school)));
            filters.add(cb.equal(root.get("role").get("code"), role.name()));

            LocalDate now = LocalDate.now();
            filters.add(cb.or(cb.lessThanOrEqualTo(root.get("validFrom"), now), cb.isNull(root.get("validFrom"))));
            filters.add(cb.or(cb.greaterThanOrEqualTo(root.get("validThru"), now), cb.isNull(root.get("validThru"))));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
    }

    private static String ACTIVE_FROM = "from user_ u " +
            "left outer join school s on u.school_id = s.id " +
            "inner join classifier c on u.role_code = c.code";

    public List<UserProjection> findAllActiveUsers(Long personId) {
        // TODO c.name_et depends on parameter
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(ACTIVE_FROM).sort("c.name_et", "s.code");

        qb.requiredCriteria("u.person_id = :personId", "personId", personId);
        qb.filter("exists(select 1 from user_rights r where u.id = r.user_id)");
        qb.validNowCriteria("u.valid_from", "u.valid_thru");

        List<?> resultList = qb.select("u.id, s.code, u.role_code", em).getResultList();
        return StreamUtil.toMappedList(r -> new UserProjection(
                resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2)
        ), resultList);
    }
}

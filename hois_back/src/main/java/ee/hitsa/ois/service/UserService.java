package ee.hitsa.ois.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.UserRepository;
import ee.hitsa.ois.util.EntityUtil;

@Transactional
@Service
public class UserService {

    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private UserRepository userRepository;

    public User createUser(Person person, Role role, School school) {
        User user = new User();
        user.setPerson(person);
        user.setRole(classifierRepository.getOne(role.name()));
        user.setSchool(school);
        return userRepository.save(user);
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
}

package ee.hitsa.ois.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.UserRepository;

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
}

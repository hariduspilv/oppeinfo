package ee.hitsa.ois.service.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.repository.UserRepository;
import ee.hitsa.ois.repository.UserRolesRepository;

@Transactional
@Service
public class HoisUserDetailsService implements UserDetailsService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRolesRepository userRolesRepository;

    @Override
    public HoisUserDetails loadUserByUsername(String idcode) throws UsernameNotFoundException {
        Person person = personRepository.findByIdcode(idcode);
        if (person == null) {
            throw new UsernameNotFoundException("No person present with idcode : " + idcode);
        }

        User user = person.getUsers().stream()
                .filter(u -> !u.getUserRights().isEmpty())
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Person had no rights : " + idcode));
        return getHoisUserDetails(user);
    }

    public HoisUserDetails getHoisUserDetails(Long userId) {
        return getHoisUserDetails(userRepository.getOne(userId));
    }

    public HoisUserDetails getHoisUserDetails(User user) {
        List<String> userRoles = userRolesRepository.findRolesByUser(user.getId());
        return new HoisUserDetails(user, userRoles);
    }
}

package ee.hitsa.ois.web;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.UserProjection;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.repository.UserRepository;
import ee.hitsa.ois.service.security.AuthenticatedUser;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.security.HoisUserDetailsService;
import ee.hitsa.ois.web.dto.SchoolWithoutLogo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;


@RestController
public class UserController {

    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final HoisUserDetailsService userDetailsService;
    private final SchoolRepository schoolRepository;

    @Autowired
    public UserController(PersonRepository personRepository, UserRepository userRepository, HoisUserDetailsService userDetailsService, SchoolRepository schoolRepository) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.schoolRepository = schoolRepository;
    }

    @RequestMapping("/user")
    @ResponseBody
    public AuthenticatedUser user(Principal principal) {
        if (principal != null) {
            HoisUserDetails userDetails = HoisUserDetails.fromPrincipal(principal);
            User user = userRepository.findOne(userDetails.getUserId());
            AuthenticatedUser authenticatedUser = new AuthenticatedUser(user);
            // todo excess line
            Person person = personRepository.findByIdcode(authenticatedUser.getName());
            List<UserProjection> users = userRepository.findByPerson_IdAndUserRightsIsNotNull(person.getId());

            // todo excess line
            SchoolWithoutLogo school = schoolRepository.findSchoolByUser(authenticatedUser.getUser());

            authenticatedUser.setSchool(school);
            authenticatedUser.setAuthorizedRoles(userDetails.getAuthorities());
            authenticatedUser.setFullname(person.getFirstname(), person.getLastname());
            authenticatedUser.setUsers(users);

            return authenticatedUser;
        }
        return null;
    }


    @PostMapping("/changeUser")
    public AuthenticatedUser updateUser(Principal principal, @RequestBody Map<String, Long> json) {
        Long id = json.get("id");

        if (principal != null && id != null) {

            HoisUserDetails oldUserDetails = HoisUserDetails.fromPrincipal(principal);
            User user = userRepository.findOne(oldUserDetails.getUserId());
            Person person = user.getPerson();
            // todo proper exceptions
            User u = person.users.stream().filter(it -> Objects.equals(it.getId(), id)).findFirst()
                    .orElseThrow(() -> new RuntimeException("Person has no user with id : " + id));

            HoisUserDetails userDetails = userDetailsService.getHoisUserDetails(u);

            AbstractAuthenticationToken auth = new PreAuthenticatedAuthenticationToken(principal, "", userDetails.getAuthorities());
            auth.setDetails(userDetails);

            SecurityContextHolder.getContext().setAuthentication(auth);

            return user(SecurityContextHolder.getContext().getAuthentication());
        }
        return null;
    }
}


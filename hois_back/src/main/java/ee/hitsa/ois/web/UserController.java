package ee.hitsa.ois.web;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.repository.UserRepository;
import ee.hitsa.ois.service.security.AuthenticatedUser;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.security.HoisUserDetailsService;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.UserProjection;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final HoisUserDetailsService userDetailsService;

    @Autowired
    public UserController(PersonRepository personRepository, UserRepository userRepository, HoisUserDetailsService userDetailsService) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping("/user")
    @ResponseBody
    public AuthenticatedUser user(Principal principal) {
        if (principal != null) {
            HoisUserDetails userDetails = HoisUserDetails.fromPrincipal(principal);
            User user = userRepository.getOne(userDetails.getUserId());
            AuthenticatedUser authenticatedUser = new AuthenticatedUser(user);
            // todo excess line
            Person person = personRepository.findByIdcode(authenticatedUser.getName());
            List<UserProjection> users = userRepository.findByPerson_IdAndUserRightsIsNotNull(person.getId());

            authenticatedUser.setSchool(user.getSchool());
            authenticatedUser.setAuthorizedRoles(userDetails.getAuthorities());
            authenticatedUser.setFullname(person.getFullname());
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
            User oldUser = userRepository.getOne(oldUserDetails.getUserId());
            User newUser = userRepository.getOne(id);
            if(!EntityUtil.getId(oldUser.getPerson()).equals(EntityUtil.getId(newUser.getPerson()))) {
                // TODO proper exception
                throw new RuntimeException("Person has no user with id : " + id);
            }

            HoisUserDetails userDetails = userDetailsService.getHoisUserDetails(newUser);

            AbstractAuthenticationToken auth = new PreAuthenticatedAuthenticationToken(principal, "", userDetails.getAuthorities());
            auth.setDetails(userDetails);

            SecurityContextHolder.getContext().setAuthentication(auth);

            return user(SecurityContextHolder.getContext().getAuthentication());
        }
        return null;
    }
}


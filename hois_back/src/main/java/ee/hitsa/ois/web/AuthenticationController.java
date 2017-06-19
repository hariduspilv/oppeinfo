package ee.hitsa.ois.web;

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

import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.repository.UserRepository;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.service.UserService;
import ee.hitsa.ois.service.security.AuthenticatedSchool;
import ee.hitsa.ois.service.security.AuthenticatedUser;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.security.HoisUserDetailsService;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.UserProjection;


@RestController
public class AuthenticationController {

    @Autowired
    private HoisUserDetailsService userDetailsService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @RequestMapping("/user")
    @ResponseBody
    public AuthenticatedUser user(Principal principal) {
        if (principal != null) {
            HoisUserDetails userDetails = HoisUserDetails.fromPrincipal(principal);
            User user = userRepository.getOne(userDetails.getUserId());
            AuthenticatedUser authenticatedUser = new AuthenticatedUser(user);
            List<UserProjection> users = userService.findAllActiveUsers(user.getPerson().getId());

            School school = user.getSchool();
            AuthenticatedSchool authenticatedSchool = null;
            if(school != null) {
                SchoolService.SchoolType type = schoolService.schoolType(school.getId());
                authenticatedSchool = new AuthenticatedSchool(school.getId(), type.isHigher(), type.isVocational(), EntityUtil.getCode(school.getEhisSchool()));
            }
            authenticatedUser.setSchool(authenticatedSchool);
            authenticatedUser.setAuthorizedRoles(userDetails.getAuthorities());
            authenticatedUser.setFullname(user.getPerson().getFullname());
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

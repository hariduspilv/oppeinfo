package ee.hitsa.ois.service.security;

import java.security.Principal;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.service.UserService;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.UserProjection;

@Transactional
@Service
public class HoisUserDetailsService implements UserDetailsService {

    @Autowired
    private EntityManager em;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private UserService userService;
    @Value("${server.session.timeout}")
    private Integer sessionTimeoutInSeconds;

    @Override
    public HoisUserDetails loadUserByUsername(String idcode) throws UsernameNotFoundException {
        Person person = personRepository.findByIdcode(idcode);
        if (person == null) {
            throw new UsernameNotFoundException("No person present with idcode : " + idcode);
        }

        UserProjection selectedUser = userService.findAllActiveUsers(person.getId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Person had no rights : " + idcode));

        return getHoisUserDetails(selectedUser.getId());
    }

    public HoisUserDetails getHoisUserDetails(Long userId) {
        return getHoisUserDetails(em.getReference(User.class, userId));
    }

    public HoisUserDetails getHoisUserDetails(User user) {
        List<String> userRoles = em.createQuery("select ('ROLE_' || u.permission || '_' || u.object) from UserRights u where u.user.id = ?1", String.class)
                .setParameter(1, user.getId()).getResultList();
        return new HoisUserDetails(user, userRoles);
    }

    public AuthenticatedUser authenticatedUser(Principal principal) {
        HoisUserDetails userDetails = HoisUserDetails.fromPrincipal(principal);
        User user = em.getReference(User.class, userDetails.getUserId());
        AuthenticatedUser authenticatedUser = new AuthenticatedUser(user, sessionTimeoutInSeconds);

        School school = user.getSchool();
        AuthenticatedSchool authenticatedSchool = null;
        if(school != null) {
            SchoolService.SchoolType type = schoolService.schoolType(school.getId());
            authenticatedSchool = new AuthenticatedSchool(school.getId(), type.isHigher(), type.isVocational(), EntityUtil.getCode(school.getEhisSchool()));
            if(user.getStudent() != null) {
                // take from curriculum version.orig_study_level classifier value field
                List<?> level = em.createNativeQuery("select cl.value from curriculum c " +
                        "join classifier cl on cl.code = c.orig_study_level_code " +
                        "join curriculum_version cv on c.id = cv.curriculum_id " +
                        "join student s on s.curriculum_version_id = cv.id where s.id = ?1").setParameter(1, user.getStudent()).setMaxResults(1).getResultList();
                if(!level.isEmpty()) {
                    String origStudyLevel = (String)level.get(0);
                    authenticatedUser.setVocational(Boolean.valueOf(CurriculumUtil.isVocational(origStudyLevel)));
                    authenticatedUser.setHigher(Boolean.valueOf(CurriculumUtil.isHigher(origStudyLevel)));
                }
            } else {
                // take values from school
                authenticatedUser.setVocational(Boolean.valueOf(type.isVocational()));
                authenticatedUser.setHigher(Boolean.valueOf(type.isHigher()));
            }
        }
        authenticatedUser.setSchool(authenticatedSchool);
        authenticatedUser.setAuthorizedRoles(userDetails.getAuthorities());
        authenticatedUser.setFullname(user.getPerson().getFullname());
        authenticatedUser.setUsers(userService.findAllActiveUsers(user.getPerson().getId()));
        authenticatedUser.setLoginMethod(userDetails.getLoginMethod());

        return authenticatedUser;
    }
}

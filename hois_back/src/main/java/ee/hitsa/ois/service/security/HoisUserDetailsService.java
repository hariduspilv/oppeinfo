package ee.hitsa.ois.service.security;

import static ee.hitsa.ois.util.JpaQueryUtil.parameterAsTimestamp;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ee.hitsa.ois.auth.LoginMethod;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.UserSessions;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.service.UserService;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.UserProjection;

@Transactional
@Service
public class HoisUserDetailsService implements UserDetailsService, LogoutHandler {

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
        List<String> userRoles = em.createQuery(
                "select ('ROLE_' || u.permission.code || '_' || u.object.code) from UserRights u where u.user.id = ?1",
                String.class).setParameter(1, user.getId()).getResultList();
        return new HoisUserDetails(user, userRoles);
    }

    public AuthenticatedUser authenticatedUser(HttpServletRequest request, Principal principal) {
        HoisUserDetails userDetails = HoisUserDetails.fromPrincipal(principal);
        User user = em.getReference(User.class, userDetails.getUserId());
        AuthenticatedUser authenticatedUser = new AuthenticatedUser(user, sessionTimeoutInSeconds);

        School school = user.getSchool();
        AuthenticatedSchool authenticatedSchool = null;
        if (school != null) {
            SchoolService.SchoolType type = schoolService.schoolType(school.getId());
            authenticatedSchool = new AuthenticatedSchool(school.getId(), type.isHigher(), type.isVocational(),
                    type.isDoctoral(), school.getIsLetterGrade() != null ? school.getIsLetterGrade().booleanValue() : false,
                    EntityUtil.getCode(school.getEhisSchool()));
            OisFile logo = school.getLogo();
            if (logo != null) {
                authenticatedSchool.setLogo(logo.getFdata());
            }
            if (user.getStudent() != null) {
                List<?> result = em.createNativeQuery("select c.is_higher, level.value "
                        + "from student s "
                        + "join curriculum_version cv on s.curriculum_version_id = cv.id "
                        + "join curriculum c on c.id = cv.curriculum_id "
                        + "join classifier level on level.code = c.orig_study_level_code "
                        + "where s.id = ?1"
                        + "").setParameter(1, user.getStudent()).setMaxResults(1).getResultList();
                Object row = result.get(0);
                Boolean higher = resultAsBoolean(row, 0);
                String studyLevel = resultAsString(row, 1);
                authenticatedUser.setVocational(Boolean.valueOf(Boolean.FALSE.equals(higher)));
                authenticatedUser.setHigher(Boolean.valueOf(Boolean.TRUE.equals(higher)));
                authenticatedUser.setDoctoral(Boolean.valueOf(studyLevel.startsWith("7")));
            } else {
                // take values from school
                authenticatedUser.setVocational(Boolean.valueOf(type.isVocational()));
                authenticatedUser.setHigher(Boolean.valueOf(type.isHigher()));
                authenticatedUser.setDoctoral(Boolean.valueOf(type.isDoctoral()));
            }
        }
        Long schoolId = school != null ? school.getId() : null;
        Long teacherId = authenticatedUser.getTeacher();
        if (teacherId != null) {
            List<?> result = em.createNativeQuery("select sg.id from student_group sg where sg.teacher_id = ?1")
                .setParameter(1, teacherId)
                .getResultList();
            authenticatedUser.setTeacherGroupIds(StreamUtil.toMappedList(r -> resultAsLong(r, 0), result));
            if (schoolId != null && em.createNativeQuery("select c.id from curriculum c where c.teacher_id = ?1 and c.school_id = ?2")
                    .setParameter(1, teacherId).setParameter(2, schoolId).getResultList().size() > 0) {
                authenticatedUser.setHasCurriculums(Boolean.TRUE);
            } else {
                authenticatedUser.setHasCurriculums(Boolean.FALSE);
            }
        }
        authenticatedUser.setSchool(authenticatedSchool);
        authenticatedUser.setAuthorizedRoles(userDetails.getAuthorities());
        authenticatedUser.setFullname(user.getPerson().getFullname());
        authenticatedUser.setUsers(userService.findAllActiveUsers(user.getPerson().getId()));
        authenticatedUser.setLoginMethod(userDetails.getLoginMethod());

        // log login information
        UserSessions login = new UserSessions();
        login.setPerson(user.getPerson());
        login.setUser(user);
        LoginMethod loginMethod = userDetails.getLoginMethod();
        if(loginMethod == null) {
            loginMethod = LoginMethod.LOGIN_TYPE_K;
        }
        login.setType(em.getReference(Classifier.class, loginMethod.name()));
        login.setIpAddress(request.getRemoteAddr());
        String userAgent = request.getHeader("User-Agent");
        login.setUserBrowser(userAgent != null ? userAgent : "missing User-Agent header");
        HttpSession session = request.getSession(false);
        login.setSessionId(session != null ? session.getId() : "no session");
        em.persist(login);

        return authenticatedUser;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            // mark session as ended
            em.createNativeQuery("update user_sessions set ended = ?1 where session_id = ?2")
                .setParameter(1, parameterAsTimestamp(LocalDateTime.now()))
                .setParameter(2, session.getId())
                .executeUpdate();
        }
    }
}

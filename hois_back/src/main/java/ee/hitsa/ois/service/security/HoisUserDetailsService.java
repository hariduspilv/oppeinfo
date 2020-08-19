package ee.hitsa.ois.service.security;

import static ee.hitsa.ois.util.JpaQueryUtil.parameterAsTimestamp;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.GuestStudentRightsFilter;
import ee.hitsa.ois.enums.StudentType;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.service.UserService;
import ee.hitsa.ois.util.ClassifierUtil;
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
        Person person = personRepository.findByIdcodeOrUniqueCode(idcode);
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
        Long schoolId = school != null ? school.getId() : null;
        Long teacherId = authenticatedUser.getTeacher();
        
        if (school != null) {
            SchoolService.SchoolType type = schoolService.schoolType(schoolId);
            authenticatedSchool = new AuthenticatedSchool(school.getId(), type.isHigher(), type.isVocational(),
                    type.isDoctoral(), school.getIsLetterGrade() != null ? school.getIsLetterGrade().booleanValue() : false,
                    EntityUtil.getCode(school.getEhisSchool()), school.getIsWithoutEkis() != null ? school.getIsWithoutEkis().booleanValue() : false,
                    Boolean.TRUE.equals(school.getIsHmodules()), Boolean.TRUE.equals(school.getIsNotAbsence()));
            OisFile logo = school.getLogo();
            if (logo != null) {
                authenticatedSchool.setLogo(logo.getFdata());
            }
            if (user.getStudent() != null) {
                List<?> result = em.createNativeQuery("select case when c.is_higher is not null then c.is_higher else d.is_higher end, level.value "
                        + "from student s "
                        + "left join curriculum_version cv on s.curriculum_version_id = cv.id "
                        + "left join curriculum c on c.id = cv.curriculum_id "
                        + "left join classifier level on level.code = c.orig_study_level_code "
                        + "left join directive_student ds on ds.student_id = s.id "
                        + "left join directive d on (d.id = ds.directive_id and d.type_code = 'KASKKIRI_KYLALIS') "
                        + "where s.id = ?1"
                        + "").setParameter(1, user.getStudent()).setMaxResults(1).getResultList();
                Object row = result.get(0);
                Boolean higher = resultAsBoolean(row, 0);
                String studyLevel = resultAsString(row, 1);
                authenticatedUser.setVocational(Boolean.valueOf(Boolean.FALSE.equals(higher)));
                authenticatedUser.setHigher(Boolean.valueOf(Boolean.TRUE.equals(higher)));
                if (studyLevel != null) {
                    authenticatedUser.setDoctoral(Boolean.valueOf(studyLevel.startsWith("7")));
                } else {
                    authenticatedUser.setDoctoral(Boolean.FALSE);
                }
                authenticatedUser.setCommittees(Collections.emptyList());
                authenticatedUser.setType(EntityUtil.getNullableCode(user.getStudent().getType()));
            } else {
                // take values from school
                authenticatedUser.setVocational(Boolean.valueOf(type.isVocational()));
                authenticatedUser.setHigher(Boolean.valueOf(type.isHigher()));
                authenticatedUser.setDoctoral(Boolean.valueOf(type.isDoctoral()));
                
                Query committeeQuery = em.createNativeQuery("select distinct c.type_code from committee_member cm "
                        + "join committee c on cm.committee_id = c.id "
                        + "where c.school_id = ?1 and (cm.person_id = ?2 "
                        + (teacherId != null ? "or cm.teacher_id = ?3)" : ")")).setParameter(1, schoolId).setParameter(2, user.getPerson().getId());
                if (teacherId != null) {
                    committeeQuery.setParameter(3, teacherId);
                }
                List<?> committeeList = committeeQuery.getResultList();
                authenticatedUser.setCommittees(StreamUtil.toMappedList(r -> resultAsString(r, 0), committeeList));
            }
        }

        if (teacherId != null) {
            List<?> result = em.createNativeQuery("select sg.id from student_group sg where sg.teacher_id = ?1")
                .setParameter(1, teacherId)
                .getResultList();
            authenticatedUser.setTeacherGroupIds(StreamUtil.toMappedList(r -> resultAsLong(r, 0), result));
            if (schoolId != null && em.createNativeQuery("select c.id from curriculum c where c.teacher_id = ?1 and c.school_id = ?2")
                    .setParameter(1, teacherId).setParameter(2, schoolId).getResultList().size() > 0) {
                authenticatedUser.setIsCurriculumTeacher(Boolean.TRUE);
            } else {
                authenticatedUser.setIsCurriculumTeacher(Boolean.FALSE);
            }
        }
        authenticatedUser.setSchool(authenticatedSchool);
        setAuthorizedRoles(authenticatedUser, userDetails);
        authenticatedUser.setFullname(user.getPerson().getFullname());
        authenticatedUser.setUsers(userService.findAllActiveUsers(user.getPerson().getId()));
        authenticatedUser.setLoginMethod(userDetails.getLoginMethod());
        // Admin school role only!
        authenticatedUser.setHasSchoolRole(Boolean.valueOf(user.getUserSchoolRole() != null));

        if (userDetails.isLeadingTeacher()) {
            authenticatedUser.setCurriculums(userDetails.getCurriculumIds());
        }
        
        authenticatedUser.setMustAgreeWithToS(Boolean.valueOf(checkIfContractAgreementNeeds(userDetails)));

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

    private void setAuthorizedRoles(AuthenticatedUser authenticatedUser, HoisUserDetails user) {
        Collection<GrantedAuthority> authorizedRoles = user.getAuthorities();
        if (user.getStudentId() != null && user.isStudent()) {
            Student student = em.getReference(Student.class, user.getStudentId());
            Classifier studentType = student.getType();
            // Filter only guest students
            if (studentType != null && ClassifierUtil.equals(StudentType.OPPUR_K, studentType)) {
                List<String> filter = GuestStudentRightsFilter.FILTER;
                authorizedRoles = authorizedRoles.stream().filter(p -> !filter.contains(p.getAuthority())).collect(Collectors.toList());
            }
        }
        authenticatedUser.setAuthorizedRoles(authorizedRoles);
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
    
    public boolean checkIfContractAgreementNeeds(HoisUserDetails user) {
        if (!user.isStudent()) {
            return false;
        }
        School school = em.getReference(School.class, user.getSchoolId());
        if (Boolean.TRUE.equals(school.getIsStudentTerms())) {
            Student student = em.getReference(Student.class, user.getStudentId());
            return !Boolean.TRUE.equals(student.getIsContractAgreed());
        }
        return false;
    }
}

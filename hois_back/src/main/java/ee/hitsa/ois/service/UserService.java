package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.UserRights;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentRepresentative;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.UserProjection;
import ee.hitsa.ois.web.dto.UserRolesDto;

@Transactional
@Service
public class UserService {

    @Autowired
    private EntityManager em;

    /**
     * Create user for logged in user without any roles in ois
     *
     * @param person
     * @return
     */
    public User createUser(Person person) {
        if(!em.contains(person)) {
            person = em.merge(person);
        }
        Role guest = Role.ROLL_X;
        User user = userFor(person, null, guest);
        if(user == null) {
            user = createUser(person, null, guest, LocalDate.now());
            em.persist(user);
        }
        return user;
    }

    /**
     * Create user for given representative
     *
     * @param representative
     * @return
     */
    public User createUser(StudentRepresentative representative) {
        User user = createUser(representative.getPerson(), representative.getStudent().getSchool(), Role.ROLL_L, LocalDate.now());
        user.setStudent(representative.getStudent());
        return EntityUtil.save(user, em);
    }

    /**
     * Delete user for given representative
     *
     * @param representative
     */
    public void deleteUser(StudentRepresentative representative) {
        User user = userFor(representative.getPerson(), EntityUtil.getId(representative.getStudent()), Role.ROLL_L);
        if(user != null) {
            EntityUtil.deleteEntity(user, em);
        }
    }

    /**
     * Disable user for given student
     *
     * @param student
     * @param disabledDate
     */
    public void disableUser(Student student, LocalDate disabledDate) {
        disableUser(student.getPerson(), student.getId(), Role.ROLL_T, disabledDate);
    }

    /**
     * Enable user for given student
     *
     * @param student
     * @param enabledDate
     */
    public void enableUser(Student student, LocalDate enabledDate) {
        User user = userFor(student.getPerson(), student.getId(), Role.ROLL_T);
        if(user == null) {
            user = createUser(student.getPerson(), student.getSchool(), Role.ROLL_T, enabledDate);
            user.setStudent(student);
            em.persist(user);
        } else {
            user.setValidFrom(enabledDate);
            user.setValidThru(null);
        }
    }

    /**
     * Disable user for given teacher
     *
     * @param teacher
     * @param disabledDate
     */
    public void disableUser(Teacher teacher, LocalDate disabledDate) {
        disableUser(teacher.getPerson(), teacher.getId(), Role.ROLL_O, disabledDate);
    }

    /**
     * Enable user for given teacher
     *
     * @param teacher
     * @param enabledDate
     */
    public void enableUser(Teacher teacher, LocalDate enabledDate) {
        User user = userFor(teacher.getPerson(), teacher.getId(), Role.ROLL_O);
        if(user == null) {
            user = createUser(teacher.getPerson(), teacher.getSchool(), Role.ROLL_O, enabledDate);
            user.setTeacher(teacher);
            em.persist(user);
        } else {
            user.setValidFrom(enabledDate);
            user.setValidThru(null);
        }
    }

    /**
     * Find all active users for given person
     *
     * @param personId
     * @return
     */
    public List<UserProjection> findAllActiveUsers(Long personId) {
        // TODO c.name_et depends on parameter
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(ACTIVE_FROM).sort("c.name_et", "s.code");

        qb.requiredCriteria("u.person_id = :personId", "personId", personId);
        qb.requiredCriteria("(u.role_code = :guestRole or exists(select 1 from user_rights r where u.id = r.user_id))", "guestRole", Role.ROLL_X);
        qb.validNowCriteria("u.valid_from", "u.valid_thru");

        List<?> resultList = qb.select("u.id, s.code, u.role_code", em).getResultList();
        List<UserProjection> users = StreamUtil.toMappedList(r -> new UserProjection(
                resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2)
        ), resultList);

        // return ROLE_X only if it's single role person does have
        if(users.size() <= 1) {
            return users;
        }
        return users.stream().filter(r -> !Role.ROLL_X.name().equals(r.getRole())).collect(Collectors.toList());
    }

    public UserRolesDto rolesDefaults() {
        List<?> data = em.createNativeQuery("select role_code, object_code, permission_code from user_role_default").getResultList();
        Map<String, Map<String, List<String>>> rights = data.stream().collect(
                Collectors.groupingBy(r -> resultAsString(r, 0),
                        Collectors.groupingBy(r -> resultAsString(r, 1), Collectors.mapping(r -> resultAsString(r, 2), Collectors.toList()))));
        return new UserRolesDto(rights);
    }

    private static void disableUser(Person person, Long id, Role role, LocalDate disabledDate) {
        User user = userFor(person, id, role);
        if(user != null && (user.getValidThru() == null || disabledDate.isBefore(user.getValidThru()))) {
            user.setValidThru(disabledDate);
        }
    }

    private static User userFor(Person person, Long id, Role role) {
        Set<User> users = person.getUsers();
        if(users == null || users.isEmpty()) {
            return null;
        }

        Predicate<User> idFilter;
        if(Role.ROLL_O.equals(role)) {
            if(id == null) {
                return null;
            }
            idFilter = u -> id.equals(EntityUtil.getNullableId(u.getTeacher()));
        } else if(Role.ROLL_T.equals(role) || Role.ROLL_L.equals(role)) {
            if(id == null) {
                return null;
            }
            idFilter = u -> id.equals(EntityUtil.getNullableId(u.getStudent()));
        } else {
            // other roles don't have id field in User
            idFilter = u -> true;
        }
        return person.getUsers().stream().filter(u -> ClassifierUtil.equals(role, u.getRole()) && idFilter.test(u)).findFirst().orElse(null);
    }

    private User createUser(Person person, School school, Role role, LocalDate validFrom) {
        User user = new User();
        user.setPerson(person);
        user.setRole(em.getReference(Classifier.class, role.name()));
        user.setSchool(school);
        user.setValidFrom(validFrom);
        setDefaultRights(user);
        return user;
    }

    private void setDefaultRights(User user) {
        List<?> data = em.createNativeQuery("select object_code, permission_code from user_role_default where role_code=?1").setParameter(1, EntityUtil.getCode(user.getRole())).getResultList();
        user.setUserRights(StreamUtil.toMappedList(r -> {
            UserRights userRights = new UserRights();
            userRights.setUser(user);
            userRights.setObject(em.getReference(Classifier.class, resultAsString(r, 0)));
            userRights.setPermission(em.getReference(Classifier.class, resultAsString(r, 1)));
            return userRights;
        }, data));
    }

    private static final String ACTIVE_FROM = "from user_ u " +
            "inner join classifier c on u.role_code = c.code " +
            "left outer join school s on u.school_id = s.id";
}

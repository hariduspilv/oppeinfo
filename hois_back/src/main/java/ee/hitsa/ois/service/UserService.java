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
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.UserProjection;
import ee.hitsa.ois.web.dto.UserRolesDto;

@Transactional
@Service
public class UserService {

    @Autowired
    private EntityManager em;

    public User createUser(StudentRepresentative representative) {
        User user = createUser(representative.getPerson(), representative.getStudent().getSchool(), Role.ROLL_L, LocalDate.now());
        return EntityUtil.save(user, em);
    }

    public void disableUser(Student student, LocalDate disabledDate) {
        disableUser(student.getPerson(), student.getId(), Role.ROLL_T, disabledDate);
    }

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

    public void disableUser(Teacher teacher, LocalDate disabledDate) {
        disableUser(teacher.getPerson(), teacher.getId(), Role.ROLL_O, disabledDate);
    }

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

    public List<UserProjection> findAllActiveUsers(Long personId) {
        // TODO c.name_et depends on parameter
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(ACTIVE_FROM).sort("c.name_et", "s.code");

        qb.requiredCriteria("u.person_id = :personId", "personId", personId);
        qb.filter("exists(select 1 from user_rights r where u.id = r.user_id)");
        qb.validNowCriteria("u.valid_from", "u.valid_thru");

        List<?> resultList = qb.select("u.id, s.code, u.role_code", em).getResultList();
        return StreamUtil.toMappedList(r -> new UserProjection(
                resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2)
        ), resultList);
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
        } else if(Role.ROLL_T.equals(role)) {
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

    private static String ACTIVE_FROM = "from user_ u " +
            "inner join classifier c on u.role_code = c.code " +
            "left outer join school s on u.school_id = s.id";
}

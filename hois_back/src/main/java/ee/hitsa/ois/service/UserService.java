package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.UserRights;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentRepresentative;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.enums.StudentType;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.service.SchoolService.SchoolType;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.TranslateUtil;
import ee.hitsa.ois.validation.EstonianIdCodeValidator;
import ee.hitsa.ois.web.commandobject.SearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.UserProjection;
import ee.hitsa.ois.web.dto.UserRolesDto;

@Transactional
@Service
public class UserService {

    @Autowired
    private EntityManager em;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private SchoolService schoolService;

    public static final Map<String, Set<String>> LEADING_TEACHER_EXTRA_RIGHTS = Collections.unmodifiableMap(
        new HashMap<String, Set<String>>() {{
            put(PermissionObject.TEEMAOIGUS_MOODULPROTOKOLL.name(), Collections.singleton(Permission.OIGUS_M.name()));
            put(PermissionObject.TEEMAOIGUS_PRAKTIKAPAEVIK.name(), new HashSet<>(Arrays.asList(Permission.OIGUS_M.name(), Permission.OIGUS_K.name())));
            put(PermissionObject.TEEMAOIGUS_PAEVIK.name(), Collections.singleton(Permission.OIGUS_M.name()));
            put(PermissionObject.TEEMAOIGUS_PAEVIKYLE.name(), Collections.singleton(Permission.OIGUS_M.name()));
            put(PermissionObject.TEEMAOIGUS_TUNNIJAOTUSPLAAN.name(), Collections.singleton(Permission.OIGUS_M.name()));
            put(PermissionObject.TEEMAOIGUS_VOTA.name(), Collections.singleton(Permission.OIGUS_M.name()));
            put(PermissionObject.TEEMAOIGUS_VOTAKOM.name(), Collections.singleton(Permission.OIGUS_M.name()));
            put(PermissionObject.TEEMAOIGUS_OPPERYHM.name(), Collections.singleton(Permission.OIGUS_M.name()));
        }});

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
    
    public void enableUser(User user, LocalDate enabledDate) {
        if (user != null) {
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
        StringBuilder from = new StringBuilder(ACTIVE_FROM).append(" ");
        from.append("left join student st on st.id = u.student_id "); // Student or Representative
        from.append("left join person p on p.id = st.person_id "); // Student person
        from.append("left join student_group sg on sg.id = st.student_group_id "); // Student group
        from.append("left join user_school_role usr on usr.id = u.user_school_role_id "); // User school role
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString()).sort("c.name_et", "s.code");

        qb.requiredCriteria("u.person_id = :personId", "personId", personId);
        qb.requiredCriteria("(u.role_code = :guestRole or exists(select 1 from user_rights r where u.id = r.user_id))", "guestRole", Role.ROLL_X);
        qb.validNowCriteria("u.valid_from", "u.valid_thru");
        // Should not shown roles for representative and student if student is not active.
        qb.requiredCriteria("case when st.id is not null then st.status_code in (:studentActive) else true end", "studentActive", StudentStatus.STUDENT_STATUS_ACTIVE);

        Map<Long, SchoolType> schoolTypes = new HashMap<>();
        
        List<?> resultList = qb.select("u.id, s.code, u.role_code, c.name_et, c.extraval1, c.name_en, c.extraval2, "
                + "p.firstname, sg.code as sg_code, s.id as s_id, usr.name_et as usr_et, usr.name_en as usr_en, st.type_code", em).getResultList();
        List<UserProjection> users = StreamUtil.toMappedList(r -> {
            String code = resultAsString(r, 2);
            
            String nameEt = null;
            String nameEn = null;
            
            if (code.equals(Role.ROLL_O.name())) {
                // Teacher in Estonian should be "Opetaja" for vocational schools and "Oppejoud" for high schools.
                Long schoolId = resultAsLong(r, 9);
                if (schoolId != null && !schoolTypes.containsKey(schoolId)) {
                    schoolTypes.put(schoolId, schoolService.schoolType(schoolId));
                }
                if (schoolId != null && schoolTypes.get(schoolId).isHigher()) {
                    String translatedEt = TranslateUtil.optionalTranslate("teacherHigher", Language.ET);
                    if (!translatedEt.equals("teacherHigher")) {
                        nameEt = translatedEt;
                    }
                }
            } else if (code.equals(Role.ROLL_A.name())) {
                String userRoleEt = resultAsString(r, 10);
                String userRoleEn = resultAsString(r, 11);
                if (userRoleEt != null) {
                    nameEt = userRoleEt;
                }
                if (userRoleEn != null) {
                    nameEn = userRoleEn;
                }
            }
            
            // Extra value is taken if exists as name
            String ext1 = resultAsString(r, 4);
            String ext2 = resultAsString(r, 6);
            if (nameEt == null) {
                nameEt = ext1 != null ? ext1 : resultAsString(r, 3);
            }
            if (nameEn == null) {
                nameEn = ext2 != null ? ext2 : resultAsString(r, 5);
            }
            
            if (StudentType.OPPUR_K.name().equals(resultAsString(r, 12))) {
                nameEt += " (KY)";
                nameEn += " (KY)";
            } else if (StudentType.OPPUR_E.name().equals(resultAsString(r, 12))) {
                nameEt += " (E)";
                nameEn += " (E)";
            }
            
            return new UserProjection(resultAsLong(r, 0), resultAsString(r, 1), code, nameEt, nameEn, resultAsString(r, 7),  resultAsString(r, 8));
        }, resultList);

        // return ROLE_X only if it's single role person does have
        if(users.size() <= 1) {
            return users;
        }
        return StreamUtil.toFilteredList(r -> !Role.ROLL_X.name().equals(r.getRole()), users);
    }

    public UserRolesDto rolesDefaults() {
        List<?> data = em.createNativeQuery("select role_code, object_code, permission_code from user_role_default").getResultList();
        Map<String, Map<String, Set<String>>> rights = data.stream().collect(
                Collectors.groupingBy(r -> resultAsString(r, 0),
                        Collectors.groupingBy(r -> resultAsString(r, 1), Collectors.mapping(r -> resultAsString(r, 2), Collectors.toSet()))));
        Map<String, Map<String, Set<String>>> extraRights = new HashMap<>();
        extraRights.put(Role.ROLL_J.name(), LEADING_TEACHER_EXTRA_RIGHTS);
        return new UserRolesDto(rights, extraRights);
    }

    public void createPersonUserIfNecessary(String idcode, String lastname, String firstname) {
        Person person = personRepository.findByIdcode(idcode);
        if (person == null || findAllActiveUsers(person.getId()).isEmpty()) {
            // either person or user not found

            // hack: we are going to change authentication to allow audit info filled
            Authentication oldAuthentication = SecurityContextHolder.getContext().getAuthentication();
            try {
                if (person == null) {
                    person = new Person();
                    person.setLastname(PersonUtil.initCapName(lastname));
                    person.setFirstname(PersonUtil.initCapName(firstname));
                    person.setIdcode(idcode);
                    person.setBirthdate(EstonianIdCodeValidator.birthdateFromIdcode(idcode));
                    person.setSex(em.getReference(Classifier.class, EstonianIdCodeValidator.sexFromIdcode(idcode)));
                }
                SecurityContextHolder.getContext().setAuthentication(createInitialAuthentication(person));
                if (person.getId() == null) {
                    em.persist(person);
                }
                createUser(person);
            } finally {
                SecurityContextHolder.getContext().setAuthentication(oldAuthentication);
            }
        }
    }

    private static Authentication createInitialAuthentication(Person person) {
        return new UsernamePasswordAuthenticationToken(PersonUtil.fullnameAndIdcode(person), null,
                Collections.singletonList((GrantedAuthority)(() -> Role.ROLL_X.name())));
    }

    private static void disableUser(Person person, Long id, Role role, LocalDate disabledDate) {
        User user = userFor(person, id, role);
        disableUser(user, disabledDate);
    }

    public static void disableUser(User user, LocalDate disabledDate) {
        if(user != null && (user.getValidThru() == null || disabledDate.isBefore(user.getValidThru()))) {
            user.setValidThru(disabledDate);
        }
    }

    static User userFor(Person person, Long id, Role role) {
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

    public List<AutocompleteResult> getCurriculums(Long schoolId, SearchCommand term) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum c "
                + "left join curriculum_speciality cs on cs.curriculum_id = c.id "
                + "left join classifier_connect clc on clc.classifier_code = c.orig_study_level_code")
                .groupBy("c.id");

        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalContains(Language.EN.equals(term.getLang()) ? "concat(c.mer_code, ' - ', c.name_en, ' (', c.code, ')')"
                : "concat(c.mer_code, ' - ', c.name_et, ' (', c.code, ')')", "name", term.getName());
        qb.requiredCriteria("c.status_code != :status", "status", CurriculumStatus.OPPEKAVA_STAATUS_C);
        
        qb.sort(Language.EN.equals(term.getLang()) ? "c.name_en, c.code" : "c.name_et, c.code");
        List<?> data = qb.select("c.id, c.mer_code, c.name_et, c.name_en, c.code", em)
                .setMaxResults(20).getResultList();
        return data.stream().map(r -> {
            String merCode = resultAsString(r, 1);
            String code = resultAsString(r, 4);
            return new AutocompleteResult(resultAsLong(r, 0),
                    CurriculumUtil.curriculumName(merCode, code, resultAsString(r, 2)),
                    CurriculumUtil.curriculumName(merCode, code, resultAsString(r, 3)));
        }).collect(Collectors.toList());
    }
}

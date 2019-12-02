package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsStringList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.UserCurriculum;
import ee.hitsa.ois.domain.UserRights;
import ee.hitsa.ois.domain.UserSchoolRole;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.teacher.TeacherOccupation;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.SchoolService.SchoolType;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.EstonianIdCodeValidator;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.PersonForm;
import ee.hitsa.ois.web.commandobject.UserForm;
import ee.hitsa.ois.web.commandobject.UsersSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.UserDto;
import ee.hitsa.ois.web.dto.UsersSearchDto;

/*
 * TODO: extra checks for Hois Automaatteade person with id = -1
 */
@Transactional
@Service
public class PersonService {

    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private SchoolService schoolService;

    private static final String PERSON_FROM = "from person p " +
            "left outer join user_ u on p.id=u.person_id " +
            "left outer join (select array_agg(uu.role_code) as roll, array_agg(usr.id) as user_role, " +
            "array_agg(case when usr.id is null then tocc.id else null end) as occupation, uu.person_id, uu.school_id " + // It should not include occupation if this occupation has a role.
            "from user_ uu " + 
            "left outer join school s on uu.school_id = s.id " +
            "left outer join teacher t on t.id = uu.teacher_id " +
            "left outer join teacher_occupation tocc on tocc.id = t.teacher_occupation_id " +
            "left outer join user_school_role usr on usr.id = uu.user_school_role_id or tocc.id = usr.teacher_occupation_id " +
            "group by uu.person_id, uu.school_id) roles " +
            "on u.person_id=roles.person_id and (u.school_id=roles.school_id or u.school_id is null and roles.school_id is null) ";
    
    private static final String PERSON_SELECT = "distinct p.idcode, p.firstname, p.lastname, u.school_id, p.id,"
            + "array_to_string(roles.roll, ', ') as r_arr, array_to_string(roles.user_role, ', ') as ur_arr, array_to_string(roles.occupation, ', ')";
    private static final String PERSON_COUNT_SELECT = "count (distinct (p.idcode, p.firstname, p.lastname, u.school_id, p.id,array_to_string(roles.roll, ', '), "
            + "array_to_string(roles.user_role, ', '), array_to_string(roles.occupation, ', ')))";

    public Page<UsersSearchDto> search(UsersSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(PERSON_FROM).sort(pageable);

        qb.requiredCriteria("p.id != :systemUserId", "systemUserId",PersonUtil.AUTOMATIC_SENDER_ID);
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", criteria.getName());

        qb.optionalCriteria("p.idcode = :idcode", "idcode", criteria.getIdcode());
        qb.optionalCriteria("roles.school_id = :school", "school", criteria.getSchool());
        qb.optionalCriteria(":roll = ANY(roles.roll)", "roll", criteria.getRole());
        
        // Same field, but 2 diff parameters on page. Still userRole comes same as occupation because md-select index it by userRole (id)
        if (criteria.getOccupation() != null) {
            qb.optionalCriteria(":occupation = ANY(roles.occupation)", "occupation", criteria.getOccupation());
        } else {
            qb.optionalCriteria(":userRole = ANY(roles.user_role)", "userRole", criteria.getUserRole());
        }

        Page<Object[]> result = JpaQueryUtil.pagingResult(qb.select(PERSON_SELECT, em), pageable, () -> qb.count(PERSON_COUNT_SELECT, em));

        Set<Long> schoolIds = result.getContent().stream().filter(s -> s[3] != null).map(s -> resultAsLong(s,3)).collect(Collectors.toSet());
        Map<Long, AutocompleteResult> schools = schoolIds.isEmpty() ? Collections.emptyMap() :
            em.createQuery("select s from School s where s.id in (?1)", School.class)
                .setParameter(1, schoolIds).getResultList().stream().collect(Collectors.toMap(School::getId, AutocompleteResult::of));

        Set<Long> userRoleIds = result.getContent().stream()
                .filter(s -> s[6] != null)
                .flatMap(s -> resultAsStringList(s, 6, ", ").stream())
                .distinct()
                .map(id -> Long.valueOf(id))
                .collect(Collectors.toSet());
        Map<Long, AutocompleteResult> userRoles = userRoleIds.isEmpty() ? Collections.emptyMap() :
            em.createQuery("select usr from UserSchoolRole usr where usr.id in (?1)", UserSchoolRole.class)
                .setParameter(1, userRoleIds).getResultList().stream().collect(Collectors.toMap(UserSchoolRole::getId, AutocompleteResult::of));
        
        Set<Long> occupationIds = result.getContent().stream()
                .filter(s -> s[7] != null)
                .flatMap(s -> resultAsStringList(s, 7, ", ").stream())
                .distinct()
                .map(id -> Long.valueOf(id))
                .collect(Collectors.toSet());
        Map<Long, AutocompleteResult> occupations = occupationIds.isEmpty() ? Collections.emptyMap() :
            em.createQuery("select tocc from TeacherOccupation tocc where tocc.id in (?1)", TeacherOccupation.class)
                .setParameter(1, occupationIds).getResultList().stream().collect(Collectors.toMap(TeacherOccupation::getId, AutocompleteResult::of));

        return result.map(r -> {
            UsersSearchDto dto = new UsersSearchDto();
            dto.setIdcode(resultAsString(r, 0));
            dto.setName(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setSchool(schools.get(resultAsLong(r, 3)));
            dto.setId(resultAsLong(r, 4));

            // TODO implements UserType(array).
            // UserType is possible to use during mapping of entity as UserType does not implement Type interface.
            // Should be done using BasicType and CustomDialect.
            dto.setRole(resultAsStringList(r, 5, ", "));
            dto.setUserRole(Stream.concat(
                    resultAsStringList(r, 6, ", ").stream().map(roleId -> userRoles.get(Long.valueOf(roleId))),
                    resultAsStringList(r, 7, ", ").stream().map(occupationId -> occupations.get(Long.valueOf(occupationId))))
                    .collect(Collectors.toList()));
            return dto;
        });
    }

    public UserDto initialValueForUser(HoisUserDetails hoisUser, Person person) {
        User user = new User();
        user.setPerson(person);
        user.setValidFrom(LocalDate.now());
        if(hoisUser.isSchoolAdmin()) {
            user.setSchool(em.getReference(School.class, hoisUser.getSchoolId()));
            UserDto dto = UserDto.of(user);
            SchoolType type = schoolService.schoolType(hoisUser.getSchoolId());
            dto.setHigher(Boolean.valueOf(type.isHigher()));
            dto.setVocational(Boolean.valueOf(type.isVocational()));
            return dto;
        }
        return UserDto.of(user);
    }

    public Person create(PersonForm personForm) {
        return save(personForm, new Person());
    }

    public Person save(PersonForm personForm, Person person) {
        if(PersonUtil.AUTOMATIC_SENDER_ID.equals(person.getId())) {
            throw new AssertionFailedException("Cannot edit system user");
        }
        EntityUtil.bindToEntity(personForm, person, classifierRepository);
        person.setBirthdate(EstonianIdCodeValidator.birthdateFromIdcode(personForm.getIdcode()));
        person.setSex(em.getReference(Classifier.class, EstonianIdCodeValidator.sexFromIdcode(personForm.getIdcode())));
        return EntityUtil.save(person, em);
    }

    public UserDto getUser(User user) {
        UserDto dto = UserDto.of(user);
        if (user.getSchool() != null) {
            SchoolType type = schoolService.schoolType(user.getSchool().getId());
            dto.setHigher(Boolean.valueOf(type.isHigher()));
            dto.setVocational(Boolean.valueOf(type.isVocational()));
        }
        return dto;
    }

    public User saveUser(HoisUserDetails userDetails, UserForm userForm, User user) {
        EntityUtil.setUsername(userDetails.getUsername(), em);
        EntityUtil.bindToEntity(userForm, user, classifierRepository, "school", "userRights", "userRole", "curriculums");
        user.setSchool(EntityUtil.getOptionalOne(School.class, userForm.getSchool(), em));
        if (ClassifierUtil.equals(Role.ROLL_A, user.getRole())) {
            user.setUserSchoolRole(EntityUtil.getOptionalOne(UserSchoolRole.class, userForm.getUserRole(), em));
        }

        // we are using List with two elements (object, permission) as tuple
        List<List<String>> newRights = new ArrayList<>();
        // ROLL_J cannot get any role which is not described in user_defaul_role
        if (ClassifierUtil.equals(Role.ROLL_J, user.getRole())) {
            // load default permissions
            List<?> defaults = em.createNativeQuery("select urd.object_code, urd.permission_code from user_role_default urd where urd.role_code = ?1")
                    .setParameter(1, Role.ROLL_J.name())
                    .getResultList();
            Map<String, Set<String>> defaultPermission = new HashMap<>();
            defaults.forEach(r -> {
                String object = resultAsString(r, 0);
                if (!defaultPermission.containsKey(object)) {
                    defaultPermission.put(object, new HashSet<>());
                }
                String permission = resultAsString(r, 1);
                defaultPermission.get(object).add(permission);
            });
    
            for(Map.Entry<String, List<String>> it : StreamUtil.nullSafeMap(userForm.getRights()).entrySet()) {
                if(!defaultPermission.containsKey(it.getKey())) {
                    // Should we throw or just blindly ignore it? XXX
//                    throw new AssertionFailedException("Unknown object code: " + it.getKey());
                    continue;
                }
                for(String p : StreamUtil.nullSafeList(it.getValue())) {
                    if(!defaultPermission.get(it.getKey()).contains(p)) {
                        // Should we throw or just blindly ignore it?
//                        throw new AssertionFailedException("Unknown permission code: " + p);
                        continue;
                    }
                    newRights.add(Arrays.asList(it.getKey(), p));
                }
            }
        } else {
            // load allowed codes
            List<?> cl = em.createNativeQuery("select c.code, c.main_class_code from classifier c where (c.main_class_code = ?1 and c.code in (select object_code from user_role_default where role_code = ?2)) or c.main_class_code = ?3")
                    .setParameter(1, MainClassCode.TEEMAOIGUS.name())
                    .setParameter(2, EntityUtil.getCode(user.getRole()))
                    .setParameter(3, MainClassCode.OIGUS.name())
                    .getResultList();
            Set<String> objects = StreamUtil.toMappedSet(r -> resultAsString(r, 0), cl.stream().filter(r -> MainClassCode.TEEMAOIGUS.name().equals(resultAsString(r, 1))));
            Set<String> permissions = StreamUtil.toMappedSet(r -> resultAsString(r, 0), cl.stream().filter(r -> MainClassCode.OIGUS.name().equals(resultAsString(r, 1))));
    
            for(Map.Entry<String, List<String>> it : StreamUtil.nullSafeMap(userForm.getRights()).entrySet()) {
                if(!objects.contains(it.getKey())) {
                    throw new AssertionFailedException("Unknown object code: " + it.getKey());
                }
                for(String p : StreamUtil.nullSafeList(it.getValue())) {
                    if(!permissions.contains(p)) {
                        throw new AssertionFailedException("Unknown permission code: " + p);
                    }
                    newRights.add(Arrays.asList(it.getKey(), p));
                }
            }
        }
        
        EntityUtil.bindEntityCollection(user.getUserRights(), r -> Arrays.asList(EntityUtil.getCode(r.getObject()), EntityUtil.getCode(r.getPermission())), newRights, id -> {
            UserRights ur = new UserRights();
            ur.setUser(user);
            ur.setObject(em.getReference(Classifier.class, id.get(0)));
            ur.setPermission(em.getReference(Classifier.class, id.get(1)));
            return ur;
        });
        
        if (Role.ROLL_J.name().equals(userForm.getRole())) {
            EntityUtil.bindEntityCollection(user.getUserCurriculums(), uc -> EntityUtil.getId(uc.getCurriculum()),
                    userForm.getCurriculums(), c -> c.getId(), c -> {
                        UserCurriculum curriculum = new UserCurriculum();
                        curriculum.setUser(user);
                        curriculum.setCurriculum(em.getReference(Curriculum.class, c.getId()));
                        return curriculum;
                    });
        } else {
            user.getUserCurriculums().clear();
        }

        if (user.getUserRights().isEmpty()) {
            throw new ValidationFailedException("user.roleNoRights");
        }
        return EntityUtil.save(user, em);
    }

    public User createUser(HoisUserDetails userDetails, UserForm userForm, Person person) {
        User user = new User();
        user.setPerson(person);
        ValidationFailedException.throwIf(userForm.getRole().equals(Role.ROLL_A.name())
                && person.getUsers().stream()
                    .filter(u -> ClassifierUtil.equals(Role.ROLL_A, u.getRole()))
                    .filter(u -> u.getSchool() != null && userForm.getSchool() != null && EntityUtil.getId(u.getSchool()).equals(userForm.getSchool().getId()))
                    .filter(u -> DateUtils.isValid(u.getValidFrom(), u.getValidThru()))
                    .findAny().isPresent(), "user.shouldBeUniqueAdmin");
        return saveUser(userDetails, userForm, user);
    }

    public void deleteUser(HoisUserDetails userDetails, User user) {
        EntityUtil.setUsername(userDetails.getUsername(), em);
        EntityUtil.deleteEntity(user, em);
    }    
    
    public void deleteUser(String username, User user) {
        EntityUtil.setUsername(username, em);
        EntityUtil.deleteEntity(user, em);
    }

    public void delete(HoisUserDetails user, Person person) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(person, em);
    }
}

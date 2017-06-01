package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.UserRights;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.repository.UserRepository;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.EstonianIdCodeValidator;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.PersonForm;
import ee.hitsa.ois.web.commandobject.UserForm;
import ee.hitsa.ois.web.commandobject.UserForm.UserRight;
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
    private PersonRepository personRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String PERSON_FROM = "from person p " +
            "left outer join user_ u on p.id=u.person_id " +
            "left outer join (select array_agg(uu.role_code) as roll, uu.person_id, uu.school_id " +
            "from user_ uu left outer join school s on uu.school_id = s.id group by uu.person_id, uu.school_id) roles on u.person_id=roles.person_id and (u.school_id=roles.school_id or u.school_id is null and roles.school_id is null)";

    private static final String PERSON_SELECT = "distinct p.idcode, p.firstname, p.lastname, u.school_id, p.id,array_to_string(roles.roll, ', ')";
    private static final String PERSON_COUNT_SELECT = "count (distinct (p.idcode, p.firstname, p.lastname, u.school_id, p.id,array_to_string(roles.roll, ', ')))";
    //private static final String PERSON_SELECT = "distinct p.idcode, p.firstname, p.lastname, u.school_id, roles.roll roll";

    public Page<UsersSearchDto> search(UsersSearchCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(PERSON_FROM).sort(pageable);

        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", criteria.getName());

        qb.optionalCriteria("p.idcode = :idcode", "idcode", criteria.getIdcode());
        qb.optionalCriteria("roles.school_id = :school", "school", criteria.getSchool());
        qb.optionalCriteria(":roll = ANY(roles.roll)", "roll", criteria.getRole());

        Page<Object[]> result = JpaQueryUtil.pagingResult(qb.select(PERSON_SELECT, em), pageable, () -> qb.count(PERSON_COUNT_SELECT, em));

        Set<Long> schoolIds = result.getContent().stream().filter(s -> s[3] != null).map(s -> resultAsLong(s,3)).collect(Collectors.toSet());

        Map<Long, AutocompleteResult> schools = schoolRepository.findAll(schoolIds).stream().collect(Collectors.toMap(School::getId, AutocompleteResult::of));

        return result.map(r -> {
            UsersSearchDto dto = new UsersSearchDto();
            dto.setIdcode(resultAsString(r, 0));
            dto.setName(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setSchool(schools.get(resultAsLong(r, 3)));
            dto.setId(resultAsLong(r, 4));

            // todo implements UserType(array)
            String roles = resultAsString(r, 5);
            dto.setRole(Arrays.asList((roles != null ? roles: "").split(", ")));

            return dto;
        });
    }

    public Person create(PersonForm personForm) {
        return save(personForm, new Person());
    }

    public Person save(PersonForm personForm, Person person) {
        EntityUtil.bindToEntity(personForm, person, classifierRepository);
        person.setBirthdate(EstonianIdCodeValidator.birthdateFromIdcode(personForm.getIdcode()));
        person.setSex(classifierRepository.getOne(EstonianIdCodeValidator.sexFromIdcode(personForm.getIdcode())));
        return personRepository.save(person);
    }

    public UserDto getUser(User user) {
        return UserDto.of(user, classifierRepository.findAllByMainClassCode(MainClassCode.TEEMAOIGUS.name()));
    }

    public User saveUser(UserForm userForm, User user) {
        EntityUtil.bindToEntity(userForm, user, classifierRepository, "school", "userRights");
        Long schoolId = userForm.getSchool() == null ? null : userForm.getSchool().getId();
        user.setSchool(EntityUtil.getOptionalOne(School.class, schoolId, em));

        Map<String, List<UserRights>> oldRights = user.getUserRights().stream().collect(Collectors.groupingBy(it -> EntityUtil.getCode(it.getObject())));
        Set<UserRights> result = new HashSet<>();

        Map<String, Classifier> oigused = StreamUtil.toMap(Classifier::getCode, classifierRepository.findAllByMainClassCode(MainClassCode.OIGUS.name()));
        Map<String, Classifier> teemaoigused = StreamUtil.toMap(Classifier::getCode, classifierRepository.findAllByMainClassCode(MainClassCode.TEEMAOIGUS.name()));

        for(UserRight it : userForm.getRights()) {
            List<UserRights> objectRights = oldRights.get(it.getObject());

            UserRights lookup = null;
            UserRights modify = null;
            UserRights confirmation = null;

            if (objectRights != null) {
                lookup = objectRights.stream().filter(ur -> ClassifierUtil.equals(Permission.OIGUS_V, ur.getPermission())).findFirst().orElse(null);
                modify = objectRights.stream().filter(ur -> ClassifierUtil.equals(Permission.OIGUS_M, ur.getPermission())).findFirst().orElse(null);
                confirmation = objectRights.stream().filter(ur -> ClassifierUtil.equals(Permission.OIGUS_K, ur.getPermission())).findFirst().orElse(null);
            }

            if (Boolean.TRUE.equals(it.getOigusV())) {
                if (lookup != null) {
                    result.add(lookup);
                } else {
                    UserRights userRights = new UserRights();
                    userRights.setUser(user);
                    userRights.setObject(teemaoigused.get(it.getObject()));
                    userRights.setPermission(oigused.get(Permission.OIGUS_V.name()));
                    result.add(userRights);
                }
            }
            if (Boolean.TRUE.equals(it.getOigusM())) {
                if (modify != null) {
                    result.add(modify);
                } else {
                    UserRights userRights = new UserRights();
                    userRights.setUser(user);
                    userRights.setObject(teemaoigused.get(it.getObject()));
                    userRights.setPermission(oigused.get(Permission.OIGUS_M.name()));
                    result.add(userRights);
                }
            }
            if (Boolean.TRUE.equals(it.getOigusK())) {
                if (confirmation != null) {
                    result.add(confirmation);
                } else {
                    UserRights userRights = new UserRights();
                    userRights.setUser(user);
                    userRights.setObject(teemaoigused.get(it.getObject()));
                    userRights.setPermission(oigused.get(Permission.OIGUS_K.name()));
                    result.add(userRights);
                }
            }
        }

        user.getUserRights().clear();
        user.getUserRights().addAll(result);

        if (user.getUserRights().isEmpty()) {
            throw new ValidationFailedException("user.roleNoRights");
        }
        return userRepository.save(user);
    }

    public User createUser(UserForm userForm, Person person) {
        User user = new User();
        user.setPerson(person);
        return saveUser(userForm, user);
    }

    public void deleteUser(User user) {
        EntityUtil.deleteEntity(userRepository, user);
    }

    public void delete(Person person) {
        EntityUtil.deleteEntity(personRepository, person);
    }
}

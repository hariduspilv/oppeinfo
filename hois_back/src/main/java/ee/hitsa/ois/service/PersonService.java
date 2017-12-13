package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
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

    private static final String PERSON_FROM = "from person p " +
            "left outer join user_ u on p.id=u.person_id " +
            "left outer join (select array_agg(uu.role_code) as roll, uu.person_id, uu.school_id " +
            "from user_ uu left outer join school s on uu.school_id = s.id group by uu.person_id, uu.school_id) roles on u.person_id=roles.person_id and (u.school_id=roles.school_id or u.school_id is null and roles.school_id is null)";

    private static final String PERSON_SELECT = "distinct p.idcode, p.firstname, p.lastname, u.school_id, p.id,array_to_string(roles.roll, ', ')";
    private static final String PERSON_COUNT_SELECT = "count (distinct (p.idcode, p.firstname, p.lastname, u.school_id, p.id,array_to_string(roles.roll, ', ')))";

    public Page<UsersSearchDto> search(UsersSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(PERSON_FROM).sort(pageable);

        qb.requiredCriteria("p.id != :systemUserId", "systemUserId",PersonUtil.AUTOMATIC_SENDER_ID);
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", criteria.getName());

        qb.optionalCriteria("p.idcode = :idcode", "idcode", criteria.getIdcode());
        qb.optionalCriteria("roles.school_id = :school", "school", criteria.getSchool());
        qb.optionalCriteria(":roll = ANY(roles.roll)", "roll", criteria.getRole());

        Page<Object[]> result = JpaQueryUtil.pagingResult(qb.select(PERSON_SELECT, em), pageable, () -> qb.count(PERSON_COUNT_SELECT, em));

        Set<Long> schoolIds = result.getContent().stream().filter(s -> s[3] != null).map(s -> resultAsLong(s,3)).collect(Collectors.toSet());
        Map<Long, AutocompleteResult> schools = schoolIds.isEmpty() ? Collections.emptyMap() :
            em.createQuery("select s from School s where s.id in (?1)", School.class)
                .setParameter(1, schoolIds).getResultList().stream().collect(Collectors.toMap(School::getId, AutocompleteResult::of));

        return result.map(r -> {
            UsersSearchDto dto = new UsersSearchDto();
            dto.setIdcode(resultAsString(r, 0));
            dto.setName(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setSchool(schools.get(resultAsLong(r, 3)));
            dto.setId(resultAsLong(r, 4));

            // TODO implements UserType(array)
            String roles = resultAsString(r, 5);
            dto.setRole(Arrays.asList((roles != null ? roles: "").split(", ")));

            return dto;
        });
    }

    public UserDto initialValueForUser(HoisUserDetails hoisUser, Person person) {
        User user = new User();
        user.setPerson(person);
        if(hoisUser.isSchoolAdmin()) {
            user.setSchool(em.getReference(School.class, hoisUser.getSchoolId()));
        }
        user.setValidFrom(LocalDate.now());
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
        return UserDto.of(user);
    }

    public User saveUser(HoisUserDetails userDetails, UserForm userForm, User user) {
        EntityUtil.setUsername(userDetails.getUsername(), em);
        EntityUtil.bindToEntity(userForm, user, classifierRepository, "school", "userRights");
        user.setSchool(EntityUtil.getOptionalOne(School.class, userForm.getSchool(), em));

        // load allowed codes
        List<?> cl = em.createNativeQuery("select c.code, c.main_class_code from classifier c where (c.main_class_code = ?1 and c.code in (select object_code from user_role_default where role_code = ?2)) or c.main_class_code = ?3")
                .setParameter(1, MainClassCode.TEEMAOIGUS.name()).setParameter(2, EntityUtil.getCode(user.getRole())).setParameter(3, MainClassCode.OIGUS.name()).getResultList();
        Set<String> objects = StreamUtil.toMappedSet(r -> resultAsString(r, 0), cl.stream().filter(r -> MainClassCode.TEEMAOIGUS.name().equals(resultAsString(r, 1))));
        Set<String> permissions = StreamUtil.toMappedSet(r -> resultAsString(r, 0), cl.stream().filter(r -> MainClassCode.OIGUS.name().equals(resultAsString(r, 1))));

        // we are using List with two elements (object, permission) as tuple
        List<List<String>> newRights = new ArrayList<>();
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
        EntityUtil.bindEntityCollection(user.getUserRights(), r -> Arrays.asList(EntityUtil.getCode(r.getObject()), EntityUtil.getCode(r.getPermission())), newRights, id -> {
            UserRights ur = new UserRights();
            ur.setUser(user);
            ur.setObject(em.getReference(Classifier.class, id.get(0)));
            ur.setPermission(em.getReference(Classifier.class, id.get(1)));
            return ur;
        });

        if (user.getUserRights().isEmpty()) {
            throw new ValidationFailedException("user.roleNoRights");
        }
        return EntityUtil.save(user, em);
    }

    public User createUser(HoisUserDetails userDetails, UserForm userForm, Person person) {
        User user = new User();
        user.setPerson(person);
        return saveUser(userDetails, userForm, user);
    }

    public void deleteUser(HoisUserDetails userDetails, User user) {
        EntityUtil.setUsername(userDetails.getUsername(), em);
        EntityUtil.deleteEntity(user, em);
    }

    public void delete(HoisUserDetails user, Person person) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(person, em);
    }
}

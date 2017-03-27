package ee.hitsa.ois.web;

import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.PersonService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.PersonForm;
import ee.hitsa.ois.web.commandobject.UserForm;
import ee.hitsa.ois.web.dto.PersonWithUsersDto;
import ee.hitsa.ois.web.dto.UserDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private ClassifierRepository classifierRepository;

    @PostMapping("")
    public PersonWithUsersDto create(@Valid @RequestBody PersonForm request) {
        return PersonWithUsersDto.of(personService.create(request), null);
    }

    @PutMapping("/{id:\\d+}")
    public PersonWithUsersDto update(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) Person person, @Valid @RequestBody PersonForm personForm) {
        return get(user, personService.save(personForm, person));
    }

    @GetMapping("/{id:\\d+}")
    public PersonWithUsersDto get(HoisUserDetails user, @WithEntity("id") Person person) {
        Set<User> users = person.getUsers();
        if (user.isSchoolAdmin()) {
            users = users.stream().filter(s -> s.getSchool() != null).filter(s -> EntityUtil.getId(s.getSchool()).equals(user.getSchoolId())).collect(Collectors.toSet());
        }
        return PersonWithUsersDto.of(person, users);
    }

    @GetMapping("/{person:\\d+}/users/{id:\\d+}")
    public UserDto getUser(HoisUserDetails userDetails, @WithEntity("person") Person person, @WithEntity("id") User user) {
        if (!EntityUtil.getId(person).equals(EntityUtil.getId(user.getPerson()))) {
            throw new AssertionFailedException("Person and user don't match");
        }
        return UserDto.of(user, classifierRepository.findAllByMainClassCode(MainClassCode.TEEMAOIGUS.name()));
    }

    @GetMapping("/{person:\\d+}/users")
    public UserDto getPersonAsUser(@WithEntity("person") Person person) {
        User user = new User();
        user.setPerson(person);
        return UserDto.of(user, null);
    }

    @PostMapping("/{person:\\d+}/users")
    public UserDto createUser(HoisUserDetails userDetails, @WithEntity("person") Person person, @Valid @RequestBody UserForm userForm) {
        return getUser(userDetails, person, personService.createUser(userForm, person));
    }

    @PutMapping("/{person:\\d+}/users/{id:\\d+}")
    public UserDto updateUser(HoisUserDetails userDetails, @WithEntity("person") Person person, @WithEntity("id") User user, @Valid @RequestBody UserForm userForm) {
        if (!EntityUtil.getId(person).equals(EntityUtil.getId(user.getPerson()))) {
            throw new AssertionFailedException("Person and user don't match");
        }
        return getUser(userDetails, person, personService.saveUser(userForm, user));
    }
}

package ee.hitsa.ois.web;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.PersonService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.PersonForm;
import ee.hitsa.ois.web.commandobject.UserForm;
import ee.hitsa.ois.web.dto.PersonWithUsersDto;
import ee.hitsa.ois.web.dto.UserDto;

/*
 * TODO: extra checks for Hois Automaatteade person with id = -1
 */
@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    //TODO: permission checks
    @PostMapping("")
    public PersonWithUsersDto create(@Valid @RequestBody PersonForm request) {
        return PersonWithUsersDto.of(personService.create(request), null);
    }

    //TODO: permission checks
    @PutMapping("/{id:\\d+}")
    public PersonWithUsersDto update(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) Person person, @Valid @RequestBody PersonForm personForm) {
        return get(user, personService.save(personForm, person));
    }

    //TODO: permission checks
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
        return personService.getUser(user);
    }

    //TODO: permission checks
    @GetMapping("/{person:\\d+}/users")
    public UserDto getPersonAsUser(@WithEntity("person") Person person) {
        User user = new User();
        user.setPerson(person);
        return UserDto.of(user, null);
    }

    @PostMapping("/{person:\\d+}/users")
    public UserDto createUser(HoisUserDetails userDetails, @WithEntity("person") Person person, @Valid @RequestBody UserForm userForm) {
        if (!userDetails.isMainAdmin()) {
            userForm.setSchool(new EntityConnectionCommand(userDetails.getSchoolId()));
        }
        UserUtil.assertCanUpdateUser(userForm.getRole());
        return getUser(userDetails, person, personService.createUser(userForm, person));
    }

    @PutMapping("/{person:\\d+}/users/{id:\\d+}")
    public UserDto updateUser(HoisUserDetails userDetails, @WithEntity("person") Person person, @WithEntity("id") User user, @Valid @RequestBody UserForm userForm) {
        UserUtil.assertUserBelongsToPerson(user, person);
        UserUtil.assertCanUpdateUser(userForm.getRole());
        if (!userDetails.isMainAdmin()) {
            UserUtil.assertSameSchool(userDetails, user.getSchool());
            userForm.setSchool(new EntityConnectionCommand(EntityUtil.getId(user.getSchool())));
        }
        return getUser(userDetails, person, personService.saveUser(userForm, user));
    }

    //TODO: permission checks
    @DeleteMapping("/{id:\\d+}")
    public void deletePerson(@WithEntity("id") Person person) {
        personService.delete(person);
    }

    //TODO: more permission checks
    @DeleteMapping("/{person:\\d+}/users/{id:\\d+}")
    public void deleteUser(@WithEntity("person") Person person, @WithEntity("id") User user) {
        UserUtil.assertUserBelongsToPerson(user, person);
        UserUtil.assertCanUpdateUser(EntityUtil.getCode(user.getRole()));
        personService.deleteUser(user);
    }
}

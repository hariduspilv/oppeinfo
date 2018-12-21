package ee.hitsa.ois.web;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.PersonService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;
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

    @PostMapping
    public PersonWithUsersDto create(HoisUserDetails user, @Valid @RequestBody PersonForm request) {
        UserUtil.assertIsMainAdminOrSchoolAdmin(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KASUTAJA);
        return PersonWithUsersDto.of(personService.create(request), null);
    }

    @PutMapping("/{id:\\d+}")
    public PersonWithUsersDto save(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) Person person, @Valid @RequestBody PersonForm personForm) {
        UserUtil.assertIsMainAdminOrSchoolAdmin(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KASUTAJA);
        return get(user, personService.save(personForm, person));
    }

    @GetMapping("/{id:\\d+}")
    public PersonWithUsersDto get(HoisUserDetails user, @WithEntity Person person) {
        UserUtil.assertIsMainAdminOrSchoolAdmin(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KASUTAJA);
        Set<User> users = person.getUsers();
        if (user.isSchoolAdmin()) {
            users = users.stream().filter(s -> s.getSchool() != null).filter(s -> EntityUtil.getId(s.getSchool()).equals(user.getSchoolId())).collect(Collectors.toSet());
        }
        return PersonWithUsersDto.of(person, users);
    }

    @GetMapping("/{person:\\d+}/users/{id:\\d+}")
    public UserDto getUser(HoisUserDetails userDetails, @WithEntity("person") Person person, @WithEntity User user) {
        UserUtil.assertIsMainAdminOrSchoolAdmin(userDetails);
        UserUtil.assertHasPermission(userDetails, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KASUTAJA);
        if (!userDetails.isMainAdmin()) {
            UserUtil.assertSameSchool(userDetails, user.getSchool());
        }
        if (!EntityUtil.getId(person).equals(EntityUtil.getId(user.getPerson()))) {
            throw new AssertionFailedException("Person and user don't match");
        }
        return personService.getUser(user);
    }

    @GetMapping("/{person:\\d+}/users")
    public UserDto initialValueForUser(HoisUserDetails user, @WithEntity("person") Person person) {
        UserUtil.assertIsMainAdminOrSchoolAdmin(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KASUTAJA);
        return personService.initialValueForUser(user, person);
    }

    @PostMapping("/{person:\\d+}/users")
    public UserDto createUser(HoisUserDetails user, @WithEntity("person") Person person, @Valid @RequestBody UserForm userForm) {
        UserUtil.assertIsMainAdminOrSchoolAdmin(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KASUTAJA);
        if (!user.isMainAdmin()) {
            userForm.setSchool(new EntityConnectionCommand(user.getSchoolId()));
        }
        UserUtil.assertCanUpdateUser(userForm.getRole());
        return getUser(user, person, personService.createUser(user, userForm, person));
    }

    @PutMapping("/{person:\\d+}/users/{id:\\d+}")
    public UserDto saveUser(HoisUserDetails userDetails, @WithEntity("person") Person person, @WithEntity User user, @Valid @RequestBody UserForm userForm) {
        UserUtil.assertIsMainAdminOrSchoolAdmin(userDetails);
        UserUtil.assertHasPermission(userDetails, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KASUTAJA);
        UserUtil.assertUserBelongsToPerson(user, person);
        UserUtil.assertCanUpdateUser(userForm.getRole());
        if (!userDetails.isMainAdmin()) {
            UserUtil.assertSameSchool(userDetails, user.getSchool());
            userForm.setSchool(new EntityConnectionCommand(EntityUtil.getId(user.getSchool())));
        }
        return getUser(userDetails, person, personService.saveUser(userDetails, userForm, user));
    }

    @DeleteMapping("/{id:\\d+}")
    public void deletePerson(HoisUserDetails user, @WithEntity Person person) {
        UserUtil.assertIsMainAdminOrSchoolAdmin(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KASUTAJA);
        personService.delete(user, person);
    }

    @DeleteMapping("/{person:\\d+}/users/{id:\\d+}")
    public void deleteUser(HoisUserDetails userDetails, @WithEntity("person") Person person, @WithEntity User user) {
        UserUtil.assertIsMainAdminOrSchoolAdmin(userDetails);
        UserUtil.assertHasPermission(userDetails, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KASUTAJA);
        UserUtil.assertUserBelongsToPerson(user, person);
        UserUtil.assertCanUpdateUser(EntityUtil.getCode(user.getRole()));
        if (!userDetails.isMainAdmin()) {
            UserUtil.assertSameSchool(userDetails, user.getSchool());
        }
        personService.deleteUser(userDetails, user);
    }
}

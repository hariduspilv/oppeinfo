package ee.hitsa.ois.web;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.PersonService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.UsersSeachCommand;
import ee.hitsa.ois.web.dto.PersonWithUsersDto;
import ee.hitsa.ois.web.dto.UsersSearchDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class PersonController {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private PersonService personService;

    @GetMapping
    public Page<UsersSearchDto> search(HoisUserDetails user, UsersSeachCommand command, Pageable pageable) {
        if (user.isSchoolAdmin()) {
            command.setSchool(EntityUtil.getCode(schoolRepository.getOne(user.getSchoolId()).getEhisSchool()));
        }
        return personService.search(command, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public PersonWithUsersDto get(HoisUserDetails user, @WithEntity("id") Person person) {
        Set<User> users = person.getUsers();
        if (user.isSchoolAdmin()) {
            users = users.stream().filter(s -> EntityUtil.getId(s.getSchool()).equals(user.getSchoolId())).collect(Collectors.toSet());
        }
        return PersonWithUsersDto.of(person, users);
    }

    @GetMapping("/list")
    public List<UsersSearchDto> searchList(HoisUserDetails user, UsersSeachCommand command, Pageable pageable) {
        List<UsersSearchDto> list = search(user, command, pageable).getContent();
        return list;
    }
}

package ee.hitsa.ois.web;

import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.repository.UserRolesDefaultRepository;
import ee.hitsa.ois.service.PersonService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.UsersSeachCommand;
import ee.hitsa.ois.web.dto.UserRolesDto;
import ee.hitsa.ois.web.dto.UsersSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private UserRolesDefaultRepository userRolesDefaultRepository;

    @GetMapping
    public Page<UsersSearchDto> search(HoisUserDetails user, UsersSeachCommand command, Pageable pageable) {
        if (user.isSchoolAdmin()) {
            command.setSchool(EntityUtil.getCode(schoolRepository.getOne(user.getSchoolId()).getEhisSchool()));
        }
        return personService.search(command, pageable);
    }

    @GetMapping("/list")
    public List<UsersSearchDto> searchList(HoisUserDetails user, UsersSeachCommand command, Pageable pageable) {
        List<UsersSearchDto> list = search(user, command, pageable).getContent();
        return list;
    }

    @GetMapping("/rolesDefaults")
    public List<UserRolesDto> allUserRoles() {
        return StreamSupport.stream(userRolesDefaultRepository.findAll().spliterator(), false).map(UserRolesDto::of).collect(Collectors.toList());
    }
}

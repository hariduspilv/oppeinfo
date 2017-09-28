package ee.hitsa.ois.web;

import ee.hitsa.ois.service.PersonService;
import ee.hitsa.ois.service.UserService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.UsersSearchCommand;
import ee.hitsa.ois.web.dto.UserRolesDto;
import ee.hitsa.ois.web.dto.UsersSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private PersonService personService;
    @Autowired
    private UserService userService;

    @GetMapping
    public Page<UsersSearchDto> search(HoisUserDetails user, UsersSearchCommand command, Pageable pageable) {
        if (user.isSchoolAdmin()) {
            command.setSchool(user.getSchoolId());
        } else {
            UserUtil.assertIsMainAdmin(user);
        }
        return personService.search(command, pageable);
    }

    @GetMapping("/rolesDefaults")
    public UserRolesDto rolesDefaults() {
        return userService.rolesDefaults();
    }
}

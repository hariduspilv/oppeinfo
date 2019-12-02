package ee.hitsa.ois.web;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.UserSchoolRole;
import ee.hitsa.ois.domain.teacher.TeacherOccupation;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.PersonService;
import ee.hitsa.ois.service.UserAdminRolesService;
import ee.hitsa.ois.service.UserRolesService;
import ee.hitsa.ois.service.UserService;
import ee.hitsa.ois.service.UserTeacherRolesService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.SearchCommand;
import ee.hitsa.ois.web.commandobject.UserSchoolRoleCheckCommand;
import ee.hitsa.ois.web.commandobject.UserSchoolRoleForm;
import ee.hitsa.ois.web.commandobject.UsersSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.UserRolesDto;
import ee.hitsa.ois.web.dto.UserSchoolRoleDto;
import ee.hitsa.ois.web.dto.UsersSearchDto;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private PersonService personService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRolesService userRoleService;
    @Autowired
    private UserAdminRolesService userAdminRolesService;
    @Autowired
    private UserTeacherRolesService userTeacherRolesService;

    @GetMapping
    public Page<UsersSearchDto> search(HoisUserDetails user, UsersSearchCommand command, Pageable pageable) {
        if (user.isSchoolAdmin()) {
            command.setSchool(user.getSchoolId());
        } else {
            UserUtil.assertIsMainAdmin(user);
        }
        return personService.search(command, pageable);
    }
    
    @GetMapping("/usersByRole/{id:\\d+}")
    public Page<AutocompleteResult> usersByRole(HoisUserDetails user, @WithEntity UserSchoolRole role, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user, role.getSchool());
        return userRoleService.usersByRole(role, pageable);
    }

    @GetMapping("/usersByOccupation/{id:\\d+}")
    public Page<AutocompleteResult> usersByOccupation(HoisUserDetails user, @WithEntity TeacherOccupation occupation, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user, occupation.getSchool());
        return userTeacherRolesService.usersByOccupation(occupation, pageable);
    }
    
    @GetMapping("/admin-roles")
    public Page<AutocompleteResult> searchAdminRoles(HoisUserDetails user, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KASUTAJA);
        return userAdminRolesService.searchRoles(user, pageable);
    }
    
    @GetMapping("/admin-roles/{id:\\d+}")
    public UserSchoolRoleDto getAdminRole(HoisUserDetails user, @WithEntity UserSchoolRole role) {
        UserUtil.assertIsSchoolAdmin(user, role.getSchool());
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KASUTAJA);
        return userAdminRolesService.getRole(role);
    }
    
    @PostMapping("/admin-roles")
    public UserSchoolRoleDto createAdminRole(HoisUserDetails user, @RequestBody @Valid UserSchoolRoleForm cmd) {
        UserUtil.assertIsSchoolAdmin(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KASUTAJA);
        return getAdminRole(user, userAdminRolesService.createRole(user, cmd));
    }
    
    @PutMapping("/admin-roles/{id:\\d+}")
    public UserSchoolRoleDto updateAdminRole(HoisUserDetails user, @WithEntity UserSchoolRole role, @RequestBody @Valid UserSchoolRoleForm cmd) {
        UserUtil.assertIsSchoolAdmin(user, role.getSchool());
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KASUTAJA);
        return getAdminRole(user, userAdminRolesService.updateRole(user, role, cmd));
    }
    
    @GetMapping("/admin-roles/check")
    public Map<String, Boolean> checkAdminRole(HoisUserDetails user, @Valid UserSchoolRoleCheckCommand cmd) {
        UserUtil.assertIsSchoolAdmin(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KASUTAJA);
        cmd.setSchoolId(user.getSchoolId());
        return userAdminRolesService.checkRole(cmd, true);
    }
    
    @DeleteMapping("/admin-roles/{id:\\d+}")
    public void deleteAdminRole(HoisUserDetails user, @WithEntity UserSchoolRole role, @RequestParam(defaultValue="false") boolean deleteRights) {
        UserUtil.assertIsSchoolAdmin(user, role.getSchool());
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KASUTAJA);
        userAdminRolesService.deleteRole(user, role, deleteRights);
    }
    
    @GetMapping("/teacher-roles")
    public Page<AutocompleteResult> searchTeacherRoles(HoisUserDetails user, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KASUTAJA);
        return userTeacherRolesService.searchRoles(user, pageable);
    }
    
    @GetMapping("/teacher-roles/{id:\\d+}")
    public UserSchoolRoleDto getTeacherRole(HoisUserDetails user, @WithEntity UserSchoolRole role) {
        UserUtil.assertIsSchoolAdmin(user, role.getSchool());
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KASUTAJA);
        return userTeacherRolesService.getRole(role);
    }
    
    @PostMapping("/teacher-roles")
    public UserSchoolRoleDto createTeacherRole(HoisUserDetails user, @RequestBody UserSchoolRoleForm cmd) {
        UserUtil.assertIsSchoolAdmin(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KASUTAJA);
        return getTeacherRole(user, userTeacherRolesService.createRole(user, cmd));
    }
    
    @PutMapping("/teacher-roles/{id:\\d+}")
    public UserSchoolRoleDto updateTeacherRole(HoisUserDetails user, @WithEntity UserSchoolRole role, @RequestBody UserSchoolRoleForm cmd) {
        UserUtil.assertIsSchoolAdmin(user, role.getSchool());
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KASUTAJA);
        return getTeacherRole(user, userTeacherRolesService.updateRole(user, role, cmd));
    }
    
    @GetMapping("/teacher-roles/check")
    public Map<String, Boolean> checkTeacherRole(HoisUserDetails user, @Valid UserSchoolRoleCheckCommand cmd) {
        UserUtil.assertIsSchoolAdmin(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KASUTAJA);
        cmd.setSchoolId(user.getSchoolId());
        return userTeacherRolesService.checkRole(cmd, true);
    }
    
    @DeleteMapping("/teacher-roles/{id:\\d+}")
    public void deleteTeacherRole(HoisUserDetails user, @WithEntity UserSchoolRole role, @RequestParam(defaultValue="false") boolean deleteRights) {
        UserUtil.assertIsSchoolAdmin(user, role.getSchool());
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KASUTAJA);
        userTeacherRolesService.deleteRole(user, role, deleteRights);
    }

    @GetMapping("/rolesDefaults")
    public UserRolesDto rolesDefaults(HoisUserDetails user) {
        UserUtil.assertIsMainAdminOrSchoolAdmin(user);
        return userService.rolesDefaults();
    }
    
    @GetMapping("/userSchoolRoles")
    public List<AutocompleteResult> userSchoolRoles(HoisUserDetails user, @RequestParam(required = false, defaultValue= "false") boolean onlyAdmin) {
        UserUtil.assertIsSchoolAdmin(user);
        return userRoleService.userSchoolRoles(user, onlyAdmin);
    }
    
    @GetMapping("/userSchoolRoleRights")
    public Map<Long, UserSchoolRoleDto> userSchoolRoleRights(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return userRoleService.userSchoolRoleRights(user);
    }
    
    @GetMapping("/curriculums")
    public List<AutocompleteResult> userCurriculums(HoisUserDetails user, SearchCommand cmd) {
        UserUtil.assertIsMainAdminOrSchoolAdmin(user);
        return userService.getCurriculums(user.getSchoolId(), cmd);
    }
}

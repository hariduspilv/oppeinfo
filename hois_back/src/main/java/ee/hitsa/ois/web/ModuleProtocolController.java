package ee.hitsa.ois.web;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.service.AutocompleteService;
import ee.hitsa.ois.service.ModuleProtocolService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.ModuleProtocolCommand;
import ee.hitsa.ois.web.commandobject.ModuleProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.ModuleProtocolSaveForm;
import ee.hitsa.ois.web.commandobject.ModuleProtocolSearchCommand;
import ee.hitsa.ois.web.commandobject.TeacherAutocompleteCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ModuleProtocolSearchDto;
import ee.hitsa.ois.web.dto.ModuleProtocolStudentSelectDto;
import ee.hitsa.ois.web.dto.ProtocolDto;

@RestController
@RequestMapping("/moduleProtocols")
public class ModuleProtocolController {

    @Autowired
    private ModuleProtocolService moduleProtocolService;

    @Autowired
    private AutocompleteService autocompleteService;

    @GetMapping
    public Page<ModuleProtocolSearchDto> search(HoisUserDetails user, ModuleProtocolSearchCommand command,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return moduleProtocolService.search(user, command, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public ProtocolDto get(@WithEntity("id") Protocol protocol) {
        return ProtocolDto.of(protocol);
    }

    @PostMapping
    public ProtocolDto create(HoisUserDetails user, @Valid @RequestBody ModuleProtocolCreateForm moduleProtocolCreateForm) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return get(moduleProtocolService.create(user, moduleProtocolCreateForm));
    }

    @PutMapping("/{id:\\d+}")
    public ProtocolDto save(HoisUserDetails user,
            @WithVersionedEntity(value = "id", versionRequestBody = true) Protocol protocol, @Valid @RequestBody ModuleProtocolSaveForm moduleProtocolSaveForm) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return get(moduleProtocolService.save(protocol, moduleProtocolSaveForm));
    }

    @PostMapping("create")
    public ProtocolDto createInitial(HoisUserDetails user,
            @Valid @RequestBody ModuleProtocolCommand moduleProtocolCommand) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return get(moduleProtocolService.createInitial(user, moduleProtocolCommand));
    }

    @GetMapping("occupationModules/{curriculumVersionId:\\d+}")
    public List<AutocompleteResult> occupationModules(HoisUserDetails user, @PathVariable Long curriculumVersionId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return moduleProtocolService.occupationModules(user, curriculumVersionId);
    }

    @GetMapping("teachers")
    public List<AutocompleteResult> teachers(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return autocompleteService.teachers(user.getSchoolId(), new TeacherAutocompleteCommand());
    }

    @GetMapping("occupationModuleStudents/{occupationalModuleId:\\d+}")
    public Collection<ModuleProtocolStudentSelectDto> occupationModuleStudents(HoisUserDetails user,
            @PathVariable Long occupationalModuleId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return moduleProtocolService.occupationModuleStudents(user, occupationalModuleId);
    }

}

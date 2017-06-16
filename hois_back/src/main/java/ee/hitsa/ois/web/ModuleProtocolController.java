package ee.hitsa.ois.web;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.repository.TeacherRepository;
import ee.hitsa.ois.service.AutocompleteService;
import ee.hitsa.ois.service.ModuleProtocolService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.ModuleProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.ModuleProtocolSaveForm;
import ee.hitsa.ois.web.commandobject.ModuleProtocolSearchCommand;
import ee.hitsa.ois.web.commandobject.TeacherAutocompleteCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ModuleProtocolDto;
import ee.hitsa.ois.web.dto.ModuleProtocolOccupationalModuleDto;
import ee.hitsa.ois.web.dto.ModuleProtocolSearchDto;
import ee.hitsa.ois.web.dto.ModuleProtocolStudentSelectDto;

@RestController
@RequestMapping("/moduleProtocols")
public class ModuleProtocolController {

    @Autowired
    private ModuleProtocolService moduleProtocolService;

    @Autowired
    private AutocompleteService autocompleteService;

    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping
    public Page<ModuleProtocolSearchDto> search(HoisUserDetails user, ModuleProtocolSearchCommand command,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return moduleProtocolService.search(user, command, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public ModuleProtocolDto get(@WithEntity("id") Protocol protocol) {
        return ModuleProtocolDto.of(protocol);
    }

    @PostMapping
    public ModuleProtocolDto create(HoisUserDetails user,
            @Valid @RequestBody ModuleProtocolCreateForm moduleProtocolCreateForm) {
        assertIsSchoolAdminOrTeacherResponsible(user, moduleProtocolCreateForm.getProtocolVdata().getTeacher());
        return get(moduleProtocolService.create(user, moduleProtocolCreateForm));
    }

    @PutMapping("/{id:\\d+}")
    public ModuleProtocolDto save(HoisUserDetails user,
            @WithVersionedEntity(value = "id", versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody ModuleProtocolSaveForm moduleProtocolSaveForm) {
        assertIsTeacherResponsible(user, protocol);
        return get(moduleProtocolService.save(protocol, moduleProtocolSaveForm));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user,
            @WithVersionedEntity(value = "id", versionRequestParam = "version") Protocol protocol,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertIsSchoolAdmin(user);
        moduleProtocolService.delete(protocol);
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

    @GetMapping("occupationModule/{curriculumVersionOccupationModuleId:\\d+}")
    public ModuleProtocolOccupationalModuleDto occupationModule(HoisUserDetails user,
            @PathVariable Long curriculumVersionOccupationModuleId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return moduleProtocolService.occupationModule(user, curriculumVersionOccupationModuleId);
    }

    @GetMapping("/{id:\\d+}/otherStudents")
    public Collection<ModuleProtocolStudentSelectDto> otherStudents(HoisUserDetails user,
            @WithEntity("id") Protocol protocol) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return moduleProtocolService.otherStudents(user, protocol);
    }

    @PostMapping("/{id:\\d+}/addStudents")
    public ModuleProtocolDto addStudents(HoisUserDetails user,
            @WithVersionedEntity(value = "id", versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody ModuleProtocolSaveForm moduleProtocolSaveForm) {
        assertIsTeacherResponsible(user, protocol);
        return get(moduleProtocolService.addStudents(protocol, moduleProtocolSaveForm));
    }

    @PostMapping("/{id:\\d+}/confirm")
    public ModuleProtocolDto confirm(HoisUserDetails user,
            @WithVersionedEntity(value = "id", versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody ModuleProtocolSaveForm moduleProtocolSaveForm) {
        assertIsTeacherResponsible(user, protocol);
        return get(moduleProtocolService.confirm(protocol, moduleProtocolSaveForm));
    }

    private void assertIsTeacherResponsible(HoisUserDetails user, Protocol protocol) {
        UserUtil.assertIsPerson(user, protocol.getProtocolVdata().getTeacher().getPerson());
    }

    private void assertIsSchoolAdminOrTeacherResponsible(HoisUserDetails user, Long teacherId) {
        if (!user.isSchoolAdmin()) {
            UserUtil.assertIsPerson(user, teacherRepository.getOne(teacherId).getPerson());
        }
    }

}

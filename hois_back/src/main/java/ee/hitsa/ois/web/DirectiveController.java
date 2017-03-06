package ee.hitsa.ois.web;

import java.util.List;

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

import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveCoordinator;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.DirectiveService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.directive.DirectiveCoordinatorForm;
import ee.hitsa.ois.web.commandobject.directive.DirectiveForm;
import ee.hitsa.ois.web.commandobject.directive.DirectiveSearchCommand;
import ee.hitsa.ois.web.dto.directive.DirectiveCoordinatorDto;
import ee.hitsa.ois.web.dto.directive.DirectiveSearchDto;
import ee.hitsa.ois.web.dto.directive.DirectiveStudentDto;

@RestController
@RequestMapping("/directives")
public class DirectiveController {

    @Autowired
    private DirectiveService directiveService;
    @Autowired
    private SchoolRepository schoolRepository;

    @GetMapping
    public Page<DirectiveSearchDto> search(HoisUserDetails user, @Valid DirectiveSearchCommand criteria, Pageable pageable) {
        return directiveService.search(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public DirectiveForm get(HoisUserDetails user, @WithEntity("id") Directive directive) {
        UserUtil.assertSameSchool(user, directive.getSchool());
        return DirectiveForm.of(directive);
    }

    @PostMapping
    public DirectiveForm create(HoisUserDetails user, @Valid @RequestBody DirectiveForm form) {
        Directive directive = new Directive();
        directive.setSchool(schoolRepository.getOne(user.getSchoolId()));
        return get(user, directiveService.save(directive, form));
    }

    @PutMapping("/{id:\\d+}")
    public DirectiveForm update(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) Directive directive, @Valid @RequestBody DirectiveForm form) {
        UserUtil.assertSameSchool(user, directive.getSchool());
        return get(user, directiveService.save(directive, form));
    }

    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") Directive directive, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertSameSchool(user, directive.getSchool());
        directiveService.delete(directive);
    }
    
    @GetMapping("/findstudents")
    public List<DirectiveStudentDto> searchStudents(HoisUserDetails user) {
        return directiveService.searchStudents(user.getSchoolId());
    }

    @GetMapping("/coordinators")
    public Page<DirectiveCoordinatorDto> searchCoordinators(HoisUserDetails user, Pageable pageable) {
        return directiveService.search(user.getSchoolId(), pageable);
    }

    @GetMapping("/coordinators/{id:\\d+}")
    public DirectiveCoordinatorDto getCoordinator(HoisUserDetails user, @WithEntity("id") DirectiveCoordinator coordinator) {
        UserUtil.assertSameSchool(user, coordinator.getSchool());
        return DirectiveCoordinatorDto.of(coordinator);
    }

    @PostMapping("/coordinators")
    public DirectiveCoordinatorDto createCoordinator(HoisUserDetails user, @Valid @RequestBody DirectiveCoordinatorForm form) {
        DirectiveCoordinator coordinator = EntityUtil.bindToEntity(form, new DirectiveCoordinator());
        coordinator.setSchool(schoolRepository.getOne(user.getSchoolId()));
        return getCoordinator(user, directiveService.save(coordinator));
    }

    @PutMapping("/coordinators/{id:\\d+}")
    public DirectiveCoordinatorDto updateCoordinator(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) DirectiveCoordinator coordinator, @Valid @RequestBody DirectiveCoordinatorForm form) {
        UserUtil.assertSameSchool(user, coordinator.getSchool());
        EntityUtil.bindToEntity(form, coordinator);
        return getCoordinator(user, directiveService.save(coordinator));
    }

    @DeleteMapping("/coordinators/{id:\\d+}")
    public void deleteCoordinator(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") DirectiveCoordinator coordinator, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertSameSchool(user, coordinator.getSchool());
        directiveService.delete(coordinator);
    }
}

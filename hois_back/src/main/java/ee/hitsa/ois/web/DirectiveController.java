package ee.hitsa.ois.web;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.DirectiveConfirmService;
import ee.hitsa.ois.service.DirectiveService;
import ee.hitsa.ois.service.JobService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.directive.DirectiveCoordinatorForm;
import ee.hitsa.ois.web.commandobject.directive.DirectiveDataCommand;
import ee.hitsa.ois.web.commandobject.directive.DirectiveForm;
import ee.hitsa.ois.web.commandobject.directive.DirectiveSearchCommand;
import ee.hitsa.ois.web.commandobject.directive.DirectiveStudentSearchCommand;
import ee.hitsa.ois.web.dto.directive.DirectiveCoordinatorDto;
import ee.hitsa.ois.web.dto.directive.DirectiveDto;
import ee.hitsa.ois.web.dto.directive.DirectiveSearchDto;
import ee.hitsa.ois.web.dto.directive.DirectiveStudentSearchDto;
import ee.hitsa.ois.web.dto.directive.DirectiveViewDto;

@RestController
@RequestMapping("/directives")
public class DirectiveController {

    @Autowired
    private DirectiveConfirmService directiveConfirmService;
    @Autowired
    private DirectiveService directiveService;
    @Autowired
    private JobService jobService;

    @GetMapping
    public Page<DirectiveSearchDto> search(HoisUserDetails user, @Valid DirectiveSearchCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return directiveService.search(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/{id:\\d+}/view")
    public DirectiveViewDto getForView(HoisUserDetails user, @WithEntity Directive directive) {
        UserUtil.assertSameSchool(user, directive.getSchool());
        return directiveService.getForView(user, directive);
    }

    @GetMapping("/{id:\\d+}")
    public DirectiveDto get(HoisUserDetails user, @WithEntity Directive directive) {
        UserUtil.assertIsSchoolAdmin(user, directive.getSchool());
        return directiveService.get(directive);
    }

    @PostMapping
    public HttpUtil.CreatedResponse create(HoisUserDetails user, @Valid @RequestBody DirectiveForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        return HttpUtil.created(directiveService.create(user, form));
    }

    @PutMapping("/{id:\\d+}")
    public DirectiveDto save(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) Directive directive, @Valid @RequestBody DirectiveForm form) {
        assertCanEditDirective(user, directive);
        return get(user, directiveService.save(directive, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(versionRequestParam = "version") Directive directive, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        assertCanEditDirective(user, directive);
        directiveService.delete(user, directive);
    }

    @PutMapping("/sendtoconfirm/{id:\\d+}")
    public void sendToConfirm(HoisUserDetails user, @WithEntity Directive directive) {
        UserUtil.assertIsSchoolAdmin(user, directive.getSchool());
        directiveConfirmService.sendToConfirm(directive);
    }

    // TODO for testing only, remove later
    @PutMapping("/confirm/{id:\\d+}")
    public DirectiveDto confirm(HoisUserDetails user, @WithEntity Directive directive) {
        UserUtil.assertIsSchoolAdmin(user, directive.getSchool());
        // start requests after save has been successful
        jobService.directiveConfirmed(EntityUtil.getId(directiveConfirmService.confirm(user.getUsername(), directive, LocalDate.now())));
        return get(user, directive);
    }

    @PostMapping("/directivedata")
    public DirectiveDto directivedata(HoisUserDetails user, @Valid @RequestBody DirectiveDataCommand cmd) {
        UserUtil.assertIsSchoolAdmin(user);
        return directiveService.directivedata(user, cmd);
    }

    @GetMapping("/findstudents")
    public Page<DirectiveStudentSearchDto> searchStudents(HoisUserDetails user, @Valid DirectiveStudentSearchCommand criteria) {
        UserUtil.assertIsSchoolAdmin(user);
        return new PageImpl<>(directiveService.searchStudents(user.getSchoolId(), criteria));
    }

    @GetMapping("/coordinators")
    public Page<DirectiveCoordinatorDto> searchCoordinators(HoisUserDetails user, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return directiveService.searchCoordinators(user.getSchoolId(), pageable);
    }

    @GetMapping("/coordinators/{id:\\d+}")
    public DirectiveCoordinatorDto getCoordinator(HoisUserDetails user, @WithEntity DirectiveCoordinator coordinator) {
        UserUtil.assertIsSchoolAdmin(user, coordinator.getSchool());
        return DirectiveCoordinatorDto.of(coordinator);
    }

    @PostMapping("/coordinators")
    public HttpUtil.CreatedResponse createCoordinator(HoisUserDetails user, @Valid @RequestBody DirectiveCoordinatorForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        return HttpUtil.created(directiveService.create(user, form));
    }

    @PutMapping("/coordinators/{id:\\d+}")
    public DirectiveCoordinatorDto saveCoordinator(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) DirectiveCoordinator coordinator, @Valid @RequestBody DirectiveCoordinatorForm form) {
        UserUtil.assertIsSchoolAdmin(user, coordinator.getSchool());
        return getCoordinator(user, directiveService.save(coordinator, form));
    }

    @DeleteMapping("/coordinators/{id:\\d+}")
    public void deleteCoordinator(HoisUserDetails user, @WithVersionedEntity(versionRequestParam = "version") DirectiveCoordinator coordinator, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertIsSchoolAdmin(user, coordinator.getSchool());
        directiveService.delete(user, coordinator);
    }

    private static void assertCanEditDirective(HoisUserDetails user, Directive directive) {
        AssertionFailedException.throwIf(!UserUtil.canEditDirective(user, directive), "User cannot edit directive");
    }
}

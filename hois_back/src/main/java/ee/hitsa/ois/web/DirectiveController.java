package ee.hitsa.ois.web;

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

import ee.hitsa.ois.domain.DirectiveCoordinator;
import ee.hitsa.ois.domain.School;
import ee.hitsa.ois.service.DirectiveService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.DirectiveCoordinatorForm;
import ee.hitsa.ois.web.dto.DirectiveCoordinatorDto;

@RestController
@RequestMapping("/directives")
public class DirectiveController {

    @Autowired
    private DirectiveService directiveService;

    @GetMapping("/coordinators")
    Page<DirectiveCoordinatorDto> searchCoordinators(HoisUserDetails user, Pageable pageable) {
        return directiveService.search(user.getSchool().getId(), pageable);
    }

    @GetMapping("/coordinators/{id}")
    DirectiveCoordinatorDto getCoordinator(HoisUserDetails user, @WithEntity("id") DirectiveCoordinator coordinator) {
        return DirectiveCoordinatorDto.of(coordinator);
    }

    @PostMapping("/coordinators")
    DirectiveCoordinatorDto createCoordinator(HoisUserDetails user, @Valid @RequestBody DirectiveCoordinatorForm form) {
        DirectiveCoordinator coordinator = EntityUtil.bindToEntity(form, new DirectiveCoordinator());
        coordinator.setSchool(user.getSchool());
        return getCoordinator(user, directiveService.save(coordinator));
    }

    @PutMapping("/coordinators/{id}")
    DirectiveCoordinatorDto updateCoordinator(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) DirectiveCoordinator coordinator, @Valid @RequestBody DirectiveCoordinatorForm form) {
        assertSameSchool(user, coordinator);
        EntityUtil.bindToEntity(form, coordinator);
        return getCoordinator(user, directiveService.save(coordinator));
    }

    @DeleteMapping("/coordinators/{id}")
    void deleteCoordinator(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") DirectiveCoordinator coordinator, @RequestParam("version") Long version) {
        assertSameSchool(user, coordinator);
        directiveService.delete(coordinator);
    }

    private static void assertSameSchool(HoisUserDetails user, DirectiveCoordinator coordinator) {
        Long schoolId = user.getSchool() != null ? user.getSchool().getId() : null;
        School school = coordinator.getSchool();
        if(schoolId == null || school == null || !schoolId.equals(school.getId())) {
            throw new IllegalArgumentException();
        }
    }
}

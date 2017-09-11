package ee.hitsa.ois.web;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

import ee.hitsa.ois.domain.Committee;
import ee.hitsa.ois.service.CommitteeService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.CommitteeSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.CommitteeDto;
import ee.hitsa.ois.web.dto.CommitteeSearchDto;

@RestController
@RequestMapping("/committees")
public class CommitteeController {

    @Autowired
    private CommitteeService committeeService;

    @GetMapping
    public Page<CommitteeSearchDto> search(HoisUserDetails user,
            @NotNull CommitteeSearchCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return committeeService.search(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public CommitteeDto get(HoisUserDetails user, @WithEntity("id") Committee committee) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        UserUtil.assertSameSchool(user, committee.getSchool());
        return committeeService.get(committee);
    }

    @PostMapping
    public CommitteeDto create(HoisUserDetails user, @NotNull @Valid @RequestBody CommitteeDto dto) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return committeeService.create(user.getSchoolId(), dto);
    }

    @PutMapping("/{id:\\d+}")
    public CommitteeDto save(HoisUserDetails user,
            @WithVersionedEntity(value = "id", versionRequestBody = true) Committee committee,
            @NotNull @Valid @RequestBody CommitteeDto dto) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        UserUtil.assertSameSchool(user, committee.getSchool());
        return committeeService.save(committee, dto);
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, 
            @WithVersionedEntity(value = "id", versionRequestParam = "version") Committee committee, 
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        UserUtil.assertSameSchool(user, committee.getSchool());
        committeeService.delete(committee);
    }
    
    @GetMapping("/members")
    public Set<AutocompleteResult> getMembers(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return committeeService.getMembers(user.getSchoolId());
    }
}

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

import ee.hitsa.ois.domain.apelapplication.ApelSchool;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.ApelSchoolService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelSchoolForm;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelSchoolSearchCommand;
import ee.hitsa.ois.web.dto.apelapplication.ApelSchoolDto;
import ee.hitsa.ois.web.dto.apelapplication.ApelSchoolSearchDto;

@RestController
@RequestMapping("/apelSchool")
public class ApelSchoolController {

    @Autowired
    private ApelSchoolService apelSchoolService;

    @GetMapping
    public Page<ApelSchoolSearchDto> search(ApelSchoolSearchCommand command, Pageable pageable, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_VOTA);
        return apelSchoolService.search(user, command, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public ApelSchoolDto get(HoisUserDetails user, @WithEntity ApelSchool school) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, school.getSchool(), Permission.OIGUS_V,
                PermissionObject.TEEMAOIGUS_VOTA);
        return apelSchoolService.get(school);
    }

    @PostMapping
    public ApelSchoolDto create(HoisUserDetails user, @Valid @RequestBody ApelSchoolForm schoolForm) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA);
        ApelSchool school = apelSchoolService.create(user, schoolForm);
        return get(user, school);
    }

    @PutMapping("/{id:\\d+}")
    public ApelSchoolDto save(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) ApelSchool school,
            @Valid @RequestBody ApelSchoolForm form) {
        UserUtil.assertIsSchoolAdmin(user, school.getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA);
        return get(user, apelSchoolService.save(user, school, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(versionRequestParam = "version") ApelSchool school,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertIsSchoolAdmin(user, school.getSchool(), Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_VOTA);
        apelSchoolService.delete(user, school);
    }

    @GetMapping("/usedEhisSchoolCodes")
    public List<String> usedEhisSchoolCodes(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return apelSchoolService.usedEhisSchoolCodes(user.getSchoolId());
    }

}

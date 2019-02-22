package ee.hitsa.ois.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.enterprise.Enterprise;
import ee.hitsa.ois.service.EnterpriseService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.EnterpriseForm;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseDto;

@RestController
@RequestMapping("/enterprises")
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    @GetMapping("/{id:\\d+}")
    public EnterpriseDto get(HoisUserDetails user, @WithEntity Enterprise enterprise) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return enterpriseService.get(enterprise);
    }

    @PostMapping
    public EnterpriseDto create(@Valid @RequestBody EnterpriseForm enterpriseForm, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return get(user, enterpriseService.create(enterpriseForm));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(versionRequestParam = "version") Enterprise enterprise,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertIsSchoolAdmin(user);
        enterpriseService.delete(user, enterprise);
    }

}

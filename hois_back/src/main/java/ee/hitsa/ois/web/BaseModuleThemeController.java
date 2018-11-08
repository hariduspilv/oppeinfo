package ee.hitsa.ois.web;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.basemodule.BaseModule;
import ee.hitsa.ois.domain.basemodule.BaseModuleTheme;
import ee.hitsa.ois.service.basemodule.BaseModuleThemeService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.BaseModuleUserRights;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.BaseModuleThemeForm;
import ee.hitsa.ois.web.dto.basemodule.BaseModuleThemeDto;

@RestController
@RequestMapping("/basemodule/theme")
public class BaseModuleThemeController {
    
    @Autowired
    BaseModuleThemeService baseModuleThemeService;

    @GetMapping("/{id:\\d+}")
    public BaseModuleThemeDto get(HoisUserDetails user, @WithEntity BaseModuleTheme theme) {
        BaseModuleUserRights.assertCanView(user, theme.getBaseModule().getSchool());
        return baseModuleThemeService.get(theme);
    }
    
    @GetMapping("/basemodule/{id:\\d+}")
    public BaseModuleThemeDto get(HoisUserDetails user, @WithEntity BaseModule module) {
        BaseModuleUserRights.assertCanView(user, module.getSchool());
        return baseModuleThemeService.getEmptyTheme(module);
    }
    
    @PutMapping("/{id:\\d+}")
    public BaseModuleThemeDto save(HoisUserDetails user, @NotNull @Valid @RequestBody BaseModuleThemeForm form,
            @WithEntity BaseModuleTheme theme) {
        BaseModuleUserRights.assertCanEdit(user, theme.getBaseModule().getSchool());
        return get(user, baseModuleThemeService.save(user, form, theme));
    }
    
    @PostMapping
    public BaseModuleThemeDto create(HoisUserDetails user, @Valid @RequestBody BaseModuleThemeForm form) {
        BaseModuleUserRights.assertCanCreate(user);
        BaseModuleThemeDto dto = new BaseModuleThemeDto();
        dto.setId(baseModuleThemeService.create(user, form).getId());
        return dto;
    }
    
    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithEntity BaseModuleTheme theme) {
        BaseModuleUserRights.assertCanDeleteTheme(user, theme.getBaseModule().getSchool());
        baseModuleThemeService.delete(theme);
    }
}

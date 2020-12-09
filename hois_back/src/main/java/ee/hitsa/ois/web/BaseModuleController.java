package ee.hitsa.ois.web;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.web.dto.PersonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.basemodule.BaseModule;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.basemodule.BaseModuleService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.BaseModuleUserRights;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.TeacherAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.basemodule.BaseModuleForm;
import ee.hitsa.ois.web.commandobject.basemodule.BaseModuleReplaceCommand;
import ee.hitsa.ois.web.commandobject.basemodule.BaseModuleReplaceForm;
import ee.hitsa.ois.web.commandobject.basemodule.BaseModuleSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.OccupiedAutocompleteResult;
import ee.hitsa.ois.web.dto.basemodule.BaseModuleCapacityDto;
import ee.hitsa.ois.web.dto.basemodule.BaseModuleDto;
import ee.hitsa.ois.web.dto.basemodule.BaseModuleSearchDto;

@RestController
@RequestMapping("/basemodule")
public class BaseModuleController {

    @Autowired
    private BaseModuleService baseModuleService;

    @GetMapping
    public Page<BaseModuleSearchDto> search(HoisUserDetails user, BaseModuleSearchCommand cmd, Pageable pageable) {
        BaseModuleUserRights.assertCanSearch(user);
        return baseModuleService.search(user, cmd, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public BaseModuleDto get(HoisUserDetails user, @WithEntity BaseModule module) {
        BaseModuleUserRights.assertCanView(user, module.getSchool());
        return baseModuleService.get(module);
    }
    
    @GetMapping("/cm/{id:\\d+}")
    public BaseModuleDto getForCurriculumModule(HoisUserDetails user, @WithEntity BaseModule bModule) {
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPEKAVA);
        UserUtil.assertSameSchool(user, bModule.getSchool());
        return baseModuleService.get(bModule);
    }

    @PutMapping("/{id:\\d+}")
    public BaseModuleDto save(HoisUserDetails user, @NotNull @Valid @RequestBody BaseModuleForm baseModuleForm,
            @WithEntity BaseModule baseModule) {
        BaseModuleUserRights.assertCanEdit(user, baseModule.getSchool());
        return get(user, baseModuleService.save(user, baseModuleForm, baseModule));
    }

    @PostMapping
    public BaseModuleDto create(HoisUserDetails user, @Valid @RequestBody BaseModuleForm baseModuleForm) {
        BaseModuleUserRights.assertCanCreate(user);
        BaseModuleDto dto = new BaseModuleDto();
        dto.setId(baseModuleService.create(user, baseModuleForm).getId());
        return dto;
    }

    @DeleteMapping("{id:\\d+}")
    public void delete(HoisUserDetails user, @WithEntity BaseModule module) {
        BaseModuleUserRights.assertCanDelete(user, module, module.getSchool());
        baseModuleService.delete(module);
    }

    @GetMapping("/teachers")
    public List<PersonResult> getTeachers(HoisUserDetails user, TeacherAutocompleteCommand lookup) {
        if (!(BaseModuleUserRights.canViewBaseModule(user)
                || UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPEKAVA))) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
        lookup.setValid(Boolean.TRUE);
        return baseModuleService.getTeachers(user.getSchoolId(), lookup);
    }

    @GetMapping("/capacities/{id:\\d+}")
    public List<BaseModuleCapacityDto> getCapacities(HoisUserDetails user, @WithEntity BaseModule module) {
        BaseModuleUserRights.assertCanSearch(user);
        return StreamUtil.toMappedList(BaseModuleCapacityDto::of, module.getCapacities());
    }

    @GetMapping("/generate/{cId:\\d+}/{oId:\\d+}")
    public BaseModuleDto generateBaseModule(HoisUserDetails user, @WithEntity("cId") CurriculumModule cModule,
            @WithEntity("oId") CurriculumVersionOccupationModule oModule) {
        BaseModuleUserRights.assertCanGenerate(user, cModule);
        return getForCurriculumModule(user, baseModuleService.generate(user, cModule, oModule));
    }

    @PostMapping("/replace")
    public void replace(HoisUserDetails user, @Valid @RequestBody BaseModuleReplaceCommand cmd) {
        BaseModuleUserRights.assertCanReplace(user);
        baseModuleService.replaceByBaseModule(user, cmd);
    }

    @GetMapping("/replace/{bModule:\\d+}/{cModule:\\d+}")
    public BaseModuleReplaceForm getReplaceForm(HoisUserDetails user, @WithEntity("bModule") BaseModule bModule,
            @WithEntity("cModule") CurriculumModule cModule) {
        BaseModuleUserRights.assertCanGetReplaceForm(user);
        return baseModuleService.getReplaceForm(bModule, cModule, null);
    }

    @GetMapping("/replace/{bModule:\\d+}/{cModule:\\d+}/{oModule:\\d+}")
    public BaseModuleReplaceForm getReplaceForm(HoisUserDetails user, @WithEntity("bModule") BaseModule bModule,
            @WithEntity("cModule") CurriculumModule cModule,
            @WithEntity("oModule") CurriculumVersionOccupationModule oModule) {
        BaseModuleUserRights.assertCanGetReplaceForm(user);
        return baseModuleService.getReplaceForm(bModule, cModule, oModule);
    }

    @GetMapping("/release/{id:\\d+}")
    public void releaseModule(HoisUserDetails user, @WithEntity CurriculumModule cModule) {
        BaseModuleUserRights.assertCanRelease(user);
        baseModuleService.releaseReferences(cModule);
    }

    @GetMapping("/releaseMin/{id:\\d+}")
    public void releaseOModule(HoisUserDetails user, @WithEntity CurriculumVersionOccupationModule oModule) {
        BaseModuleUserRights.assertCanRelease(user);
        baseModuleService.releaseReferences(oModule);
    }

    @GetMapping("/expired")
    public Set<AutocompleteResult> getExpiredBaseModules(HoisUserDetails user) {
        BaseModuleUserRights.assertCanSearch(user);
        return baseModuleService.getExpired(user, false);
    }

    @GetMapping("/expiredhascurriculums")
    public Set<AutocompleteResult> getExpiredBaseModulesWithCurriculumModules(HoisUserDetails user) {
        BaseModuleUserRights.assertCanSearch(user);
        return baseModuleService.getExpired(user, true);
    }
}

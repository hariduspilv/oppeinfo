package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculumModule;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.report.StateCurriculumReport;
import ee.hitsa.ois.repository.StateCurriculumRepository;
import ee.hitsa.ois.service.PdfService;
import ee.hitsa.ois.service.StateCurriculumService;
import ee.hitsa.ois.service.StateCurriculumValidationService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.StateCurriculumUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.StateCurriculumForm;
import ee.hitsa.ois.web.commandobject.StateCurriculumModuleForm;
import ee.hitsa.ois.web.commandobject.StateCurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.UniqueCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.StateCurriculumDto;
import ee.hitsa.ois.web.dto.StateCurriculumModuleDto;
import ee.hitsa.ois.web.dto.StateCurriculumSearchDto;


@RestController
@RequestMapping("/stateCurriculum")
public class StateCurriculumController {
    
    @Autowired
    private StateCurriculumService stateCurriculumService;
    @Autowired
    private StateCurriculumRepository stateCurriculumRepository;
    @Autowired
    private PdfService pdfService;
    @Autowired 
    private StateCurriculumValidationService stateCurriculumValidationService;

    @GetMapping("/print/{id:\\d+}/stateCurriculum.pdf")
    public void print(HoisUserDetails user, @WithEntity("id") StateCurriculum stateCurriculum, HttpServletResponse response) throws IOException {
        StateCurriculumValidationService.assertCanView(user, stateCurriculum);
        HttpUtil.pdf(response, EntityUtil.getId(stateCurriculum) + ".pdf", pdfService.generate(StateCurriculumReport.TEMPLATE_NAME, new StateCurriculumReport(stateCurriculum)));
    }
    
    @GetMapping("/{id:\\d+}")
    public StateCurriculumDto get(HoisUserDetails user, @WithEntity("id") StateCurriculum stateCurriculum) {
        StateCurriculumValidationService.assertCanView(user, stateCurriculum);
        return stateCurriculumService.get(user, stateCurriculum);
    }
    
    @GetMapping("/canCreate")
    public Map<String, ?> canCreate(HoisUserDetails user) {
        return Collections.singletonMap("canCreate", Boolean.valueOf(StateCurriculumUtil.canCreate(user)));
    }
    
    @GetMapping("/canView")
    public Map<String, ?> canView(HoisUserDetails user) {
        return Collections.singletonMap("canView", Boolean.valueOf(StateCurriculumUtil.hasPermissionToView(user)));
    }
    
    @GetMapping
    public Page<StateCurriculumSearchDto> search(HoisUserDetails user, StateCurriculumSearchCommand stateCurriculumSearchCommand, Pageable pageable) {
        return stateCurriculumService.search(user, stateCurriculumSearchCommand, pageable);
    }

    @PostMapping
    public StateCurriculumDto create(HoisUserDetails user, @Valid @RequestBody StateCurriculumForm stateCurriculumForm) {
        StateCurriculumValidationService.assertCanCreate(user); 
        stateCurriculumValidationService.assertNameIsUnique(null, stateCurriculumForm);
        return get(user, stateCurriculumService.create(stateCurriculumForm));
    }

    @PutMapping("/{id:\\d+}")
    public StateCurriculumDto save(HoisUserDetails user, @Valid @RequestBody StateCurriculumForm stateCurriculumForm, @WithEntity("id") StateCurriculum stateCurriculum) {
       StateCurriculumValidationService.assertCanChange(user, stateCurriculum);
       stateCurriculumValidationService.assertNameIsUnique(stateCurriculum, stateCurriculumForm);
       return get(user, stateCurriculumService.save(stateCurriculum, stateCurriculumForm));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, 
            @WithEntity("id") StateCurriculum stateCurriculum) {
        StateCurriculumValidationService.assertCanDelete(user, stateCurriculum);
        stateCurriculumService.delete(stateCurriculum);
    }

    @GetMapping("/unique")
    public boolean isUnique(UniqueCommand command) {
		return stateCurriculumValidationService.isUnique(command);
    }

    @GetMapping("/all")
    public List<AutocompleteResult> searchAll(StateCurriculumSearchCommand stateCurriculumSearchCommand, Sort sort) {
        return StreamUtil.toMappedList(AutocompleteResult::of, stateCurriculumService.searchAll(stateCurriculumSearchCommand, sort));
    }

    @PostMapping("/modules")
    public StateCurriculumModuleDto createModule(HoisUserDetails user, 
            @NotNull @Valid @RequestBody StateCurriculumModuleForm form) {
        StateCurriculum sc = stateCurriculumRepository.getOne(form.getStateCurriculum());
        StateCurriculumValidationService.assertCanChange(user, sc);
        return StateCurriculumModuleDto.of(stateCurriculumService.createModule(form));
    }

    @PutMapping("/modules/{id:\\d+}")
    public StateCurriculumModuleDto updateModule(HoisUserDetails user, 
            @NotNull @Valid @RequestBody StateCurriculumModuleForm form, 
            @WithEntity("id") StateCurriculumModule module) {
        StateCurriculumValidationService.assertCanChange(user, module.getStateCurriculum());
        return StateCurriculumModuleDto.of(stateCurriculumService.updateModule(module, form));
    }

    @DeleteMapping("/modules/{id:\\d+}")
    public void deleteModule(HoisUserDetails user, 
            @WithVersionedEntity(value = "id", versionRequestParam = "version") 
    StateCurriculumModule module, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        StateCurriculumValidationService.assertCanChange(user, module.getStateCurriculum());
        stateCurriculumService.deleteModule(module);
    }

    @PutMapping("/close/{id:\\d+}")
    public StateCurriculumDto close(HoisUserDetails user, @WithEntity("id") StateCurriculum stateCurriculum) {
        StateCurriculumValidationService.assertCanClose(user, stateCurriculum);
       return get(user, stateCurriculumService.setStatus(stateCurriculum, CurriculumStatus.OPPEKAVA_STAATUS_C));
    }

    @PutMapping("/closeAndSave/{id:\\d+}")
    public StateCurriculumDto closeAndSave(HoisUserDetails user, @WithEntity("id") StateCurriculum stateCurriculum,
            @NotNull @Valid @RequestBody StateCurriculumForm form) {
       StateCurriculumValidationService.assertCanChange(user, stateCurriculum);
       StateCurriculumValidationService.assertCanClose(user, stateCurriculum);
       stateCurriculumValidationService.assertNameIsUnique(stateCurriculum, form);
       return get(user, stateCurriculumService.setStatusAndSave(stateCurriculum, form, CurriculumStatus.OPPEKAVA_STAATUS_C));
    }
    
    @PutMapping("/confirmAndSave/{id:\\d+}")
    public StateCurriculumDto confirmAndSave(HoisUserDetails user, @WithEntity("id") StateCurriculum stateCurriculum,
            @NotNull @Valid @RequestBody StateCurriculumForm form) {
       StateCurriculumValidationService.assertCanChange(user, stateCurriculum);
       StateCurriculumValidationService.assertCanConfirm(user, stateCurriculum);
       stateCurriculumValidationService.validateStateCurriculumForm(form);
       stateCurriculumValidationService.assertNameIsUnique(stateCurriculum, form);
       return get(user, stateCurriculumService.setStatusAndSave(stateCurriculum, form, CurriculumStatus.OPPEKAVA_STAATUS_K));
    }
}

package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
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
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.report.StateCurriculumReport;
import ee.hitsa.ois.repository.StateCurriculumRepository;
import ee.hitsa.ois.service.PdfService;
import ee.hitsa.ois.service.StateCurriculumService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.validation.StateCurriculumValidator;
import ee.hitsa.ois.validation.ValidationFailedException;
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
    private Validator validator;
    @Autowired
    private PdfService pdfService;
    
    @GetMapping("/print/{id:\\d+}/stateCurriculum.pdf")
    public void print(@WithEntity("id") StateCurriculum stateCurriculum, HttpServletResponse response) throws IOException {
        HttpUtil.pdf(response, stateCurriculum.getNameEt() + ".pdf", pdfService.generate(StateCurriculumReport.TEMPLATE_NAME, new StateCurriculumReport(stateCurriculum)));
    }

    @PostMapping
    public StateCurriculumDto create(HoisUserDetails user, @Valid @RequestBody StateCurriculumForm stateCurriculumForm) {
        UserUtil.assertIsMainAdmin(user);
        assertNameIsUnique(null, stateCurriculumForm);
        return get(stateCurriculumService.create(stateCurriculumForm));
    }

    @PutMapping("/{id:\\d+}")
    public StateCurriculumDto update(HoisUserDetails user, @Valid @RequestBody StateCurriculumForm stateCurriculumForm, @WithEntity("id") StateCurriculum stateCurriculum) {
       UserUtil.assertIsMainAdmin(user);
       checkStatus(stateCurriculum);
       assertNameIsUnique(stateCurriculum, stateCurriculumForm);
       return get(stateCurriculumService.save(stateCurriculum, stateCurriculumForm));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, 
            @WithEntity("id") StateCurriculum stateCurriculum) {
        UserUtil.assertIsMainAdmin(user);
        checkStatus(stateCurriculum);
        stateCurriculumService.delete(stateCurriculum);
    }
    
    @GetMapping
    public Page<StateCurriculumSearchDto> search(StateCurriculumSearchCommand stateCurriculumSearchCommand, Pageable pageable) {
        return stateCurriculumService.search(stateCurriculumSearchCommand, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public StateCurriculumDto get(@WithEntity("id") StateCurriculum curriculum) {
        return StateCurriculumDto.of(curriculum);
    }

    @GetMapping("/unique")
    public boolean isUnique(UniqueCommand command) {
		return stateCurriculumService.isUnique(command);
    }

    @GetMapping("/all")
    public List<AutocompleteResult> searchAll(StateCurriculumSearchCommand stateCurriculumSearchCommand, Sort sort) {
        return StreamUtil.toMappedList(AutocompleteResult::of, stateCurriculumService.searchAll(stateCurriculumSearchCommand, sort));
    }
    
    @PostMapping("/modules")
    public StateCurriculumModuleDto createModule(HoisUserDetails user, 
            @NotNull @Valid @RequestBody StateCurriculumModuleForm form) {
        UserUtil.assertIsMainAdmin(user);
        checkStatus(stateCurriculumRepository.getOne(form.getStateCurriculum()));
        return StateCurriculumModuleDto.of(stateCurriculumService.createModule(form));
    }
    
    @PutMapping("/modules/{id:\\d+}")
    public StateCurriculumModuleDto updateModule(HoisUserDetails user, 
            @NotNull @Valid @RequestBody StateCurriculumModuleForm form, 
            @WithEntity("id") StateCurriculumModule module) {
        UserUtil.assertIsMainAdmin(user);
        checkStatus(module.getStateCurriculum());
        return StateCurriculumModuleDto.of(stateCurriculumService.updateModule(module, form));
    }
    
    @DeleteMapping("/modules/{id:\\d+}")
    public void deleteModule(HoisUserDetails user, 
            @WithVersionedEntity(value = "id", versionRequestParam = "version") 
    StateCurriculumModule module, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertIsMainAdmin(user);
        checkStatus(module.getStateCurriculum());
        stateCurriculumService.deleteModule(module);
    }

    @PutMapping("/close/{id:\\d+}")
    public StateCurriculumDto close(HoisUserDetails user, @WithEntity("id") StateCurriculum stateCurriculum) {
       UserUtil.assertIsMainAdmin(user);
       return get(stateCurriculumService.setStatus(stateCurriculum, CurriculumStatus.OPPEKAVA_STAATUS_C));
    }
    
    @PutMapping("/closeAndSave/{id:\\d+}")
    public StateCurriculumDto closeAndSave(HoisUserDetails user, @WithEntity("id") StateCurriculum stateCurriculum,
            @NotNull @Valid @RequestBody StateCurriculumForm form) {
       UserUtil.assertIsMainAdmin(user);
       assertNameIsUnique(stateCurriculum, form);
       return get(stateCurriculumService.setStatusAndSave(stateCurriculum, form, CurriculumStatus.OPPEKAVA_STAATUS_C));
    }
    
    @PutMapping("/confirmAndSave/{id:\\d+}")
    public StateCurriculumDto confirmAndSave(HoisUserDetails user, @WithEntity("id") StateCurriculum stateCurriculum,
            @NotNull @Valid @RequestBody StateCurriculumForm form) {
       UserUtil.assertIsMainAdmin(user);
       validateStateCurriculumForm(form);
       checkStatus(stateCurriculum);
       assertNameIsUnique(stateCurriculum, form);
       return get(stateCurriculumService.setStatusAndSave(stateCurriculum, form, CurriculumStatus.OPPEKAVA_STAATUS_K));
    }
    
    private void assertNameIsUnique(StateCurriculum stateCurriculum, StateCurriculumForm stateCurriculumForm) {
        Long id = EntityUtil.getNullableId(stateCurriculum);
        
        UniqueCommand nameEtUnique = new UniqueCommand();
        nameEtUnique.setId(id);
        nameEtUnique.setParamName("nameEt");
        nameEtUnique.setParamValue(stateCurriculumForm.getNameEt());
        
        UniqueCommand nameEnUnique = new UniqueCommand();
        nameEnUnique.setId(id);
        nameEnUnique.setParamName("nameEn");
        nameEnUnique.setParamValue(stateCurriculumForm.getNameEn());

        if(!stateCurriculumService.isUnique(nameEtUnique) || !stateCurriculumService.isUnique(nameEnUnique)) {
            throw new ValidationFailedException("stateCurriculum.error.unique.name");
        }
    }
    
    public void checkStatus(StateCurriculum stateCurriculum) {
        AssertionFailedException.throwIf(!ClassifierUtil.equals(CurriculumStatus.OPPEKAVA_STAATUS_S, stateCurriculum.getStatus()), 
                "Only state curriculums with status OPPEKAVA_STAATUS_S can be changed");
    }
    
    public void validateStateCurriculumForm(StateCurriculumForm stateCurriculumForm) {
        Set<ConstraintViolation<StateCurriculumForm>> errors = 
                validator.validate(stateCurriculumForm, StateCurriculumValidator.Confirmed.class);
        if(!errors.isEmpty()) {
            throw new ValidationFailedException(errors);
        }
    }
}

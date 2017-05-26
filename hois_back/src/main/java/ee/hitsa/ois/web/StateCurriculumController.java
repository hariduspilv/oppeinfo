package ee.hitsa.ois.web;

import java.util.List;

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
import ee.hitsa.ois.service.StateCurriculumService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.StateCurriculumForm;
import ee.hitsa.ois.web.commandobject.StateCurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.UniqueCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.StateCurriculumDto;
import ee.hitsa.ois.web.dto.StateCurriculumSearchDto;


@RestController
@RequestMapping("/stateCurriculum")
public class StateCurriculumController {

    @Autowired
    private StateCurriculumService stateCurriculumService;

    @PostMapping
    public StateCurriculumDto create(HoisUserDetails user, @Valid @RequestBody StateCurriculumForm stateCurriculumForm) {
        UserUtil.assertIsMainAdmin(user);
        return get(stateCurriculumService.create(stateCurriculumForm));
    }

    @PutMapping("/{id:\\d+}")
    public StateCurriculumDto update(HoisUserDetails user, @Valid @RequestBody StateCurriculumForm stateCurriculumForm, @WithEntity("id") StateCurriculum stateCurriculum) {
       UserUtil.assertIsMainAdmin(user);
       return get(stateCurriculumService.save(stateCurriculum, stateCurriculumForm));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") StateCurriculum curriculum, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertIsMainAdmin(user);
        stateCurriculumService.delete(curriculum);
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
    
    @PutMapping("/modules/{id:\\d+}")
    public StateCurriculumDto updateCurriculumModule(HoisUserDetails user, @NotNull @RequestBody StateCurriculumForm form, @WithEntity("id") StateCurriculum stateCurriculum) {
        UserUtil.assertIsMainAdmin(user);
        return StateCurriculumDto.of(stateCurriculumService.updateStateCurriculumModules(stateCurriculum, form));
    }

}

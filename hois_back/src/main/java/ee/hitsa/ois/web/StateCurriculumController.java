package ee.hitsa.ois.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.service.StateCurriculumService;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.StateCurriculumForm;
import ee.hitsa.ois.web.commandobject.StateCurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.UniqueCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.StateCurriculumDto;
import ee.hitsa.ois.web.dto.StateCurriculumSearchDto;

/**
 * TODO: there are going to be exception
 * when trying to delete or update object, which does not exist.
 */
@RestController
@RequestMapping("/stateCurriculum")
public class StateCurriculumController {

    private final StateCurriculumService stateCurriculumService;

    @Autowired
    public StateCurriculumController(StateCurriculumService service) {
        this.stateCurriculumService = service;
    }

    @PostMapping("")
    public StateCurriculumDto create(@Valid @RequestBody StateCurriculumForm stateCurriculumForm) {
        StateCurriculumDto dto = StateCurriculumDto.of(stateCurriculumService.save(new StateCurriculum(), stateCurriculumForm));
        return dto;
    }

    @PutMapping("/{id}")
    public StateCurriculumDto update(@Valid @RequestBody StateCurriculumForm stateCurriculumForm, @WithEntity("id") StateCurriculum stateCurriculum) {
       return StateCurriculumDto.of(stateCurriculumService.save(stateCurriculum, stateCurriculumForm));
    }

    @DeleteMapping("/{id}")
    public void delete(@WithEntity("id") StateCurriculum curriculum) {
        stateCurriculumService.delete(curriculum);
    }

    @GetMapping("")
    public Page<StateCurriculumSearchDto> search(StateCurriculumSearchCommand stateCurriculumSearchCommand, Pageable pageable) {
        Page<StateCurriculumSearchDto> page = stateCurriculumService.search(stateCurriculumSearchCommand, pageable);
        return page;
    }

    @GetMapping("/{id}")
    public StateCurriculumDto get(@WithEntity("id") StateCurriculum curriculum) {
        return StateCurriculumDto.of(curriculum);
    }

    @GetMapping("/unique")
    public boolean isUnique(UniqueCommand command) {
		return stateCurriculumService.isUnique(command);
    }

    @GetMapping("/all")
    public List<AutocompleteResult<Long>> searchAll(StateCurriculumSearchCommand stateCurriculumSearchCommand, Sort sort) {
        return stateCurriculumService.searchAll(stateCurriculumSearchCommand, sort)
                .stream().map(it -> AutocompleteResult.of(it)).collect(Collectors.toList());
    }
}

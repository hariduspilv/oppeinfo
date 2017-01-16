package ee.hitsa.ois.web;

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
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.StateCurriculum;
import ee.hitsa.ois.service.StateCurriculumService;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.StateCurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.UniqueCommand;

/**
 * TODO: there are going to be exception 
 * when trying to delete or update object, which does not exist.
 */
@RestController
@RequestMapping("/stateCurriculum")
public class StateCurriculumController {
	
    private final StateCurriculumService service;

    @Autowired
    public StateCurriculumController(StateCurriculumService service) {
        this.service = service;
    }

    @PostMapping(value = "")
    public StateCurriculum create(@Valid @RequestBody StateCurriculum curriculum) {
        return service.create(curriculum);
    }

    @PutMapping(value = "/{id}")
    public StateCurriculum update(@NotNull @RequestBody StateCurriculum newStateCurriculum, @WithEntity("id") StateCurriculum stateCurriculum) {
       return service.update(newStateCurriculum, stateCurriculum);
    }

    @DeleteMapping(value = "/{id}")
    public boolean delete(@WithEntity("id") StateCurriculum curriculum) {
        service.delete(curriculum);
        return true;
    }

    @GetMapping(value = "")
    public Page<StateCurriculum> search(StateCurriculumSearchCommand stateCurriculumSearchCommand, Pageable pageable) {
        return service.search(stateCurriculumSearchCommand, pageable);
    }

    @GetMapping(value = "/{id}")
    public StateCurriculum get(@WithEntity("id") StateCurriculum curriculum) {
        return curriculum;
    }
    
    @GetMapping(value = "/unique")
    public boolean isUnique(UniqueCommand command) {
		return service.isUnique(command);
    }
}

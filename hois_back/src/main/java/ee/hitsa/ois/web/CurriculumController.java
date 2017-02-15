package ee.hitsa.ois.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumDepartment;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.service.CurriculumService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.CurriculumForm;
import ee.hitsa.ois.web.commandobject.CurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.UniqueCommand;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.curriculum.CurriculumDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSearchDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionDto;

@RestController
@RequestMapping("curriculum")
public class CurriculumController {

	@Autowired
	private CurriculumService curriculumService;

	@GetMapping(value = "/{id}")
    public CurriculumDto get(@WithEntity("id") Curriculum curriculum) {
	    CurriculumDto dto = CurriculumDto.of(curriculum);
        return dto;
    }

    @GetMapping(value = "")
    public Page<CurriculumSearchDto> search(CurriculumSearchCommand curriculumSearchCommand, Pageable pageable) {
        return curriculumService.search(curriculumSearchCommand, pageable);
    }
    // TODO: use dto or AutocompleteResult
    @GetMapping(value = "/departments")
    public Iterable<CurriculumDepartment> getAllDepartments() {
        return curriculumService.findAllDepartments();
    }

    @PostMapping(value = "")
    public CurriculumDto create(HoisUserDetails user, @RequestBody CurriculumForm curriculumForm) {
        Curriculum curriculum = new Curriculum();
        curriculum.setSchool(user.getSchool());
        return CurriculumDto.of(curriculumService.save(curriculum, curriculumForm));
    }

    @PutMapping(value = "/{id}")
    public CurriculumDto update(@NotNull @Valid @RequestBody CurriculumForm curriculumForm, @WithEntity("id") Curriculum curriculum) {
        return CurriculumDto.of(curriculumService.save(curriculum, curriculumForm));
    }

    @GetMapping(value = "/unique")
    public boolean isUnique(HoisUserDetails user, UniqueCommand command) {
		return curriculumService.isUnique(user.getSchoolId(), command);
    }

    /**
     * TODO: test not added yet!
     */
    @GetMapping(value = "/version/unique")
    public boolean isVersionUnique(HoisUserDetails user, UniqueCommand command) {
        return curriculumService.isVersionUnique(user.getSchoolId(), command);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@WithEntity("id") Curriculum curriculum) {
    	curriculumService.delete(curriculum);
    }

    //TODO: add tests!
    @GetMapping(value = "/areasOfStudyByGroupOfStudy/{code}")
    public List<ClassifierSelection> getAreasOfStudyByGroupOfStudy(@NotNull @PathVariable("code") String code) {
        return curriculumService.getAreasOfStudyByGroupOfStudy(code).stream().
                map(c -> ClassifierSelection.of(c)).collect(Collectors.toList());
    }

    @PostMapping(value = "/{curriculumId}/versions")
    public CurriculumVersionDto createVersion(@WithEntity(value = "curriculumId") Curriculum curriculum,
            @Valid @RequestBody CurriculumVersionDto dto) {
        return curriculumService.save(curriculum, dto);
    }

    @PutMapping("/{curriculumId}/versions/{id}")
    public CurriculumVersionDto updateVersion(@WithEntity(value = "id") CurriculumVersion curriculumVersion,
            @Valid @RequestBody CurriculumVersionDto curriculumVersionDto) {
        return curriculumService.save(curriculumVersion, curriculumVersionDto);
    }
}

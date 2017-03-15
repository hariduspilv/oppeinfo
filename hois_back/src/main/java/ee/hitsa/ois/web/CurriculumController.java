package ee.hitsa.ois.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.CurriculumService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.CurriculumForm;
import ee.hitsa.ois.web.commandobject.CurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.SubjectSearchCommand;
import ee.hitsa.ois.web.commandobject.UniqueCommand;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.curriculum.CurriculumDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSearchDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleSubjectDto;

@RestController
@RequestMapping("curriculum")
public class CurriculumController {

	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private SchoolRepository schoolRepository;

	@GetMapping("/{id:\\d+}")
    public CurriculumDto get(HoisUserDetails user, @WithEntity("id") Curriculum curriculum) {
        assertSameOrJoinSchool(user, curriculum);
        return CurriculumDto.of(curriculum);
    }

    @GetMapping("")
    public Page<CurriculumSearchDto> search(HoisUserDetails user, CurriculumSearchCommand curriculumSearchCommand, Pageable pageable) {
        return curriculumService.search(user.getSchoolId(), curriculumSearchCommand, pageable);
    }
    // TODO: use dto or AutocompleteResult
    @GetMapping("/departments")
    public List<CurriculumDepartment> getAllDepartments() {
        return curriculumService.findAllDepartments();
    }

    @PostMapping("")
    public CurriculumDto create(HoisUserDetails user, @RequestBody CurriculumForm curriculumForm) {
        return CurriculumDto.of(curriculumService.create(user, curriculumForm));
    }

    @PutMapping("/{id:\\d+}")
    public CurriculumDto update(HoisUserDetails user, @NotNull @Valid @RequestBody CurriculumForm curriculumForm, @WithEntity("id") Curriculum curriculum) {
        assertSameOrJoinSchool(user, curriculum);
        return CurriculumDto.of(curriculumService.save(curriculum, curriculumForm));
    }

    @GetMapping("/unique")
    public boolean isUnique(HoisUserDetails user, UniqueCommand command) {
		return curriculumService.isUnique(user.getSchoolId(), command);
    }

    /**
     * TODO: test not added yet!
     */
    @GetMapping("/version/unique")
    public boolean isVersionUnique(HoisUserDetails user, UniqueCommand command) {
        return curriculumService.isVersionUnique(user.getSchoolId(), command);
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithEntity("id") Curriculum curriculum) {
        UserUtil.assertSameSchool(user, curriculum.getSchool());
    	curriculumService.delete(curriculum);
    }

    //TODO: add tests!
    @GetMapping("/areasOfStudyByGroupOfStudy/{code}")
    public List<ClassifierSelection> getAreasOfStudyByGroupOfStudy(@NotNull @PathVariable("code") String code) {
        return curriculumService.getAreasOfStudyByGroupOfStudy(code).stream().
                map(c -> ClassifierSelection.of(c)).collect(Collectors.toList());
    }

    @PostMapping("/{curriculumId:\\d+}/versions")
    public CurriculumVersionDto createVersion(HoisUserDetails user, @WithEntity(value = "curriculumId") Curriculum curriculum,
            @Valid @RequestBody CurriculumVersionDto dto) {
        assertSameOrJoinSchool(user, curriculum);
        return curriculumService.create(curriculum, dto);
    }

    @PutMapping("/{curriculumId:\\d+}/versions/{id:\\d+}")
    public CurriculumVersionDto updateVersion(HoisUserDetails user, @WithEntity(value = "id") CurriculumVersion curriculumVersion,
            @Valid @RequestBody CurriculumVersionDto curriculumVersionDto) {
        assertSameOrJoinSchool(user, curriculumVersion.getCurriculum());
        return curriculumService.save(curriculumVersion, curriculumVersionDto);
    }

    /**
     * TODO: test
     */
    @GetMapping("/subjects")
    public Page<CurriculumVersionHigherModuleSubjectDto> getSubjects(@Valid SubjectSearchCommand subjectSearchCommand, Pageable pageable) {
        return curriculumService.getSubjects(subjectSearchCommand, pageable);
    }

    /**
     * All schools which are joint parters have right to see curriculum in case of joint curriculum.
     */
    private void assertSameOrJoinSchool(HoisUserDetails user, Curriculum curriculum) {
        Set<String> ehisSchools = new HashSet<>();
        ehisSchools.add(EntityUtil.getCode(curriculum.getSchool().getEhisSchool()));
        ehisSchools.addAll(curriculum.getJointPartners().stream().filter(it -> it.getEhisSchool() != null)
                .map(it -> EntityUtil.getCode(it.getEhisSchool())).collect(Collectors.toList()));

        if (!ehisSchools.contains(EntityUtil.getNullableCode(schoolRepository.getOne(user.getSchoolId()).getEhisSchool()))) {
            throw new IllegalArgumentException();
        }
    }
}

package ee.hitsa.ois.web;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.AutocompleteService;
import ee.hitsa.ois.service.CurriculumService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.CurriculumForm;
import ee.hitsa.ois.web.commandobject.CurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.SubjectSearchCommand;
import ee.hitsa.ois.web.commandobject.UniqueCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.curriculum.CurriculumDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSearchDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSpecialityDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleSubjectDto;

@RestController
@RequestMapping("curriculum")
public class CurriculumController {

	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private SchoolRepository schoolRepository;
    @Autowired
    private AutocompleteService autocompleteService;

	@GetMapping("/{id:\\d+}")
    public CurriculumDto get(HoisUserDetails user, @WithEntity("id") Curriculum curriculum) {
	    // External expert can watch curriculum
	    if(!user.isExternalExpert()) {
	        assertSameOrJoinSchool(user, curriculum);
	    }
        return CurriculumDto.of(curriculum);
    }

    @GetMapping
    public Page<CurriculumSearchDto> search(HoisUserDetails user, CurriculumSearchCommand curriculumSearchCommand, Pageable pageable) {
        return curriculumService.search(user.getSchoolId(), curriculumSearchCommand, pageable);
    }
    // TODO: use dto or AutocompleteResult
    @GetMapping("/departments")
    public List<CurriculumDepartment> getAllDepartments() {
        return curriculumService.findAllDepartments();
    }

    @PostMapping
    public CurriculumDto create(HoisUserDetails user, @Valid @RequestBody CurriculumForm curriculumForm) {
        UserUtil.assertIsSchoolAdmin(user);
        return CurriculumDto.of(curriculumService.create(user, curriculumForm));
    }

    @PutMapping("/{id:\\d+}")
    public CurriculumDto update(HoisUserDetails user, @NotNull @Valid @RequestBody CurriculumForm curriculumForm, @WithEntity("id") Curriculum curriculum) {
        assertSameOrJoinSchool(user, curriculum);
        UserUtil.assertIsSchoolAdmin(user);
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
        UserUtil.assertIsSchoolAdmin(user);
    	curriculumService.delete(curriculum);
    }

    @PostMapping("/speciality")
    public CurriculumSpecialityDto createCurriculumSpeciality(HoisUserDetails user, @Valid @RequestBody CurriculumSpecialityDto form) {
        UserUtil.assertIsSchoolAdmin(user);
        return CurriculumSpecialityDto.of(curriculumService.createCurriculumSpeciality(form));
    }

    @PutMapping("/speciality/{id:\\d+}")
    public CurriculumSpecialityDto updateCurriculumSpeciality(HoisUserDetails user, @NotNull @Valid @RequestBody CurriculumSpecialityDto form, @WithEntity("id") CurriculumSpeciality speciality) {
        UserUtil.assertIsSchoolAdmin(user);
        return CurriculumSpecialityDto.of(curriculumService.saveCurriculumSpeciality(speciality, form));
    }

    @DeleteMapping("/speciality/{id:\\d+}")
    public void deleteCurriculumSpeciality(HoisUserDetails user, @WithEntity("id") CurriculumSpeciality speciality) {
        UserUtil.assertIsSchoolAdmin(user);
        curriculumService.deleteSpeciality(speciality);
    }

    //TODO: add tests!
    @GetMapping("/areasOfStudyByGroupOfStudy/{code}")
    public List<ClassifierSelection> getAreasOfStudyByGroupOfStudy(@NotNull @PathVariable("code") String code) {
        return StreamUtil.toMappedList(ClassifierSelection::of, curriculumService.getAreasOfStudyByGroupOfStudy(code));
    }

    @PostMapping("/{curriculumId:\\d+}/versions")
    public CurriculumVersionDto createVersion(HoisUserDetails user, @WithEntity(value = "curriculumId") Curriculum curriculum,
            @Valid @RequestBody CurriculumVersionDto dto) {
        assertSameOrJoinSchool(user, curriculum);
        UserUtil.assertIsSchoolAdmin(user);
        return curriculumService.create(curriculum, dto);
    }

    @PutMapping("/{curriculumId:\\d+}/versions/{id:\\d+}")
    public CurriculumVersionDto updateVersion(HoisUserDetails user, @WithEntity(value = "id") CurriculumVersion curriculumVersion,
            @Valid @RequestBody CurriculumVersionDto curriculumVersionDto) {
        assertSameOrJoinSchool(user, curriculumVersion.getCurriculum());
        UserUtil.assertIsSchoolAdmin(user);
        return curriculumService.saveVersion(curriculumVersion, curriculumVersionDto);
    }

    @DeleteMapping("/{curriculumId:\\d+}/versions/{id:\\d+}")
    public void deleteVersion(HoisUserDetails user, @WithEntity(value = "id") CurriculumVersion curriculumVersion) {
        assertSameOrJoinSchool(user, curriculumVersion.getCurriculum());
        UserUtil.assertIsSchoolAdmin(user);
        curriculumService.deleteVersion(curriculumVersion);
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
        ehisSchools.addAll(StreamUtil.toMappedList(it -> EntityUtil.getCode(it.getEhisSchool()), curriculum.getJointPartners().stream().filter(it -> it.getEhisSchool() != null)));

        AssertionFailedException.throwIf(!ehisSchools.contains(EntityUtil.getNullableCode(schoolRepository.getOne(user.getSchoolId()).getEhisSchool())), "EHIS school mismatch");
    }

    @GetMapping("/versionHmoduleTypes")
    public List<ClassifierSelection> getCurriculumVersionHmoduleTypes(HoisUserDetails user) {
        /*
         * is_valid and is_higher is not considered at autocompleteService.classifiers()
         */
        List<ClassifierSelection> classifiers = autocompleteService.classifiers(Collections.singletonList(MainClassCode.KORGMOODUL.name()));
        List<ClassifierSelection> otherTypes = curriculumService.getCurriculumVersionHmoduleTypes(user.getSchoolId());
        classifiers.addAll(otherTypes);
        return classifiers;
    }
    
    /**
     * 
     * @param stateCurriculumSearchCommand
     * @param sort
     * @return
     */
    @GetMapping("/stateCurricula")
    public List<AutocompleteResult> getStateCurricula(Sort sort) {
        return StreamUtil.toMappedList(AutocompleteResult::of, curriculumService.getStateCurricula(sort));
    }

    @PutMapping("/module/{id:\\d+}")
    public CurriculumDto updateCurriculumModule(HoisUserDetails user, @NotNull @RequestBody CurriculumDto form, @WithEntity("id") Curriculum curriculum) {
        UserUtil.assertIsSchoolAdmin(user);
        return CurriculumDto.of(curriculumService.saveCurriculumModule(curriculum, form));
    }

    @DeleteMapping("/module/{id:\\d+}")
    public void deleteCurriculumModule(HoisUserDetails user, @WithEntity("id") CurriculumModule module) {
        UserUtil.assertIsSchoolAdmin(user);
        curriculumService.deleteModule(module);
    }
    
    @PutMapping("/higher/version/modules/{id:\\d+}")
    public CurriculumVersionDto updateHigherCurriculumVersionModules(HoisUserDetails user, @NotNull @RequestBody CurriculumVersionDto form, @WithEntity("id") CurriculumVersion curriculumVersion) {
        UserUtil.assertIsSchoolAdmin(user);
        return CurriculumVersionDto.of(curriculumService.updateHigherCurriculumVersionModules(curriculumVersion, form));
    }
}

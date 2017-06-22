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
import ee.hitsa.ois.domain.curriculum.CurriculumFile;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.report.CurriculumReport;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.AutocompleteService;
import ee.hitsa.ois.service.CurriculumService;
import ee.hitsa.ois.service.PdfService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.CurriculumFileForm;
import ee.hitsa.ois.web.commandobject.CurriculumFileUpdateDto;
import ee.hitsa.ois.web.commandobject.CurriculumForm;
import ee.hitsa.ois.web.commandobject.CurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.UniqueCommand;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.curriculum.CurriculumDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSearchDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSpecialityDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleSubjectDto;

/*
 * TODO: for now when user saves/deletes such objects as CurriculumSpeciality, CurriculumGrade, CurriculumModule, 
 * CurriculumVersionOModule, CurriculumVersionHModule, the whole list is sent and updated.
 * 
 * It's better to make distinct apis for creating/updating/deleting single items.
 */
@RestController
@RequestMapping("curriculum")
public class CurriculumController {

    @Autowired
    private AutocompleteService autocompleteService;
	@Autowired
	private CurriculumService curriculumService;
    @Autowired
    private PdfService pdfService;
	@Autowired
	private SchoolRepository schoolRepository;

	@GetMapping("/{id:\\d+}")
    public CurriculumDto get(@WithEntity("id") Curriculum curriculum) {
        return CurriculumDto.of(curriculum);
    }

    @GetMapping(value = "/print/{id:\\d+}", produces = HttpUtil.APPLICATION_PDF)
    public byte[] print(@WithEntity("id") Curriculum curriculum) {
        return pdfService.generatePdf(CurriculumReport.TEMPLATE_NAME, new CurriculumReport(curriculum));
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
    
    @PutMapping("/close/{id:\\d+}")
    public CurriculumDto closeCurriculum(HoisUserDetails user, @WithEntity("id") Curriculum curriculum) {
        assertSameOrJoinSchool(user, curriculum);
        UserUtil.assertIsSchoolAdmin(user);
        return CurriculumDto.of(curriculumService.closeCurriculum(curriculum));
    }

    @GetMapping("/unique/code")
    public boolean isHigherCodeUnique(HoisUserDetails user, UniqueCommand command) {
        return curriculumService.isCodeUnique(user.getSchoolId(), command);
    }

    @GetMapping("/unique/merCode")
    public boolean isHigherMerCodeUnique(UniqueCommand command) {
        return curriculumService.isMerCodeUnique(command);
    }
    
    @GetMapping("/unique/version/code")
    public boolean isVersionUnique(HoisUserDetails user, UniqueCommand command) {
        return curriculumService.isVersionUnique(user.getSchoolId(), command);
    }
    
    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithEntity("id") Curriculum curriculum) {
        UserUtil.assertSameSchool(user, curriculum.getSchool());
        UserUtil.assertIsSchoolAdmin(user);
    	curriculumService.delete(curriculum);
    }

    //TODO: add tests!
    @GetMapping("/areasOfStudyByGroupOfStudy/{code}")
    public List<String> getAreasOfStudyByGroupOfStudy(@NotNull @PathVariable("code") String code) {
        return StreamUtil.toMappedList(EntityUtil::getCode, curriculumService.getAreasOfStudyByGroupOfStudy(code));
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
    @GetMapping("{id:\\d+}/possibleSubjects")
    public List<CurriculumVersionHigherModuleSubjectDto> getSubjects(@WithEntity(value = "id") Curriculum curriculum) {
        return curriculumService.getSubjects(curriculum);
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
        /*
         * This check caused error while adding subjects to joint curriculum
         */
//        UserUtil.assertSameSchool(user, curriculumVersion.getCurriculum().getSchool());
        return CurriculumVersionDto.of(curriculumService.updateHigherCurriculumVersionModules(curriculumVersion, form));
    }
    
    @PutMapping("/vocational/implementationPlan/modules/{id:\\d+}")
    public CurriculumVersionDto updateVocationalCurriculumImplementationPlanModules(HoisUserDetails user, @NotNull @RequestBody CurriculumVersionDto form, @WithEntity("id") CurriculumVersion curriculumVersion) {
        UserUtil.assertIsSchoolAdmin(user);
        UserUtil.assertSameSchool(user, curriculumVersion.getCurriculum().getSchool());
        return CurriculumVersionDto.of(curriculumService.updateVocationalCurriculumImplementationPlanModules(curriculumVersion, form));
    }
    
    @PutMapping("/grade/{id:\\d+}")
    public CurriculumDto updateCurriculumGrades(HoisUserDetails user, @NotNull @RequestBody CurriculumDto form, @WithEntity("id") Curriculum curriculum) {
        UserUtil.assertIsSchoolAdmin(user);
        UserUtil.assertSameSchool(user, curriculum.getSchool());
        return CurriculumDto.of(curriculumService.updateCurriculumGrades(curriculum, form));
    }

    @PutMapping("/speciality/{id:\\d+}")
    public CurriculumDto updateCurriculumSpecialities(HoisUserDetails user, @NotNull @RequestBody CurriculumDto form, @WithEntity("id") Curriculum curriculum) {
        UserUtil.assertIsSchoolAdmin(user);
        UserUtil.assertSameSchool(user, curriculum.getSchool());
        return CurriculumDto.of(curriculumService.updateCurriculumSpecialities(curriculum, form));
    }

    @PostMapping("/{curriculumId:\\d+}/file")
    public CurriculumFileUpdateDto createCurriculumFile(HoisUserDetails user, @Valid @RequestBody 
            CurriculumFileForm curriculumFileForm, @WithEntity("curriculumId") Curriculum curriculum) {
        UserUtil.assertIsSchoolAdmin(user);
        assertSameOrJoinSchool(user, curriculum);
        return CurriculumFileUpdateDto.of(curriculumService.createCurriculumFile(curriculum, curriculumFileForm));
    }

    @DeleteMapping("/{curriculumId:\\d+}/file/{fileId:\\d+}")
    public void deleteCurriculumFile(HoisUserDetails user, @WithEntity("curriculumId") Curriculum curriculum, 
            @WithEntity("fileId") CurriculumFile curriculumFile) {
      UserUtil.assertSameSchool(user, curriculum.getSchool());
      UserUtil.assertIsSchoolAdmin(user);
      curriculumService.deleteCurriculumFile(curriculumFile);
    }

    @PostMapping("/speciality")
    public CurriculumSpecialityDto createCurriculumSpeciality(HoisUserDetails user, @Valid @RequestBody CurriculumSpecialityDto form) {
        UserUtil.assertIsSchoolAdmin(user);
        return CurriculumSpecialityDto.of(curriculumService.createCurriculumSpeciality(form));
    }
}

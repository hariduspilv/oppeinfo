package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.JAXBException;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumDepartment;
import ee.hitsa.ois.domain.curriculum.CurriculumFile;
import ee.hitsa.ois.domain.curriculum.CurriculumGrade;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.enums.CurriculumVersionStatus;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.report.CurriculumReport;
import ee.hitsa.ois.service.AutocompleteService;
import ee.hitsa.ois.service.CurriculumService;
import ee.hitsa.ois.service.CurriculumValidationService;
import ee.hitsa.ois.service.PdfService;
import ee.hitsa.ois.service.XmlService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.CurriculumFileForm;
import ee.hitsa.ois.web.commandobject.CurriculumFileUpdateDto;
import ee.hitsa.ois.web.commandobject.CurriculumForm;
import ee.hitsa.ois.web.commandobject.CurriculumModuleForm;
import ee.hitsa.ois.web.commandobject.CurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.CurriculumVersionHigherModuleForm;
import ee.hitsa.ois.web.commandobject.UniqueCommand;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.curriculum.CurriculumDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumGradeDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSearchDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSpecialityDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleSubjectDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleDto;

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
    private XmlService xmlService;
    @Autowired
    private CurriculumValidationService curriculumValidationService;

    @GetMapping("/{id:\\d+}")
    public CurriculumDto get(@WithEntity("id") Curriculum curriculum) {
        return CurriculumDto.of(curriculum);
    }

    @GetMapping("/xml/{id:\\d+}/curriculum.xml")
    public void xml(@WithEntity("id") Curriculum curriculum, HttpServletResponse response)
            throws IOException, JAXBException {
        HttpUtil.xml(response, curriculum.getCode() + ".xml",
                xmlService.generateFromObject(CurriculumDto.of(curriculum)));
    }

    @GetMapping("/print/{id:\\d+}/curriculum.pdf")
    public void print(@WithEntity("id") Curriculum curriculum, HttpServletResponse response) throws IOException {
        HttpUtil.pdf(response, curriculum.getNameEt() + ".pdf",
                pdfService.generate(CurriculumReport.TEMPLATE_NAME, new CurriculumReport(curriculum)));
    }

    @GetMapping
    public Page<CurriculumSearchDto> search(HoisUserDetails user, CurriculumSearchCommand curriculumSearchCommand,
            Pageable pageable) {
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
        curriculumValidationService.validateCreateCurriculumForm(curriculumForm);
        curriculumValidationService.assertCodeAndMerCodeAreUnique(user, curriculumForm, null);
        return CurriculumDto.of(curriculumService.create(user, curriculumForm));
    }

    @PutMapping("/{id:\\d+}")
    public CurriculumDto update(HoisUserDetails user, @NotNull @Valid @RequestBody CurriculumForm curriculumForm,
            @WithEntity("id") Curriculum curriculum) {
        curriculumValidationService.assertSameOrJoinSchool(user, curriculum);
        UserUtil.assertIsSchoolAdmin(user);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);
        curriculumValidationService.validateCurriculumFormWithStatusCheck(curriculum, curriculumForm);
        curriculumValidationService.assertCodeAndMerCodeAreUnique(user, curriculumForm, curriculum);
        return CurriculumDto.of(curriculumService.save(curriculum, curriculumForm));
    }

    @PutMapping("/close/{id:\\d+}")
    public CurriculumDto closeCurriculum(HoisUserDetails user, @WithEntity("id") Curriculum curriculum) {
        curriculumValidationService.assertSameOrJoinSchool(user, curriculum);
        UserUtil.assertIsSchoolAdmin(user);
        return CurriculumDto.of(curriculumService.closeCurriculum(curriculum));
    }

    @PutMapping("/saveAndProceed/{id:\\d+}")
    public CurriculumDto saveAndProceedCurriculum(HoisUserDetails user, @WithEntity("id") Curriculum curriculum,
            @NotNull @Valid @RequestBody CurriculumForm curriculumForm) {
        curriculumValidationService.assertSameOrJoinSchool(user, curriculum);
        UserUtil.assertIsSchoolAdmin(user);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);
        curriculumValidationService.validateCurriculumForm(curriculum, curriculumForm);
        curriculumValidationService.assertCodeAndMerCodeAreUnique(user, curriculumForm, curriculum);
        return CurriculumDto.of(curriculumService.saveAndProceedCurriculum(curriculum, curriculumForm));
    }

    @PutMapping("/sendToEhis/{id:\\d+}")
    public CurriculumDto sendToEhis(HoisUserDetails user, @WithEntity("id") Curriculum curriculum) {
        curriculumValidationService.assertSameOrJoinSchool(user, curriculum);
        UserUtil.assertIsSchoolAdmin(user);
        curriculumValidationService.validateCurriculum(curriculum);
        return CurriculumDto.of(curriculumService.sendToEhis(curriculum));
    }

    @PutMapping("/updateFromEhis/{id:\\d+}")
    public CurriculumDto updateFromEhis(HoisUserDetails user, @WithEntity("id") Curriculum curriculum) {
        curriculumValidationService.assertSameOrJoinSchool(user, curriculum);
        UserUtil.assertIsSchoolAdmin(user);
        curriculumValidationService.validateCurriculum(curriculum);
        return CurriculumDto.of(curriculumService.updateFromEhis(curriculum));
    }

    @GetMapping("/unique/code")
    public boolean isCodeUnique(HoisUserDetails user, UniqueCommand command) {
        return curriculumService.isCodeUnique(user.getSchoolId(), command);
    }

    @GetMapping("/unique/merCode")
    public boolean isMerCodeUnique(UniqueCommand command) {
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
        curriculumValidationService.assertCurriculumCanBeDeleted(curriculum);
        curriculumService.delete(curriculum);
    }

    @GetMapping("/areasOfStudyByGroupOfStudy/{code}")
    public List<String> getAreasOfStudyByGroupOfStudy(@NotNull @PathVariable("code") String code) {
        return StreamUtil.toMappedList(EntityUtil::getCode, curriculumService.getAreasOfStudyByGroupOfStudy(code));
    }

    @PostMapping("/{curriculumId:\\d+}/versions")
    public CurriculumVersionDto createVersion(HoisUserDetails user,
            @WithEntity(value = "curriculumId") Curriculum curriculum, @Valid @RequestBody CurriculumVersionDto dto) {
        curriculumValidationService.assertSameOrJoinSchool(user, curriculum);
        UserUtil.assertIsSchoolAdmin(user);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);
        curriculumValidationService.assertVersionCodeIsUnique(user, dto, null);
        return curriculumService.createVersion(curriculum, dto);
    }

    @PutMapping("/{curriculumId:\\d+}/versions/{id:\\d+}")
    public CurriculumVersionDto updateVersion(HoisUserDetails user,
            @WithEntity(value = "id") CurriculumVersion curriculumVersion,
            @Valid @RequestBody CurriculumVersionDto curriculumVersionDto) {
        curriculumValidationService.assertSameOrJoinSchool(user, curriculumVersion.getCurriculum());
        UserUtil.assertIsSchoolAdmin(user);
        curriculumValidationService.assertCurriculumVersionCanBeEdited(curriculumVersion);
        curriculumValidationService.validateCurriculumVersionFormWithStatus(curriculumVersion, curriculumVersionDto);
        curriculumValidationService.assertVersionCodeIsUnique(user, curriculumVersionDto, curriculumVersion);
        return curriculumService.saveVersion(curriculumVersion, curriculumVersionDto);
    }

    @PutMapping("/{curriculumId:\\d+}/versions/close/{id:\\d+}")
    public CurriculumVersionDto closeVersion(HoisUserDetails user,
            @WithEntity(value = "id") CurriculumVersion curriculumVersion) {
        curriculumValidationService.assertSameOrJoinSchool(user, curriculumVersion.getCurriculum());
        UserUtil.assertIsSchoolAdmin(user);
        return CurriculumVersionDto.of(
                curriculumService.setStatus(curriculumVersion, CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_C));
    }

    @PutMapping("/{curriculumId:\\d+}/versions/confirm/{id:\\d+}")
    public CurriculumVersionDto confirmVersion(HoisUserDetails user,
            @WithEntity(value = "id") CurriculumVersion curriculumVersion) {
        curriculumValidationService.assertSameOrJoinSchool(user, curriculumVersion.getCurriculum());
        UserUtil.assertIsSchoolAdmin(user);
        curriculumValidationService.validateCurriculumVersion(curriculumVersion);
        return CurriculumVersionDto.of(
                curriculumService.setStatus(curriculumVersion, CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_K));
    }

    @PutMapping("/{curriculumId:\\d+}/versions/saveAndConfirm/{id:\\d+}")
    public CurriculumVersionDto saveAndConfirmVersion(HoisUserDetails user,
            @WithEntity(value = "id") CurriculumVersion curriculumVersion,
            @Valid @RequestBody CurriculumVersionDto curriculumVersionDto) {
        curriculumValidationService.assertSameOrJoinSchool(user, curriculumVersion.getCurriculum());
        UserUtil.assertIsSchoolAdmin(user);
        curriculumValidationService.validateCurriculumVersionForm(curriculumVersion, curriculumVersionDto);
        curriculumValidationService.assertCurriculumVersionCanBeEdited(curriculumVersion);
        curriculumValidationService.assertVersionCodeIsUnique(user, curriculumVersionDto, curriculumVersion);
        return curriculumService.saveAndConfirmVersion(curriculumVersion, curriculumVersionDto);
    }

    @DeleteMapping("/{curriculumId:\\d+}/versions/{id:\\d+}")
    public void deleteVersion(HoisUserDetails user, @WithEntity(value = "id") CurriculumVersion curriculumVersion) {
        curriculumValidationService.assertSameOrJoinSchool(user, curriculumVersion.getCurriculum());
        UserUtil.assertIsSchoolAdmin(user);
        curriculumValidationService.assertCurriculumVersionCanDeleted(curriculumVersion);
        curriculumService.deleteVersion(curriculumVersion);
    }

    @GetMapping("{id:\\d+}/possibleSubjects")
    public List<CurriculumVersionHigherModuleSubjectDto> getSubjects(@WithEntity(value = "id") Curriculum curriculum) {
        return curriculumService.getSubjects(curriculum);
    }

    @GetMapping("/versionHmoduleTypes")
    public List<ClassifierSelection> getCurriculumVersionHmoduleTypes(HoisUserDetails user) {
        /*
         * is_valid and is_higher is not considered at
         * autocompleteService.classifiers()
         */
        List<ClassifierSelection> classifiers = autocompleteService
                .classifiers(Collections.singletonList(MainClassCode.KORGMOODUL.name()));
        List<ClassifierSelection> otherTypes = curriculumService.getCurriculumVersionHmoduleTypes(user.getSchoolId());
        classifiers.addAll(otherTypes);
        return classifiers;
    }

    @PostMapping("/{curriculumId:\\d+}/module")
    public CurriculumModuleForm createCurriculumModule(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumModuleForm form, @WithEntity("curriculumId") Curriculum curriculum) {
        UserUtil.assertIsSchoolAdmin(user);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);
        return CurriculumModuleForm.of(curriculumService.createCurriculumModule(curriculum, form));
    }

    @PutMapping("/{curriculumId:\\d+}/module/{id:\\d+}")
    public CurriculumModuleForm updateCurriculumModule(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumModuleForm form, @WithEntity("curriculumId") Curriculum curriculum,
            @WithEntity("id") CurriculumModule module) {
        UserUtil.assertIsSchoolAdmin(user);
        UserUtil.assertSameSchool(user, curriculum.getSchool());
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);
        return CurriculumModuleForm.of(curriculumService.updateCurriculumModule(module, form));
    }

    @DeleteMapping("/{curriculumId:\\d+}/module/{id:\\d+}")
    public void deleteCurriculumModule(HoisUserDetails user, @WithEntity("id") CurriculumModule module, 
            @WithEntity("curriculumId") Curriculum curriculum) {
        UserUtil.assertIsSchoolAdmin(user);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);
        curriculumService.deleteModule(module);
    }

    @PutMapping("/module/{id:\\d+}")
    public CurriculumDto updateCurriculumModule(HoisUserDetails user, @NotNull @RequestBody CurriculumDto form,
            @WithEntity("id") Curriculum curriculum) {
        UserUtil.assertIsSchoolAdmin(user);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);
        return CurriculumDto.of(curriculumService.saveCurriculumModule(curriculum, form));
    }

    @Deprecated
    @PutMapping("/higher/version/modules/{id:\\d+}")
    public CurriculumVersionDto updateHigherCurriculumVersionModules(HoisUserDetails user,
            @NotNull @RequestBody CurriculumVersionDto form, @WithEntity("id") CurriculumVersion curriculumVersion) {
        UserUtil.assertIsSchoolAdmin(user);
        /*
         * This check caused error while adding subjects to joint curriculum
         */
        // UserUtil.assertSameSchool(user,
        // curriculumVersion.getCurriculum().getSchool());
        return CurriculumVersionDto.of(curriculumService.updateHigherCurriculumVersionModules(curriculumVersion, form));
    }

    @PostMapping("/version/{curriculumVersionId:\\d+}/higherModule")
    public CurriculumVersionHigherModuleDto createHigherCurriculumVersionModule(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumVersionHigherModuleForm form,
            @WithEntity("curriculumVersionId") CurriculumVersion curriculumVersion) {
        UserUtil.assertIsSchoolAdmin(user);
        /*
         * This check caused error while adding subjects to joint curriculum
         */
        // UserUtil.assertSameSchool(user,
        // curriculumVersion.getCurriculum().getSchool());
        curriculumValidationService.validateHigherModuleForm(form);
        return CurriculumVersionHigherModuleDto
                .of(curriculumService.createHigherCurriculumVersionModule(curriculumVersion, form));
    }

    @PutMapping("/version/{curriculumVersionId:\\d+}/higherModule/{id:\\d+}")
    public CurriculumVersionHigherModuleDto updateHigherCurriculumVersionModule(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumVersionHigherModuleForm form,
            @WithEntity("curriculumVersionId") CurriculumVersion curriculumVersion,
            @WithEntity("id") CurriculumVersionHigherModule module) {
        UserUtil.assertIsSchoolAdmin(user);
        /*
         * This check caused error while adding subjects to joint curriculum
         */
        // UserUtil.assertSameSchool(user,
        // curriculumVersion.getCurriculum().getSchool());
        curriculumValidationService.validateHigherModuleForm(form);
        return CurriculumVersionHigherModuleDto
                .of(curriculumService.updateHigherCurriculumVersionModule(module, curriculumVersion, form));
    }

    @DeleteMapping("/version/{curriculumVersionId:\\d+}/higherModule/{id:\\d+}")
    public void deleteHigherCurriculumVersionModule(HoisUserDetails user,
            @WithVersionedEntity(value = "id", versionRequestParam = "version") CurriculumVersionHigherModule module,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertIsSchoolAdmin(user);
        curriculumValidationService.assertCurriculumVersionCanBeEdited(module.getCurriculumVersion());
        curriculumService.deleteHigherCurriculumVersionModule(module);
    }

    @PostMapping("/implementationPlan/{versionId:\\d+}/module")
    public CurriculumVersionOccupationModuleDto createImplementationPlanModule(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumVersionOccupationModuleDto dto,
            @WithEntity("versionId") CurriculumVersion curriculumVersion) {
        UserUtil.assertIsSchoolAdmin(user);
        UserUtil.assertSameSchool(user, curriculumVersion.getCurriculum().getSchool());
        curriculumValidationService.assertCurriculumVersionCanBeEdited(curriculumVersion);
        return CurriculumVersionOccupationModuleDto
                .of(curriculumService.createImplementationPlanModule(curriculumVersion, dto));
    }

    @PutMapping("/implementationPlan/{versionId:\\d+}/module/{id:\\d+}")
    public CurriculumVersionOccupationModuleDto updateImplementationPlanModule(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumVersionOccupationModuleDto dto,
            @WithEntity("versionId") CurriculumVersion curriculumVersion,
            @WithEntity("id") CurriculumVersionOccupationModule occupationModule) {
        UserUtil.assertIsSchoolAdmin(user);
        UserUtil.assertSameSchool(user, curriculumVersion.getCurriculum().getSchool());
        curriculumValidationService.assertCurriculumVersionCanBeEdited(curriculumVersion);
        return CurriculumVersionOccupationModuleDto
                .of(curriculumService.updateImplementationPlanModule(occupationModule, dto));
    }

    @PostMapping("/{curriculumId:\\d+}/grade")
    public CurriculumGradeDto createCurriculumGrade(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumGradeDto form, @WithEntity("curriculumId") Curriculum curriculum) {
        UserUtil.assertIsSchoolAdmin(user);
        UserUtil.assertSameSchool(user, curriculum.getSchool());
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);
        return CurriculumGradeDto.of(curriculumService.createCurriculumGrade(curriculum, form));
    }

    @PutMapping("/{curriculumId:\\d+}/grade/{id:\\d+}")
    public CurriculumGradeDto updateCurriculumGrade(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumGradeDto form, @WithEntity("curriculumId") Curriculum curriculum,
            @WithEntity("id") CurriculumGrade grade) {
        UserUtil.assertIsSchoolAdmin(user);
        UserUtil.assertSameSchool(user, curriculum.getSchool());
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);
        return CurriculumGradeDto.of(curriculumService.updateCurriculumGrade(form, grade));
    }

    @DeleteMapping("/{curriculumId:\\d+}/grade/{id:\\d+}")
    public void deleteCurriculumGrade(HoisUserDetails user, @WithEntity("curriculumId") Curriculum curriculum,
            @WithVersionedEntity(value = "id", versionRequestParam = "version") CurriculumGrade grade,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertSameSchool(user, curriculum.getSchool());
        UserUtil.assertIsSchoolAdmin(user);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);
        curriculumService.deleteCurriculumGrade(grade);
    }

    @PostMapping("/{curriculumId:\\d+}/file")
    public CurriculumFileUpdateDto createCurriculumFile(HoisUserDetails user,
            @Valid @RequestBody CurriculumFileForm curriculumFileForm,
            @WithEntity("curriculumId") Curriculum curriculum) {
        UserUtil.assertIsSchoolAdmin(user);
        curriculumValidationService.assertSameOrJoinSchool(user, curriculum);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);
        return CurriculumFileUpdateDto.of(curriculumService.createCurriculumFile(curriculum, curriculumFileForm));
    }

    @DeleteMapping("/{curriculumId:\\d+}/file/{fileId:\\d+}")
    public void deleteCurriculumFile(HoisUserDetails user, @WithEntity("curriculumId") Curriculum curriculum,
            @WithEntity("fileId") CurriculumFile curriculumFile) {
        UserUtil.assertSameSchool(user, curriculum.getSchool());
        UserUtil.assertIsSchoolAdmin(user);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);
        curriculumService.deleteCurriculumFile(curriculumFile);
    }

    @PostMapping("/{curriculumId:\\d+}/speciality")
    public CurriculumSpecialityDto createCurriculumSpeciality(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumSpecialityDto dto,
            @WithEntity("curriculumId") Curriculum curriculum) {
        UserUtil.assertSameSchool(user, curriculum.getSchool());
        UserUtil.assertIsSchoolAdmin(user);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);
        return CurriculumSpecialityDto.of(curriculumService.createCurriculumSpeciality(curriculum, dto));
    }

    @PutMapping("/{curriculumId:\\d+}/speciality/{id:\\d+}")
    public CurriculumSpecialityDto updateCurriculumSpeciality(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumSpecialityDto dto, @WithEntity("id") CurriculumSpeciality speciality,
            @WithEntity("curriculumId") Curriculum curriculum) {
        UserUtil.assertSameSchool(user, curriculum.getSchool());
        UserUtil.assertIsSchoolAdmin(user);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);
        return CurriculumSpecialityDto.of(curriculumService.updateCurriculumSpeciality(speciality, dto));
    }

    @DeleteMapping("/{curriculumId:\\d+}/speciality/{id:\\d+}")
    public void deleteCurriculumSpeciality(HoisUserDetails user, @WithEntity("id") CurriculumSpeciality speciality,
            @WithEntity("curriculumId") Curriculum curriculum) {
        UserUtil.assertSameSchool(user, curriculum.getSchool());
        UserUtil.assertIsSchoolAdmin(user);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);
        curriculumService.deleteCurriculumSpeciality(speciality);
    }

}

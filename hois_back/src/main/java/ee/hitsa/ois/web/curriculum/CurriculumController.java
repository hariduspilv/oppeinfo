package ee.hitsa.ois.web.curriculum;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
import ee.hitsa.ois.domain.curriculum.CurriculumFile;
import ee.hitsa.ois.domain.curriculum.CurriculumGrade;
import ee.hitsa.ois.domain.curriculum.CurriculumSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.report.CurriculumReport;
import ee.hitsa.ois.service.PdfService;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.service.XmlService;
import ee.hitsa.ois.service.curriculum.CurriculumCopyService;
import ee.hitsa.ois.service.curriculum.CurriculumSearchService;
import ee.hitsa.ois.service.curriculum.CurriculumService;
import ee.hitsa.ois.service.curriculum.CurriculumValidationService;
import ee.hitsa.ois.service.curriculum.StateCurriculumCopyService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.StateCurriculumCopyCommand;
import ee.hitsa.ois.web.commandobject.UniqueCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumFileForm;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumForm;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumSchoolDepartmentCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumStudyLevelCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumFileUpdateDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumGradeDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSearchDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSpecialityDto;
import ee.hitsa.ois.xml.curriculum.CurriculumVersionXml;
import ee.hitsa.ois.xml.curriculum.CurriculumXml;

@RestController
@RequestMapping("curriculum")
public class CurriculumController {

    @Autowired
    private CurriculumService curriculumService;
    @Autowired
    private CurriculumSearchService curriculumSearchService;
    @Autowired
    private PdfService pdfService;
    @Autowired
    private XmlService xmlService;
    @Autowired
    private CurriculumValidationService curriculumValidationService;
    @Autowired
    private CurriculumCopyService curriculumCopyService;
    @Autowired
    private StateCurriculumCopyService stateCurriculumCopyService;
    @Autowired
    private SchoolService schoolService;

    @GetMapping("/{id:\\d+}")
    public CurriculumDto get(HoisUserDetails user, @WithEntity Curriculum curriculum) {
        CurriculumUtil.assertCanView(user, schoolService.getEhisSchool(user.getSchoolId()), curriculum);
        return curriculumService.get(user, curriculum);
    }

    @GetMapping("/xml/{id:\\d+}/curriculum.xml")
    public void curriculumXml(HoisUserDetails user, @WithEntity Curriculum curriculum, HttpServletResponse response)
            throws IOException, JAXBException {
        CurriculumUtil.assertCanView(user, schoolService.getEhisSchool(user.getSchoolId()), curriculum);
        HttpUtil.xml(response, curriculum.getCode() + ".xml",
                xmlService.generateFromObject(CurriculumXml.of(curriculum)));
    }
    
    @GetMapping("/xml/{id:\\d+}/curriculum.version.xml")
    public void curriculumVersionXml(HoisUserDetails user, @WithEntity CurriculumVersion curriculumVersion, HttpServletResponse response)
            throws IOException, JAXBException {
        CurriculumUtil.assertCanView(user, schoolService.getEhisSchool(user.getSchoolId()), curriculumVersion.getCurriculum());
        HttpUtil.xml(response, curriculumVersion.getCode() + ".xml",
                xmlService.generateFromObject(CurriculumVersionXml.get(curriculumVersion)));
    }

    @GetMapping("/print/{id:\\d+}/curriculum.pdf")
    public void print(HoisUserDetails user, @WithEntity CurriculumVersion curriculumVersion, HttpServletResponse response) throws IOException {
        CurriculumUtil.assertCanView(user, schoolService.getEhisSchool(user.getSchoolId()), curriculumVersion.getCurriculum());
        HttpUtil.pdf(response, curriculumVersion.getCode() + ".pdf",
                pdfService.generate(CurriculumReport.TEMPLATE_NAME, new CurriculumReport(curriculumVersion)));
    }

    @GetMapping
    public Page<CurriculumSearchDto> search(HoisUserDetails user, CurriculumSearchCommand curriculumSearchCommand,
            Pageable pageable) {
        return curriculumSearchService.search(user, curriculumSearchCommand, pageable);
    }

    @PostMapping
    public CurriculumDto create(HoisUserDetails user, @Valid @RequestBody CurriculumForm curriculumForm) {

        CurriculumUtil.assertCanCreate(user);
        curriculumValidationService.validateCreateCurriculumForm(curriculumForm);
        curriculumValidationService.assertCodeAndMerCodeAreUnique(user, curriculumForm, null);
        
        return CurriculumDto.onlyId(curriculumService.create(user, curriculumForm));
    }
    
    @PutMapping("/copy/curriculum/{id:\\d+}")
    public CurriculumDto copyCurriculum(HoisUserDetails user, @WithEntity Curriculum curriculum) {
        CurriculumUtil.assertCanCreate(user);
        return CurriculumDto.onlyId(curriculumCopyService.copyCurriculum(user, curriculum));
    }
    
    @PutMapping("/copy/statecurriculum")
    public CurriculumDto copyStateCurriculum(HoisUserDetails user, @Valid @RequestBody StateCurriculumCopyCommand command) {
        CurriculumUtil.assertCanCreate(user);
        return CurriculumDto.onlyId(stateCurriculumCopyService.copyStateCurriculum(user, command));
    }

    @PutMapping("/{id:\\d+}")
    public CurriculumDto save(HoisUserDetails user, @NotNull @Valid @RequestBody CurriculumForm curriculumForm,
            @WithEntity Curriculum curriculum) {
        CurriculumUtil.assertCanChange(user, schoolService.getEhisSchool(user.getSchoolId()), curriculum);

        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);
        curriculumValidationService.validateCurriculumFormWithStatusCheck(curriculum, curriculumForm);
        curriculumValidationService.assertCodeAndMerCodeAreUnique(user, curriculumForm, curriculum);

        return get(user, curriculumService.save(user, curriculum, curriculumForm));
    }

    @PutMapping("/close/{id:\\d+}")
    public CurriculumDto closeCurriculum(HoisUserDetails user, @WithEntity Curriculum curriculum) {
        CurriculumUtil.assertCanClose(user, schoolService.getEhisSchool(user.getSchoolId()), curriculum);
        return get(user, curriculumService.closeCurriculum(curriculum));
    }

    @PutMapping("/saveAndProceed/{id:\\d+}")
    public CurriculumDto saveAndProceedCurriculum(HoisUserDetails user, @WithEntity Curriculum curriculum,
            @NotNull @Valid @RequestBody CurriculumForm curriculumForm) {

        String myEhisSchool = schoolService.getEhisSchool(user.getSchoolId());
        CurriculumUtil.assertCanChange(user, myEhisSchool, curriculum);
        CurriculumUtil.assertCanConfirm(user, myEhisSchool, curriculum);

        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);
        curriculumValidationService.validateCurriculumForm(curriculum, curriculumForm);
        curriculumValidationService.assertCodeAndMerCodeAreUnique(user, curriculumForm, curriculum);

        return get(user, curriculumService.saveAndProceedCurriculum(user, curriculum, curriculumForm));
    }

    @PutMapping("/sendToEhis/{id:\\d+}")
    public CurriculumDto sendToEhis(HoisUserDetails user, @WithEntity Curriculum curriculum) {
        
        CurriculumUtil.assertCanConfirm(user, schoolService.getEhisSchool(user.getSchoolId()), curriculum);
        curriculumValidationService.validateCurriculum(curriculum);
        
        return get(user, curriculumService.sendToEhis(user, curriculum));
    }

    @PutMapping("/updateFromEhis/{id:\\d+}")
    public CurriculumDto updateFromEhis(HoisUserDetails user, @WithEntity Curriculum curriculum) {

        CurriculumUtil.assertCanConfirm(user, schoolService.getEhisSchool(user.getSchoolId()), curriculum);
        curriculumValidationService.validateCurriculum(curriculum);
        
        return get(user, curriculumService.updateFromEhis(user, curriculum));
    }

    @GetMapping("/unique/code")
    public boolean isCodeUnique(HoisUserDetails user, UniqueCommand command) {
        return curriculumService.isCodeUnique(user.getSchoolId(), command);
    }

    @GetMapping("/unique/merCode")
    public boolean isMerCodeUnique(UniqueCommand command) {
        return curriculumService.isMerCodeUnique(command);
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithEntity Curriculum curriculum) {
        CurriculumUtil.assertCanDelete(user, schoolService.getEhisSchool(user.getSchoolId()), curriculum);
        curriculumValidationService.assertCurriculumCanBeDeleted(curriculum);

        curriculumService.delete(user, curriculum);
    }

    @GetMapping("/areasOfStudyByGroupOfStudy/{code}")
    public List<String> getAreasOfStudyByGroupOfStudy(@NotNull @PathVariable("code") String code) {
        return StreamUtil.toMappedList(EntityUtil::getCode, curriculumService.getAreasOfStudyByGroupOfStudy(code));
    }

    @PostMapping("/{curriculumId:\\d+}/grade")
    public CurriculumGradeDto createCurriculumGrade(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumGradeDto form, @WithEntity("curriculumId") Curriculum curriculum) {

        CurriculumUtil.assertCanChange(user, schoolService.getEhisSchool(user.getSchoolId()), curriculum);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);
        
        return CurriculumGradeDto.of(curriculumService.createCurriculumGrade(curriculum, form));
    }

    @PutMapping("/{curriculumId:\\d+}/grade/{id:\\d+}")
    public CurriculumGradeDto updateCurriculumGrade(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumGradeDto form, @WithEntity("curriculumId") Curriculum curriculum,
            @WithEntity CurriculumGrade grade) {
        
        CurriculumUtil.assertCanChange(user, schoolService.getEhisSchool(user.getSchoolId()), curriculum);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);
        
        return CurriculumGradeDto.of(curriculumService.updateCurriculumGrade(form, grade));
    }

    @DeleteMapping("/{curriculumId:\\d+}/grade/{id:\\d+}")
    public void deleteCurriculumGrade(HoisUserDetails user, @WithEntity("curriculumId") Curriculum curriculum,
            @WithVersionedEntity(versionRequestParam = "version") CurriculumGrade grade,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {

        CurriculumUtil.assertCanChange(user, schoolService.getEhisSchool(user.getSchoolId()), curriculum);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);

        curriculumService.deleteCurriculumGrade(user, grade);
    }

    @PostMapping("/{curriculumId:\\d+}/file")
    public CurriculumFileUpdateDto createCurriculumFile(HoisUserDetails user,
            @Valid @RequestBody CurriculumFileForm curriculumFileForm,
            @WithEntity("curriculumId") Curriculum curriculum) {

        CurriculumUtil.assertCanChange(user, schoolService.getEhisSchool(user.getSchoolId()), curriculum);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);

        return CurriculumFileUpdateDto.of(curriculumService.createCurriculumFile(curriculum, curriculumFileForm));
    }

    @DeleteMapping("/{curriculumId:\\d+}/file/{fileId:\\d+}")
    public void deleteCurriculumFile(HoisUserDetails user, @WithEntity("curriculumId") Curriculum curriculum,
            @WithEntity("fileId") CurriculumFile curriculumFile) {

        CurriculumUtil.assertCanChange(user, schoolService.getEhisSchool(user.getSchoolId()), curriculum);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);

        curriculumService.deleteCurriculumFile(user, curriculumFile);
    }

    @PostMapping("/{curriculumId:\\d+}/speciality")
    public CurriculumSpecialityDto createCurriculumSpeciality(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumSpecialityDto dto,
            @WithEntity("curriculumId") Curriculum curriculum) {
        
        CurriculumUtil.assertCanChange(user, schoolService.getEhisSchool(user.getSchoolId()), curriculum);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);

        return CurriculumSpecialityDto.of(curriculumService.createCurriculumSpeciality(curriculum, dto));
    }

    @PutMapping("/{curriculumId:\\d+}/speciality/{id:\\d+}")
    public CurriculumSpecialityDto updateCurriculumSpeciality(HoisUserDetails user,
            @NotNull @Valid @RequestBody CurriculumSpecialityDto dto, @WithEntity CurriculumSpeciality speciality,
            @WithEntity("curriculumId") Curriculum curriculum) {
        
        CurriculumUtil.assertCanChange(user, schoolService.getEhisSchool(user.getSchoolId()), curriculum);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);

        return CurriculumSpecialityDto.of(curriculumService.updateCurriculumSpeciality(speciality, dto));
    }

    @DeleteMapping("/{curriculumId:\\d+}/speciality/{id:\\d+}")
    public void deleteCurriculumSpeciality(HoisUserDetails user, @WithEntity CurriculumSpeciality speciality,
            @WithEntity("curriculumId") Curriculum curriculum) {

        CurriculumUtil.assertCanChange(user, schoolService.getEhisSchool(user.getSchoolId()), curriculum);
        curriculumValidationService.assertCurriculumCanBeEdited(curriculum);

        curriculumService.deleteCurriculumSpeciality(user, speciality);
    }

    /**
     * Getting school departments on initial page load on new curriculum form 
     * and on changing joint partners
     */
    @GetMapping("/schoolDepartments")
    public List<AutocompleteResult> getSchoolDepartments(HoisUserDetails user, @Valid CurriculumSchoolDepartmentCommand command) {
        AssertionFailedException.throwIf(user.getSchoolId() == null && command.getCurriculum() == null, "no school and curriculum!");
        return curriculumService.getSchoolDepartments(user.getSchoolId(), command);
    }
    
    /**
     * Getting school departments on initial page load
     */
    @GetMapping("/schoolDepartments/{curriculumId:\\d+}")
    public List<AutocompleteResult> getSchoolDepartments(@WithEntity("curriculumId") Curriculum curriculum) {
        return curriculumService.getSchoolDepartments(curriculum);
    }

    @GetMapping("/studyLevels")
    public List<String> getStudyLevels(HoisUserDetails user, @Valid CurriculumStudyLevelCommand command) {
        AssertionFailedException.throwIf(user.getSchoolId() == null && command.getCurriculum() == null, "no school and curriculum!");
        return curriculumService.getStudyLevels(user.getSchoolId(), command);
    }

    @GetMapping("/canView")
    public Map<String, ?> canView(HoisUserDetails user) {
        return Collections.singletonMap("canView", Boolean.valueOf(CurriculumUtil.canView(user)));
    }
    
    @GetMapping("/canCreate")
    public Map<String, ?> canCreate(HoisUserDetails user) {
        return Collections.singletonMap("canCreate", Boolean.valueOf(CurriculumUtil.canCreate(user)));
    }
}

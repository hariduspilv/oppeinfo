package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.service.GradingSchemaService;
import ee.hitsa.ois.web.dto.gradingSchema.GradingSchemaDto;
import ee.hitsa.ois.web.dto.timetable.SchoolPublicDataSettingsDto;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgram;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.CurriculumVersionStatus;
import ee.hitsa.ois.report.SubjectProgramReport;
import ee.hitsa.ois.report.curriculum.CurriculumModulesReport;
import ee.hitsa.ois.report.curriculum.CurriculumVersionModulesReport;
import ee.hitsa.ois.report.curriculum.CurriculumVersionReport;
import ee.hitsa.ois.service.AutocompleteService;
import ee.hitsa.ois.service.ClassifierService;
import ee.hitsa.ois.service.PdfService;
import ee.hitsa.ois.service.PublicDataService;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.service.StateCurriculumService;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CryptoUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.SubjectProgramUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.SchoolCapacityTypeCommand;
import ee.hitsa.ois.web.commandobject.StateCurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.StudyYearScheduleDtoContainer;
import ee.hitsa.ois.web.commandobject.StudyYearScheduleForm;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.subject.SubjectSearchCommand;
import ee.hitsa.ois.web.dto.AcademicCalendarDto;
import ee.hitsa.ois.web.dto.ClassifierDto;
import ee.hitsa.ois.web.dto.SchoolDepartmentResult;
import ee.hitsa.ois.web.dto.StateCurriculumDto;
import ee.hitsa.ois.web.dto.StateCurriculumSearchDto;
import ee.hitsa.ois.web.dto.StudyPeriodWithWeeksDto;
import ee.hitsa.ois.web.dto.StudyYearDto;
import ee.hitsa.ois.web.dto.SubjectDto;
import ee.hitsa.ois.web.dto.SubjectSearchDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSearchDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionResult;
import ee.hitsa.ois.web.dto.student.StudentGroupSearchDto;
import ee.hitsa.ois.web.dto.studymaterial.JournalDto;
import ee.hitsa.ois.web.dto.studymaterial.StudyMaterialSearchDto;
import ee.hitsa.ois.web.dto.studymaterial.SubjectStudyPeriodDto;

@RestController
@RequestMapping("/public")
public class PublicDataController {

    @Autowired
    private PublicDataService publicDataService;
    @Autowired
    private StateCurriculumService stateCurriculumService;
    @Autowired
    private PdfService pdfService;
    @Autowired
    private SubjectProgramUtil subjectProgramUtil;
    @Autowired
    private ClassifierService classifierService;
    @Autowired
    private AutocompleteService autocompleteService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private GradingSchemaService gradingSchemaService;
    @Value("${hois.frontend.baseUrl}")
    private String frontendBaseUrl;
    @Value("${file.cypher.key}")
    private String encryptionKey;

    @GetMapping("/schoolSettings")
    public SchoolPublicDataSettingsDto schoolSettings(Long schoolId) {
        return publicDataService.schoolPublicSettings(schoolId);
    }

    @GetMapping("/gradingSchemas")
    public List<GradingSchemaDto> gradingSchemas(Long schoolId, @RequestParam String type) {
        return gradingSchemaService.typeSchemas(schoolId, type);
    }

    @GetMapping("/academicCalendar/{schoolId:\\d+}")
    public AcademicCalendarDto academicCalendar(@PathVariable("schoolId") Long schoolId) {
        return publicDataService.academicCalendar(schoolId);
    }
    
    @GetMapping("/academicCalendar/{schoolId:\\d+}/{studyYearId:\\d+}")
    public AcademicCalendarDto academicCalendar(@WithEntity("schoolId") School school, @WithEntity("studyYearId") StudyYear studyYear) {
        return publicDataService.academicCalendar(school, studyYear);
    }

    @GetMapping("/curriculum/{id:\\d+}")
    public Object curriculum(@PathVariable("id") Long id) {
        return publicDataService.curriculum(id);
    }

    @GetMapping("/curriculum/{curriculum:\\d+}/{id:\\d+}")
    public Object curriculumVersion(@PathVariable("curriculum") Long curriculum, @PathVariable("id") Long id) {
        return publicDataService.curriculumVersion(curriculum, id);
    }
    
    @GetMapping("/{id:\\d+}/schoolCapacityTypes")
    public List<ClassifierDto> schoolCapacityTypes(@WithEntity Curriculum curriculum, @Valid SchoolCapacityTypeCommand lookup) {
        return autocompleteService.schoolCapacityTypeDtos(EntityUtil.getId(curriculum.getSchool()), lookup);
    }

    @GetMapping("/print/curriculum/{id:\\d+}/general.pdf")
    public void printCurriculumGeneral(@WithEntity Curriculum curriculum, HttpServletResponse response) throws IOException {
        UserUtil.throwAccessDeniedIf(!ClassifierUtil.equals(CurriculumStatus.OPPEKAVA_STAATUS_K, curriculum.getStatus())
                || !Boolean.FALSE.equals(curriculum.getHigher()));
        HttpUtil.pdf(response, "curriculum.pdf",
                pdfService.generate(ee.hitsa.ois.report.curriculum.CurriculumReport.VOCATIONAL_TEMPLATE_NAME, 
                        new ee.hitsa.ois.report.curriculum.CurriculumReport(curriculum, frontendBaseUrl)));
    }

    @GetMapping("/print/curriculum/{id:\\d+}/modules.pdf")
    public void printCurriculumModules(@WithEntity Curriculum curriculum, HttpServletResponse response) throws IOException {
        UserUtil.throwAccessDeniedIf(!ClassifierUtil.equals(CurriculumStatus.OPPEKAVA_STAATUS_K, curriculum.getStatus())
                || !Boolean.FALSE.equals(curriculum.getHigher()));
        HttpUtil.pdf(response, "curriculum_modules.pdf",
                pdfService.generate(CurriculumModulesReport.VOCATIONAL_TEMPLATE_NAME, new CurriculumModulesReport(curriculum)));
    }
    
    @GetMapping("/print/curriculumVersion/{id:\\d+}/general.pdf")
    public void printCurriculumVersion(@WithEntity CurriculumVersion curriculumVersion, HttpServletResponse response) throws IOException {
        UserUtil.throwAccessDeniedIf(!ClassifierUtil.equals(CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_K, curriculumVersion.getStatus())
                || !Boolean.FALSE.equals(curriculumVersion.getCurriculum().getHigher()));
        HttpUtil.pdf(response, "curriculum_version.pdf",
                pdfService.generate(CurriculumVersionReport.VOCATIONAL_TEMPLATE_NAME, new CurriculumVersionReport(curriculumVersion)));
    }

    @GetMapping("/print/curriculumVersion/{id:\\d+}/modules.pdf")
    public void printCurriculumVersionModules(@WithEntity CurriculumVersion curriculumVersion, HttpServletResponse response) throws IOException {
        UserUtil.throwAccessDeniedIf(!ClassifierUtil.equals(CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_K, curriculumVersion.getStatus())
                || !Boolean.FALSE.equals(curriculumVersion.getCurriculum().getHigher()));
        CurriculumVersionModulesReport report = new CurriculumVersionModulesReport(curriculumVersion);
        report.setIsHigherSchool(Boolean.valueOf(schoolService.schoolType(EntityUtil.getId(curriculumVersion.getCurriculum().getSchool())).isHigher()));
        HttpUtil.pdf(response, "curriculum_version_modules.pdf",
                pdfService.generate(CurriculumVersionModulesReport.VOCATIONAL_TEMPLATE_NAME, report));
    }

    @GetMapping("/subject/{id:\\d+}")
    public Object subject(@PathVariable("id") Long id) {
        return publicDataService.subject(id);
    }
    
    @GetMapping("/subjectProgram/{id:\\d+}")
    public Object subjectProgram(@WithEntity SubjectProgram program) {
        subjectProgramUtil.assertCanView(null, program);
        return publicDataService.subjectProgram(program);
    }
    
    @GetMapping("/print/subjectProgram/{id:\\d+}/program.pdf")
    public void print(@WithEntity SubjectProgram program, HttpServletResponse response) throws IOException {
        subjectProgramUtil.assertCanView(null, program);
        HttpUtil.pdf(response, "subject_program_" + program.getSubjectStudyPeriodTeacher().getSubjectStudyPeriod().getSubject().getCode() + ".pdf",
                pdfService.generate(SubjectProgramReport.TEMPLATE_NAME, new SubjectProgramReport(program, new ClassifierUtil.ClassifierCache(classifierService))));
    }

    @GetMapping("/curriculumsearch")
    public Page<CurriculumSearchDto> curriculumSearch(CurriculumSearchCommand curriculumSearchCommand,
            Pageable pageable) {
        return publicDataService.curriculumSearch(curriculumSearchCommand, pageable);
    }

    @GetMapping("/statecurriculumsearch")
    public Page<StateCurriculumSearchDto> stateCurriculumSearch(StateCurriculumSearchCommand curriculumSearchCommand,
            Pageable pageable) {
        return stateCurriculumService.search(null, curriculumSearchCommand, pageable);
    }

    @GetMapping("/statecurriculum/{id:\\d+}")
    public StateCurriculumDto stateCurriculum(@WithEntity StateCurriculum stateCurriculum) {
        return publicDataService.stateCurriculum(stateCurriculum);
    }

    @GetMapping("/schooldepartments")
    public List<SchoolDepartmentResult> schoolDepartments(Long schoolId) {
        return publicDataService.schoolDepartments(schoolId);
    }

    @GetMapping("/curriculumversions")
    public List<CurriculumVersionResult> curriculumVersions(Long schoolId) {
        return publicDataService.curriculumVersions(schoolId);
    }
    
    @GetMapping("/subjectsearch")
    public Page<SubjectSearchDto> search(@Valid SubjectSearchCommand subjectSearchCommand, Pageable pageable) {
        return publicDataService.searchSubjects(subjectSearchCommand, pageable);
    }

    @GetMapping("/subject/view/{id:\\d+}")
    public SubjectDto subjectView(@WithEntity Subject subject) {
        return publicDataService.subjectView(subject);
    }
    
    @GetMapping("/studyMaterial/journal/{id:\\d+}")
    public JournalDto journal(@WithEntity Journal journal) {
        return publicDataService.journal(journal);
    }

    @GetMapping("/studyMaterial/journal/{id:\\d+}/materials")
    public List<StudyMaterialSearchDto> journalMaterials(@WithEntity Journal journal) {
        return publicDataService.journalMaterials(journal);
    }

    @GetMapping("/studyMaterial/subjectStudyPeriod/{id:\\d+}")
    public SubjectStudyPeriodDto subjectStudyPeriod(@WithEntity SubjectStudyPeriod subjectStudyPeriod) {
        return publicDataService.subjectStudyPeriod(subjectStudyPeriod);
    }

    @GetMapping("/studyMaterial/subjectStudyPeriod/{id:\\d+}/materials")
    public List<StudyMaterialSearchDto> subjectStudyPeriodMaterials(@WithEntity SubjectStudyPeriod subjectStudyPeriod) {
        return publicDataService.subjectStudyPeriodMaterials(subjectStudyPeriod);
    }
    
    @GetMapping("/studyYearScheduleLegends")
    public Map<String, ?> studyYearScheduleLegends(String schoolId) {
        String decryptedSchoolIdString = decrypt(schoolId);
        Long decryptedSchoolId = null;
        if (decryptedSchoolIdString == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("failedDecrypt", Boolean.TRUE);
            return response;
        }
        try {
            decryptedSchoolId = Long.valueOf(decryptedSchoolIdString);
        } catch (@SuppressWarnings("unused") NumberFormatException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("failedDecrypt", Boolean.TRUE);
            return response;
        }
        return publicDataService.getStudyYearScheduleLegends(decryptedSchoolId);
    }
    
    @GetMapping("studyYearSchedule/studentGroups")
    public List<StudentGroupSearchDto> getStudyYearScheduleStudentGroups(String schoolId) {
        String decryptedSchoolIdString = decrypt(schoolId);
        if (decryptedSchoolIdString == null) return null;
        Long decryptedSchoolId = Long.valueOf(decryptedSchoolIdString);
        return publicDataService.getStudyYearScheduleStudentGroups(decryptedSchoolId);
    }
    
    @GetMapping("/studyYears")
    public List<StudyYearDto> getStudyYearsWithStudyPeriods(String schoolId) {
        String decryptedSchoolIdString = decrypt(schoolId);
        if (decryptedSchoolIdString == null) return null;
        Long decryptedSchoolId = Long.valueOf(decryptedSchoolIdString);
        return publicDataService.getStudyYearsWithStudyPeriods(decryptedSchoolId);
    }
    
    @PostMapping("/studyYearSchedule/{id}")
    public StudyYearScheduleDtoContainer getStudyYearSchedules(@PathVariable("id") String schoolId, @NotNull @Valid @RequestBody StudyYearScheduleDtoContainer schedulesCmd) {
        // user can select school department with no student groups, and it should not cause an error
        String decryptedSchoolIdString = decrypt(schoolId);
        if (decryptedSchoolIdString == null) {
            return null;
        }
        Long decryptedSchoolId = Long.valueOf(decryptedSchoolIdString);
        if(!CollectionUtils.isEmpty(schedulesCmd.getStudyPeriods())) {
            schedulesCmd.setStudyYearSchedules(publicDataService.getStudyYearSchedule(decryptedSchoolId, schedulesCmd));
        }
        return schedulesCmd;
    }
    
    @GetMapping("/studyYearSchedule/studyYearPeriods/{studyYearId:\\d+}")
    public List<StudyPeriodWithWeeksDto> getStudyYearPeriods(@WithEntity("studyYearId") StudyYear studyYear) {
        return publicDataService.getStudyYearPeriods(studyYear);
    }
    
    @GetMapping("/studyYearSchedule/schooldepartments")
    public List<SchoolDepartmentResult> studyYearScheduleSchoolDepartments(String schoolId) {
        String decryptedSchoolIdString = decrypt(schoolId);
        if (decryptedSchoolIdString == null) return null;
        Long decryptedSchoolId = Long.valueOf(decryptedSchoolIdString);
        return publicDataService.studyYearScheduleSchoolDepartments(decryptedSchoolId);
    }
    
    @GetMapping("/{id}/studyYearSchedule.xlsx")
    public void studyYearScheduleAsExcel(@PathVariable("id") String schoolId, @NotNull @Valid StudyYearScheduleForm schedulesCmd, 
            HttpServletResponse response) throws IOException {
        String decryptedSchoolIdString = decrypt(schoolId);
        if (decryptedSchoolIdString == null) return;
        Long decryptedSchoolId = Long.valueOf(decryptedSchoolIdString);
        HttpUtil.xls(response, "studyyearschedule.xlsx", publicDataService.studyYearScheduleAsExcel(decryptedSchoolId, schedulesCmd));
    }
    
    @GetMapping("/{id}/studyYearSchedule.pdf")
    public void studyYearScheduleAsPdf(@PathVariable("id") String schoolId, @NotNull @Valid StudyYearScheduleForm schedulesCmd, 
            HttpServletResponse response) throws IOException {
        String decryptedSchoolIdString = decrypt(schoolId);
        if (decryptedSchoolIdString == null) return;
        Long decryptedSchoolId = Long.valueOf(decryptedSchoolIdString);
        HttpUtil.pdf(response, "studyyearschedule.pdf", publicDataService.studyYearScheduleAsPdf(decryptedSchoolId, schedulesCmd));
    }
    
    private String decrypt(String encryptedSchoolId) {
        return CryptoUtil.decrypt(encryptionKey, Base64.decodeBase64(encryptedSchoolId));
    }
    

}

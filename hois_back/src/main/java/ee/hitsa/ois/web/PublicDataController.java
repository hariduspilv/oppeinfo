package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
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
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.SubjectProgramUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.SchoolCapacityTypeCommand;
import ee.hitsa.ois.web.commandobject.StateCurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.subject.SubjectSearchCommand;
import ee.hitsa.ois.web.dto.AcademicCalendarDto;
import ee.hitsa.ois.web.dto.ClassifierDto;
import ee.hitsa.ois.web.dto.SchoolDepartmentResult;
import ee.hitsa.ois.web.dto.StateCurriculumDto;
import ee.hitsa.ois.web.dto.StateCurriculumSearchDto;
import ee.hitsa.ois.web.dto.SubjectDto;
import ee.hitsa.ois.web.dto.SubjectSearchDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSearchDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionResult;
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
    @Value("${hois.frontend.baseUrl}")
    private String frontendBaseUrl;

    @GetMapping("/academicCalendar/{schoolId:\\d+}")
    public AcademicCalendarDto academicCalendar(@PathVariable("schoolId") Long schoolId) {
        return publicDataService.academicCalendar(schoolId);
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

}

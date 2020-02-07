package ee.hitsa.ois.web;

import static ee.hitsa.ois.util.UserUtil.assertIsSchoolAdmin;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.concurrent.AsyncManager;
import ee.hitsa.ois.concurrent.AsyncMemoryManager;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentAbsence;
import ee.hitsa.ois.domain.student.StudentSupportService;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.report.SupportServicesReport;
import ee.hitsa.ois.service.ApplicationService;
import ee.hitsa.ois.service.PdfService;
import ee.hitsa.ois.service.StudentResultCardService;
import ee.hitsa.ois.service.StudentResultHigherService;
import ee.hitsa.ois.service.StudentService;
import ee.hitsa.ois.service.SupportServiceService;
import ee.hitsa.ois.service.ehis.EhisStudentService;
import ee.hitsa.ois.service.ehis.EhisStudentService.ExportStudentsRequest;
import ee.hitsa.ois.service.rr.PopulationRegisterService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.StudentAbsenceUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.ehis.EhisStudentForm;
import ee.hitsa.ois.web.commandobject.student.StudentAbsenceForm;
import ee.hitsa.ois.web.commandobject.student.StudentForm;
import ee.hitsa.ois.web.commandobject.student.StudentModuleListChangeForm;
import ee.hitsa.ois.web.commandobject.student.StudentResultCardForm;
import ee.hitsa.ois.web.commandobject.student.StudentSearchCommand;
import ee.hitsa.ois.web.commandobject.student.StudentSpecialitySearchCommand;
import ee.hitsa.ois.web.commandobject.student.StudentSupportServiceForm;
import ee.hitsa.ois.web.commandobject.student.StudentSupportServicePrintCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.EhisStudentExportRequestDto;
import ee.hitsa.ois.web.dto.FutureStatusResponse;
import ee.hitsa.ois.web.dto.StudentSupportServiceDto;
import ee.hitsa.ois.web.dto.student.StudentAbsenceDto;
import ee.hitsa.ois.web.dto.student.StudentApplicationDto;
import ee.hitsa.ois.web.dto.student.StudentDirectiveDto;
import ee.hitsa.ois.web.dto.student.StudentForeignstudyDto;
import ee.hitsa.ois.web.dto.student.StudentHigherResultDto;
import ee.hitsa.ois.web.dto.student.StudentModuleResultDto;
import ee.hitsa.ois.web.dto.student.StudentPracticeContractDto;
import ee.hitsa.ois.web.dto.student.StudentResultCardDto;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;
import ee.hitsa.ois.web.dto.student.StudentSpecialitySearchDto;
import ee.hitsa.ois.web.dto.student.StudentViewDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalConnectedEntity;
import ee.hitsa.ois.web.dto.student.StudentVocationalResultByTimeDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalResultDto;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private EhisStudentService ehisStudentService;
    @Autowired
    private StudentResultHigherService studentResultHigherService;
    @Autowired
    private StudentResultCardService studentResultCardService;
    @Autowired
    private PopulationRegisterService rrService;
    @Autowired
    private SupportServiceService supportServiceService;
    @Autowired
    private PdfService pdfService;
    @Autowired
    private EntityManager em;
    @Autowired
    private AsyncManager asyncManager;

    @GetMapping
    public Page<StudentSearchDto> search(HoisUserDetails user, @Valid StudentSearchCommand criteria,
            Pageable pageable) {
        UserUtil.throwAccessDeniedIf(user.isStudent(), "Students cannot search other students");
        return studentService.search(user, criteria, pageable);
    }

    /**
     * 
     * @param user
     * @param criteria
     * @param pageable
     * @return
     */
    @GetMapping("/highspecialities")
    public Page<StudentSpecialitySearchDto> search(HoisUserDetails user, @Valid StudentSpecialitySearchCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user);
        return studentService.search(user, criteria, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public StudentViewDto get(HoisUserDetails user, @WithEntity Student student) {
        UserUtil.assertCanViewStudent(user, student);
        return studentService.getStudentView(user, student);
    }

    @PutMapping("/{id:\\d+}")
    public StudentViewDto save(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) Student student, @Valid @RequestBody StudentForm form) {
        UserUtil.throwAccessDeniedIf(!UserUtil.canEditStudent(user, student), "User cannot edit student data");
        return get(user, studentService.save(user, student, form));
    }

    @GetMapping("/{id:\\d+}/absences")
    public Page<StudentAbsenceDto> absences(HoisUserDetails user, @WithEntity Student student, Pageable pageable) {
        UserUtil.assertCanViewStudentSpecificData(user, student);
        return studentService.absences(user, student, pageable);
    }

    @PostMapping("/{studentId:\\d+}/absences")
    public void createAbsence(HoisUserDetails user, @WithEntity("studentId") Student student, @Valid @RequestBody StudentAbsenceForm form) {
        StudentAbsenceUtil.assertCanCreate(user, student);
        studentService.create(user, student, form);
    }
    
    @GetMapping("/{studentId:\\d+}/canCreateAbsence")
    public Map<String, Object> canCreateAbsence(HoisUserDetails user, @WithEntity("studentId") Student student) {
        HashMap<String, Object> result = new HashMap<>();
        boolean canCreate = StudentAbsenceUtil.canCreate(user, student);
        result.put("canCreate", Boolean.valueOf(canCreate));
        if (canCreate) {
            result.put("studentName", student.getPerson().getFullname());
            if (student.getStudentGroup() != null) {
                result.put("studentGroup", student.getStudentGroup().getCode());
            }
        }
        return result;
    }

    @PutMapping("/{studentId:\\d+}/absences/{id:\\d+}")
    public void saveAbsence(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) StudentAbsence absence, @Valid @RequestBody StudentAbsenceForm form) {
        StudentAbsenceUtil.assertCanEdit(user, absence);
        studentService.save(absence, form);
    }

    @DeleteMapping("/{studentId:\\d+}/absences/{id:\\d+}")
    public void deleteAbsence(HoisUserDetails user, @WithVersionedEntity(versionRequestParam = "version") StudentAbsence absence, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        StudentAbsenceUtil.assertCanEdit(user, absence);
        studentService.delete(user, absence);
    }

    /**
     * Load whole documents tab of student with single request.
     *
     * @param user
     * @param student
     * @return
     */
    @GetMapping("/{id:\\d+}/documents")
    public Map<String, Object> loadDocumentsPage(HoisUserDetails user, @WithEntity Student student) {
        Map<String, Object> result = new HashMap<>();
        int pagesize = 5;
        // TODO correct sorting
        result.put("applications", applications(user, student, new PageRequest(0, pagesize, new Sort(
                new Sort.Order(Direction.DESC, "inserted")
        ))));
        if(user.isStudent()) {
            result.put("applicationTypesApplicable", applicationService.applicableApplicationTypes(student));
        }
        result.put("directives", directives(user, student, new PageRequest(0, pagesize, null, "confirm_date, headline")));
        result.put("practiceContracts", practiceContracts(user, student, new PageRequest(0, pagesize, null, "contract_nr")));
        Map<String, Object> studentDto = new HashMap<>();
        studentDto.put("isVocational", Boolean.valueOf(StudentUtil.isVocational(student)));
        studentDto.put("status", EntityUtil.getCode(student.getStatus()));
        result.put("student", studentDto);
        return result;
    }

    @GetMapping("/{id:\\d+}/applications")
    public Page<StudentApplicationDto> applications(HoisUserDetails user, @WithEntity Student student, Pageable pageable) {
        UserUtil.assertCanViewStudentSpecificData(user, student);
        return studentService.applications(EntityUtil.getId(student), pageable);
    }

    @GetMapping("/{id:\\d+}/directives")
    public Page<StudentDirectiveDto> directives(HoisUserDetails user, @WithEntity Student student, Pageable pageable) {
        UserUtil.assertCanViewStudentSpecificData(user, student);
        return studentService.directives(user, student, pageable);
    }
    
    @GetMapping("/{id:\\d+}/practicecontracts")
    public Page<StudentPracticeContractDto> practiceContracts(HoisUserDetails user, @WithEntity Student student, Pageable pageable) {
        UserUtil.assertCanViewStudentSpecificData(user, student);
        return studentService.practiceContracts(user, student, pageable);
    }

    @GetMapping("/{id:\\d+}/foreignstudies")
    public Page<StudentForeignstudyDto> foreignstudies(HoisUserDetails user, @WithEntity Student student, Pageable pageable) {
        UserUtil.assertCanViewStudent(user, student);
        return studentService.foreignstudies(user, student, pageable);
    }

    @GetMapping("/{id:\\d+}/subjects")
    public List<AutocompleteResult> subjects(HoisUserDetails user, @WithEntity Student student) {
        UserUtil.assertCanViewStudent(user, student);
        return studentService.subjects(student);
    }
    
    @GetMapping("/{id:\\d+}/specialities")
    public List<AutocompleteResult> specialities(HoisUserDetails user, @WithEntity Student student) {
        UserUtil.assertCanViewStudentSpecificData(user, student);
        return studentService.specialities(student);
    }

    @GetMapping("/{id:\\d+}/supportservices")
    public Page<StudentSupportServiceDto> supportServices(HoisUserDetails user, @WithEntity Student student, Pageable pageable) {
        UserUtil.assertCanViewStudentSupportServices(user, student);
        if (user.isTeacher() && !UserUtil.isStudentGroupTeacher(user, student)) {
            return studentService.supportServices(student, pageable, false);
        }
        if (user.isSchoolAdmin() && !UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_TUGITEENUS)) {
            return studentService.supportServices(student, pageable, false);
        }
        return studentService.supportServices(student, pageable, true);
    }

    @GetMapping("/{id:\\d+}/supportservices/print.pdf")
    public void supportServices(HoisUserDetails user, HttpServletResponse response, @RequestParam(required = false) Language lang,
            @WithEntity Student student, @Valid StudentSupportServicePrintCommand cmd) throws IOException {
        UserUtil.assertCanViewPrivateStudentSupportServices(user, student);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMYYYY");
        String fileName = String.format("%s_%s_%s_%s.pdf", "tugiteenuste_valjavote", student.getPerson().getFullname(), formatter.format(cmd.getFrom()),
                formatter.format(cmd.getThru())).replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
        HttpUtil.pdf(response, fileName, pdfService.generate(SupportServicesReport.TEMPLATE_NAME,
                new SupportServicesReport(student, cmd.getFrom(), cmd.getThru(),
                        studentService.supportServicesList(student, true, cmd.getFrom(), cmd.getThru()),
                        em, lang == null ? Language.ET : lang)));
    }
    
    @GetMapping("/{id:\\d+}/supportservice/{serviceId:\\d+}")
    public StudentSupportServiceDto getSupportService(HoisUserDetails user, @WithEntity Student student, @WithEntity("serviceId") StudentSupportService service) {
        UserUtil.assertCanViewStudentSupportServices(user, student, service);
        return supportServiceService.get(service);
    }
    
    @PostMapping("/{id:\\d+}/supportservice")
    public StudentSupportServiceDto createSupportService(HoisUserDetails user, @WithEntity Student student, @RequestBody @Valid StudentSupportServiceForm form) {
        UserUtil.assertCanEditStudentSupportServices(user, student);
        return supportServiceService.get(supportServiceService.create(student, form));
    }

    @PutMapping("/{id:\\d+}/supportservice/{serviceId:\\d+}")
    public StudentSupportServiceDto updateSupportService(HoisUserDetails user, @WithEntity Student student,
            @WithEntity("serviceId") StudentSupportService service, @RequestBody @Valid StudentSupportServiceForm form) {
        UserUtil.assertCanEditStudentSupportServices(user, student);
        return supportServiceService.get(supportServiceService.update(service, form));
    }
    
    @DeleteMapping("/{id:\\d+}/supportservice/{serviceId:\\d+}")
    public void deleteSupportService(HoisUserDetails user, @WithEntity Student student, @WithEntity("serviceId") StudentSupportService service) {
        UserUtil.assertCanEditStudentSupportServices(user, student);
        supportServiceService.delete(service);
    }

    @PostMapping("/ehisStudentExport")
    public Map<String, Object> ehisStudentExport(HoisUserDetails user, @Valid @RequestBody EhisStudentForm ehisStudentForm) {
        assertIsSchoolAdmin(user);
        String requestHash = asyncManager.generateKey(user);
        ExportStudentsRequest request = ehisStudentService.createRequest(user, requestHash, ehisStudentForm);
        asyncManager.createRequest(user, AsyncMemoryManager.EHIS_STUDENT, requestHash, request);
        asyncManager.processRequest(request);
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", requestHash);
        return map;
    }
    
    @GetMapping("/ehisStudentExportCheck")
    public EhisStudentExportRequestDto ehisStudentExportCheck(HoisUserDetails user, @Valid EhisStudentForm ehisStudentForm) {
        assertIsSchoolAdmin(user);
        Optional<ExportStudentsRequest> overlappedRequest = ehisStudentService.findOverlappedActiveExportStudentsRequest(user, ehisStudentForm);
        return overlappedRequest.isPresent() && !overlappedRequest.get().isDone() ? EhisStudentExportRequestDto.of(overlappedRequest.get()) : null;
    }

    @GetMapping("/ehisStudentExportStatus")
    public FutureStatusResponse ehisStudentExportStatus(HoisUserDetails user, @RequestParam(required = true) String key) {
        assertIsSchoolAdmin(user);
        return asyncManager.getState(user, AsyncMemoryManager.EHIS_STUDENT, key, true);
    }

    @GetMapping("/{id:\\d+}/vocationalResults")
    public StudentVocationalResultDto vocationalResults(HoisUserDetails user, @WithEntity Student student) {
        UserUtil.assertCanViewStudentSpecificData(user, student);
        return studentService.vocationalResults(student);
    }
    
    @GetMapping("/{id:\\d+}/vocationalResultsByTime")
    public Collection<StudentVocationalResultByTimeDto> vocationalResultsByTime(HoisUserDetails user, @WithEntity Student student) {
        UserUtil.assertCanViewStudentSpecificData(user, student);
        return studentService.vocationalResultsByTimeResults(student);
    }

    @GetMapping("/{id:\\d+}/higherResults")
    public StudentHigherResultDto higherResults(HoisUserDetails user, @WithEntity Student student) {
        UserUtil.assertCanViewStudentSpecificData(user, student);
        return studentResultHigherService.higherResults(student);
    }

    @GetMapping("/{id:\\d+}/vocationalConnectedEntities")
    public List<StudentVocationalConnectedEntity> vocationalConnectedEntities(HoisUserDetails user, @WithEntity Student student) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacherOrStudentGroupTeacher(user, student);
        return studentService.vocationalConnectedEntities(student.getId());
    }

    @GetMapping("/{id:\\d+}/higherChangeableModules")
    public List<StudentModuleResultDto> higherChangeableModules(HoisUserDetails user, @WithEntity Student student) {
        UserUtil.canChangeStudentModules(user, student);
        return studentResultHigherService.higherChangeableModules(student);
    }

    @GetMapping("/{id:\\d+}/higherCurriculumModules")
    public List<AutocompleteResult> higherCurriculumModulesForSelection(HoisUserDetails user,
            @WithEntity Student student) {
        UserUtil.canChangeStudentModules(user, student);
        return studentResultHigherService.higherCurriculumModulesForSelection(student);
    }

    @PostMapping("/{id:\\d+}/changeHigherCurriculumModules")
    public List<StudentModuleResultDto> changeHigherCurriculumModules(HoisUserDetails user, @WithEntity Student student,
            @Valid @RequestBody StudentModuleListChangeForm form) {
        UserUtil.canChangeStudentModules(user, student);
        studentResultHigherService.changeHigherCurriculumVersionModules(student, form);
        return higherChangeableModules(user, student);
    }

    @PostMapping("/highspecialities")
    public List<StudentSpecialitySearchDto> saveSpecialities(HoisUserDetails user, @RequestBody List<StudentSpecialitySearchDto> form) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user);
        return studentService.saveSpecialities(user, form);
    }

    @GetMapping("/{id:\\d+}/studentResultCard")
    public StudentResultCardDto studentResultCard(HoisUserDetails user, @WithEntity Student student) {
        Long studentId = EntityUtil.getId(student);
        studentResultCardService.assertIsAllowedToSeeStudentResultCard(user, Arrays.asList(studentId));
        return studentResultCardService.studentResultCard(studentId);
    }

    @GetMapping("/studentResultCards.pdf")
    public void studentResultCardPdf(HoisUserDetails user, StudentResultCardForm form, HttpServletResponse response)
            throws IOException {
        studentResultCardService.assertIsAllowedToSeeStudentResultCard(user, form.getStudentIds());
        HttpUtil.pdf(response, "student_result_card.pdf",
                studentResultCardService.studentResultCardsPrint(user.getSchoolId(), form));
    }

    @GetMapping("/{id:\\d+}/populationRegister")
    public void updateStudentPersonalData(HoisUserDetails user, @WithEntity Student student) {
        UserUtil.assertCanUpdateStudentRR(user, student);
        Throwable error = rrService.updatePersonData(PopulationRegisterService.generateRequest(student.getPerson()));
        if (error != null) {
            throw new HoisException(error.getMessage(), error);
        }
    }
    
    /**
     * Used for testing mail service
     */
    @PutMapping("/testGuestJob")
    public void testGuestJob() {
        studentService.endGuestStudent();
    }
}

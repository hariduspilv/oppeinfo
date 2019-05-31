package ee.hitsa.ois.web;

import static ee.hitsa.ois.util.UserUtil.assertIsSchoolAdmin;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentAbsence;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.service.ApplicationService;
import ee.hitsa.ois.service.StudentResultCardService;
import ee.hitsa.ois.service.StudentResultHigherService;
import ee.hitsa.ois.service.StudentService;
import ee.hitsa.ois.service.ehis.EhisStudentService;
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
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.EhisStudentReport;
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

    @GetMapping
    public Page<StudentSearchDto> search(HoisUserDetails user, @Valid StudentSearchCommand criteria, Pageable pageable) {
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
        UserUtil.assertIsSchoolAdmin(user);
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

    @PostMapping("/ehisStudentExport")
    public List<? extends EhisStudentReport> ehisStudentExport(HoisUserDetails user, @Valid @RequestBody EhisStudentForm ehisStudentForm) {
        assertIsSchoolAdmin(user);
        return ehisStudentService.exportStudents(user.getSchoolId(), ehisStudentForm);
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
        UserUtil.assertIsSchoolAdminOrStudentGroupTeacher(user, student);
        return studentService.vocationalConnectedEntities(student.getId());
    }

    @GetMapping("/{id:\\d+}/higherChangeableModules")
    public List<StudentModuleResultDto> higherChangeableModules(HoisUserDetails user, @WithEntity Student student) {
        assertIsSchoolAdmin(user, student.getSchool());
        return studentService.higherChangeableModules(student.getId());
    }

    @GetMapping("/{id:\\d+}/higherCurriculumModules")
    public List<AutocompleteResult> higherCurriculumModulesForSelection(HoisUserDetails user, @WithEntity Student student) {
        assertIsSchoolAdmin(user, student.getSchool());
        return studentService.higherCurriculumModulesForSelection(student.getCurriculumVersion().getId());
    }

    @PostMapping("/{id:\\d+}/changeHigherCurriculumModules")
    public List<StudentModuleResultDto> changeHigherCurriculumModules(HoisUserDetails user, @WithEntity Student student, @Valid @RequestBody StudentModuleListChangeForm form) {
        if(!StudentUtil.isActive(student) || !UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPUR)) {
            throw new AssertionFailedException("User cannot edit student data");
        }
        studentService.changeHigherCurriculumVersionModules(student, form);
        return higherChangeableModules(user, student);
    }

    @PostMapping("/highspecialities")
    public List<StudentSpecialitySearchDto> saveSpecialities(HoisUserDetails user, @RequestBody List<StudentSpecialitySearchDto> form) {
        assertIsSchoolAdmin(user);
        return studentService.saveSpecialities(user, form);
    }

    @GetMapping("/{id:\\d+}/studentResultCard")
    public StudentResultCardDto studentResultCard(HoisUserDetails user, @WithEntity Student student) {
        assertIsSchoolAdmin(user, student.getSchool());
        return studentResultCardService.studentResultCard(EntityUtil.getId(student));
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
}

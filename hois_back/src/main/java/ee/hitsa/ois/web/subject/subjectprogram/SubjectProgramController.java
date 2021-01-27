package ee.hitsa.ois.web.subject.subjectprogram;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.util.PersonUtil;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgram;
import ee.hitsa.ois.report.SubjectProgramReport;
import ee.hitsa.ois.service.ClassifierService;
import ee.hitsa.ois.service.PdfService;
import ee.hitsa.ois.service.SubjectProgramService;
import ee.hitsa.ois.service.SubjectService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.SubjectProgramValidation;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.SearchCommand;
import ee.hitsa.ois.web.commandobject.subject.subjectprogram.SubjectProgramForm;
import ee.hitsa.ois.web.commandobject.subject.subjectprogram.SubjectProgramSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectDto;
import ee.hitsa.ois.web.dto.subject.subjectprogram.SubjectProgramDto;
import ee.hitsa.ois.web.dto.subject.subjectprogram.SubjectProgramSearchDto;

@RestController
@RequestMapping("/subject/subjectProgram")
public class SubjectProgramController {
    
    @Autowired
    private SubjectProgramService service;
    @Autowired
    private SubjectProgramValidation validation;
    @Autowired
    private EntityManager em;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private PdfService pdfService;
    @Autowired
    private ClassifierService classifierService;
    @Value("${hois.subject.program.lock}")
    private Integer lockTime;

    
    @GetMapping("/myPrograms")
    public Page<SubjectProgramSearchDto> searchMyPrograms(HoisUserDetails user, SubjectProgramSearchCommand cmd, Pageable pageable) {
        validation.assertCanSearch(user);
        return service.searchMyPrograms(user, cmd, pageable);
    }
    
    @GetMapping()
    public Page<SubjectProgramSearchDto> search(HoisUserDetails user, SubjectProgramSearchCommand cmd, Pageable pageable) {
        validation.assertCanSearch(user);
        return service.search(user, cmd, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public SubjectProgramDto get(HoisUserDetails user, @WithEntity SubjectProgram program) {
        validation.assertCanView(user, program);
        SubjectProgramDto dto = service.get(program);
        if (user.isSchoolAdmin() || user.isTeacher() || user.isLeadingTeacher()) {
            dto.setCanEdit(validation.canEdit(user, program));
            if (program.getAccessedUserId() != null &&
                    !user.getUserId().equals(program.getAccessedUserId()) &&
                    program.getAccessed().isAfter(LocalDateTime.now().minusMinutes(lockTime))) {
                dto.setLocked(program.getAccessed());
                dto.setLockedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(program.getAccessedBy()));
            }
        }
        return dto;
    }

    @GetMapping("/checkLock/{id:\\d+}")
    public Map<String, Object> checkLockProgram(HoisUserDetails user, @WithEntity SubjectProgram program) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacherOrTeacher(user);
        Map<String, Object> result = new HashMap<>();
        if (program.getAccessedUserId() != null &&
                !user.getUserId().equals(program.getAccessedUserId()) &&
                program.getAccessed().isAfter(LocalDateTime.now().minusMinutes(lockTime))) {
            result.put("locked", program.getAccessed());
            result.put("lockedBy", PersonUtil.stripIdcodeFromFullnameAndIdcode(program.getAccessedBy()));
        }
        return result;
    }

    @GetMapping("/lock/{id:\\d+}")
    public Map<String, Object> lockProgram(HoisUserDetails user, @WithEntity SubjectProgram program) {
        validation.assertCanEdit(user, program);
        Map<String, Object> result = new HashMap<>();
        result.put("version", service.lockProgram(user, program).getVersion());
        return result;
    }

    @GetMapping("/unlock/{id:\\d+}")
    public Map<String, Object> unlockProgram(HoisUserDetails user, @WithEntity SubjectProgram program) {
        validation.assertCanEdit(user, program);
        Map<String, Object> result = new HashMap<>();
        result.put("version", service.unlockProgram(user, program).getVersion());
        return result;
    }
    
    @GetMapping("/subject/{id:\\d+}")
    public SubjectDto get(HoisUserDetails user, @WithEntity Subject subject, @RequestParam(value="program", required=false) Long programId) {
        if (programId != null) {
            SubjectProgram program = em.getReference(SubjectProgram.class, programId);
            validation.hasConnection(program, subject);
            validation.assertCanView(user, program);
        } else {
            validation.assertCanSearch(user);
            UserUtil.assertSameSchool(user, subject.getSchool());
        }
        return subjectService.get(user, subject);
    }
    
    @PostMapping()
    public SubjectProgramDto create(HoisUserDetails user, @NotNull @Valid @RequestBody SubjectProgramForm form) {
        validation.assertCanCreate(user);
        return service.get(service.create(user, form));
    }
    
    @PutMapping("/{id:\\d+}")
    public SubjectProgramDto save(HoisUserDetails user,
                                  @WithEntity SubjectProgram program,
                                  @NotNull @Valid @RequestBody SubjectProgramForm form) {
        validation.assertCanEdit(user, program);
        return service.get(service.save(user, program, form));
    }
    
    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithEntity SubjectProgram program) {
        validation.assertCanDelete(user, program);
        service.delete(user, program);
    }
    
    @GetMapping("/teacher/{id:\\d+}")
    public Set<AutocompleteResult> getTeacherSubjectPrograms(HoisUserDetails user, @WithEntity Subject subject) {
        validation.assertCanSearch(user);
        return service.getProgramsRelatedToTeacher(user, subject);
    }
    
    @GetMapping("/program/subjects")
    public Set<AutocompleteResult> getSubjectsViaPrograms(HoisUserDetails user, @RequestParam(value="teacher", required=false) Long teacherId, SearchCommand lookup) {
        validation.assertCanSearch(user);
        return service.getSubjectsViaPrograms(user, teacherId, lookup);
    }
    
    @GetMapping("/curriculum/subjects")
    public Set<AutocompleteResult> getSubjectsViaCurriculums(HoisUserDetails user, SearchCommand lookup) {
        validation.assertCanSearch(user);
        return service.getSubjectsViaCurriculums(user, lookup);
    }
    
    @GetMapping("/complete/{id:\\d+}")
    public SubjectProgramDto complete(HoisUserDetails user, @WithEntity SubjectProgram program) {
        validation.assertCanComplete(user, program);
        return service.get(service.complete(user, program));
    }
    
    @GetMapping("/confirm/{id:\\d+}")
    public SubjectProgramDto confirm(HoisUserDetails user, @WithEntity SubjectProgram program) {
        validation.assertCanConfirm(user, program);
        return service.get(service.confirm(user, program));
    }
    
    @PostMapping("/reject/{id:\\d+}")
    public SubjectProgramDto reject(HoisUserDetails user, @WithEntity SubjectProgram program, @RequestBody @NotBlank String rejectInfo) {
        validation.assertCanReject(user, program);
        return service.get(service.reject(user, program, rejectInfo));
    }
    
    @GetMapping("/print/{id:\\d+}/program.pdf")
    public void print(HoisUserDetails user, @WithEntity SubjectProgram program, HttpServletResponse response) throws IOException {
        validation.assertCanView(user, program);
        HttpUtil.pdf(response, "subject_program_" + program.getSubjectStudyPeriod().getSubject().getCode() + ".pdf",
                pdfService.generate(SubjectProgramReport.TEMPLATE_NAME, new SubjectProgramReport(program, new ClassifierUtil.ClassifierCache(classifierService))));
    }

    @GetMapping("/hasunconfirmed")
    public SimpleEntry<String, Boolean> hasUnconfirmedSubjectPrograms(HoisUserDetails user) {
        return service.hasUnconfirmedSubjectPrograms(user);
    }
    
    @GetMapping("/hasuncompleted")
    public SimpleEntry<String, Boolean> hasUncompletedSubjectPrograms(HoisUserDetails user) {
        return service.hasUncompletedSubjectPrograms(user);
    }

    @GetMapping("/additionalData/{id:\\d+}")
    public Map<String, Object> additionalData(HoisUserDetails user,
                                              @WithEntity SubjectStudyPeriod ssp,
                                              @RequestParam(name = "joint", required = false) boolean joint,
                                              @RequestParam(name = "teacher", required = false) Long teacherId) {
        return service.getAdditionalData(user, ssp, joint, teacherId);
    }
}

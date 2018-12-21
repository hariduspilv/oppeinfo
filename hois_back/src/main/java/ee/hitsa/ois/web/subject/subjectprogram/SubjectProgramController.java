package ee.hitsa.ois.web.subject.subjectprogram;

import java.io.IOException;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgram;
import ee.hitsa.ois.report.SubjectProgramReport;
import ee.hitsa.ois.service.PdfService;
import ee.hitsa.ois.service.SubjectProgramService;
import ee.hitsa.ois.service.SubjectService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.SubjectProgramUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.SubjectProgramForm;
import ee.hitsa.ois.web.commandobject.SubjectProgramSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectDto;
import ee.hitsa.ois.web.dto.SubjectProgramDto;
import ee.hitsa.ois.web.dto.SubjectProgramSearchDto;

@RestController
@RequestMapping("/subject/subjectProgram")
public class SubjectProgramController {
    
    @Autowired
    private SubjectProgramService service;
    @Autowired
    private SubjectProgramUtil util;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private PdfService pdfService;
    
    @GetMapping()
    public Page<SubjectProgramSearchDto> search(HoisUserDetails user, SubjectProgramSearchCommand cmd, Pageable pageable) {
        util.assertCanSearch(user);
        return service.search(user, cmd, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public SubjectProgramDto get(HoisUserDetails user, @WithEntity SubjectProgram program) {
        util.assertCanView(user, program);
        return service.get(program);
    }
    
    @GetMapping("/subject/{id:\\d+}")
    public SubjectDto get(HoisUserDetails user, @WithEntity Subject subject) {
        util.assertCanSearch(user);
        UserUtil.isSameSchool(user, subject.getSchool());
        return subjectService.get(user, subject);
    }
    
    @PostMapping()
    public SubjectProgramDto create(HoisUserDetails user, @NotNull @Valid @RequestBody SubjectProgramForm form) {
        util.assertCanCreate(user);
        return service.get(service.create(user, form));
    }
    
    @PutMapping("/{id:\\d+}")
    public SubjectProgramDto save(HoisUserDetails user, @WithEntity SubjectProgram program, @NotNull @Valid @RequestBody SubjectProgramForm form) {
        util.assertCanEdit(user, program);
        return service.get(service.save(user, program, form));
    }
    
    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithEntity SubjectProgram program) {
        util.assertCanDelete(user, program);
        service.delete(program);
    }
    
    @GetMapping("/teacher/{id:\\d+}")
    public Set<AutocompleteResult> getTeacherSubjectPrograms(HoisUserDetails user, @WithEntity Subject subject) {
        util.assertCanSearch(user);
        return service.getProgramsRelatedToTeacher(user, subject);
    }
    
    @GetMapping("/subjects")
    public Set<AutocompleteResult> getSubjects(HoisUserDetails user) {
        util.assertCanSearch(user);
        return service.getSubjects(user);
    }
    
    @GetMapping("/complete/{id:\\d+}")
    public SubjectProgramDto complete(HoisUserDetails user, @WithEntity SubjectProgram program) {
        util.assertCanComplete(user, program);
        return service.get(service.complete(user, program));
    }
    
    @GetMapping("/confirm/{id:\\d+}")
    public SubjectProgramDto confirm(HoisUserDetails user, @WithEntity SubjectProgram program) {
        util.assertCanConfirm(user, program);
        return service.get(service.confirm(user, program));
    }
    
    @PostMapping("/reject/{id:\\d+}")
    public SubjectProgramDto reject(HoisUserDetails user, @WithEntity SubjectProgram program, @RequestBody @NotBlank String rejectInfo) {
        util.assertCanReject(user, program);
        return service.get(service.reject(user, program, rejectInfo));
    }
    
    @GetMapping("/print/{id:\\d+}/program.pdf")
    public void print(HoisUserDetails user, @WithEntity SubjectProgram program, HttpServletResponse response) throws IOException {
        util.assertCanView(user, program);
        HttpUtil.pdf(response, "subject_program_" + program.getSubjectStudyPeriodTeacher().getSubjectStudyPeriod().getSubject().getCode() + ".pdf", pdfService.generate(SubjectProgramReport.TEMPLATE_NAME, new SubjectProgramReport(program)));
    }
    
    @GetMapping("/hasunconfirmed")
    public SimpleEntry<String, Boolean> hasUnconfirmedSubjectPrograms(HoisUserDetails user) {
        return service.hasUnconfirmedSubjectPrograms(user);
    }
    
    @GetMapping("/hasuncompleted")
    public SimpleEntry<String, Boolean> hasUncompletedSubjectPrograms(HoisUserDetails user) {
        return service.hasUncompletedSubjectPrograms(user);
    }
}
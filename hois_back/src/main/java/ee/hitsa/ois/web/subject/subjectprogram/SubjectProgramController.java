package ee.hitsa.ois.web.subject.subjectprogram;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgram;
import ee.hitsa.ois.service.SubjectProgramService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.SubjectProgramUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.SubjectProgramForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectProgramDto;

@RestController
@RequestMapping("/subject/subjectProgram")
public class SubjectProgramController {
    
    @Autowired
    private SubjectProgramService service;

    @GetMapping("/{id:\\d+}")
    public SubjectProgramDto get(HoisUserDetails user, @WithEntity SubjectProgram program) {
        SubjectProgramUtil.assertCanView(user, program);
        return service.get(program);
    }
    
    @PostMapping()
    public SubjectProgramDto create(HoisUserDetails user, @NotNull @Valid @RequestBody SubjectProgramForm form) {
        SubjectProgramUtil.assertCanCreate(user);
        return service.get(service.create(user, form));
    }
    
    @PutMapping("/{id:\\d+}")
    public SubjectProgramDto save(HoisUserDetails user, @WithEntity SubjectProgram program, @NotNull @Valid @RequestBody SubjectProgramForm form) {
        SubjectProgramUtil.assertCanEdit(user, program);
        return service.get(service.save(user, program, form));
    }
    
    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithEntity SubjectProgram program) {
        SubjectProgramUtil.assertCanDelete(user, program);
        service.delete(program);
    }
    
    @GetMapping("/teacher/{id:\\d+}")
    public Set<AutocompleteResult> getTeacherSubjectPrograms(HoisUserDetails user, @WithEntity Subject subject) {
        SubjectProgramUtil.assertCanSearch(user);
        return service.getProgramsRelatedToTeacher(user, subject);
    }
    
    @GetMapping("/complete/{id:\\d+}")
    public SubjectProgramDto complete(HoisUserDetails user, @WithEntity SubjectProgram program) {
        SubjectProgramUtil.assertCanComplete(user, program);
        return service.get(service.complete(user, program));
    }
    
    @GetMapping("/confirm/{id:\\d+}")
    public SubjectProgramDto confirm(HoisUserDetails user, @WithEntity SubjectProgram program) {
        SubjectProgramUtil.assertCanConfirm(user, program);
        return service.get(service.confirm(user, program));
    }
}

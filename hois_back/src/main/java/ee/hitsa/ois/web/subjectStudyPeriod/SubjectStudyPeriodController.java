package ee.hitsa.ois.web.subjectStudyPeriod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.subjectstudyperiod.SubjectStudyPeriodSearchService;
import ee.hitsa.ois.service.subjectstudyperiod.SubjectStudyPeriodService;
import ee.hitsa.ois.util.MoodleUtil;
import ee.hitsa.ois.util.SubjectStudyPeriodUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodForm;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodSearchDto;

@RestController
@RequestMapping("/subjectStudyPeriods")
public class SubjectStudyPeriodController {

    @Autowired
    private SubjectStudyPeriodService subjectStudyPeriodService;
    @Autowired
    private SubjectStudyPeriodSearchService subjectStudyPeriodSearchService;
    
    @GetMapping
    public Page<SubjectStudyPeriodSearchDto> search(HoisUserDetails user, SubjectStudyPeriodSearchCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return subjectStudyPeriodSearchService.search(user, criteria, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public SubjectStudyPeriodDto get(HoisUserDetails user, @WithEntity SubjectStudyPeriod subjectStudyPeriod) {
        UserUtil.assertIsSchoolAdminOrTeacher(user, subjectStudyPeriod.getSubject().getSchool());
        return SubjectStudyPeriodDto.of(subjectStudyPeriod);
    }

    @PostMapping
    public SubjectStudyPeriodDto create(@Valid @RequestBody SubjectStudyPeriodForm form, HoisUserDetails user, 
            HttpServletRequest request) {
        UserUtil.assertIsSchoolAdmin(user);
        return get(user, subjectStudyPeriodService.create(form, MoodleUtil.createContext(user, request)));
    }

    @PutMapping("/{id:\\d+}")
    public SubjectStudyPeriodDto update(
            @WithVersionedEntity(versionRequestBody = true) SubjectStudyPeriod subjectStudyPeriod, 
            @Valid @RequestBody SubjectStudyPeriodForm form, HoisUserDetails user, HttpServletRequest request) {
        SubjectStudyPeriodUtil.assertCanUpdate(user, subjectStudyPeriod);
        return get(user, subjectStudyPeriodService.update(subjectStudyPeriod, form,
                MoodleUtil.createContext(user, request, subjectStudyPeriod)));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(versionRequestParam = "version") 
    SubjectStudyPeriod subjectStudyPeriod, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        SubjectStudyPeriodUtil.assertCanUpdate(user, subjectStudyPeriod);
        subjectStudyPeriodService.delete(user, subjectStudyPeriod);
    }
    
    @GetMapping("/teacher/{id:\\d+}")
    public AutocompleteResult getTeacher(HoisUserDetails user, @WithEntity Teacher teacher) {
        UserUtil.assertIsSchoolAdminOrTeacher(user, teacher.getSchool());
        return AutocompleteResult.of(teacher); 
    }
    
    @GetMapping("/subject/{id:\\d+}")
    public AutocompleteResult getSubject(HoisUserDetails user, @WithEntity Subject subject) {
        UserUtil.assertIsSchoolAdminOrTeacher(user, subject.getSchool());
        return AutocompleteResult.of(subject); 
    }
    
    @GetMapping("/studyPeriod/{id:\\d+}")
    public AutocompleteResult getStudyPeriod(HoisUserDetails user, @WithEntity StudyPeriod studyPeriod) {
        UserUtil.assertIsSchoolAdminOrTeacher(user, studyPeriod.getStudyYear().getSchool());
        return AutocompleteResult.of(studyPeriod); 
    }
}

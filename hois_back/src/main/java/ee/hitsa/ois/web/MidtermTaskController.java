package ee.hitsa.ois.web;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.service.MidtermTaskService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.MidtermTaskUpdateForm;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodMidtermTaskForm;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodMidtermTaskDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodSearchDto;

@RestController
@RequestMapping("/midtermTasks")
public class MidtermTaskController {

    @Autowired
    private MidtermTaskService midtermTaskService;

    @GetMapping("/{id:\\d+}")
    public MidtermTaskUpdateForm getMidtermTasks(HoisUserDetails user, 
            @WithEntity("id") SubjectStudyPeriod subjectStudyPeriod) {
        MidtermTaskUpdateForm form = MidtermTaskUpdateForm.of(subjectStudyPeriod);
        form.setCanBeEdited(Boolean.valueOf(canBeEdited(user, subjectStudyPeriod)));
        return form;
    }

    @PutMapping("/{id:\\d+}")
    public MidtermTaskUpdateForm updateMidtermTasks(HoisUserDetails user, 
            @WithEntity("id") SubjectStudyPeriod subjectStudyPeriod, 
            @NotNull @Valid @RequestBody MidtermTaskUpdateForm form) {
        AssertionFailedException.throwIf(!canBeEdited(user, subjectStudyPeriod),
                "You cannot change midtermTasks!");
        midtermTaskService.updateMidtermTasks(subjectStudyPeriod, form);
        return getMidtermTasks(user, subjectStudyPeriod);
    }

    @GetMapping("/studentResults/{id:\\d+}")
    public SubjectStudyPeriodMidtermTaskDto getStudentsResults(HoisUserDetails user, 
            @WithEntity("id") SubjectStudyPeriod subjectStudyPeriod) {
        return null;
    }

    @PutMapping("/studentResults/{id:\\d+}")
    public SubjectStudyPeriodMidtermTaskDto updateStudentsResults(HoisUserDetails user, 
            @WithEntity("id") SubjectStudyPeriod subjectStudyPeriod, 
            @NotNull @Valid @RequestBody SubjectStudyPeriodMidtermTaskForm form) {
        midtermTaskService.updateStudentsResults(form);
        return getStudentsResults(user, null);
    }
    
    
    @GetMapping("/subjectStudyPeriods")
    public Page<SubjectStudyPeriodSearchDto> searchSubjectStudyPeriods(HoisUserDetails user, SubjectStudyPeriodSearchCommand criteria, Pageable pageable) {
        return midtermTaskService.searchSubjectStudyPeriods(criteria, pageable);
    }
    
    @PutMapping("/{id:\\d+}/subjectStudyPeriodCopy/{copiedId:\\d+}")
    public MidtermTaskUpdateForm copyMidtermTasks(HoisUserDetails user, 
            @WithEntity("id") SubjectStudyPeriod subjectStudyPeriod, 
            @WithEntity("copiedId") SubjectStudyPeriod copiedSubjectStudyPeriod) {
        midtermTaskService.copyMidtermTasks(subjectStudyPeriod, copiedSubjectStudyPeriod);
        return getMidtermTasks(user, subjectStudyPeriod);
    }
    
    private static boolean canBeEdited(HoisUserDetails user, SubjectStudyPeriod subjectStudyPeriod) {
        return  LocalDate.now().isBefore(subjectStudyPeriod.getStudyPeriod().getEndDate()) && (
                user.isSchoolAdmin() || user.isTeacher());
    }
}

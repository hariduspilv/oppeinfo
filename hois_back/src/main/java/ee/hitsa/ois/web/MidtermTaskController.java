package ee.hitsa.ois.web;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.service.MidtermTaskService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.MidtermTaskUtil;
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
        MidtermTaskUtil.checkUserRights(user, subjectStudyPeriod);
        MidtermTaskUpdateForm form = MidtermTaskUpdateForm.of(subjectStudyPeriod);
        form.setCanBeEdited(Boolean.valueOf(MidtermTaskUtil.midtermTaskCanBeEdited(user, subjectStudyPeriod)));
        return form;
    }

    @PutMapping("/{id:\\d+}")
    public MidtermTaskUpdateForm updateMidtermTasks(HoisUserDetails user, 
            @WithEntity("id") SubjectStudyPeriod subjectStudyPeriod, 
            @NotNull @Valid @RequestBody MidtermTaskUpdateForm form) {
        MidtermTaskUtil.checkUserRights(user, subjectStudyPeriod);
        MidtermTaskUtil.checkIfMidtermTasksCanBeEdited(user, subjectStudyPeriod);
        midtermTaskService.updateMidtermTasks(subjectStudyPeriod, form);
        return getMidtermTasks(user, subjectStudyPeriod);
    }
    
    @GetMapping("/subjectStudyPeriods/{id:\\d+}")
    public Page<SubjectStudyPeriodSearchDto> searchSubjectStudyPeriods
    (HoisUserDetails user, SubjectStudyPeriodSearchCommand criteria, Pageable pageable, 
            @NotNull @PathVariable("id") Long subjectStudyPeriodId) {
        Long teachersPersonId = null;
        if(user.isTeacher()) {
            teachersPersonId = user.getPersonId();
        }
        return midtermTaskService.searchSubjectStudyPeriods(subjectStudyPeriodId, criteria, pageable, teachersPersonId);
    }
    
    @PutMapping("/{id:\\d+}/subjectStudyPeriodCopy/{copiedId:\\d+}")
    public MidtermTaskUpdateForm copyMidtermTasks(HoisUserDetails user, 
            @WithEntity("id") SubjectStudyPeriod subjectStudyPeriod, 
            @WithEntity("copiedId") SubjectStudyPeriod copiedSubjectStudyPeriod) {
        MidtermTaskUtil.checkUserRights(user, subjectStudyPeriod);
        MidtermTaskUtil.checkIfMidtermTasksCanBeEdited(user, subjectStudyPeriod);
        return MidtermTaskUpdateForm.ofWithCopiedMidtermTasks
                (midtermTaskService.copyMidtermTasks(subjectStudyPeriod, copiedSubjectStudyPeriod));
    }

    @GetMapping("/studentResults/{id:\\d+}")
    public SubjectStudyPeriodMidtermTaskDto getStudentsResults(
            HoisUserDetails user, @WithEntity("id") SubjectStudyPeriod subjectStudyPeriod) {
        MidtermTaskUtil.checkUserRights(user, subjectStudyPeriod);
        return SubjectStudyPeriodMidtermTaskDto.of(subjectStudyPeriod);
    }

    @PutMapping("/studentResults/{id:\\d+}")
    public SubjectStudyPeriodMidtermTaskDto updateStudentsResults(HoisUserDetails user, 
            @WithEntity("id") SubjectStudyPeriod subjectStudyPeriod, 
            @NotNull @Valid @RequestBody SubjectStudyPeriodMidtermTaskForm form) {
        MidtermTaskUtil.checkUserRights(user, subjectStudyPeriod);
        midtermTaskService.updateStudentsResults(form, subjectStudyPeriod);
        return getStudentsResults(user, subjectStudyPeriod);
    }
}

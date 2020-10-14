package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.school.StudyYearSchedule;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.dto.StudyPeriodWithWeeksDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.service.StudyYearScheduleService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.web.commandobject.StudyYearScheduleCommand;
import ee.hitsa.ois.web.commandobject.StudyYearScheduleDtoContainer;
import ee.hitsa.ois.web.commandobject.StudyYearScheduleForm;
import ee.hitsa.ois.web.dto.StudyYearDto;
import ee.hitsa.ois.web.dto.student.StudentGroupSearchDto;

@RestController
@RequestMapping("/studyYearSchedule")
public class StudyYearScheduleController {

    @Autowired
    private StudyYearScheduleService studyYearScheduleService;

    @PostMapping
    public StudyYearScheduleDtoContainer getStudyYearSchedules(HoisUserDetails user, @NotNull @Valid @RequestBody StudyYearScheduleDtoContainer schedulesCmd) {
        // user can select school department with no student groups, and it should not cause an error
        if(!CollectionUtils.isEmpty(schedulesCmd.getStudyPeriods())) {
            schedulesCmd.setStudyYearSchedules(studyYearScheduleService.getSet(user, schedulesCmd));
        }
        return schedulesCmd;
    }
    
    @PostMapping("/schedule")
    public void saveStudyYearSchedule(HoisUserDetails user, 
            @Valid @RequestBody StudyYearScheduleCommand scheduleCmd) {
        /*
         * User can select school department with no student groups and click Save
         * This code prevents changes to database and errors
         */
        if(!CollectionUtils.isEmpty(scheduleCmd.getStudyPeriods())) {
            studyYearScheduleService.update(user.getSchoolId(), scheduleCmd, null);
        }
    }

    @PutMapping("/schedule/{id:\\d+}")
    public void updateStudyYearSchedule(HoisUserDetails user, 
            @Valid @RequestBody StudyYearScheduleCommand scheduleCmd, @WithEntity StudyYearSchedule oldSchedule) {
        /*
         * User can select school department with no student groups and click Save
         * This code prevents changes to database and errors
         */
        if(!CollectionUtils.isEmpty(scheduleCmd.getStudyPeriods())) {
            studyYearScheduleService.update(user.getSchoolId(), scheduleCmd, oldSchedule);
        }
    }
    
    /**
     * Used for deleting study year schedule entity
     * Putmapping is used, so command can be added for returning new list of schedules
     * 
     * @param user
     * @param scheduleCmd
     * @param studyYearSchedule
     * @return
     */
    @PutMapping("/{id:\\d+}")
    public void deleteStudyYearSchedule(HoisUserDetails user, @Valid @RequestBody StudyYearScheduleCommand scheduleCmd, 
            @WithEntity StudyYearSchedule studyYearSchedule) {
        /*
         * User can select school department with no student groups and click Save
         * This code prevents changes to database and errors
         */
        if(!CollectionUtils.isEmpty(scheduleCmd.getStudyPeriods())) {
            studyYearScheduleService.delete(studyYearSchedule);
        }
    }

    /**
     * This method is used because student groups 
     * should be filtered by school departments in front end
     */
    @GetMapping("/studentGroups")
    public List<StudentGroupSearchDto> getStudentGroups(HoisUserDetails user, Boolean showMine) {
        return studyYearScheduleService.getStudentGroups(user, showMine);
    }

    @GetMapping("/studyYears")
    public List<StudyYearDto> getStudyYearsWithStudyPeriods(HoisUserDetails user) {
        return studyYearScheduleService.getStudyYearsWithStudyPeriods(user.getSchoolId());
    }

    @GetMapping("/studyYearPeriods/{studyYearId:\\d+}")
    public List<StudyPeriodWithWeeksDto> getStudyYearPeriods(@WithEntity("studyYearId") StudyYear studyYear) {
        return studyYearScheduleService.getStudyYearPeriods(studyYear);
    }

    @GetMapping("/studyYearSchedule.xlsx")
    public void studyYearScheduleAsExcel(HoisUserDetails user, 
            @NotNull @Valid StudyYearScheduleForm schedulesCmd, 
            HttpServletResponse response) throws IOException {
        HttpUtil.xls(response, "studyyearschedule.xlsx", studyYearScheduleService.studyYearScheduleAsExcel(user, schedulesCmd));
    }

    @GetMapping("/studyYearSchedule.pdf")
    public void studyYearScheduleAsPdf(HoisUserDetails user, 
            @NotNull @Valid StudyYearScheduleForm schedulesCmd, 
            HttpServletResponse response) throws IOException {
        HttpUtil.pdf(response, "studyyearschedule.pdf", studyYearScheduleService.studyYearScheduleAsPdf(user, schedulesCmd));
    }
}

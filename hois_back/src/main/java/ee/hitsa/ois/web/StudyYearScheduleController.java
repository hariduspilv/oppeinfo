package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

    @PutMapping
    public StudyYearScheduleDtoContainer updateStudyYearSchedules(HoisUserDetails user, 
            @Valid @RequestBody StudyYearScheduleDtoContainer schedulesCmd) {
        /*
         * User can select school department with no student groups and click Save
         * This code prevents changes to database and errors
         */
        if(!CollectionUtils.isEmpty(schedulesCmd.getStudyPeriods())) {
            studyYearScheduleService.update(user.getSchoolId(), schedulesCmd);
        }
        return getStudyYearSchedules(user, schedulesCmd);
    }

    /**
     * This method is used because student groups 
     * should be filtered by school departments in front end
     */
    @GetMapping("/studentGroups")
    public List<StudentGroupSearchDto> getStudentGroups(HoisUserDetails user, Boolean showMine) {
        return studyYearScheduleService.getStudentGroups(user.getSchoolId(), Boolean.TRUE.equals(showMine) ? user.getStudentId() : null);
    }

    @GetMapping("/studyYears")
    public List<StudyYearDto> getStudyYearsWithStudyPeriods(HoisUserDetails user) {
        return studyYearScheduleService.getStudyYearsWithStudyPeriods(user.getSchoolId());
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

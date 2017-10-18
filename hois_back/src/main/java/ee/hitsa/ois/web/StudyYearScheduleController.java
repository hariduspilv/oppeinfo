package ee.hitsa.ois.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.service.StudyYearScheduleService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.web.commandobject.StudyYearScheduleDtoContainer;
import ee.hitsa.ois.web.dto.StudyYearDto;
import ee.hitsa.ois.web.dto.student.StudentGroupSearchDto;

@RestController
@RequestMapping("/studyYearSchedule")
public class StudyYearScheduleController {

    @Autowired
    private StudyYearScheduleService studyYearScheduleService;

    @GetMapping
    public StudyYearScheduleDtoContainer getStudyYearSchedules(HoisUserDetails user, StudyYearScheduleDtoContainer schedulesCmd) {
        // user can select school department with no student groups, and it should not cause an error
        if(!CollectionUtils.isEmpty(schedulesCmd.getStudyPeriods())) {
            schedulesCmd.setStudyYearSchedules(studyYearScheduleService.getSet(user.getSchoolId(), schedulesCmd));
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
    public List<StudentGroupSearchDto> getStudentGroups(HoisUserDetails user) {
        return studyYearScheduleService.getStudentGroups(user.getSchoolId());
    }

    @GetMapping("/studyYears")
    public List<StudyYearDto> getStudyYearsWithStudyPeriods(HoisUserDetails user) {
        return studyYearScheduleService.getStudyYearsWithStudyPeriods(user.getSchoolId());
    }
}

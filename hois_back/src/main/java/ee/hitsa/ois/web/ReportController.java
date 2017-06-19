package ee.hitsa.ois.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.service.ReportService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.report.CurriculumCompletionCommand;
import ee.hitsa.ois.web.commandobject.report.CurriculumSubjectsCommand;
import ee.hitsa.ois.web.commandobject.report.StudentSearchCommand;
import ee.hitsa.ois.web.commandobject.report.StudentStatisticsByPeriodCommand;
import ee.hitsa.ois.web.commandobject.report.StudentStatisticsCommand;
import ee.hitsa.ois.web.commandobject.report.TeacherLoadCommand;
import ee.hitsa.ois.web.dto.report.CurriculumCompletionDto;
import ee.hitsa.ois.web.dto.report.CurriculumSubjectsDto;
import ee.hitsa.ois.web.dto.report.StudentSearchDto;
import ee.hitsa.ois.web.dto.report.StudentStatisticsDto;
import ee.hitsa.ois.web.dto.report.TeacherLoadDto;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/students")
    public Page<StudentSearchDto> students(HoisUserDetails user, @Valid StudentSearchCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return reportService.students(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping(value = "/students/students.xls", produces = HttpUtil.APPLICATION_XLS)
    public byte[] studentsAsExcel(HoisUserDetails user, @Valid StudentSearchCommand criteria) {
        UserUtil.assertIsSchoolAdmin(user);
        return reportService.studentsAsExcel(user.getSchoolId(), criteria);
    }

    @GetMapping("/students/statistics")
    public Page<StudentStatisticsDto> studentStatistics(HoisUserDetails user, @Valid StudentStatisticsCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return reportService.studentStatistics(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/students/statistics/byperiod")
    public Page<StudentStatisticsDto> studentStatisticsByPeriod(HoisUserDetails user, @Valid StudentStatisticsByPeriodCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return reportService.studentStatisticsByPeriod(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/curriculums/completion")
    public Page<CurriculumCompletionDto> curriculumCompletion(HoisUserDetails user, @Valid CurriculumCompletionCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return reportService.curriculumCompletion(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/curriculums/subjects")
    public Page<CurriculumSubjectsDto> curriculumSubjects(HoisUserDetails user, @Valid CurriculumSubjectsCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return reportService.curriculumSubjects(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/teachers/load")
    public Page<TeacherLoadDto> teacherLoad(HoisUserDetails user, @Valid TeacherLoadCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return reportService.teacherLoad(user.getSchoolId(), criteria, pageable);
    }
}

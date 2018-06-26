package ee.hitsa.ois.web;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.report.studentgroupteacher.StudentGroupTeacherReport;
import ee.hitsa.ois.service.ClassifierService;
import ee.hitsa.ois.service.PdfService;
import ee.hitsa.ois.service.ReportService;
import ee.hitsa.ois.service.StudentGroupTeacherReportService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.ClassifierUtil.ClassifierCache;
import ee.hitsa.ois.web.commandobject.report.CurriculumCompletionCommand;
import ee.hitsa.ois.web.commandobject.report.CurriculumSubjectsCommand;
import ee.hitsa.ois.web.commandobject.report.StudentGroupTeacherCommand;
import ee.hitsa.ois.web.commandobject.report.StudentSearchCommand;
import ee.hitsa.ois.web.commandobject.report.StudentStatisticsByPeriodCommand;
import ee.hitsa.ois.web.commandobject.report.StudentStatisticsCommand;
import ee.hitsa.ois.web.commandobject.report.TeacherLoadCommand;
import ee.hitsa.ois.web.commandobject.report.VotaCommand;
import ee.hitsa.ois.web.dto.report.CurriculumCompletionDto;
import ee.hitsa.ois.web.dto.report.CurriculumSubjectsDto;
import ee.hitsa.ois.web.dto.report.StudentSearchDto;
import ee.hitsa.ois.web.dto.report.StudentStatisticsDto;
import ee.hitsa.ois.web.dto.report.TeacherLoadDto;
import ee.hitsa.ois.web.dto.report.VotaDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentGroupTeacherDto;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;
    @Autowired
    private StudentGroupTeacherReportService studentGroupTeacherReportService;
    @Autowired
    private PdfService pdfService;
    @Autowired
    private ClassifierService classifierService;
    @Autowired
    private EntityManager em;

    @GetMapping("/students")
    public Page<StudentSearchDto> students(HoisUserDetails user, @Valid StudentSearchCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return reportService.students(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/students/students.xls")
    public void studentsAsExcel(HoisUserDetails user, @Valid StudentSearchCommand criteria, HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdmin(user);
        HttpUtil.xls(response, "students.xls", reportService.studentsAsExcel(user.getSchoolId(), criteria));
    }

    @GetMapping("/students/statistics")
    public Page<StudentStatisticsDto> studentStatistics(HoisUserDetails user, @Valid StudentStatisticsCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return reportService.studentStatistics(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/students/statistics/studentstatistics.xls")
    public void studentStatisticsAsExcel(HoisUserDetails user, @Valid StudentStatisticsCommand criteria, HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdmin(user);
        HttpUtil.xls(response, "studentstatistics.xls", reportService.studentStatisticsAsExcel(user.getSchoolId(), criteria));
    }

    @GetMapping("/students/statistics/byperiod")
    public Page<StudentStatisticsDto> studentStatisticsByPeriod(HoisUserDetails user, @Valid StudentStatisticsByPeriodCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return reportService.studentStatisticsByPeriod(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/students/statistics/studentstatisticsbyperiod.xls")
    public void studentStatisticsByPeriodAsExcel(HoisUserDetails user, @Valid StudentStatisticsByPeriodCommand criteria, HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdmin(user);
        HttpUtil.xls(response, "studentstatisticsbyperiod.xls", reportService.studentStatisticsByPeriodAsExcel(user.getSchoolId(), criteria));
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

    @GetMapping("/teachers/load/higher")
    public Page<TeacherLoadDto> teacherLoadHigher(HoisUserDetails user, @Valid TeacherLoadCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return reportService.teacherLoadHigher(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/teachers/load/vocational")
    public Page<TeacherLoadDto> teacherLoadVocational(HoisUserDetails user, @Valid TeacherLoadCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return reportService.teacherLoadVocational(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/vota")
    public Page<VotaDto> vota(HoisUserDetails user, @Valid VotaCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return reportService.vota(user.getSchoolId(), criteria, pageable);
    }
    
    @GetMapping("/studentgroupteacher")
    public StudentGroupTeacherDto studentGroupTeacher(HoisUserDetails user, @Valid StudentGroupTeacherCommand criteria) {
        UserUtil.assertIsSchoolAdminOrStudentGroupTeacher(user, em.getReference(StudentGroup.class, criteria.getStudentGroup()));
        return studentGroupTeacherReportService.studentGroupTeacher(criteria);
    }
    
    @GetMapping("/studentgroupteacher/studentgroupteacher.xls")
    public void studentGroupTeacherAsExcel(HoisUserDetails user, @Valid StudentGroupTeacherCommand criteria,
            HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdminOrStudentGroupTeacher(user, em.getReference(StudentGroup.class, criteria.getStudentGroup()));
        HttpUtil.xls(response, "student_group_teacher.xls", studentGroupTeacherReportService
                .studentGroupTeacherAsExcel(criteria, new ClassifierCache(classifierService)));
    }
    
    @GetMapping("/studentgroupteacher/studentgroupteacher.pdf")
    public void studentGroupTeacherAsPdf(HoisUserDetails user, @Valid StudentGroupTeacherCommand criteria,
            HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdminOrStudentGroupTeacher(user, em.getReference(StudentGroup.class, criteria.getStudentGroup()));
        StudentGroupTeacherReport report = new StudentGroupTeacherReport(criteria, 
                studentGroupTeacherReportService.studentGroupTeacher(criteria), new ClassifierCache(classifierService));
        HttpUtil.pdf(response, criteria.getStudentGroup() + ".pdf",
                pdfService.generate(StudentGroupTeacherReport.TEMPLATE_NAME, report));
    }
}   

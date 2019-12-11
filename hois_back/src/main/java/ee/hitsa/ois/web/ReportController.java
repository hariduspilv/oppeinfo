package ee.hitsa.ois.web;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

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
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.report.ReportUtil;
import ee.hitsa.ois.report.studentgroupteacher.NegativeResultsReport;
import ee.hitsa.ois.report.studentgroupteacher.StudentGroupTeacherReport;
import ee.hitsa.ois.service.ClassifierService;
import ee.hitsa.ois.service.PdfService;
import ee.hitsa.ois.service.ReportService;
import ee.hitsa.ois.service.StudentGroupTeacherReportService;
import ee.hitsa.ois.service.TeacherDetailLoadService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil.ClassifierCache;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.report.CurriculumCompletionCommand;
import ee.hitsa.ois.web.commandobject.report.CurriculumSubjectsCommand;
import ee.hitsa.ois.web.commandobject.report.ForeignStudentStatisticsCommand;
import ee.hitsa.ois.web.commandobject.report.GuestStudentStatisticsCommand;
import ee.hitsa.ois.web.commandobject.report.IndividualCurriculumStatisticsCommand;
import ee.hitsa.ois.web.commandobject.report.ScholarshipStatisticsCommand;
import ee.hitsa.ois.web.commandobject.report.StudentGroupTeacherCommand;
import ee.hitsa.ois.web.commandobject.report.StudentSearchCommand;
import ee.hitsa.ois.web.commandobject.report.StudentStatisticsByPeriodCommand;
import ee.hitsa.ois.web.commandobject.report.StudentStatisticsCommand;
import ee.hitsa.ois.web.commandobject.report.TeacherDetailLoadCommand;
import ee.hitsa.ois.web.commandobject.report.TeacherLoadCommand;
import ee.hitsa.ois.web.commandobject.report.VotaCommand;
import ee.hitsa.ois.web.dto.report.CurriculumCompletionDto;
import ee.hitsa.ois.web.dto.report.CurriculumSubjectsDto;
import ee.hitsa.ois.web.dto.report.ForeignStudentStatisticsDto;
import ee.hitsa.ois.web.dto.report.GuestStudentStatisticsDto;
import ee.hitsa.ois.web.dto.report.IndividualCurriculumSatisticsDto;
import ee.hitsa.ois.web.dto.report.StudentSearchDto;
import ee.hitsa.ois.web.dto.report.StudentStatisticsDto;
import ee.hitsa.ois.web.dto.report.TeacherLoadDto;
import ee.hitsa.ois.web.dto.report.VotaDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentGroupTeacherDto;
import ee.hitsa.ois.web.dto.report.teacherdetailload.TeacherDetailLoadDto;
import ee.hitsa.ois.web.dto.report.teacherdetailload.TeacherDetailLoadReportDataDto;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;
    @Autowired
    private StudentGroupTeacherReportService studentGroupTeacherReportService;
    @Autowired
    private TeacherDetailLoadService teacherDetailLoadService;
    @Autowired
    private PdfService pdfService;
    @Autowired
    private ClassifierService classifierService;
    @Autowired
    private EntityManager em;

    @GetMapping("/students")
    public Page<StudentSearchDto> students(HoisUserDetails user, @Valid StudentSearchCommand criteria,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        return reportService.students(user, criteria, pageable);
    }

    @GetMapping("/students/students.xls")
    public void studentsAsExcel(HoisUserDetails user, @Valid StudentSearchCommand criteria,
            HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        HttpUtil.xls(response, "students.xls", reportService.studentsAsExcel(user, criteria));
    }

    @GetMapping("/students/statistics")
    public Page<StudentStatisticsDto> studentStatistics(HoisUserDetails user, @Valid StudentStatisticsCommand criteria,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        return reportService.studentStatistics(user, criteria, pageable);
    }
    
    @GetMapping("/foreignstudents/statistics")
    public Page<ForeignStudentStatisticsDto> foreignStudentStatistics(HoisUserDetails user, @Valid ForeignStudentStatisticsCommand criteria,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        return reportService.foreignStudentStatistics(user, criteria, pageable);
    }
    
    @GetMapping("/foreignstudents/statistics/foreignstudentstatistics.xls")
    public void foreignStudentStatisticsAsExcel(HoisUserDetails user, @Valid ForeignStudentStatisticsCommand criteria,
            HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        HttpUtil.xls(response, "foreignstudentstatistics.xls", reportService.foreignStudentStatisticsAsExcel(user, criteria));
    }
    
    @GetMapping("/gueststudents/statistics")
    public Page<GuestStudentStatisticsDto> guestStudentStatistics(HoisUserDetails user, @Valid GuestStudentStatisticsCommand criteria,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        return reportService.guestStudentStatistics(user, criteria, pageable);
    }
    
    @GetMapping("/gueststudents/statistics/gueststudentstatistics.xls")
    public void guestDtudentStatisticsAsExcel(HoisUserDetails user, @Valid GuestStudentStatisticsCommand criteria,
            HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        HttpUtil.xls(response, "gueststudentstatistics.xls", reportService.guestStudentStatisticsAsExcel(user, criteria));
    }

    @GetMapping("/students/statistics/studentstatistics.xls")
    public void studentStatisticsAsExcel(HoisUserDetails user, @Valid StudentStatisticsCommand criteria,
            HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        HttpUtil.xls(response, "studentstatistics.xls", reportService.studentStatisticsAsExcel(user, criteria));
    }

    @GetMapping("/students/statistics/byperiod")
    public Page<StudentStatisticsDto> studentStatisticsByPeriod(HoisUserDetails user,
            @Valid StudentStatisticsByPeriodCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        return reportService.studentStatisticsByPeriod(user, criteria, pageable);
    }

    @GetMapping("/students/statistics/studentstatisticsbyperiod.xls")
    public void studentStatisticsByPeriodAsExcel(HoisUserDetails user, @Valid StudentStatisticsByPeriodCommand criteria,
            HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        HttpUtil.xls(response, "studentstatisticsbyperiod.xls",
                reportService.studentStatisticsByPeriodAsExcel(user, criteria));
    }

    @GetMapping("/curriculums/completion")
    public Page<CurriculumCompletionDto> curriculumCompletion(HoisUserDetails user,
            @Valid CurriculumCompletionCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        return reportService.curriculumCompletion(user, criteria, pageable);
    }

    @GetMapping("/curriculums/completion/curriculumscompletion.xls")
    public void curriculumCompletionAsExcel(HoisUserDetails user, @Valid CurriculumCompletionCommand criteria,
            HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        HttpUtil.xls(response, "curriculumscompletion.xls", reportService.curriculumCompletionAsExcel(user, criteria));
    }

    @GetMapping("/curriculums/subjects")
    public Page<CurriculumSubjectsDto> curriculumSubjects(HoisUserDetails user, @Valid CurriculumSubjectsCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return reportService.curriculumSubjects(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/teachers/load/higher")
    public Page<TeacherLoadDto> teacherLoadHigher(HoisUserDetails user, @Valid TeacherLoadCommand criteria,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        return reportService.teacherLoadHigher(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/teachers/load/higher/teachersloadhigher.xls")
    public void teacherLoadHigherAsExcel(HoisUserDetails user, @Valid TeacherLoadCommand criteria,
            HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        HttpUtil.xls(response, "teachersloadhigher.xls", reportService.teacherLoadHigherAsExcel(user.getSchoolId(), criteria));
    }

    @GetMapping("/teachers/load/vocational")
    public Page<TeacherLoadDto> teacherLoadVocational(HoisUserDetails user, @Valid TeacherLoadCommand criteria,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        return reportService.teacherLoadVocational(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/teachers/load/vocational/teachersloadvocational.xls")
    public void teacherLoadVocationalAsExcel(HoisUserDetails user, @Valid TeacherLoadCommand criteria,
            HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        HttpUtil.xls(response, "teachersloadvocational.xls",
                reportService.teacherLoadVocationalAsExcel(user.getSchoolId(), criteria));
    }

    @GetMapping("/vota")
    public Page<VotaDto> vota(HoisUserDetails user, @Valid VotaCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        return reportService.vota(user, criteria, pageable);
    }

    @GetMapping("/studentgroupteacher")
    public StudentGroupTeacherDto studentGroupTeacher(HoisUserDetails user,
            @Valid StudentGroupTeacherCommand criteria) {
        ReportUtil.assertCanViewStudentGroupTeacherReport(user,
                em.getReference(StudentGroup.class, criteria.getStudentGroup()));
        return studentGroupTeacherReportService.studentGroupTeacher(criteria);
    }

    @GetMapping("/studentgroupteacher/studentgroupteacher.xls")
    public void studentGroupTeacherAsExcel(HoisUserDetails user, @Valid StudentGroupTeacherCommand criteria,
            HttpServletResponse response) throws IOException {
        ReportUtil.assertCanViewStudentGroupTeacherReport(user,
                em.getReference(StudentGroup.class, criteria.getStudentGroup()));
        HttpUtil.xls(response, "student_group_teacher.xls", studentGroupTeacherReportService
                .studentGroupTeacherAsExcel(criteria, new ClassifierCache(classifierService)));
    }

    @GetMapping("/studentgroupteacher/studentgroupteacher.pdf")
    public void studentGroupTeacherAsPdf(HoisUserDetails user, @Valid StudentGroupTeacherCommand criteria,
            HttpServletResponse response) throws IOException {
        ReportUtil.assertCanViewStudentGroupTeacherReport(user,
                em.getReference(StudentGroup.class, criteria.getStudentGroup()));
        StudentGroupTeacherReport report = new StudentGroupTeacherReport(criteria,
                studentGroupTeacherReportService.studentGroupTeacher(criteria), new ClassifierCache(classifierService));
        HttpUtil.pdf(response, criteria.getStudentGroup() + ".pdf",
                pdfService.generate(StudentGroupTeacherReport.TEMPLATE_NAME, report));
    }

    @GetMapping("/studentgroupteacher/negativeresults.xls")
    public void studentGroupTeacherNegativeResultsAsExcel(HoisUserDetails user,
            @Valid StudentGroupTeacherCommand criteria, HttpServletResponse response) throws IOException {
        ReportUtil.assertCanViewStudentGroupTeacherReport(user,
                em.getReference(StudentGroup.class, criteria.getStudentGroup()));
        HttpUtil.xls(response, "negative_results.xls",
                studentGroupTeacherReportService.negativeResultsAsExcel(user, criteria));
    }

    @GetMapping("/studentgroupteacher/negativeresults.pdf")
    public void studentGroupTeacherNegativeResultsAsPdf(HoisUserDetails user,
            @Valid StudentGroupTeacherCommand criteria, HttpServletResponse response) throws IOException {
        ReportUtil.assertCanViewStudentGroupTeacherReport(user,
                em.getReference(StudentGroup.class, criteria.getStudentGroup()));
        HttpUtil.pdf(response, criteria.getStudentGroup() + ".pdf",
                pdfService.generate(NegativeResultsReport.TEMPLATE_NAME, studentGroupTeacherReportService
                        .negativeResultsAsPdfData(criteria, new ClassifierCache(classifierService))));
    }

    @GetMapping("/teachers/detailload/data")
    public TeacherDetailLoadReportDataDto teacherDetailLoadReportData(HoisUserDetails user,
            @Valid TeacherDetailLoadCommand criteria) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        return teacherDetailLoadService.teacherDetailLoadReportData(criteria);
    }

    @GetMapping("/teachers/detailload/vocational")
    public Page<TeacherDetailLoadDto> teacherVocationalDetailLoad(HoisUserDetails user,
            @Valid TeacherDetailLoadCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        return teacherDetailLoadService.teacherVocationalDetailLoad(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/teachers/detailload/vocational/{id:\\d+}")
    public TeacherDetailLoadDto teacherDetailLoadJournals(HoisUserDetails user,
            @Valid TeacherDetailLoadCommand criteria, @WithEntity Teacher teacher) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        return teacherDetailLoadService.teacherDetailLoadJournals(user.getSchoolId(), criteria, teacher);
    }

    @GetMapping("/teachers/detailload/higher")
    public Page<TeacherDetailLoadDto> teacherHigherDetailLoad(HoisUserDetails user,
            @Valid TeacherDetailLoadCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        return teacherDetailLoadService.teacherHigherDetailLoad(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/teachers/detailload/higher/{id:\\d+}")
    public TeacherDetailLoadDto teacherDetailLoadSubjects(HoisUserDetails user,
            @Valid TeacherDetailLoadCommand criteria, @WithEntity Teacher teacher) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        return teacherDetailLoadService.teacherDetailLoadSubjects(user.getSchoolId(), criteria, teacher);
    }

    @GetMapping("/teachers/detailload/teachersdetailload.xlsx")
    public void teacherDetailLoadAsExcel(HoisUserDetails user, @Valid TeacherDetailLoadCommand criteria,
            HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        HttpUtil.xls(response, "teachersdetailload.xlsx",
                teacherDetailLoadService.teacherDetailLoadAsExcel(user.getSchoolId(), criteria));
    }

    @GetMapping("/teachers/detailload/teacher/{id:\\d+}/teachersdetailload.xlsx")
    public void teacherDetailLoadJournalsAsExcel(HoisUserDetails user, @Valid TeacherDetailLoadCommand criteria,
            @WithEntity Teacher teacher, HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        HttpUtil.xls(response, "teachersdetailload.xlsx", teacherDetailLoadService
                .teacherDetailLoadJournalSubjectsAsExcel(user.getSchoolId(), criteria, teacher));
    }

    @GetMapping("/scholarships/statistics.xlsx")
    public void scholarshipStatisticsAsExcel(HoisUserDetails user, @Valid ScholarshipStatisticsCommand criteria,
            HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PARING);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMYYYY");
        HttpUtil.xls(response, String.format("toestip_rmp_%s_%s.xlsx", formatter.format(criteria.getFrom()),
                formatter.format(criteria.getThru())), reportService.scholarshipStatisticsAsExcel(user, criteria));
    }

    @GetMapping("/individualcurriculumstatistics")
    public Page<IndividualCurriculumSatisticsDto> individualCurriculumStatistics(HoisUserDetails user,
            @Valid IndividualCurriculumStatisticsCommand criteria, Pageable pageable) {
        ReportUtil.assertCanViewIndividualCurriculumStatistics(user);
        return reportService.individualCurriculumStatistics(user, criteria, pageable);
    }

    @GetMapping("/individualcurriculumstatistics.xls")
    public void individualCurriculumStatistics(HoisUserDetails user,
            @Valid IndividualCurriculumStatisticsCommand criteria, HttpServletResponse response) throws IOException {
        ReportUtil.assertCanViewIndividualCurriculumStatistics(user);
        HttpUtil.xls(response, "individualcurriculumstatistics.xls",
                reportService.individualCurriculumStatisticsAsExcel(user, criteria));
    }

}

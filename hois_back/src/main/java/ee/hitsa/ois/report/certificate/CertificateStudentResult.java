package ee.hitsa.ois.report.certificate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectSearchDto;
import ee.hitsa.ois.web.dto.student.StudentHigherSubjectResultDto;
import ee.hitsa.ois.web.dto.student.StudentHigherSubjectResultGradeDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalResultModuleThemeDto;

public class CertificateStudentResult {

    private String subject;
    private String module;
    private String theme;
    private BigDecimal hours;
    private String gradeValue;
    private String gradeName;
    private String date;
    private List<String> teachers;
    private String assessedBy;

    public static CertificateStudentResult of(StudentHigherSubjectResultDto dto) {
        CertificateStudentResult result = new CertificateStudentResult();

        SubjectSearchDto subject = dto.getSubject();
        result.setSubject(subject.getNameEt());
        result.setHours(subject.getCredits());

        StudentHigherSubjectResultGradeDto grade = dto.getLastGrade();
        result.setGradeName(grade.getGradeNameEt());
        result.setGradeValue(grade.getGradeValue());
        result.setDate(DateUtils.date(grade.getGradeDate()));
        result.setAssessedBy(String.join(", ", StreamUtil.nullSafeList(grade.getTeachers())));
        return result;
    }

    public static CertificateStudentResult of(StudentVocationalResultModuleThemeDto dto, Map<String, Classifier> vocationalGrades) {
        CertificateStudentResult result = new CertificateStudentResult();

        result.setTheme(dto.getTheme() != null ? dto.getTheme().getNameEt() : null);
        result.setModule(dto.getModule() != null ? dto.getModule().getNameEt() : null);
        result.setHours(dto.getCredits());

        Classifier grade = vocationalGrades.get(dto.getGrade());
        result.setGradeName(grade != null ? grade.getNameEt() : null);
        result.setGradeValue(grade != null ? grade.getValue() : null);
        result.setDate(DateUtils.date(dto.getDate()));
        result.setAssessedBy(String.join(", ", StreamUtil.toMappedList(AutocompleteResult::getNameEt, dto.getTeachers())));
        return result;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getTheme() {
        return theme;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getAssessedBy() {
        return assessedBy;
    }

    public void setAssessedBy(String assessedBy) {
        this.assessedBy = assessedBy;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public BigDecimal getHours() {
        return hours;
    }

    public void setHours(BigDecimal hours) {
        this.hours = hours;
    }

    public String getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(String gradeValue) {
        this.gradeValue = gradeValue;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }
}

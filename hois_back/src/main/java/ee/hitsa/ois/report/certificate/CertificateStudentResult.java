package ee.hitsa.ois.report.certificate;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

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
    private String subjectEn;
    private String subjectCode;
    private String module;
    private String moduleEn;
    private String theme;
    private BigDecimal hours;
    private String gradeValue;
    private String gradeName;
    private String gradeNameEn;
    private String date;
    private List<String> teachers;
    private String assessedBy;
    private String outcomes;
    private String outcomesEn;
    private Boolean isActive;

    public static CertificateStudentResult of(StudentHigherSubjectResultDto dto) {
        CertificateStudentResult result = new CertificateStudentResult();

        SubjectSearchDto subject = dto.getSubject();
        result.setSubject(subject.getNameEt());
        result.setSubjectEn(subject.getNameEn());
        result.setHours(subject.getCredits());
        result.setSubjectCode(subject.getCode());

        StudentHigherSubjectResultGradeDto grade = dto.getLastGrade();
        if (grade != null) {
            result.setGradeName(grade.getGradeNameEt());
            result.setGradeNameEn(grade.getGradeNameEn());
            result.setGradeValue(grade.getGradeValue());
            result.setDate(DateUtils.date(grade.getGradeDate()));
            result.setIsActive(grade.getIsActive());
            List<String> teachers = grade.getTeachers();
            if (teachers.contains(null)) {
                teachers.removeAll(Collections.singleton(null));
            }
            result.setAssessedBy(String.join(", ", StreamUtil.nullSafeList(teachers)));
        } else {
            result.setAssessedBy(dto.getAllTeachers());
        }
        return result;
    }

    public static CertificateStudentResult of(StudentVocationalResultModuleThemeDto dto, Map<String, Classifier> vocationalGrades) {
        CertificateStudentResult result = new CertificateStudentResult();

        result.setTheme(dto.getTheme() != null ? dto.getTheme().getNameEt() : null);
        result.setModule(dto.getModule() != null ? dto.getModule().getNameEt() : null);
        result.setModuleEn(dto.getModule() != null ? dto.getModule().getNameEn() : null);
        result.setHours(dto.getCredits());

        Classifier grade = vocationalGrades.get(dto.getGrade());
        result.setGradeName(grade != null ? grade.getNameEt() : null);
        result.setGradeNameEn(grade != null ? grade.getNameEn() : null);
        result.setGradeValue(grade != null ? grade.getValue() : null);
        result.setDate(DateUtils.date(dto.getDate()));
        result.setAssessedBy(String.join(", ", StreamUtil.toMappedList(AutocompleteResult::getNameEt, dto.getTeachers().stream().filter(p->p.getNameEn()!=null).collect(Collectors.toList()))));
        if (StringUtils.isEmpty(result.getAssessedBy())) result.setAssessedBy(dto.getTeachersAsString());
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

    public String getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(String outcomes) {
        this.outcomes = outcomes;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getGradeNameEn() {
        return gradeNameEn;
    }

    public void setGradeNameEn(String gradeNameEn) {
        this.gradeNameEn = gradeNameEn;
    }

    public String getSubjectEn() {
        return subjectEn;
    }

    public void setSubjectEn(String subjectEn) {
        this.subjectEn = subjectEn;
    }

    public String getOutcomesEn() {
        return outcomesEn;
    }

    public void setOutcomesEn(String outcomesEn) {
        this.outcomesEn = outcomesEn;
    }

    public String getModuleEn() {
        return moduleEn;
    }

    public void setModuleEn(String moduleEn) {
        this.moduleEn = moduleEn;
    }

}

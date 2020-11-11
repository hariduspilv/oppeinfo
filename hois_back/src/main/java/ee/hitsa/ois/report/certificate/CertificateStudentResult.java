package ee.hitsa.ois.report.certificate;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.gradingschema.GradingSchemaRow;
import ee.hitsa.ois.util.TranslateUtil;
import ee.hitsa.ois.web.dto.student.StudentHigherModuleResultDto;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierDto;
import ee.hitsa.ois.web.dto.SubjectSearchDto;
import ee.hitsa.ois.web.dto.student.StudentHigherSubjectResultDto;
import ee.hitsa.ois.web.dto.student.StudentHigherResultGradeDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalResultModuleThemeDto;

public class CertificateStudentResult {

    private String subject;
    private String subjectEn;
    private String subjectCode;
    private ClassifierDto moduleCode;
    private AutocompleteResult module;
    private Long occupationModuleId;
    private String theme;
    private String outcome;
    private String outcomeEn;
    private BigDecimal hours;
    private BigDecimal moduleCredits;
    private String gradeValue;
    private String gradeValueEn;
    private String gradeName;
    private String gradeNameEn;
    private String date;
    private List<String> teachers;
    private String assessedBy;
    private Boolean addResultOutcomes;
    private String outcomes;
    private String outcomesEn;
    private Short orderNr;
    private Long curriculumId;
    private String versionCode;
    
    private Boolean isHeader = Boolean.FALSE;
    private Boolean isSameCurriculum = Boolean.FALSE;

    public static CertificateStudentResult of(StudentHigherSubjectResultDto dto, boolean stateGradingSchema) {
        CertificateStudentResult result = new CertificateStudentResult();

        SubjectSearchDto subject = dto.getSubject();
        result.setSubject(subject.getNameEt());
        result.setSubjectEn(TranslateUtil.getNonNullableNameEn(subject));
        result.setHours(subject.getCredits());
        result.setSubjectCode(subject.getCode());
        result.setModule(dto.getHigherModule());

        StudentHigherResultGradeDto grade = dto.getLastGrade();
        if (grade != null) {
            result.setGradeName(grade.getGradeNameEt());
            result.setGradeNameEn(grade.getGradeNameEn());
            if (grade.getGrade().getGradingSchemaRowId() == null || stateGradingSchema) {
                result.setGradeValue(grade.getGradeValue());
                result.setGradeValueEn(grade.getGradeValue());
            } else {
                result.setGradeValue(grade.getGradingSchemaGradeEt());
                result.setGradeValueEn(grade.getGradingSchemaGradeEn());
            }
            result.setDate(DateUtils.date(grade.getGradeDate()));
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

    public static CertificateStudentResult of(StudentHigherModuleResultDto dto, boolean stateGradingSchema) {
        CertificateStudentResult result = new CertificateStudentResult();
        result.setModule(new AutocompleteResult(dto.getId(), dto.getNameEt(), dto.getNameEn()));
        result.setIsHeader(Boolean.TRUE);
        result.setModuleCredits(dto.getTotalCredits());

        StudentHigherResultGradeDto grade = dto.getLastGrade();
        if (grade != null) {
            result.setGradeName(grade.getGradeNameEt());
            result.setGradeNameEn(grade.getGradeNameEn());
            if (grade.getGrade().getGradingSchemaRowId() == null || stateGradingSchema) {
                result.setGradeValue(grade.getGradeValue());
                result.setGradeValueEn(grade.getGradeValue());
            } else {
                result.setGradeValue(grade.getGradingSchemaGradeEt());
                result.setGradeValueEn(grade.getGradingSchemaGradeEn());
            }
            result.setDate(DateUtils.date(grade.getGradeDate()));
            result.setAssessedBy(String.join(", ", StreamUtil.nullSafeList(grade.getTeachers())));
        }
        return result;
    }

    public static CertificateStudentResult of(StudentVocationalResultModuleThemeDto dto,
            Map<String, Classifier> vocationalGrades, Map<Long, GradingSchemaRow> gradingSchemaRows,
            Long studentCurriculumId) {
        CertificateStudentResult result = new CertificateStudentResult();

        result.setTheme(dto.getTheme() != null ? dto.getTheme().getNameEt() : null);
        result.setAddResultOutcomes(Boolean.valueOf(dto.getModule() != null && dto.getModule().getId() != null && dto.getOutcome() == null));
        result.setOutcome(dto.getOutcome() != null ? dto.getOutcome().getNameEt() : null);
        result.setOutcomeEn(dto.getOutcome() != null ? TranslateUtil.getNonNullableNameEn(dto.getOutcome()) : null);
        result.setModule(dto.getModule() != null ? new AutocompleteResult(dto.getModule().getId(), dto.getModule().getNameEt(), TranslateUtil.getNonNullableNameEn(dto.getModule())) : null);
        result.setModuleCode(dto.getModule() != null ? ClassifierDto.ofMin(dto.getModule().getModuleCode()) : null);
        result.setOrderNr(dto.getModule() != null ? dto.getModule().getOrderNr() : null);
        result.setVersionCode(dto.getModule() != null ? dto.getModule().getVersionCode() : null);
        if (result.getVersionCode() == null) {
            // in case of outcomes, it does not have any version code, so we put curriculum code instead
            result.setVersionCode(dto.getCurriculum() != null ? dto.getCurriculum().getCode() : null);
        }
        result.setHours(dto.getCredits());
        result.setModuleCredits(dto.getModule() != null ? dto.getModule().getCredits() : null);
        result.setOccupationModuleId(dto.getCurriculumVersionModuleId());
        result.setCurriculumId(dto.getCurriculum() != null ? dto.getCurriculum().getId() : null);

        if (dto.getGrade() != null) {
            Classifier grade = vocationalGrades.get(dto.getGrade().getCode());
            result.setGradeName(grade != null ? grade.getNameEt() : null);
            result.setGradeNameEn(grade != null ? grade.getNameEn() : null);
            if (dto.getGrade().getGradingSchemaRowId() == null || gradingSchemaRows.isEmpty()) {
                String gradeValue = grade != null ? grade.getValue() : null;
                result.setGradeValue(gradeValue);
                result.setGradeValueEn(gradeValue);
            } else {
                GradingSchemaRow schemaGrade = gradingSchemaRows.get(dto.getGrade().getGradingSchemaRowId());
                result.setGradeValue(schemaGrade != null ? schemaGrade.getGrade() : null);
                result.setGradeValueEn(schemaGrade != null ? schemaGrade.getGradeEn() : null);
            }
        }
        result.setDate(DateUtils.date(dto.getDate()));
        result.setAssessedBy(String.join(", ", StreamUtil.toMappedList(AutocompleteResult::getNameEt, dto.getTeachers().stream().filter(p->p.getNameEn()!=null).collect(Collectors.toList()))));
        if (StringUtils.isEmpty(result.getAssessedBy())) result.setAssessedBy(dto.getTeachersAsString());
        
        result.setIsSameCurriculum(studentCurriculumId != null ? Boolean.valueOf(studentCurriculumId.equals(result.getCurriculumId())) : Boolean.FALSE);
        return result;
    }

    public ClassifierDto getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(ClassifierDto moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getTheme() {
        return theme;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getOutcomeEn() {
        return outcomeEn;
    }

    public void setOutcomeEn(String outcomeEn) {
        this.outcomeEn = outcomeEn;
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

    public String getGradeValueEn() {
        return gradeValueEn;
    }

    public void setGradeValueEn(String gradeValueEn) {
        this.gradeValueEn = gradeValueEn;
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

    public Boolean getAddResultOutcomes() {
        return addResultOutcomes;
    }

    public void setAddResultOutcomes(Boolean addResultOutcomes) {
        this.addResultOutcomes = addResultOutcomes;
    }

    public String getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(String outcomes) {
        this.outcomes = outcomes;
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

    public Short getOrderNr() {
        return orderNr;
    }

    public void setOrderNr(Short orderNr) {
        this.orderNr = orderNr;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public AutocompleteResult getModule() {
        return module;
    }

    public void setModule(AutocompleteResult module) {
        this.module = module;
    }

    public Boolean getIsHeader() {
        return isHeader;
    }

    public void setIsHeader(Boolean isHeader) {
        this.isHeader = isHeader;
    }

    public Boolean getIsSameCurriculum() {
        return isSameCurriculum;
    }

    public void setIsSameCurriculum(Boolean isSameCurriculum) {
        this.isSameCurriculum = isSameCurriculum;
    }

    public BigDecimal getModuleCredits() {
        return moduleCredits;
    }

    public void setModuleCredits(BigDecimal moduleCredits) {
        this.moduleCredits = moduleCredits;
    }

    public Long getOccupationModuleId() {
        return occupationModuleId;
    }

    public void setOccupationModuleId(Long occupationModuleId) {
        this.occupationModuleId = occupationModuleId;
    }

    public Long getCurriculumId() {
        return curriculumId;
    }

    public void setCurriculumId(Long curriculumId) {
        this.curriculumId = curriculumId;
    }

}

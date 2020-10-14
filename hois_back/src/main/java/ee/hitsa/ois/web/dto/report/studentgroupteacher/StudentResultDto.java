package ee.hitsa.ois.web.dto.report.studentgroupteacher;

import ee.hitsa.ois.web.dto.GradeDto;

import java.time.LocalDate;

public class StudentResultDto {

    private Long moduleId;
    private Long moduleThemeId;
    private Long outcomeId;
    private GradeDto grade;
    private LocalDate gradeInserted;
    private String gradeInsertedBy;

    public StudentResultDto() {

    }

    public StudentResultDto(StudentResultDto result) {
        this.moduleId = result.getModuleId();
        this.moduleThemeId = result.getModuleThemeId();
        this.outcomeId = result.getOutcomeId();
        this.grade = result.getGrade();
        this.gradeInserted = result.getGradeInserted();
        this.gradeInsertedBy = result.getGradeInsertedBy();
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getModuleThemeId() {
        return moduleThemeId;
    }

    public void setModuleThemeId(Long moduleThemeId) {
        this.moduleThemeId = moduleThemeId;
    }

    public Long getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(Long outcomeId) {
        this.outcomeId = outcomeId;
    }

    public GradeDto getGrade() {
        return grade;
    }

    public void setGrade(GradeDto grade) {
        this.grade = grade;
    }

    public LocalDate getGradeInserted() {
        return gradeInserted;
    }

    public void setGradeInserted(LocalDate gradeInserted) {
        this.gradeInserted = gradeInserted;
    }

    public String getGradeInsertedBy() {
        return gradeInsertedBy;
    }

    public void setGradeInsertedBy(String gradeInsertedBy) {
        this.gradeInsertedBy = gradeInsertedBy;
    }
}

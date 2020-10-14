package ee.hitsa.ois.web.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ModuleProtocolOutcomeResultDto {

    private Long curriculumModuleOutcomeId;
    private GradeDto grade;
    private LocalDate gradeDate;
    private LocalDateTime gradeInserted;

    public ModuleProtocolOutcomeResultDto() {
        
    }

    public ModuleProtocolOutcomeResultDto(Long curriculumModuleOutcomeId, String gradeCode, Long gradingSchemaRowId,
            LocalDate gradeDate, LocalDateTime gradeInserted) {
        this.curriculumModuleOutcomeId = curriculumModuleOutcomeId;
        this.grade = new GradeDto(gradeCode, gradingSchemaRowId);
        this.gradeDate = gradeDate;
        this.gradeInserted = gradeInserted;
    }

    public Long getCurriculumModuleOutcomeId() {
        return curriculumModuleOutcomeId;
    }

    public void setCurriculumModuleOutcomeId(Long curriculumModuleOutcomeId) {
        this.curriculumModuleOutcomeId = curriculumModuleOutcomeId;
    }

    public GradeDto getGrade() {
        return grade;
    }

    public void setGrade(GradeDto grade) {
        this.grade = grade;
    }

    public LocalDate getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(LocalDate gradeDate) {
        this.gradeDate = gradeDate;
    }

    public LocalDateTime getGradeInserted() {
        return gradeInserted;
    }

    public void setGradeInserted(LocalDateTime gradeInserted) {
        this.gradeInserted = gradeInserted;
    }
}

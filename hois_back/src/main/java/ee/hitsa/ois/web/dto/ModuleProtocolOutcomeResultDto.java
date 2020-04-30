package ee.hitsa.ois.web.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class ModuleProtocolOutcomeResultDto {

    private Long curriculumModuleOutcomeId;
    @ClassifierRestriction(MainClassCode.KUTSEHINDAMINE)
    private String grade;
    private LocalDate gradeDate;
    private LocalDateTime gradeInserted;

    public ModuleProtocolOutcomeResultDto() {
        
    }

    public ModuleProtocolOutcomeResultDto(Long curriculumModuleOutcomeId, String grade, LocalDate gradeDate,
            LocalDateTime gradeInserted) {
        this.curriculumModuleOutcomeId = curriculumModuleOutcomeId;
        this.grade = grade;
        this.gradeDate = gradeDate;
        this.gradeInserted = gradeInserted;
    }

    public Long getCurriculumModuleOutcomeId() {
        return curriculumModuleOutcomeId;
    }

    public void setCurriculumModuleOutcomeId(Long curriculumModuleOutcomeId) {
        this.curriculumModuleOutcomeId = curriculumModuleOutcomeId;
    }
    
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
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

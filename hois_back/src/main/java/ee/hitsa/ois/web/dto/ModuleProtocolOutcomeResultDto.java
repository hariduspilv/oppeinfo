package ee.hitsa.ois.web.dto;

import java.time.LocalDateTime;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class ModuleProtocolOutcomeResultDto {

    private Long journalId;
    private Long curriculumModuleOutcomeId;
    private LocalDateTime gradeInserted;
    
    @ClassifierRestriction(MainClassCode.KUTSEHINDAMINE)
    private String grade;
    
    public ModuleProtocolOutcomeResultDto(Long journalId, Long curriculumModuleOutcomeId, String grade, LocalDateTime gradeInserted) {
        this.journalId = journalId;
        this.curriculumModuleOutcomeId = curriculumModuleOutcomeId;
        this.grade = grade;
        this.gradeInserted = gradeInserted;
    }

    public Long getJournalId() {
        return journalId;
    }

    public void setJournalId(Long journalId) {
        this.journalId = journalId;
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
    
    public LocalDateTime getGradeInserted() {
        return gradeInserted;
    }
    
    public void setGradeInserted(LocalDateTime gradeInserted) {
        this.gradeInserted = gradeInserted;
    }
}

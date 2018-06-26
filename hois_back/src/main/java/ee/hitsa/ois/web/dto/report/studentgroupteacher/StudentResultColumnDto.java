package ee.hitsa.ois.web.dto.report.studentgroupteacher;

public class StudentResultColumnDto {
    
    private StudentJournalResultDto journalResult;
    private StudentModuleResultDto practiceModuleThemeResult;
    private StudentModuleResultDto practiceModuleResult;
    private StudentModuleResultDto moduleResult;
    
    public StudentResultColumnDto() {
        
    }
    
    public StudentResultColumnDto(StudentResultColumnDto studentResult) {
        if (studentResult.getJournalResult() != null) {
            this.journalResult = new StudentJournalResultDto(studentResult.getJournalResult());
        } else if (studentResult.getPracticeModuleThemeResult() != null) {
            this.practiceModuleThemeResult = new StudentModuleResultDto(studentResult.getPracticeModuleThemeResult());
        }  else if (studentResult.getPracticeModuleResult() != null) {
            this.practiceModuleResult = new StudentModuleResultDto(studentResult.getPracticeModuleResult());
        } else if (studentResult.getModuleResult() != null) {
            this.moduleResult = new StudentModuleResultDto(studentResult.getModuleResult());
        }
    }

    public StudentJournalResultDto getJournalResult() {
        return journalResult;
    }

    public void setJournalResult(StudentJournalResultDto journalResult) {
        this.journalResult = journalResult;
    }

    public StudentModuleResultDto getPracticeModuleThemeResult() {
        return practiceModuleThemeResult;
    }

    public void setPracticeModuleThemeResult(StudentModuleResultDto practiceModuleThemeResult) {
        this.practiceModuleThemeResult = practiceModuleThemeResult;
    }

    public StudentModuleResultDto getPracticeModuleResult() {
        return practiceModuleResult;
    }

    public void setPracticeModuleResult(StudentModuleResultDto practiceModuleResult) {
        this.practiceModuleResult = practiceModuleResult;
    }

    public StudentModuleResultDto getModuleResult() {
        return moduleResult;
    }

    public void setModuleResult(StudentModuleResultDto moduleResult) {
        this.moduleResult = moduleResult;
    }
    
}

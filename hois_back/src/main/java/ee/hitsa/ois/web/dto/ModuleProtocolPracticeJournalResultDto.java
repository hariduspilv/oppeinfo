package ee.hitsa.ois.web.dto;

import java.time.LocalDateTime;

public class ModuleProtocolPracticeJournalResultDto {

    private Long journalId;
    private String grade;
    private AutocompleteResult theme;
    private LocalDateTime inserted; 
    
    public ModuleProtocolPracticeJournalResultDto(Long journalId, String grade, AutocompleteResult theme, LocalDateTime inserted) {
        this.journalId = journalId;
        this.grade = grade;
        this.theme = theme;
        this.inserted = inserted;
    }

    public Long getJournalId() {
        return journalId;
    }
    
    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }
    
    public String getGrade() {
        return grade;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    public AutocompleteResult getTheme() {
        return theme;
    }
    
    public void setTheme(AutocompleteResult theme) {
        this.theme = theme;
    }

    public LocalDateTime getInserted() {
        return inserted;
    }

    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }
    
}

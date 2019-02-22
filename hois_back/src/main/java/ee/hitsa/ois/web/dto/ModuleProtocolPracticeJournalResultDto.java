package ee.hitsa.ois.web.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.PracticeJournalModuleSubject;
import ee.hitsa.ois.util.StreamUtil;

public class ModuleProtocolPracticeJournalResultDto {

    private Long journalId;
    private String grade;
    private List<AutocompleteResult> themes;
    private LocalDateTime inserted; 
    
    public ModuleProtocolPracticeJournalResultDto(Long journalId, Set<PracticeJournalModuleSubject> moduleSubject,
            String grade, LocalDateTime inserted) {
        this.journalId = journalId;
        this.grade = grade;
        this.themes = StreamUtil.nullSafeSet(moduleSubject).stream().filter(ms -> ms.getTheme() != null)
                .map(ms -> AutocompleteResult.of(ms.getTheme())).collect(Collectors.toList());
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
    
    public List<AutocompleteResult> getThemes() {
        return themes;
    }
    
    public void setTheme(List<AutocompleteResult> themes) {
        this.themes = themes;
    }

    public LocalDateTime getInserted() {
        return inserted;
    }

    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }
    
}

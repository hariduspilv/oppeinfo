package ee.hitsa.ois.web.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.PracticeJournalModuleSubject;
import ee.hitsa.ois.util.StreamUtil;

public class ProtocolPracticeJournalResultDto {

    private Long journalId;
    private String grade;
    private LocalDateTime inserted;
    private List<AutocompleteResult> themes;

    public ProtocolPracticeJournalResultDto() {
        
    }

    public ProtocolPracticeJournalResultDto(Long journalId, String grade, LocalDateTime inserted,
            Set<PracticeJournalModuleSubject> moduleSubject) {
        this.journalId = journalId;
        this.grade = grade;
        this.inserted = inserted;
        this.themes = StreamUtil.nullSafeSet(moduleSubject).stream().filter(ms -> ms.getTheme() != null)
                .map(ms -> AutocompleteResult.of(ms.getTheme())).collect(Collectors.toList());
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

    public LocalDateTime getInserted() {
        return inserted;
    }

    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }

    public List<AutocompleteResult> getThemes() {
        return themes;
    }

    public void setTheme(List<AutocompleteResult> themes) {
        this.themes = themes;
    }

}

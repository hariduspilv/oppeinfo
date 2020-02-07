package ee.hitsa.ois.web.dto.poll;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.service.poll.GraphSubjectDto;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class GraphThemeDto {
    
    private Boolean isRepetitive;
    private AutocompleteResult title;
    private List<GraphSubjectDto> journalOrSubject = new ArrayList<>();

    public AutocompleteResult getTitle() {
        return title;
    }

    public void setTitle(AutocompleteResult title) {
        this.title = title;
    }

    public List<GraphSubjectDto> getJournalOrSubject() {
        return journalOrSubject;
    }

    public void setJournalOrSubject(List<GraphSubjectDto> journalOrSubject) {
        this.journalOrSubject = journalOrSubject;
    }

    public Boolean getIsRepetitive() {
        return isRepetitive;
    }

    public void setIsRepetitive(Boolean isRepetitive) {
        this.isRepetitive = isRepetitive;
    }
}

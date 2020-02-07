package ee.hitsa.ois.web.dto.poll;

import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class PollRelatedObjectsDto {
    
    private List<AutocompleteResult> journals;
    private List<AutocompleteResult> subjects;
    private Boolean themes;
    
    public List<AutocompleteResult> getJournals() {
        return journals;
    }
    public void setJournals(List<AutocompleteResult> journals) {
        this.journals = journals;
    }
    public List<AutocompleteResult> getSubjects() {
        return subjects;
    }
    public void setSubjects(List<AutocompleteResult> subjects) {
        this.subjects = subjects;
    }
    public Boolean getThemes() {
        return themes;
    }
    public void setThemes(Boolean themes) {
        this.themes = themes;
    }
}

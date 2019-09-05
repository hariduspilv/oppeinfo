package ee.hitsa.ois.web.dto.poll;

import java.util.Set;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class PollRelatedObjectsDto {
    
    private Set<AutocompleteResult> journals;
    private Set<AutocompleteResult> subjects;
    private Boolean themes;
    
    public Set<AutocompleteResult> getJournals() {
        return journals;
    }
    public void setJournals(Set<AutocompleteResult> journals) {
        this.journals = journals;
    }
    public Set<AutocompleteResult> getSubjects() {
        return subjects;
    }
    public void setSubjects(Set<AutocompleteResult> subjects) {
        this.subjects = subjects;
    }
    public Boolean getThemes() {
        return themes;
    }
    public void setThemes(Boolean themes) {
        this.themes = themes;
    }
}

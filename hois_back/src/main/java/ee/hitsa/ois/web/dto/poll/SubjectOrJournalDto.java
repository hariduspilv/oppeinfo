package ee.hitsa.ois.web.dto.poll;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class SubjectOrJournalDto {
    
    private AutocompleteResult journal;
    private AutocompleteResult subject;
    
    public AutocompleteResult getJournal() {
        return journal;
    }
    public void setJournal(AutocompleteResult journal) {
        this.journal = journal;
    }
    public AutocompleteResult getSubject() {
        return subject;
    }
    public void setSubject(AutocompleteResult subject) {
        this.subject = subject;
    }

}

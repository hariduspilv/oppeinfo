package ee.hitsa.ois.web.dto;

public class JournalAutocompleteResult extends AutocompleteResult {

    private Long studyYear;
    
    public JournalAutocompleteResult(Long id, String nameEt, String nameEn, Long studyYear) {
        super(id, nameEt, nameEn);
        this.studyYear = studyYear;
    }

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }
    
}

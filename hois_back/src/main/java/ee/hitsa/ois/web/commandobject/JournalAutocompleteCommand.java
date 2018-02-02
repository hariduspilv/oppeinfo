package ee.hitsa.ois.web.commandobject;

public class JournalAutocompleteCommand extends SearchCommand {

    private Long studyYear;

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }
}

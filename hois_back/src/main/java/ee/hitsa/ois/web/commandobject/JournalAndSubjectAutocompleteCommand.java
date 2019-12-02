package ee.hitsa.ois.web.commandobject;

public class JournalAndSubjectAutocompleteCommand extends SearchCommand {

    private Long studyYear;
    private Boolean practice;

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }

    public Boolean getPractice() {
        return practice;
    }

    public void setPractice(Boolean practice) {
        this.practice = practice;
    }

}

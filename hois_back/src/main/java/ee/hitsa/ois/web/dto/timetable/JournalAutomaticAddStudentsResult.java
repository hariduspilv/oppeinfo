package ee.hitsa.ois.web.dto.timetable;

public class JournalAutomaticAddStudentsResult {

    private Long numberOfJournals;
    private Long numberOfAddedStudents;

    public JournalAutomaticAddStudentsResult(Long numberOfJournals, Long numberOfAddedStudents) {
        this.numberOfJournals = numberOfJournals;
        this.numberOfAddedStudents = numberOfAddedStudents;
    }

    public Long getNumberOfJournals() {
        return numberOfJournals;
    }

    public void setNumberOfJournals(Long numberOfJournals) {
        this.numberOfJournals = numberOfJournals;
    }

    public Long getNumberOfAddedStudents() {
        return numberOfAddedStudents;
    }

    public void setNumberOfAddedStudents(Long numberOfAddedStudents) {
        this.numberOfAddedStudents = numberOfAddedStudents;
    }

}

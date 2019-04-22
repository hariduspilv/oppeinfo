package ee.hitsa.ois.report.studentgroupteacher;

import java.util.List;

public class NegativeResultsStudentReport {

    private String firstname;
    private String lastname;
    private List<NegativeResultsJournalReport> journals;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<NegativeResultsJournalReport> getJournals() {
        return journals;
    }

    public void setJournals(List<NegativeResultsJournalReport> journals) {
        this.journals = journals;
    }

}

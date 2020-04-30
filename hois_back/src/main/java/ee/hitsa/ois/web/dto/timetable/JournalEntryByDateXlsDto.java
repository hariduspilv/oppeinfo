package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class JournalEntryByDateXlsDto extends JournalEntryByDateBaseDto {

    private Map<Long, String> journalStudentGrade = new HashMap<>();
    private Map<Long, String> journalStudentAbsence = new HashMap<>();
    private Map<Long, String> journalStudentAddInfo = new HashMap<>();
    private Map<Long, String> studentOutcomeResults  = new HashMap<>();

    public Map<Long, String> getJournalStudentGrade() {
        return journalStudentGrade;
    }

    public void setJournalStudentGrade(Map<Long, String> journalStudentGrade) {
        this.journalStudentGrade = journalStudentGrade;
    }

    public Map<Long, String> getJournalStudentAbsence() {
        return journalStudentAbsence;
    }

    public void setJournalStudentAbsence(Map<Long, String> journalStudentAbsence) {
        this.journalStudentAbsence = journalStudentAbsence;
    }

    public Map<Long, String> getJournalStudentAddInfo() {
        return journalStudentAddInfo;
    }

    public void setJournalStudentAddInfo(Map<Long, String> journalStudentAddInfo) {
        this.journalStudentAddInfo = journalStudentAddInfo;
    }

    public Map<Long, String> getStudentOutcomeResults() {
        return studentOutcomeResults;
    }

    public void setStudentOutcomeResults(Map<Long, String> studentOutcomeResults) {
        this.studentOutcomeResults = studentOutcomeResults;
    }
}

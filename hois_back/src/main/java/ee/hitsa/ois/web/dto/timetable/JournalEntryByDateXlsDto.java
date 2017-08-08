package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class JournalEntryByDateXlsDto {

    private LocalDate entryDate;
    private String nameEt;

    private Map<Long, String> journalStudentGrade = new HashMap<>();
    private Map<Long, String> journalStudentAbsence = new HashMap<>();
    private Map<Long, String> journalStudentAddInfo = new HashMap<>();

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

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

}

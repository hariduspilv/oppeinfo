package ee.hitsa.ois.web.dto.timetable;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;

import java.util.List;

public class StudentJournalDto {
    private final Long id;
    private final String nameEt;
    private final Long studyYearId;
    private final String yearCode;
    private final Boolean moduleOutcomesAsEntries;
    private final Short absenceH;
    private final Short absenceP;
    private final Short absencePV;
    private List<StudentJournalEntryDto> journalEntries;
    
    public StudentJournalDto(Object[] row) {
        this.id = resultAsLong(row, 0);
        this.nameEt = (String) row[1];
        this.studyYearId = resultAsLong(row, 2);
        this.yearCode = (String) row[3];
        this.moduleOutcomesAsEntries = resultAsBoolean(row, 4);
        this.absenceH = resultAsShort(row, 5);
        this.absenceP = resultAsShort(row, 6);
        this.absencePV = resultAsShort(row, 7);
    }

    public Long getId() {
        return id;
    }

    public String getNameEt() {
        return nameEt;
    }

    public Long getStudyYearId() {
        return studyYearId;
    }

    public String getYearCode() {
        return yearCode;
    }
    
    public Boolean getModuleOutcomesAsEntries() {
        return moduleOutcomesAsEntries;
    }

    public Short getAbsenceH() {
        return absenceH;
    }

    public Short getAbsenceP() {
        return absenceP;
    }

    public Short getAbsencePV() {
        return absencePV;
    }
    
    public void setJournalEntries(List<StudentJournalEntryDto> journalEntries) {
        this.journalEntries = journalEntries;
    }

    public List<StudentJournalEntryDto> getJournalEntries() {
        return journalEntries;
    }
}

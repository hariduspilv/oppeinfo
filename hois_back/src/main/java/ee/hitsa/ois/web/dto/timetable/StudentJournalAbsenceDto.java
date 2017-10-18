package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class StudentJournalAbsenceDto {
    private final Long entryId;
    @ClassifierRestriction(MainClassCode.SISSEKANNE)
    private final LocalDate entryDate;
    private final String journalName;
    @ClassifierRestriction(MainClassCode.PUUDUMINE)
    private final String absenceCode;
    private final Short startLessonNr;
    private final String teachers;
    
    public StudentJournalAbsenceDto(Long entryId, LocalDate entryDate, String journalName, String absenceCode, Short startLessonNr,
            String teachers) {
        this.entryId = entryId;
        this.entryDate = entryDate;
        this.journalName = journalName;
        this.absenceCode = absenceCode;
        this.startLessonNr = startLessonNr;
        this.teachers = teachers;
    }

    public Long getEntryId() {
        return entryId;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }
    
    public String getJournalName() {
        return journalName;
    }

    public String getAbsenceCode() {
        return absenceCode;
    }

    public Short getStartLessonNr() {
        return startLessonNr;
    }

    public String getTeachers() {
        return teachers;
    }
    
}

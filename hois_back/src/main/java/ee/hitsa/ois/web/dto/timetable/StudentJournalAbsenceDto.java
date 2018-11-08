package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class StudentJournalAbsenceDto {

    private Long entryId;
    @ClassifierRestriction(MainClassCode.SISSEKANNE)
    private LocalDate entryDate;
    private String journalName;
    @ClassifierRestriction(MainClassCode.PUUDUMINE)
    private String absenceCode;
    private Short lessons;
    private Long lessonNrStart;
    private Long lessonNrEnd;
    
    public StudentJournalAbsenceDto() {
        
    }
    
    public StudentJournalAbsenceDto(Long entryId, LocalDate entryDate, String journalName, String absenceCode,
            Short startLessonNr, Short lessons, Short lessonNr) {
        this.entryId = entryId;
        this.entryDate = entryDate;
        this.journalName = journalName;
        this.absenceCode = absenceCode;
        this.lessons = lessons;
        setAbsenceLessonNrs(lessonNr, startLessonNr, lessons);
    }

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }

    public String getAbsenceCode() {
        return absenceCode;
    }

    public void setAbsenceCode(String absenceCode) {
        this.absenceCode = absenceCode;
    }

    public Short getLessons() {
        return lessons;
    }

    public void setLessons(Short lessons) {
        this.lessons = lessons;
    }

    public Long getLessonNrStart() {
        return lessonNrStart;
    }

    public void setLessonNrStart(Long lessonNrStart) {
        this.lessonNrStart = lessonNrStart;
    }

    public Long getLessonNrEnd() {
        return lessonNrEnd;
    }

    public void setLessonNrEnd(Long lessonNrEnd) {
        this.lessonNrEnd = lessonNrEnd;
    }

    private void setAbsenceLessonNrs(Short lessonNr, Short startLessonNr, Short lessons) {
        Long absenceLessonNrStart = null;
        Long absenceLessonNrEnd = null;
        if (lessonNr == null && startLessonNr != null) {
            if (lessons != null && lessons.intValue() > 1) {
                absenceLessonNrStart = Long.valueOf(startLessonNr.intValue());
                absenceLessonNrEnd = Long.valueOf(startLessonNr.intValue() + lessons.intValue() - 1);
            }
            absenceLessonNrStart = Long.valueOf(startLessonNr.intValue());
        } else if (lessonNr != null && startLessonNr != null) {
            absenceLessonNrStart = Long.valueOf(lessonNr.intValue() + startLessonNr.intValue() - 1);
        } else if (lessonNr != null && startLessonNr == null) {
            absenceLessonNrStart = Long.valueOf(lessonNr.intValue());
        }
        this.lessonNrStart = absenceLessonNrStart;
        this.lessonNrEnd = absenceLessonNrEnd;
    }

}

package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class JournalEntryByDateDto {

    private LocalDate entryDate;
    private Integer startLessonNr;
    private String nameEt;
    @ClassifierRestriction(MainClassCode.SISSEKANNE)
    private String entryType;
    private String teacher;
    // Key is JournalStudent ID
    private Map<Long, List<JournalEntryStudentResultDto>> journalStudentResults = new HashMap<>();

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public Integer getStartLessonNr() {
        return startLessonNr;
    }

    public void setStartLessonNr(Integer startLessonNr) {
        this.startLessonNr = startLessonNr;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Map<Long, List<JournalEntryStudentResultDto>> getJournalStudentResults() {
        return journalStudentResults;
    }

    public void setJournalStudentResults(Map<Long, List<JournalEntryStudentResultDto>> journalStudentResults) {
        this.journalStudentResults = journalStudentResults;
    }

}

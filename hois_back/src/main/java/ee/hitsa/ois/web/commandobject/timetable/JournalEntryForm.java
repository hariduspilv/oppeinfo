package ee.hitsa.ois.web.commandobject.timetable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.dto.timetable.JournalEntryStudentDto;

public class JournalEntryForm {

    @NotEmpty
    @ClassifierRestriction(MainClassCode.SISSEKANNE)
    private String entryType;
    private String nameEt;
    private LocalDate entryDate;
    private Integer startLessonNr;
    private Integer lessons;
    private String content;
    private String homework;
    private LocalDate homeworkDuedate;
    private List<String> journalEntryCapacityTypes = new ArrayList<>();
    private List<JournalEntryStudentDto> journalEntryStudents = new ArrayList<>();

    public String getEntryType() {
        return entryType;
    }
    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }
    public String getNameEt() {
        return nameEt;
    }
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
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
    public Integer getLessons() {
        return lessons;
    }
    public void setLessons(Integer lessons) {
        this.lessons = lessons;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getHomework() {
        return homework;
    }
    public void setHomework(String homework) {
        this.homework = homework;
    }
    public LocalDate getHomeworkDuedate() {
        return homeworkDuedate;
    }
    public void setHomeworkDuedate(LocalDate homeworkDuedate) {
        this.homeworkDuedate = homeworkDuedate;
    }
    public List<String> getJournalEntryCapacityTypes() {
        return journalEntryCapacityTypes;
    }
    public void setJournalEntryCapacityTypes(List<String> journalEntryCapacityTypes) {
        this.journalEntryCapacityTypes = journalEntryCapacityTypes;
    }
    public List<JournalEntryStudentDto> getJournalEntryStudents() {
        return journalEntryStudents;
    }
    public void setJournalEntryStudents(List<JournalEntryStudentDto> journalEntryStudents) {
        this.journalEntryStudents = journalEntryStudents;
    }

}

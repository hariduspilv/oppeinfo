package ee.hitsa.ois.web.commandobject.timetable;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.JournalEntryValidation.Homework;
import ee.hitsa.ois.validation.JournalEntryValidation.Lesson;
import ee.hitsa.ois.validation.NotEmpty;

public class JournalEntryForm {

    @NotEmpty
    @ClassifierRestriction(MainClassCode.SISSEKANNE)
    private String entryType;
    private String nameEt;

    @NotNull(groups = {Lesson.class})
    private LocalDate entryDate;
    @NotNull(groups = {Lesson.class})
    private Integer startLessonNr;
    @NotNull(groups = {Lesson.class})
    private Integer lessons;
    private String content;


    private String homework;
    @NotNull(groups = {Homework.class})
    private LocalDate homeworkDuedate;
    private List<String> journalEntryCapacityTypes;
    private List<JournalEntryStudentForm> journalEntryStudents;

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
    public List<JournalEntryStudentForm> getJournalEntryStudents() {
        return journalEntryStudents;
    }
    public void setJournalEntryStudents(List<JournalEntryStudentForm> journalEntryStudents) {
        this.journalEntryStudents = journalEntryStudents;
    }

}

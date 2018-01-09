package ee.hitsa.ois.web.commandobject.timetable;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.JournalEntryValidation.Homework;
import ee.hitsa.ois.validation.JournalEntryValidation.Lesson;
import ee.hitsa.ois.validation.Required;

public class JournalEntryForm {

    @Required
    @ClassifierRestriction(MainClassCode.SISSEKANNE)
    private String entryType;
    @Size(max=100)
    private String nameEt;

    @NotNull(groups = {Lesson.class})
    private LocalDate entryDate;

    @NotNull(groups = {Lesson.class})
    @Min(1)
    @Max(100)
    private Long startLessonNr;

    @NotNull(groups = {Lesson.class})
    @Min(1)
    @Max(100)
    private Long lessons;
    @Size(max=10000)
    private String content;

    @Size(max=10000)
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
    public Long getStartLessonNr() {
        return startLessonNr;
    }
    public void setStartLessonNr(Long startLessonNr) {
        this.startLessonNr = startLessonNr;
    }
    public Long getLessons() {
        return lessons;
    }
    public void setLessons(Long lessons) {
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

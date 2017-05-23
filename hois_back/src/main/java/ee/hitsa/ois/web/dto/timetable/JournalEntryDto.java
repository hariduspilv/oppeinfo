package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.timetable.JournalEntry;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class JournalEntryDto {

    private Long id;
    private LocalDateTime inserted;
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

    public static JournalEntryDto of(JournalEntry journalEntry) {
        JournalEntryDto dto = EntityUtil.bindToDto(journalEntry, new JournalEntryDto(), "journalEntryStudents", "journalEntryCapacityTypes");
        dto.setJournalEntryStudents(journalEntry.getJournalEntryStudents().stream().map(JournalEntryStudentDto::of).collect(Collectors.toList()));
        dto.setJournalEntryCapacityTypes(journalEntry.getJournalEntryCapacityTypes().stream().map(type -> EntityUtil.getCode(type.getCapacityType())).collect(Collectors.toList()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getInserted() {
        return inserted;
    }

    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }

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

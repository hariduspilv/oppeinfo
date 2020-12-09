package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.domain.timetable.JournalEntry;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class JournalEntryDto extends VersionedCommand  {

    private Long id;
    private LocalDateTime inserted;
    @ClassifierRestriction(MainClassCode.SISSEKANNE)
    private String entryType;
    private String nameEt;
    private LocalDate entryDate;
    private Long startLessonNr;
    private Long lessons;
    private String content;
    private String homework;
    private LocalDate homeworkDuedate;
    private Long moodleGradeItemId;
    private Long journalStudent;
    private List<String> journalEntryCapacityTypes = new ArrayList<>();
    private List<Long> journalEntryTeachers = new ArrayList<>();
    private List<JournalEntryStudentDto> journalEntryStudents = new ArrayList<>();

    public static JournalEntryDto of(JournalEntry journalEntry) {
        JournalEntryDto dto = EntityUtil.bindToDto(journalEntry, new JournalEntryDto(), "journalEntryStudents",
                "journalEntryCapacityTypes", "journalEntryTeachers");
        dto.setJournalEntryStudents(StreamUtil.toMappedList(JournalEntryStudentDto::of, journalEntry.getJournalEntryStudents()));
        dto.setJournalEntryCapacityTypes(StreamUtil.toMappedList(type -> EntityUtil.getCode(type.getCapacityType()), journalEntry.getJournalEntryCapacityTypes()));
        dto.setJournalEntryTeachers(StreamUtil.toMappedList(jt -> EntityUtil.getId(jt.getTeacher()),
                journalEntry.getJournalEntryTeachers()));
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

    public Long getMoodleGradeItemId() {
        return moodleGradeItemId;
    }

    public void setMoodleGradeItemId(Long moodleGradeItemId) {
        this.moodleGradeItemId = moodleGradeItemId;
    }

    public Long getJournalStudent() {
        return journalStudent;
    }

    public void setJournalStudent(Long journalStudent) {
        this.journalStudent = journalStudent;
    }

    public List<String> getJournalEntryCapacityTypes() {
        return journalEntryCapacityTypes;
    }

    public void setJournalEntryCapacityTypes(List<String> journalEntryCapacityTypes) {
        this.journalEntryCapacityTypes = journalEntryCapacityTypes;
    }

    public List<Long> getJournalEntryTeachers() {
        return journalEntryTeachers;
    }

    public void setJournalEntryTeachers(List<Long> journalEntryTeachers) {
        this.journalEntryTeachers = journalEntryTeachers;
    }

    public List<JournalEntryStudentDto> getJournalEntryStudents() {
        return journalEntryStudents;
    }

    public void setJournalEntryStudents(List<JournalEntryStudentDto> journalEntryStudents) {
        this.journalEntryStudents = journalEntryStudents;
    }

}

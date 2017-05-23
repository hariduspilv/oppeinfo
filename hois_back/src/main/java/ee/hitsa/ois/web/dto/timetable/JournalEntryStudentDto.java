package ee.hitsa.ois.web.dto.timetable;

import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.timetable.JournalEntryStudent;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class JournalEntryStudentDto {

    private Long id;
    private Long journalStudent;
    private String studentName;
    private String studentGroup;

    @ClassifierRestriction(MainClassCode.PUUDUMINE)
    private String absence;

    @ClassifierRestriction(MainClassCode.KUTSEHINDAMINE)
    private String grade;

    private String addInfo;

    private List<JournalEntryStudentHistoryDto> journalEntryStudentHistories;

    public static JournalEntryStudentDto of(JournalEntryStudent journalEntryStudent) {
        JournalEntryStudentDto dto = EntityUtil.bindToDto(journalEntryStudent, new JournalEntryStudentDto(), "journalEntryStudentHistories");
        dto.journalEntryStudentHistories = journalEntryStudent.getJournalEntryStudentHistories().stream().map(JournalEntryStudentHistoryDto::of).collect(Collectors.toList());
        return dto;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getJournalStudent() {
        return journalStudent;
    }
    public void setJournalStudent(Long journalStudent) {
        this.journalStudent = journalStudent;
    }
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public String getStudentGroup() {
        return studentGroup;
    }
    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }
    public String getAbsence() {
        return absence;
    }
    public void setAbsence(String absence) {
        this.absence = absence;
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public String getAddInfo() {
        return addInfo;
    }
    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }
    public List<JournalEntryStudentHistoryDto> getJournalEntryStudentHistories() {
        return journalEntryStudentHistories;
    }
    public void setJournalEntryStudentHistories(List<JournalEntryStudentHistoryDto> journalEntryStudentHistories) {
        this.journalEntryStudentHistories = journalEntryStudentHistories;
    }


}

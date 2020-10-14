package ee.hitsa.ois.web.commandobject.timetable;

import java.util.HashMap;
import java.util.Map;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.dto.GradeDto;

public class JournalEntryStudentForm {

    private Long id;
    private Long journalStudent;
    @ClassifierRestriction(MainClassCode.PUUDUMINE)
    private String absence;
    private GradeDto grade;
    private String verbalGrade;
    private Boolean removeStudentHistory = Boolean.FALSE;
    private String addInfo;
    private Boolean isLessonAbsence;
    private Boolean hasOverlappingLessonAbsence;
    private Boolean isRemark;
    private Map<Long, JournalEntryStudentLessonAbsenceForm> lessonAbsences = new HashMap<>();

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

    public String getAbsence() {
        return absence;
    }

    public void setAbsence(String absence) {
        this.absence = absence;
    }

    public GradeDto getGrade() {
        return grade;
    }

    public void setGrade(GradeDto grade) {
        this.grade = grade;
    }

    public String getVerbalGrade() {
        return verbalGrade;
    }

    public void setVerbalGrade(String verbalGrade) {
        this.verbalGrade = verbalGrade;
    }

    public Boolean getRemoveStudentHistory() {
        return removeStudentHistory;
    }

    public void setRemoveStudentHistory(Boolean removeStudentHistory) {
        this.removeStudentHistory = removeStudentHistory;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public Boolean getIsLessonAbsence() {
        return isLessonAbsence;
    }

    public void setIsLessonAbsence(Boolean isLessonAbsence) {
        this.isLessonAbsence = isLessonAbsence;
    }

    public Boolean getHasOverlappingLessonAbsence() {
        return hasOverlappingLessonAbsence;
    }

    public void setHasOverlappingLessonAbsence(Boolean hasOverlappingLessonAbsence) {
        this.hasOverlappingLessonAbsence = hasOverlappingLessonAbsence;
    }

    public Boolean getIsRemark() {
        return isRemark;
    }

    public void setIsRemark(Boolean isRemark) {
        this.isRemark = isRemark;
    }

    public Map<Long, JournalEntryStudentLessonAbsenceForm> getLessonAbsences() {
        return lessonAbsences;
    }

    public void setLessonAbsences(Map<Long, JournalEntryStudentLessonAbsenceForm> lessonAbsences) {
        this.lessonAbsences = lessonAbsences;
    }

}

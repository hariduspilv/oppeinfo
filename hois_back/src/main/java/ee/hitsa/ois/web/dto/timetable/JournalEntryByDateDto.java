package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class JournalEntryByDateDto {

    private LocalDate entryDate;
    private Long startLessonNr;
    private Integer lessons;
    private String nameEt;
    @ClassifierRestriction(MainClassCode.SISSEKANNE)
    private String entryType;
    private String teacher;
    private Long curriculumModuleOutcomes;
    private Long outcomeOrderNr;
    private Long moodleGradeItemId;
    
    // Key is JournalStudent ID
    private Map<Long, List<JournalEntryStudentResultDto>> journalStudentResults = new HashMap<>();

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

    public Integer getLessons() {
        return lessons;
    }

    public void setLessons(Integer lessons) {
        this.lessons = lessons;
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

    public Long getCurriculumModuleOutcomes() {
        return curriculumModuleOutcomes;
    }

    public void setCurriculumModuleOutcomes(Long curriculumModuleOutcomes) {
        this.curriculumModuleOutcomes = curriculumModuleOutcomes;
    }
    
    public Long getOutcomeOrderNr() {
        return outcomeOrderNr;
    }

    public void setOutcomeOrderNr(Long outcomeOrderNr) {
        this.outcomeOrderNr = outcomeOrderNr;
    }

    public Long getMoodleGradeItemId() {
        return moodleGradeItemId;
    }

    public void setMoodleGradeItemId(Long moodleGradeItemId) {
        this.moodleGradeItemId = moodleGradeItemId;
    }

    public Map<Long, List<JournalEntryStudentResultDto>> getJournalStudentResults() {
        return journalStudentResults;
    }

    public void setJournalStudentResults(Map<Long, List<JournalEntryStudentResultDto>> journalStudentResults) {
        this.journalStudentResults = journalStudentResults;
    }

}

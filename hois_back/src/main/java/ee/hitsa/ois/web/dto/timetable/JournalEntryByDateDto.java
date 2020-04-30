package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class JournalEntryByDateDto extends JournalEntryByDateBaseDto {

    private Long id;
    private String teacher;
    private Long moodleGradeItemId;
    
    // Key is JournalStudent ID
    private Map<Long, List<JournalEntryStudentResultDto>> journalStudentResults;
    // Key is Student ID
    private Map<Long, StudentCurriculumModuleOutcomesResultDto> studentOutcomeResults;
    private List<JournalSearchDto> otherJournals;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Long getMoodleGradeItemId() {
        return moodleGradeItemId;
    }

    public void setMoodleGradeItemId(Long moodleGradeItemId) {
        this.moodleGradeItemId = moodleGradeItemId;
    }

    public Map<Long, List<JournalEntryStudentResultDto>> getJournalStudentResults() {
        return journalStudentResults != null ? journalStudentResults : new HashMap<>();
    }

    public void setJournalStudentResults(Map<Long, List<JournalEntryStudentResultDto>> journalStudentResults) {
        this.journalStudentResults = journalStudentResults;
    }

    public Map<Long, StudentCurriculumModuleOutcomesResultDto> getStudentOutcomeResults() {
        return studentOutcomeResults != null ? studentOutcomeResults : new HashMap<>();
    }

    public void setStudentOutcomeResults(Map<Long, StudentCurriculumModuleOutcomesResultDto> studentOutcomeResults) {
        this.studentOutcomeResults = studentOutcomeResults;
    }

    public List<JournalSearchDto> getOtherJournals() {
        return otherJournals != null ? otherJournals : new ArrayList<>();
    }

    public void setOtherJournals(List<JournalSearchDto> otherJournals) {
        this.otherJournals = otherJournals;
    }
}

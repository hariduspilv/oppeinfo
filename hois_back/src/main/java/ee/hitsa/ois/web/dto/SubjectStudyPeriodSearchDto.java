package ee.hitsa.ois.web.dto;

import java.util.List;
import java.util.Set;

public class SubjectStudyPeriodSearchDto {

    private Long id;
    private Long studentsNumber;
    private Long hours;
    private List<String> teachers;
    private List<SubjectProgramResult> programs;
    private AutocompleteResult subject;
    private AutocompleteResult studyPeriod;
    private Set<AutocompleteResult> midtermTasks;
    private Boolean isPracticeSubject;
    private Long moodleCourseId;
    private Long subjectProgramId;
    private String subjectProgramStatus;

    public Boolean getIsPracticeSubject() {
        return isPracticeSubject;
    }

    public void setIsPracticeSubject(Boolean isPracticeSubject) {
        this.isPracticeSubject = isPracticeSubject;
    }

    public Set<AutocompleteResult> getMidtermTasks() {
        return midtermTasks;
    }

    public void setMidtermTasks(Set<AutocompleteResult> midtermTasks) {
        this.midtermTasks = midtermTasks;
    }

    public Long getHours() {
        return hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AutocompleteResult getSubject() {
        return subject;
    }

    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }

    public List<SubjectProgramResult> getPrograms() {
        return programs;
    }

    public void setPrograms(List<SubjectProgramResult> programs) {
        this.programs = programs;
    }

    public void setSubject(AutocompleteResult subject) {
        this.subject = subject;
    }

    public AutocompleteResult getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(AutocompleteResult studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public Long getStudentsNumber() {
        return studentsNumber;
    }

    public void setStudentsNumber(Long studentsNumber) {
        this.studentsNumber = studentsNumber;
    }

    public Long getMoodleCourseId() {
        return moodleCourseId;
    }

    public void setMoodleCourseId(Long moodleCourseId) {
        this.moodleCourseId = moodleCourseId;
    }

    public Long getSubjectProgramId() {
        return subjectProgramId;
    }

    public void setSubjectProgramId(Long subjectProgramId) {
        this.subjectProgramId = subjectProgramId;
    }

    public String getSubjectProgramStatus() {
        return subjectProgramStatus;
    }

    public void setSubjectProgramStatus(String subjectProgramStatus) {
        this.subjectProgramStatus = subjectProgramStatus;
    }
}

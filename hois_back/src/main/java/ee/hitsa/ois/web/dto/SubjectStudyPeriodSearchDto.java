package ee.hitsa.ois.web.dto;

import java.util.Set;

public class SubjectStudyPeriodSearchDto {
    private Long id;
    private Long studentsNumber;
    private Long hours;
    private Set<String> teachers;
    private AutocompleteResult subject;
    private AutocompleteResult studyPeriod;
    private Set<AutocompleteResult> midtermTasks;

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
    public Set<String> getTeachers() {
        return teachers;
    }
    public void setTeachers(Set<String> teachers) {
        this.teachers = teachers;
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
}

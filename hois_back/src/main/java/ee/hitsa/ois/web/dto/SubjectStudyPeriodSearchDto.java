package ee.hitsa.ois.web.dto;

import java.util.List;
import java.util.Set;

public class SubjectStudyPeriodSearchDto {

    private Long id;
    private Long studentsNumber;
    private Long hours;
    private List<String> teachers;
    private AutocompleteResult subject;
    private AutocompleteResult studyPeriod;
    private Set<AutocompleteResult> midtermTasks;
    private Boolean isPracticeSubject;

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

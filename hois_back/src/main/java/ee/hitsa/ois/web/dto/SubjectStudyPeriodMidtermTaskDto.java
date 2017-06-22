package ee.hitsa.ois.web.dto;

import java.util.Set;

import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodMidtermTaskForm;

public class SubjectStudyPeriodMidtermTaskDto extends SubjectStudyPeriodMidtermTaskForm {
    
    private AutocompleteResult subject;
    private AutocompleteResult studyPeriod;
    private AutocompleteResult assessment;
    private Set<String> teachers;
    private Set<MidtermTaskDto> midtermTasks;

    public AutocompleteResult getSubject() {
        return subject;
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
    public AutocompleteResult getAssessment() {
        return assessment;
    }
    public void setAssessment(AutocompleteResult assessment) {
        this.assessment = assessment;
    }
    public Set<String> getTeachers() {
        return teachers;
    }
    public void setTeachers(Set<String> teachers) {
        this.teachers = teachers;
    }
    public Set<MidtermTaskDto> getMidtermTasks() {
        return midtermTasks;
    }
    public void setMidtermTasks(Set<MidtermTaskDto> midtermTasks) {
        this.midtermTasks = midtermTasks;
    }
}

package ee.hitsa.ois.web.dto;

public class SubjectStudyPeriodSearchDto {
    private Long id;
    private Long studentsNumber;
    private AutocompleteResult teacher;
    private AutocompleteResult subject;
    private AutocompleteResult studyPeriod;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public AutocompleteResult getTeacher() {
        return teacher;
    }
    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
    }
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
    public Long getStudentsNumber() {
        return studentsNumber;
    }
    public void setStudentsNumber(Long studentsNumber) {
        this.studentsNumber = studentsNumber;
    }
}

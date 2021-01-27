package ee.hitsa.ois.web.commandobject.exam;

import java.time.LocalDate;
import java.util.List;

public class ExamSearchForm {

    private Long studyPeriod;
    private LocalDate from;
    private LocalDate thru;
    private Long subject;
    private Long teacher;
    private List<Long> studentGroups;

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getThru() {
        return thru;
    }

    public void setThru(LocalDate thru) {
        this.thru = thru;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public Long getTeacher() {
        return teacher;
    }

    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }

    public List<Long> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<Long> studentGroups) {
        this.studentGroups = studentGroups;
    }
}

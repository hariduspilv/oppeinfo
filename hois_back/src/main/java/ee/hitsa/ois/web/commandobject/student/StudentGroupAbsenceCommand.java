package ee.hitsa.ois.web.commandobject.student;

import java.time.LocalDate;

public class StudentGroupAbsenceCommand {

    private Long studyYear;
    private Long studentGroup;
    private LocalDate studyWeekStart;
    private LocalDate studyWeekEnd;
    private Boolean todaysAbsences;

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }

    public Long getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }

    public LocalDate getStudyWeekStart() {
        return studyWeekStart;
    }

    public void setStudyWeekStart(LocalDate studyWeekStart) {
        this.studyWeekStart = studyWeekStart;
    }

    public LocalDate getStudyWeekEnd() {
        return studyWeekEnd;
    }

    public void setStudyWeekEnd(LocalDate studyWeekEnd) {
        this.studyWeekEnd = studyWeekEnd;
    }

    public Boolean getTodaysAbsences() {
        return todaysAbsences;
    }

    public void setTodaysAbsences(Boolean todaysAbsences) {
        this.todaysAbsences = todaysAbsences;
    }

}

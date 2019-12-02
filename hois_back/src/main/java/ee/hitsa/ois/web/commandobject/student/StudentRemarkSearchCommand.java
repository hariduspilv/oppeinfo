package ee.hitsa.ois.web.commandobject.student;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.validation.DateRange;

@DateRange(from = "from", thru = "thru")
public class StudentRemarkSearchCommand {

    @NotNull
    private Long studyYear;
    private LocalDate studyYearStart;
    private LocalDate studyYearEnd;
    private Long studentGroup;
    private List<Long> studentGroups;
    private Long student;
    private List<Long> students;
    private LocalDate from;
    private LocalDate thru;
    private List<String> reasons;
    private Boolean showJournalRemarks;

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }

    public LocalDate getStudyYearStart() {
        return studyYearStart;
    }

    public void setStudyYearStart(LocalDate studyYearStart) {
        this.studyYearStart = studyYearStart;
    }

    public LocalDate getStudyYearEnd() {
        return studyYearEnd;
    }

    public void setStudyYearEnd(LocalDate studyYearEnd) {
        this.studyYearEnd = studyYearEnd;
    }

    public Long getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }

    public List<Long> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<Long> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public Long getStudent() {
        return student;
    }

    public void setStudent(Long student) {
        this.student = student;
    }

    public List<Long> getStudents() {
        return students;
    }

    public void setStudents(List<Long> students) {
        this.students = students;
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

    public List<String> getReasons() {
        return reasons;
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }

    public Boolean getShowJournalRemarks() {
        return showJournalRemarks;
    }

    public void setShowJournalRemarks(Boolean showJournalRemarks) {
        this.showJournalRemarks = showJournalRemarks;
    }

}

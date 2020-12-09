package ee.hitsa.ois.web.dto.timetable;

import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.GradeDto;

import java.time.LocalDate;

public class StudentCurriculumModuleOutcomesResultForm extends VersionedCommand {

    private Long id;
    private Long studentId;
    private GradeDto grade;
    private LocalDate gradeDate;
    private Boolean removeStudentHistory = Boolean.FALSE;
    private String addInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public GradeDto getGrade() {
        return grade;
    }

    public void setGrade(GradeDto grade) {
        this.grade = grade;
    }

    public LocalDate getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(LocalDate gradeDate) {
        this.gradeDate = gradeDate;
    }

    public Boolean getRemoveStudentHistory() {
        return removeStudentHistory;
    }

    public void setRemoveStudentHistory(Boolean removeStudentHistory) {
        this.removeStudentHistory = removeStudentHistory;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }
}

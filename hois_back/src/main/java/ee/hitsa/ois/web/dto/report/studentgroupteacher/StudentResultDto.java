package ee.hitsa.ois.web.dto.report.studentgroupteacher;

import java.time.LocalDate;

public class StudentResultDto {

    private Long moduleId;
    private Long moduleThemeId;
    private String grade;
    private LocalDate gradeInserted;

    public StudentResultDto() {

    }

    public StudentResultDto(StudentResultDto result) {
        this.moduleId = result.getModuleId();
        this.moduleThemeId = result.getModuleThemeId();
        this.grade = result.getGrade();
        this.gradeInserted = result.getGradeInserted();
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getModuleThemeId() {
        return moduleThemeId;
    }

    public void setModuleThemeId(Long moduleThemeId) {
        this.moduleThemeId = moduleThemeId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDate getGradeInserted() {
        return gradeInserted;
    }

    public void setGradeInserted(LocalDate gradeInserted) {
        this.gradeInserted = gradeInserted;
    }

}

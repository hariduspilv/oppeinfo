package ee.hitsa.ois.web.dto.report.studentgroupteacher;

import ee.hitsa.ois.web.dto.GradeDto;

import java.time.LocalDate;

public class StudentModuleResultDto {

    private Long id;
    private GradeDto grade;
    private LocalDate gradeInserted;
    private String gradeInsertedBy;

    public StudentModuleResultDto() {

    }

    public StudentModuleResultDto(StudentModuleResultDto columnResult) {
        this.id = columnResult.getId();
        this.grade = columnResult.getGrade();
        this.gradeInserted = columnResult.getGradeInserted();
        this.gradeInsertedBy = columnResult.getGradeInsertedBy();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GradeDto getGrade() {
        return grade;
    }

    public void setGrade(GradeDto grade) {
        this.grade = grade;
    }

    public LocalDate getGradeInserted() {
        return gradeInserted;
    }

    public void setGradeInserted(LocalDate gradeInserted) {
        this.gradeInserted = gradeInserted;
    }

    public String getGradeInsertedBy() {
        return gradeInsertedBy;
    }

    public void setGradeInsertedBy(String gradeInsertedBy) {
        this.gradeInsertedBy = gradeInsertedBy;
    }
}

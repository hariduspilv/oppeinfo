package ee.hitsa.ois.web.dto.report.studentgroupteacher;

import java.time.LocalDate;

public class StudentModuleResultDto {

    private Long id;
    private String grade;
    private LocalDate gradeInserted;
    
    public StudentModuleResultDto() {
        
    }
    
    public StudentModuleResultDto(StudentModuleResultDto columnResult) {
        this.id = columnResult.getId();
        this.grade = columnResult.getGrade();
        this.gradeInserted = columnResult.getGradeInserted();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    

    public String getGrade() {
        return grade;
    }

    public LocalDate getGradeInserted() {
        return gradeInserted;
    }

    public void setGradeInserted(LocalDate gradeInserted) {
        this.gradeInserted = gradeInserted;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    
}

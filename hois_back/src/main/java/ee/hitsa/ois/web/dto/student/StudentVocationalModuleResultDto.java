package ee.hitsa.ois.web.dto.student;

import java.math.BigDecimal;
import java.time.LocalDate;

public class StudentVocationalModuleResultDto {
    
    private Long id;
    private Long curriculumVersionOmoduleId;
    private String nameEt;
    private String nameEn;
    private BigDecimal credits;
    private String grade;
    private LocalDate gradeDate;
    
    public StudentVocationalModuleResultDto(Long id, Long curriculumVersionOmoduleId, String nameEt, 
            String nameEn, BigDecimal credits, String grade, LocalDate gradeDate) {
        super();
        this.id = id;
        this.curriculumVersionOmoduleId = curriculumVersionOmoduleId;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
        this.credits = credits;
        this.grade = grade;
        this.gradeDate = gradeDate;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCurriculumVersionOmoduleId() {
        return curriculumVersionOmoduleId;
    }

    public void setCurriculumVersionOmoduleId(Long curriculumVersionOmoduleId) {
        this.curriculumVersionOmoduleId = curriculumVersionOmoduleId;
    }

    public String getNameEt() {
        return nameEt;
    }
    
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    
    public String getNameEn() {
        return nameEn;
    }
    
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
    
    public BigDecimal getCredits() {
        return credits;
    }
    
    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }
    
    public String getGrade() {
        return grade;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    public LocalDate getGradeDate() {
        return gradeDate;
    }
    
    public void setGradeDate(LocalDate gradeDate) {
        this.gradeDate = gradeDate;
    }
    
}

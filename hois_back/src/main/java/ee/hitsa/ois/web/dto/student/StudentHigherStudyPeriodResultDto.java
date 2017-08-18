package ee.hitsa.ois.web.dto.student;

import java.math.BigDecimal;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.web.dto.StudyPeriodDto;

public class StudentHigherStudyPeriodResultDto {
    private StudyPeriodDto studyPeriod;
    private BigDecimal averageGrade;
    private BigDecimal total;
    
    public static StudentHigherStudyPeriodResultDto of(StudyPeriod studyPeriod) { 
        StudentHigherStudyPeriodResultDto dto = new StudentHigherStudyPeriodResultDto();
        dto.setStudyPeriod(StudyPeriodDto.of(studyPeriod));
        return dto;
    }

    public StudyPeriodDto getStudyPeriod() {
        return studyPeriod;
    }
    public void setStudyPeriod(StudyPeriodDto studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
    public BigDecimal getAverageGrade() {
        return averageGrade;
    }
    public void setAverageGrade(BigDecimal averageGrade) {
        this.averageGrade = averageGrade;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }    
}   

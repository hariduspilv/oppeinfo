package ee.hitsa.ois.web.dto.student;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StudentHigherProgressPeriodDto {

    private Short studyYearNumber;
    private Boolean autumn = Boolean.FALSE;
    private Boolean spring = Boolean.FALSE;

    private List<StudentHigherProgressSubjectDto> subjects = new ArrayList<>();
    private BigDecimal averageGrade = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;

    public Short getStudyYearNumber() {
        return studyYearNumber;
    }

    public void setStudyYearNumber(Short studyYearNumber) {
        this.studyYearNumber = studyYearNumber;
    }

    public Boolean getAutumn() {
        return autumn;
    }

    public void setAutumn(Boolean autumn) {
        this.autumn = autumn;
    }

    public Boolean getSpring() {
        return spring;
    }

    public void setSpring(Boolean spring) {
        this.spring = spring;
    }

    public List<StudentHigherProgressSubjectDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<StudentHigherProgressSubjectDto> subjects) {
        this.subjects = subjects;
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

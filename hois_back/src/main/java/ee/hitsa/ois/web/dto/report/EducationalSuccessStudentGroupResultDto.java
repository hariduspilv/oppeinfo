package ee.hitsa.ois.web.dto.report;

import ee.hitsa.ois.web.dto.AutocompleteResult;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;

import java.math.BigDecimal;

public class EducationalSuccessStudentGroupResultDto {
    
    private AutocompleteResult studentGroup;
    private Long students;
    private Long gradeCount;
    private Long debts;
    private Long educationalSuccess = Long.valueOf(0L);
    private BigDecimal average = BigDecimal.ZERO;
    
    public EducationalSuccessStudentGroupResultDto(Object r) {
        this.studentGroup = new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 1));
        this.students = resultAsLong(r, 2);
        this.gradeCount = resultAsLong(r, 3);
        this.debts = resultAsLong(r, 4);
        Long positiveGrades = resultAsLong(r, 5);
        Long allGrades = resultAsLong(r, 6);
        if (allGrades != null && positiveGrades != null && allGrades.longValue() != 0) {
            this.educationalSuccess = Long.valueOf(positiveGrades.longValue() * 100 / allGrades.longValue());
        }
        this.average = resultAsDecimal(r, 9);
    }
    public AutocompleteResult getStudentGroup() {
        return studentGroup;
    }
    public void setStudentGroup(AutocompleteResult studentGroup) {
        this.studentGroup = studentGroup;
    }
    public Long getStudents() {
        return students;
    }
    public void setStudents(Long students) {
        this.students = students;
    }
    public Long getGradeCount() {
        return gradeCount;
    }
    public void setGradeCount(Long gradeCount) {
        this.gradeCount = gradeCount;
    }
    public Long getDebts() {
        return debts;
    }
    public void setDebts(Long debts) {
        this.debts = debts;
    }
    public Long getEducationalSuccess() {
        return educationalSuccess;
    }
    public void setEducationalSuccess(Long educationalSuccess) {
        this.educationalSuccess = educationalSuccess;
    }
    public BigDecimal getAverage() {
        return average;
    }
    public void setAverage(BigDecimal average) {
        this.average = average;
    }

}

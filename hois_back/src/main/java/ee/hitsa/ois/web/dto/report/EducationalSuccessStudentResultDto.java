package ee.hitsa.ois.web.dto.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;

import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class EducationalSuccessStudentResultDto {
    
    private AutocompleteResult student;
    private String studentGroup;
    private Long gradeAmount;
    private Long debts;
    private Long educationalSuccess = Long.valueOf(0L);
    private BigDecimal weightedAverage = BigDecimal.ZERO;
    private BigDecimal average;
    
    public EducationalSuccessStudentResultDto(Object r) {
        String name = PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2));
        this.student = new AutocompleteResult(resultAsLong(r, 0), name, name);
        this.studentGroup = resultAsString(r, 3);
        this.gradeAmount = resultAsLong(r, 4);
        this.debts = resultAsLong(r, 5);
        Long positiveGrades = resultAsLong(r, 6);
        Long allGrades = resultAsLong(r, 7);
        if (allGrades != null && positiveGrades != null && allGrades.longValue() != 0) {
            this.educationalSuccess = Long.valueOf(positiveGrades.longValue() * 100 / allGrades.longValue());
        }
        this.weightedAverage = resultAsDecimal(r, 8);
        String journalGrades = resultAsString(r, 9);
        String outcomeGrades = resultAsString(r, 10);
        List<String> journalGradesList = new ArrayList<>();
        List<String> outcomeGradesList = new ArrayList<>();
        if (journalGrades != null) {
            journalGradesList = Arrays.asList(journalGrades.split(", "));
        }
        if (outcomeGrades != null) {
            outcomeGradesList = Arrays.asList(outcomeGrades.split(", "));
        }
        double calcualtedAverage = Stream.concat(outcomeGradesList.stream(), journalGradesList.stream())
                .mapToInt(OccupationalGrade::getGradeMark)
                .filter(m -> m != 0)
                .average().orElse(0);
        this.setAverage(BigDecimal.valueOf(calcualtedAverage).setScale(2, BigDecimal.ROUND_HALF_UP));
        
    }
    public AutocompleteResult getStudent() {
        return student;
    }
    public void setStudent(AutocompleteResult student) {
        this.student = student;
    }
    public String getStudentGroup() {
        return studentGroup;
    }
    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }
    public Long getGradeAmount() {
        return gradeAmount;
    }
    public void setGradeAmount(Long gradeAmount) {
        this.gradeAmount = gradeAmount;
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
    public BigDecimal getWeightedAverage() {
        return weightedAverage;
    }
    public void setWeightedAverage(BigDecimal weightedAverage) {
        this.weightedAverage = weightedAverage;
    }
    public BigDecimal getAverage() {
        return average;
    }
    public void setAverage(BigDecimal average) {
        this.average = average;
    }

}

package ee.hitsa.ois.web.commandobject.report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.validation.Required;

public class EducationalSuccessCommand {
    
    @Required
    private String queryType;
    
    private LocalDate from;
    private LocalDate thru;
    private Long student;
    private List<Long> studentGroup;
    private List<Long> curriculum;
    private List<Long> studentGroupTeacher;
    
    private List<String> gradeType;
    
    private Long teacher;
    private String debtSign;
    private Long debt;
    
    // false means query is per student
    private Boolean perGroup;
    private List<String> countableGrades;
    private List<String> countableGradeValues;
    
    private String weightedAverageSign;
    private BigDecimal weightedAverage;
    
    private String moduleFourSign;
    private Long moduleFour;
    
    private Long absence;
    private Long causelessAbsence;
    
    public String getQueryType() {
        return queryType;
    }
    public void setQueryType(String queryType) {
        this.queryType = queryType;
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
    public Long getStudent() {
        return student;
    }
    public void setStudent(Long student) {
        this.student = student;
    }
    public List<Long> getStudentGroup() {
        return studentGroup;
    }
    public void setStudentGroup(List<Long> studentGroup) {
        this.studentGroup = studentGroup;
    }
    public List<Long> getStudentGroupTeacher() {
        return studentGroupTeacher;
    }
    public void setStudentGroupTeacher(List<Long> studentGroupTeacher) {
        this.studentGroupTeacher = studentGroupTeacher;
    }
    public List<String> getGradeType() {
        return gradeType;
    }
    public void setGradeType(List<String> gradeType) {
        this.gradeType = gradeType;
    }
    public Long getTeacher() {
        return teacher;
    }
    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }
    public String getDebtSign() {
        return debtSign;
    }
    public void setDebtSign(String debtSign) {
        this.debtSign = debtSign;
    }
    public Long getDebt() {
        return debt;
    }
    public void setDebt(Long debt) {
        this.debt = debt;
    }
    public Boolean getPerGroup() {
        return perGroup;
    }
    public void setPerGroup(Boolean perGroup) {
        this.perGroup = perGroup;
    }
    public List<String> getCountableGrades() {
        return countableGrades;
    }
    public void setCountableGrades(List<String> countableGrades) {
        this.countableGrades = countableGrades;
    }
    public String getWeightedAverageSign() {
        return weightedAverageSign;
    }
    public void setWeightedAverageSign(String weightedAverageSign) {
        this.weightedAverageSign = weightedAverageSign;
    }
    public BigDecimal getWeightedAverage() {
        return weightedAverage;
    }
    public void setWeightedAverage(BigDecimal weightedAverage) {
        this.weightedAverage = weightedAverage;
    }
    public String getModuleFourSign() {
        return moduleFourSign;
    }
    public void setModuleFourSign(String moduleFourSign) {
        this.moduleFourSign = moduleFourSign;
    }
    public Long getModuleFour() {
        return moduleFour;
    }
    public void setModuleFour(Long moduleFour) {
        this.moduleFour = moduleFour;
    }
    public Long getAbsence() {
        return absence;
    }
    public void setAbsence(Long absence) {
        this.absence = absence;
    }
    public Long getCauselessAbsence() {
        return causelessAbsence;
    }
    public void setCauselessAbsence(Long causelessAbsence) {
        this.causelessAbsence = causelessAbsence;
    }
    public List<Long> getCurriculum() {
        return curriculum;
    }
    public void setCurriculum(List<Long> curriculum) {
        this.curriculum = curriculum;
    }
    public List<String> getCountableGradeValues() {
        return countableGradeValues;
    }
    public void setCountableGradeValues(List<String> countableGradeValues) {
        this.countableGradeValues = countableGradeValues;
    }
    
}

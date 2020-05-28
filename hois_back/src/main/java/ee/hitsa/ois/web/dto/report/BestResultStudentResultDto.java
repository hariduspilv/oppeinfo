package ee.hitsa.ois.web.dto.report;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;

import java.math.BigDecimal;

import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class BestResultStudentResultDto {
    
    private AutocompleteResult student;
    private String studentGroup;
    private BigDecimal average;
    private Long moduleGradeFour;
    private Long absences;
    private Long absencesP;
    
    public BestResultStudentResultDto(Object r) {
        String name = PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2));
        this.student = new AutocompleteResult(resultAsLong(r, 0), name, name);
        this.studentGroup = resultAsString(r, 3);
        this.average = resultAsDecimal(r, 4);
        this.moduleGradeFour = resultAsLong(r, 5);
        this.absences = resultAsLong(r, 6);
        this.absencesP = resultAsLong(r, 7);
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
    public BigDecimal getAverage() {
        return average;
    }
    public void setAverage(BigDecimal average) {
        this.average = average;
    }
    public Long getModuleGradeFour() {
        return moduleGradeFour;
    }
    public void setModuleGradeFour(Long moduleGradeFour) {
        this.moduleGradeFour = moduleGradeFour;
    }
    public Long getAbsences() {
        return absences;
    }
    public void setAbsences(Long absences) {
        this.absences = absences;
    }
    public Long getAbsencesP() {
        return absencesP;
    }
    public void setAbsencesP(Long absencesP) {
        this.absencesP = absencesP;
    }

}

package ee.hitsa.ois.web.dto.report;

import java.util.List;

import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

public class EducationalSuccessDebtDto {
    
    private AutocompleteResult student;
    private AutocompleteResult studentGroup;
    private Long debts;
    private List<EducationalSuccessDebtModule> modules;
    
    public EducationalSuccessDebtDto(Object r) {
        String name = PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2));
        this.student = new AutocompleteResult(resultAsLong(r, 0), name, name);
        String code = resultAsString(r, 4);
        this.studentGroup = new AutocompleteResult(resultAsLong(r, 3), code, code);
        this.debts = resultAsLong(r, 5);
    }
    
    public AutocompleteResult getStudent() {
        return student;
    }
    public void setStudent(AutocompleteResult student) {
        this.student = student;
    }
    public AutocompleteResult getStudentGroup() {
        return studentGroup;
    }
    public void setStudentGroup(AutocompleteResult studentGroup) {
        this.studentGroup = studentGroup;
    }
    public Long getDebts() {
        return debts;
    }
    public void setDebts(Long debts) {
        this.debts = debts;
    }
    public List<EducationalSuccessDebtModule> getModules() {
        return modules;
    }
    public void setModules(List<EducationalSuccessDebtModule> modules) {
        this.modules = modules;
    }

}

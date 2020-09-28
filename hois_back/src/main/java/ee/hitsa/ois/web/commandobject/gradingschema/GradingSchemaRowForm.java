package ee.hitsa.ois.web.commandobject.gradingschema;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GradingSchemaRowForm {

    private Long id;
    @NotNull
    @Size(max = 100)
    private String grade;
    @Size(max = 100)
    private String gradeEn;
    @NotNull
    @ClassifierRestriction({MainClassCode.KUTSEHINDAMINE, MainClassCode.KORGHINDAMINE})
    private String gradeReal;
    private Boolean isValid;

    public GradingSchemaRowForm() { }

    public GradingSchemaRowForm(Long id, String grade, String gradeEn, String gradeReal, Boolean isValid) {
        this.id = id;
        this.grade = grade;
        this.gradeEn = gradeEn;
        this.gradeReal = gradeReal;
        this.isValid = isValid;
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

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGradeEn() {
        return gradeEn;
    }

    public void setGradeEn(String gradeEn) {
        this.gradeEn = gradeEn;
    }

    public String getGradeReal() {
        return gradeReal;
    }

    public void setGradeReal(String gradeReal) {
        this.gradeReal = gradeReal;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
}

package ee.hitsa.ois.web.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ContractStudentModuleDto {

    private AutocompleteResult module;
    private BigDecimal credits;
    private String assessmentMethodsEt;
    private List<ContractStudentThemeDto> themes = new ArrayList<>();

    public AutocompleteResult getModule() {
        return module;
    }

    public void setModule(AutocompleteResult module) {
        this.module = module;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public String getAssessmentMethodsEt() {
        return assessmentMethodsEt;
    }

    public void setAssessmentMethodsEt(String assessmentMethodsEt) {
        this.assessmentMethodsEt = assessmentMethodsEt;
    }

    public List<ContractStudentThemeDto> getThemes() {
        return themes;
    }

    public void setThemes(List<ContractStudentThemeDto> themes) {
        this.themes = themes;
    }

}

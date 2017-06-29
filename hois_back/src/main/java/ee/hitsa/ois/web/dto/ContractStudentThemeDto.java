package ee.hitsa.ois.web.dto;

import java.math.BigDecimal;

public class ContractStudentThemeDto {

    private AutocompleteResult theme;
    private BigDecimal credits;
    private String subthemes;

    public AutocompleteResult getTheme() {
        return theme;
    }

    public void setTheme(AutocompleteResult theme) {
        this.theme = theme;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public String getSubthemes() {
        return subthemes;
    }

    public void setSubthemes(String subthemes) {
        this.subthemes = subthemes;
    }

}
